package devv.rbfz.entityBuilder.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    public static String processColorCodes(String message) {
        if (message == null) {
            return "";
        }
        Pattern hexPattern = Pattern.compile("(?i)&#([a-f0-9]{6})");
        message = ChatColor.translateAlternateColorCodes('&', message);
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String hexCode = matcher.group(1);
            StringBuilder replacement = new StringBuilder("§x");
            for (char c : hexCode.toCharArray()) {
                replacement.append("§").append(c);
            }
            matcher.appendReplacement(buffer, replacement.toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
