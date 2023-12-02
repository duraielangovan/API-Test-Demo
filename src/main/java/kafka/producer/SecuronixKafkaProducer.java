package kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.util.KafkaUtil;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Future;

public class SecuronixKafkaProducer<T extends Object> {

    protected Logger logger = LoggerFactory.getLogger(SecuronixKafkaProducer.class);

    protected KafkaUtil kafkaUtil = new KafkaUtil();
    protected String broker;
    protected String topicName;
    protected String clientId=kafkaUtil.getUniqueId();
    protected String securityProtocol;
    protected String sslTrustStoreLocation;

    public SecuronixKafkaProducer<T> setBroker(String broker) {
        this.broker = broker.trim();
        return this;
    }

    public SecuronixKafkaProducer<T> setTopicName(String topicName) {
        this.topicName = topicName.trim();
        return this;
    }

    public SecuronixKafkaProducer<T> setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol.trim();
        return this;
    }

    public SecuronixKafkaProducer<T> setSslTrustStoreLocation(String sslTrustStoreLocation) {
        this.sslTrustStoreLocation = sslTrustStoreLocation;
        return this;
    }

    public void publishToTopic(String inKey, T inValue) throws Exception {
        publishToTopic(initProducer(),inKey,inValue);
    }

    protected void publishToTopic(Producer<String, String> inProducerToUse, String inKey, T inValue) throws Exception{
        ObjectMapper objMapper = new ObjectMapper();
        try
        {
            Future<RecordMetadata> results;
            results= inProducerToUse.send(
                    new ProducerRecord<>(topicName, inKey, objMapper.writeValueAsString(inValue)));
            inProducerToUse.flush();
            results.get();

        }catch (Exception e){
            logger.info("Exception in publishing the message");
            inProducerToUse.close();
            throw new Exception("Failed to publish topic : "+ topicName + " in value : " + inValue, e);
        }
    }

      protected Producer<String, String> initProducer() {

        logger.info("Creating producer");

        if(Objects.equals(broker,"") || Objects.equals(broker,null))
            throw new IllegalArgumentException("Broker should be specified before publishing");
        else if(Objects.equals(topicName,"") || Objects.equals(topicName,null))
            throw new IllegalArgumentException("topicName should be specified before publishing");
        else if(Objects.equals(sslTrustStoreLocation,"") || Objects.equals(sslTrustStoreLocation,null))
            throw new IllegalArgumentException("sslTrustStoreLocation should be specified before publishing");
        else if(Objects.equals(securityProtocol,"") || Objects.equals(securityProtocol,null))
            throw new IllegalArgumentException("securityProtocol should be specified before publishing");

        Properties props = new Properties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, sslTrustStoreLocation);

        return new KafkaProducer<>(props);
    }
}
