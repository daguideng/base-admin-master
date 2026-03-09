package cn.huanzi.qch.baseadmin.hjj.tools;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadJson {
    /**
     * JSONObject中替换指定文件的key的value值，并返回替换后的文件内容
     */
    public StringBuffer getDataSetJsonxxxx(String filePath) {
        // JSON对象，用于存放需要替换的键值对
        JSONObject replacementMap = new JSONObject();
        replacementMap.put("alg_id", "111111111111");
        replacementMap.put("name", "1111111111111");
        replacementMap.put("descript", "1111111111111");

        // 获取文件输入流
        InputStream sourceFile = ReadJson.class.getClassLoader().getResourceAsStream(filePath);

        // 用于存放替换后的文件内容
        StringBuffer sbf = new StringBuffer();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(sourceFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 处理每一行内容
                String modifiedLine = replaceValues(line, replacementMap);
                sbf.append(modifiedLine).append("\n"); // 添加替换后的行到StringBuffer中
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sbf;
    }

    /**
     * 根据替换Map替换JSON文件中的对应键值
     */
    private String replaceValues(String line, JSONObject replacementMap) {
        // 假设每行是JSON格式的字符串
        JSONObject jsonLine = new JSONObject(line);

        // 遍历replacementMap，替换对应的键值
        for (String key : replacementMap.keySet()) {
            if (jsonLine.has(key)) {
                jsonLine.put(key, replacementMap.get(key));
            }
        }

        // 返回替换后的JSON字符串
        return jsonLine.toString();
    }

    public static void main(String[] args) {
        ReadJson reader = new ReadJson();
        StringBuffer modifiedContent = reader.getDataSetJsonxxxx("/Users/dengdagui/Documents/4444/4444/black-Visible_Img_Img_Classfication.json");
        System.out.println(modifiedContent.toString());
    }
}
