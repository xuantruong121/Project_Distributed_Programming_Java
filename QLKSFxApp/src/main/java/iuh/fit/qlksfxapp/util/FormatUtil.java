package iuh.fit.qlksfxapp.util;

public class FormatUtil {
    public static String formatMoney(double doubleValue) {
        String formattedValue = String.format("%,.0f", doubleValue);
        return formattedValue.replace(",", ".");
    }
}
