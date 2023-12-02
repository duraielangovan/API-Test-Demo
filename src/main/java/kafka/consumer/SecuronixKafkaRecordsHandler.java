package kafka.consumer;

@FunctionalInterface
public interface SecuronixKafkaRecordsHandler<K,V> {
 void handleRecords(K inkey, V listValue);
}
