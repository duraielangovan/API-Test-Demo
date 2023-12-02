package kafka.consumer;

import java.util.List;

@FunctionalInterface
public interface SecuronixKafkaListRecordHandler<K,V> {
 void handleListRecord(K inKey, List<V> inListValue);
}
