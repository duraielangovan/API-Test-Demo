package kafka.consumer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.util.KafkaUtil;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;


public class VeyyonKafkaConsumer<T extends Object> {

  protected Logger logger = LoggerFactory.getLogger(VeyyonKafkaConsumer.class);
  protected Consumer<String,T> recordConsumer;
  protected Thread consumerThread;
  protected VeyyonKafkaRecordsHandler<String,T> recordsHandler;
  protected Class<T> valueClass;
  protected VeyyonKafkaListRecordHandler<String,T> listRecordHandler;
  protected TypeReference<List<T>> valueTypeRef;
  protected long pollDelay;
  protected AtomicBoolean stopRequested = new AtomicBoolean(false);

  public synchronized void startProcessing(){

    if (consumerThread==null || !consumerThread.isAlive()){
      logger.info("Starting the thread :"+recordConsumer.subscription());
      stopRequested.set(false);
      consumerThread = new Thread(this::runProcessing);
      consumerThread.start();
    }else{
      logger.info("Consumer process already running, ignoring the request");
    }
  }

  public synchronized void stopProcessing(){
    stopRequested.set(true);
    recordConsumer.wakeup();
  }

  private void runProcessing() {
    ConsumerRecords<String,T> records=null;
    try{

      while(!stopRequested.get()){
        records=recordConsumer.poll(Duration.ofMillis(pollDelay));

        if(!records.isEmpty()){
          processRecords(records);
        }
      }
    } catch (WakeupException we){
      if(!stopRequested.get()) throw we;
    }
    catch (Exception e){
        stopRequested.set(true);
        logger.info("Exception while reading records :"+e);
    }

    logger.info("consumer stopped");
  }

  private void processRecords(ConsumerRecords<String,T> inRecords) {
    if(recordsHandler!=null){
      inRecords.forEach(record->callHandlerWithProperValue(recordsHandler,record));
    }else{
      inRecords.forEach(record->callListHandlerWithProperValue(listRecordHandler,record));
    }
  }

  private void callHandlerWithProperValue(VeyyonKafkaRecordsHandler<String,T> inRecordHandler, ConsumerRecord<String,T> inRecord) {

    try{
      if(valueClass.isAssignableFrom(String.class)){
        inRecordHandler.handleRecords(inRecord.key(),inRecord.value());
      }else{
        inRecordHandler.handleRecords(inRecord.key(),
                new ObjectMapper().readValue(inRecord.value().toString(),valueClass));
      }
    }catch (Exception e){
      logger.info("Error processing the record: "+ e);
    }
  }
  private void callListHandlerWithProperValue(VeyyonKafkaListRecordHandler<String,T> inListRecordHandler, ConsumerRecord<String,T> inRecord) {
    try{
      inListRecordHandler.handleListRecord(inRecord.key(),
              new ObjectMapper().readValue(inRecord.value().toString(),valueTypeRef));
    }catch (Exception e){
      logger.info("Error processing the record: "+ e);
    }
  }


  public static class ConsumerBuilder<T2>{

    private final VeyyonKafkaConsumer<T2> consumer;
    private KafkaUtil kafkaUtil = new KafkaUtil();
    private String broker;
    private String topicName;
    private String consumerGroupId=kafkaUtil.getUniqueId();
    private String securityProtocol;
    private String sslTrustStoreLocation;
    private long customPollDelay=-1;
    private boolean autoCommit =false;
    private boolean autResetToBeginning =false;

    public ConsumerBuilder() {
      this.consumer = new VeyyonKafkaConsumer<>();
    }

    public ConsumerBuilder<T2> setBroker(String broker) {
      this.broker = broker;
      return this;
    }

    public ConsumerBuilder<T2>  setTopicName(String topicName) {
      this.topicName = topicName;
      return this;
    }

    public ConsumerBuilder<T2>  setSecurityProtocol(String securityProtocol) {
      this.securityProtocol = securityProtocol;
      return this;
    }

    public ConsumerBuilder<T2>  setSslTrustStoreLocation(String sslTrustStoreLocation) {
      this.sslTrustStoreLocation = sslTrustStoreLocation;
      return this;
    }

