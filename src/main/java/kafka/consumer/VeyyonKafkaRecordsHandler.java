package kafka.consumer;

@FunctionalInterface
public interface VeyyonKafkaRecordsHandler<K,V> {
 void handleRecords(K inkey, V listValue);
}
