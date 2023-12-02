package kafka.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KafkaUtil {

    public String getUniqueId(){
        return "securonix" + LocalDate.now().format(DateTimeFormatter
                .ofPattern("yyyyMMddHHmmss"));
    }
}
