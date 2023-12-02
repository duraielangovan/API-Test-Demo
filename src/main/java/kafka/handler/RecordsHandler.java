package kafka.handler;

import kafka.consumer.SecuronixKafkaRecordsHandler;

import java.util.List;

public class RecordsHandler<T> implements SecuronixKafkaRecordsHandler<String,T> {

    private final List<T> recordsList;

    public RecordsHandler(List<T> inList) {
        this.recordsList = inList;
    }

    @Override
    public void handleRecords(String inkey, T listValue) {
        recordsList.add(listValue);
    }
}
