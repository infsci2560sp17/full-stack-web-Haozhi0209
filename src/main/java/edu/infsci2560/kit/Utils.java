package edu.infsci2560.kit;

import com.blade.kit.StringKit;
import com.blade.kit.http.HttpRequest;
import com.blade.kit.json.JSONKit;
import com.blade.mvc.http.Request;
import edu.infsci2560.Constant;
// import org.commonmark.Extension;
// import org.commonmark.ext.gfm.tables.TablesExtension;
// import org.commonmark.node.Node;
// import org.commonmark.parser.Parser;
// import org.commonmark.renderer.html.HtmlRenderer;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {


    public static String getIpAddr(Request request) {
        if (null == request) {
            return "0.0.0.0";
        }
        String ip = request.header("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.header("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.header("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.address();
        }
        return ip;
    }

    public static Set<String> getAtUsers(String str) {
        Set<String> users = new HashSet<String>();
        if (StringKit.isNotBlank(str)) {
            Pattern pattern = Pattern.compile("\\@([a-zA-Z_0-9-]+)\\s");
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                users.add(matcher.group(1));
            }
        }

        return users;
    }

    public static boolean isEmail(String str) {
        if (StringKit.isBlank(str)) {
            return false;
        }
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        return matcher.matches();
    }

    public static boolean isSignup(String user_name) {
        if (StringKit.isNotBlank(user_name)) {
            user_name = user_name.toLowerCase();
            if (user_name.contains("admin") ||
                    user_name.contains("test") ||
                    user_name.contains("support")) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isLegalName(String str) {
        if (StringKit.isNotBlank(str)) {
            Pattern pattern = Pattern.compile("^[a-zA-Z_0-9]{4,16}$");
            if (!pattern.matcher(str).find()) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static void run(Runnable t) {
        Executors.newSingleThreadExecutor().submit(t);
    }



    public static String stripXSS(String value) {
        String cleanValue = null;
        if (value != null) {
            cleanValue = Normalizer.normalize(value, Normalizer.Form.NFD);

            // Avoid null characters
            cleanValue = cleanValue.replaceAll("\0", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            cleanValue = scriptPattern.matcher(cleanValue).replaceAll("");
        }
        return cleanValue;
    }

    public static boolean isImage(File imageFile) {
        if (!imageFile.exists()) {
            return false;
        }
        try {
            Image img = ImageIO.read(imageFile);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static double getWeight(Integer loves, Integer favorites, Integer comment, Integer sinks, Integer create_time) {

        long score = Math.max(loves - 1, 1) + favorites * 2 + comment * 2 - sinks;

        int sign = (score == 0) ? 0 : (score > 0 ? 1 : -1);

        double order = Math.log10(Math.max(Math.abs(score), 1));

        double seconds = create_time - 1459440000;
        return Double.parseDouble(String.format("%.2f", order + sign * seconds / 45000));
    }

    public static String encrypt(String plainText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return new BASE64Encoder().encode(encryptedBytes);
    }

    public static String decrypt(String cipherText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cipherTextBytes = new BASE64Decoder().decodeBuffer(cipherText);
        byte[] decValue = cipher.doFinal(cipherTextBytes);
        return new String(decValue);
    }

}
