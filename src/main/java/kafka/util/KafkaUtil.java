package kafka.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KafkaUtil {

    public String getUniqueId(){
        return "veyyon" + LocalDate.now().format(DateTimeFormatter
                .ofPattern("yyyyMMddHHmmssSSS"));
    }
}
