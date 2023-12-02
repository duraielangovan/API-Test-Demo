package com.soar.connectors.vmwarbonblack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securonix.at.common.base.ServiceBaseTest;
import kafka.consumer.SecuronixKafkaConsumer;
import kafka.handler.RecordsHandler;
import kafka.producer.SecuronixKafkaProducer;
import models.ContainerMeta;
import models.Player;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
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

   // @Test
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

   // @Test
    public Player testKafkaProducerForPlayer() throws Exception {

        List<Player> listPlayer = new ArrayList<>();
        RecordsHandler<Player> playersKafkaHandler = new RecordsHandler<>(listPlayer);

        SecuronixKafkaProducer<Player> testProducer = new SecuronixKafkaProducer<Player>()
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

    @Test
    public void testKafkaConsumer() throws Exception {
        String soarTopicName = "soar-container-topic";
        List<ContainerMeta> alertList = new ArrayList<>();
        SecuronixKafkaConsumer testConsumer=
                super.runConsumer(soarTopicName,alertList,new ContainerMeta());
        testConsumer.startProcessing();

        //trigger the event to publish the message
         //ingest violation or write to topic

        Thread.sleep(10000);
        testConsumer.stopProcessing();
        int size = alertList.size();
        extentLogger.logInfo(String.valueOf(size));

    /*   Player consumedPlayer =   alertList.stream().filter(p->p.getPlayerId()==publishedPlayer.getPlayerId())
               .findAny().orElse(null);*/

    }

}
