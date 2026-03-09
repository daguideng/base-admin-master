package cn.huanzi.qch.baseadmin.hjj.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Calculate MD5 hash value of a file
 * 文件的MD5哈希值
 *
 */
public class CalculateMD5 {

    public static String calculateMD5(String filePath) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
        }
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
