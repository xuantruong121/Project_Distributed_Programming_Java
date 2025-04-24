package iuh.fit.qlksfxapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtil {
    public static String formatMoney(double doubleValue) {
        String formattedValue = String.format("%,.0f", doubleValue);
        return formattedValue.replace(",", ".");
    }
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        if(localDateTime== null)
            return "không xác định";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return localDateTime.format(formatter);
    }
}
