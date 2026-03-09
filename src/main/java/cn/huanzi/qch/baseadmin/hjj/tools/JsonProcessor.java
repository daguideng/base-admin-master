package cn.huanzi.qch.baseadmin.hjj.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Map;

public class JsonProcessor {

    // 替换指定文件中 JSON 的键值
    public StringBuffer  replaceJsonKeyValue(String filePath, JSONObject xx ) {

          StringBuffer sb = new StringBuffer();
        // 读取文件内容
        String jsonStr = FileUtil.readUtf8String(filePath);

        // 解析 JSON 字符串为 JSONObject
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);


        for (Map.Entry<String, Object> key :xx ){
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

    public static void main(String[] args) {
        // 示例用法
        String filePath = "/Users/dengdagui/Documents/4444/4444/black-Visible_Img_Img_Classfication.json";  // JSON 文件路径
        String keyToReplace = "datasets";   // 要替换的键
        String newValue = "[\"9999999\"]";
        JSONObject xx = new JSONObject();

        JSONArray newDatasets = new JSONArray();
        newDatasets.put("New Dataset 1");
        newDatasets.put("New Dataset 2");
        xx.put("datasets", newDatasets);



        // 调用替换方法
      //  replaceJsonKeyValue(filePath, xx);
    }
}
