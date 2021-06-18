package MyClient.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String format(LocalTime time) {
        if (time == null) {
            return null;
        }
        return DATE_TIME_FORMATTER.format(time);
    }

    public static LocalTime parse(String timeString) {
        try {
            return DATE_TIME_FORMATTER.parse(timeString, LocalTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean validTime(String timeString) {
        return TimeUtil.parse(timeString) != null;
    }
}
