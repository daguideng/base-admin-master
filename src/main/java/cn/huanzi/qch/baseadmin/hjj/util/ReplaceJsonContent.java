package cn.huanzi.qch.baseadmin.hjj.util;





import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.json.JSONException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Component
public class ReplaceJsonContent {


    /**
     * Recursively traverse the JSON tree and replace values based on the replacement map.
     *
     * @param node           The current node in the JSON tree
     * @param replacementMap The map containing key-value pairs for replacement
     */
    public  void replaceJsonContent(JsonNode node, JSONObject replacementMap) {
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String fieldName = entry.getKey();

            JsonNode fieldValue = entry.getValue();
          //  System.out.println("key,fieldValue--->"+fieldName+":"+fieldValue);


            if (fieldValue.isObject()) {

                // Recursively call for nested objects
                replaceJsonContent(fieldValue, replacementMap);
            } else if (fieldValue.isTextual()) {
                // Replace field value if it matches any key in replacementMap
                Object replacement = replacementMap.get(fieldName);
                System.out.println("fieldName : replacement--->"+fieldName);

                if (replacement != null) {

                  //  System.out.println("fieldName--->"+fieldName);

                    ((ObjectNode) node).put(fieldName, (String)replacement);
                }
            }
            // Add conditions here for other types if needed (arrays, etc.)
        }
    }


    public StringBuffer getDataSetJsonXXX(String filePath, JSONObject replacementMap) {
        // String filePath = "/Users/dengdagui/Documents/4444/white-Img_Classfication.json";

        // Predefined mappings for replacement
        /**
         Map<String, String> replacementMap = new HashMap<>();
         replacementMap.put("alg_id", "111111111111");
         replacementMap.put("name", "1111111111111");
         replacementMap.put("descript", "1111111111111");
         **/


        InputStream sourceFile = ReadJson.class.getClassLoader().getResourceAsStream(filePath);

        StringBuffer sbf = new StringBuffer(1000);

        try {
            // Read JSON file and convert to JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(sourceFile);

            // Replace content in JSON
            replaceJsonContent(jsonNode, replacementMap);

            // Convert updated JSON structure back to string for output
            String updatedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            System.out.println(updatedJson);

            sbf.append(updatedJson);



        } catch (IOException e) {
            e.printStackTrace();
        }

        return sbf;
    }





    public StringBuffer  getDataSetJsonXXXXX(String filePath, JSONObject replacementMap) {

        StringBuffer sb = new StringBuffer();

        // String xxx = ReadJson.class.getClassLoader().getResourceAsStream(filePath);
        URL resourceUrl = getClass().getClassLoader().getResource(filePath);

        filePath = resourceUrl.getPath();

        //  InputStream sourceFile = ReadJson.class.getClassLoader().getResourceAsStream(filePath);
      // String xxx = ReadJson.class.getClassLoader().getResourceAsStream(filePath);

        // 读取文件内容
        String jsonStr = FileUtil.readUtf8String(filePath);

        // 解析 JSON 字符串为 JSONObject
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);


        for (Map.Entry<String, Object> key :replacementMap){
            System.out.printf("xx"+key.getKey(),key.getValue());
            // 替换指定键的值
            jsonObject.set(key.getKey(), key.getValue());

        }

        // 输出替换后的 JSON 字符串
        return   sb.append(jsonObject.toStringPretty());
        // System.out.println(jsonObject.toStringPretty());

        // 可选：将替换后的 JSON 字符串写回到文件中
        //  FileUtil.writeUtf8String(jsonObject.toStringPretty(), filePath);
    }


    public StringBuffer getDataSetJsonBBB(String filePath, JSONObject replacementMap) {
        StringBuffer sb = new StringBuffer();

        // 获取资源流
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        // 使用 BufferedReader 逐行读取文件内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            StringBuilder jsonBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            // 将文件内容解析为 JSON 对象
            JSONObject jsonObject = JSONUtil.parseObj(jsonBuilder.toString());

            for(int i = 0; i < jsonObject.size(); i++){

               // System.out.println("key: " + jsonObject.keySet().toArray()[i] + " value: " + jsonObject.get(jsonObject.keySet().toArray()[i]));

                // 替换指定键的值
                for (Map.Entry<String, Object> entry : replacementMap.entrySet()) {
                    // 如果 JSON 对象中存在指定的键，则替换其值
                    if(jsonObject.keySet().toArray()[i].equals(entry.getKey())){
                        System.out.printf("key: " + jsonObject.keySet().toArray()[i] + " value: " + entry.getValue());
                        jsonObject.set((String) jsonObject.keySet().toArray()[i], entry.getValue());

                    }
                }
            }


           // System.out.println(jsonObject.toStringPretty());

            // 输出替换后的 JSON 字符串
            sb.append(jsonObject.toStringPretty());
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
        }

        return sb;
    }




    public StringBuffer getDataSetJson(String filePath, JSONObject replacementMap) {
        StringBuffer sb = new StringBuffer();

        // 获取资源流
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        // 使用 BufferedReader 逐行读取文件内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            StringBuilder jsonBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            // 将文件内容解析为 JSON 对象
            JSONObject jsonObject = JSONUtil.parseObj(jsonBuilder.toString());

            // 递归遍历并替换 JSON 对象中的键值对
            recursivelyReplace(jsonObject, replacementMap);

            // 输出替换后的 JSON 字符串
            sb.append(jsonObject.toStringPretty());
        } catch (IOException e) {
            nameCount = 0 ;
            // 处理异常
            e.printStackTrace();
        }

        nameCount = 0 ;

        return sb;
    }


      int nameCount = 0;

    private void recursivelyReplace(JSONObject jsonObject, JSONObject replacementMap) {
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // 如果当前值是 JSONObject，递归替换内部键值对
            if (value instanceof JSONObject) {
                recursivelyReplace((JSONObject) value, replacementMap);
            } else if (value instanceof JSONArray) {
                // 如果当前值是 JSONArray，遍历数组中的每个元素，递归替换内部键值对
                JSONArray array = (JSONArray) value;
                for (int i = 0; i < array.size(); i++) {
                    Object arrayElement = array.get(i);
                    if (arrayElement instanceof JSONObject) {
                        recursivelyReplace((JSONObject) arrayElement, replacementMap);
                    }
                }
            }

            // 替换最外层的键值对
            if (replacementMap.containsKey(key) && key.equals("name") ) {
                // 如果当前值是name则只替换最外层的name一次
                if(nameCount == 0){
                    System.out.println("key: " + key + " value: " + replacementMap.get(key));
                    jsonObject.put(key, replacementMap.get(key));
                    nameCount++;
                }

            }else if(replacementMap.containsKey(key) && !key.equals("name") ){
                jsonObject.put(key, replacementMap.get(key));
                }
            }


        }


    public static void main(String[] args) {
        ReplaceJsonContent processor = new ReplaceJsonContent();
        JSONObject replace_map = new JSONObject();

        replace_map.set("algorithm", "1111");
        replace_map.set("algorithm_name", "222");
        replace_map.set("algorithm_model", "");


        JSONArray datasets = new JSONArray();
        datasets.put("00000000");


        replace_map.set("datasets", datasets);
        replace_map.set("category", "99999");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(System.currentTimeMillis());

        replace_map.put("name", "schemeName" + "_" + time);
        replace_map.put("description", "schemeName" + "_" + time);

        String filePath = "schemeJson/dataset/dataset-Visible_Img_Target_Detection.json";
        StringBuffer result = processor.getDataSetJson(filePath, replace_map);
        System.out.println(result.toString());
    }








}



