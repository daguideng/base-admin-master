package cn.huanzi.qch.baseadmin.hjj.tools;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

public class ReplaceValues {

    public static void main(String[] args) {
        // 原始的 JSON 数据
        Map<String, Object> data = new HashMap<>();
        data.put("algorithm_model", "black");
        data.put("algorithm", "66719e86d82a2a000bfc6912");
        data.put("datasets", "[\"aaa\"]");
        data.put("description", "黑盒-可见光-图像分类-multiple-0618224906");

        // 要替换的键值对
        Map<String, Object> replacements = new HashMap<>();
        replacements.put("algorithm_model", "white");
        replacements.put("algorithm", "new_algorithm_id");
        replacements.put("datasets", "[\"aaa\"]");
        replacements.put("description", "白盒-红外-图像识别-single-0618224906");

        // 替换操作
        for (String key : replacements.keySet()) {
            if (data.containsKey(key)) {
                data.put(key, replacements.get(key));
            }
        }

        // 打印替换后的数据
        System.out.println(data);
    }
}