    public ConsumerBuilder<T2>  setRecordsHandler(VeyyonKafkaRecordsHandler inRecordsHandler) {
      consumer.recordsHandler = inRecordsHandler;
      return this;
    }

    public ConsumerBuilder<T2>  setListRecordHandler(VeyyonKafkaListRecordHandler inListRecordsHandler) {
      consumer.listRecordHandler = inListRecordsHandler;
      return this;
    }
    public ConsumerBuilder<T2>  setvalueClass(Class<T2> inValueClass) {
      consumer.valueClass = inValueClass;
      return this;
    }
    public ConsumerBuilder<T2>  setValueTypeRef(TypeReference<List<T2>> valueTypeRef) {
      consumer.valueTypeRef = valueTypeRef;
      return this;
    }

    public ConsumerBuilder<T2>  setCustomPollDelay(long customPollDelay) {
      this.customPollDelay = customPollDelay;
      return this;
    }

    public ConsumerBuilder<T2> setAutoCommit(boolean autoCommit) {
      this.autoCommit = autoCommit;
      return this;
    }

    public ConsumerBuilder<T2>  setAutResetToBeginning(boolean autResetToBeginning) {
      this.autResetToBeginning = autResetToBeginning;
      return this;
    }

    private Consumer<String,T2> createConsumer(){

      if(Objects.equals(broker,"") || Objects.equals(broker,null))
        throw new IllegalArgumentException("Broker should be specified before publishing");
      else if(Objects.equals(topicName,"") || Objects.equals(topicName,null))
        throw new IllegalArgumentException("topicName should be specified before publishing");
      else if(Objects.equals(sslTrustStoreLocation,"") || Objects.equals(sslTrustStoreLocation,null))
        throw new IllegalArgumentException("sslTrustStoreLocation should be specified before publishing");
      else if(Objects.equals(securityProtocol,"") || Objects.equals(securityProtocol,null))
        throw new IllegalArgumentException("securityProtocol should be specified before publishing");

      Properties props = new Properties();
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autResetToBeginning ? "earliest":"latest");
      props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit ? "earliest":"latest");
      props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
      props.put("security.protocol", securityProtocol);
      props.put("security.trustore.location", sslTrustStoreLocation);

      return new KafkaConsumer<>(props);

    }

    public VeyyonKafkaConsumer<T2> build() {

      if(Objects.equals(broker,"") || Objects.equals(broker,null))
        throw new IllegalArgumentException("Broker should be specified before running the build()");
      if(Objects.equals(topicName,"") || Objects.equals(topicName,null))
        throw new IllegalArgumentException("topicName should be specified before running the build()");
      if(Objects.equals(sslTrustStoreLocation,"") || Objects.equals(sslTrustStoreLocation,null))
        throw new IllegalArgumentException("sslTrustStoreLocation should be specified before running the build()");
      if(Objects.equals(securityProtocol,"") || Objects.equals(securityProtocol,null))
        throw new IllegalArgumentException("securityProtocol should be specified before running the build()");

      if((consumer.valueClass==null && consumer.valueTypeRef==null)
              ||(consumer.valueClass!=null && consumer.valueTypeRef!=null))
        throw new IllegalArgumentException("Must speicif either valueClass or valueTypeRef before running the build()");

      if((consumer.recordsHandler==null && consumer.listRecordHandler==null)
              ||(consumer.recordsHandler!=null && consumer.listRecordHandler!=null))
        throw new IllegalArgumentException("Must speicif either recordhandler or listRecordHandler before running the build()");

      if((consumer.recordsHandler!=null && consumer.valueClass==null)
              ||(consumer.listRecordHandler!=null && consumer.valueTypeRef!=null))
        throw new IllegalArgumentException("Handler and valueClass pair is not valid fir runnign the build(");

      consumer.pollDelay = customPollDelay>0 ? customPollDelay : 100;
      consumer.recordConsumer=createConsumer();
      consumer.recordConsumer.subscribe(Arrays.asList(topicName));

      return consumer;
    }
  }


}
