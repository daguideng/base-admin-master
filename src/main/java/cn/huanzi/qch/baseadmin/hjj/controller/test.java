package cn.huanzi.qch.baseadmin.hjj.controller;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.HashUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class test {


    /**
    public static void main(String[] args) {
        String filePath = "/Users/dengdagui/Documents/4444/1111/图像目标检测算法模板/model.zip"; // 替换为你的 Java 文件路径
        String md5Hash = HashUtil.md5File(filePath);
        System.out.println("MD5 hash value of the Java file: " + md5Hash);
    }
     ***/




    public static void main(String[] args) {
        String filePath = "/Users/dengdagui/Documents/4444/1111/图像目标检测算法模板/model.zip"; // 替换为你的 Java 文件路径
        try {
            String md5Hash = calculateMD5(filePath);
            System.out.println("MD5 hash value of the Java file: " + md5Hash);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

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

