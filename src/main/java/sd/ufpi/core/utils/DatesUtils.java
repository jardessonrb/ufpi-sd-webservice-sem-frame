package sd.ufpi.core.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatesUtils {
    public static String FORMAT_LOCALDATETIME_DEFAULT = "yyyy-MM-dd HH:mm";

    public static LocalDateTime stringToLocalDatetime(String date){
        if(date == null){
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_LOCALDATETIME_DEFAULT);
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
    
            return dateTime;
        } catch (Exception e) {
            return null;
        }
    }

    public static String localDateTimeToString(LocalDateTime date){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_LOCALDATETIME_DEFAULT);
        String data = format.format(date);

        return data;
    }
}
