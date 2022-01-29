import com.fasterxml.jackson.databind.ObjectMapper;
import com.veyyon.at.common.base.ServiceBaseTest;
import kafka.consumer.VeyyonKafkaConsumer;
import kafka.handler.RecordsHandler;
import kafka.producer.VeyyonKafkaProducer;
import kafka.util.KafkaUtil;
import models.Player;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;

public class KafkaConsumerTest extends ServiceBaseTest {

    private final String brokerName = "b-1.veyyonkafkacluster.bk3xvx.c21.kafka.us-east-1.amazonaws.com:9092,b-2.veyyonkafkacluster.bk3xvx.c21.kafka.us-east-1.amazonaws.com:9092";
    private final String topicName = "Orders.received";
    private final String securityProtocol = "SSL";
    private final String trustStoreLocation = "C://Kafka//confluent/bin/File.jks";

    @Test
    public void myTest() throws Exception {
        System.out.println("ajfl");
        Properties props = new Properties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerName);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "PLAINTEXT");
        KafkaProducer producer = new KafkaProducer<>(props);

        ObjectMapper objMapper = new ObjectMapper();
        try
        {
            Future<RecordMetadata> results;
            results= producer.send(
                    new ProducerRecord<>(topicName, "message from automation script"));
            producer.flush();
            results.get();

        }catch (Exception e){
            logger.info("Exception in publishing the message");
            producer.close();
            throw new Exception("Failed to publish topic : "+ topicName + " in value : " + e);
        }
    }

    @Test
    public Player testKafkaProducerForPlayer() throws Exception {

        List<Player> listPlayer = new ArrayList<>();
        RecordsHandler<Player> playersKafkaHandler = new RecordsHandler<>(listPlayer);

        VeyyonKafkaProducer<Player> testProducer = new VeyyonKafkaProducer<Player>()
                .setBroker(brokerName)
                .setTopicName(topicName)
                 .setSecurityProtocol(securityProtocol)
                .setSslTrustStoreLocation(trustStoreLocation);
        Player player = new Player(14675956, "Ronaldo","Christino","26");
        player.setRank(135);
        player.setScore(900);
        testProducer.publishToTopic(Long.toString(new Random().nextLong()),player);
        return player;
    }

    public void testKafkaConsumer() throws Exception {

        List<Player> playerList = new ArrayList<>();
        RecordsHandler<Player> playersKafkaHandler = new RecordsHandler<>(playerList);

        VeyyonKafkaConsumer testConsumer = new VeyyonKafkaConsumer.ConsumerBuilder<Player>()
                .setBroker(brokerName)
                .setTopicName(topicName)
                .setSecurityProtocol(securityProtocol)
                .setSslTrustStoreLocation(trustStoreLocation)
                .setRecordsHandler(playersKafkaHandler)
                .setvalueClass(Player.class)
                .build();
        testConsumer.startProcessing();
        //trigger the event to publish the message
        Player publishedPlayer = testKafkaProducerForPlayer();

        Thread.sleep(10000);
        testConsumer.stopProcessing();

       Player consumedPlayer =   playerList.stream().filter(p->p.getPlayerId()==publishedPlayer.getPlayerId())
               .findAny().orElse(null);

    }

}
