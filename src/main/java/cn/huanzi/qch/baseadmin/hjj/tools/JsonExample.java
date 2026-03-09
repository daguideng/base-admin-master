package cn.huanzi.qch.baseadmin.hjj.tools;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonExample {
    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "  \"name\": \"黑盒-可见光-图像分类-multiple-061822490\",\n" +
                "  \"category\": \"6672b4a7d82a2a000ab782d6\",\n" +
                "  \"tested_type\": \"algorithm\",\n" +
                "  \"tested_modality\": \"visible_img\",\n" +
                "  \"tested_task\": \"img_classfication\",\n" +
                "  \"test_items\": [\n" +
                "    {\n" +
                "      \"id\": \"7bf6-f7ef-4df4-543d-11c7\",\n" +
                "      \"name\": \"基础性能测试\",\n" +
                "      \"tool_name\": \"基础性能测试\",\n" +
                "      \"test_type\": \"img_classification_base\",\n" +
                "      \"tool\": \"img_classification_base\",\n" +
                "      \"params\": {}\n" +
                "    }\n" +
                "  ],\n" +
                "  \"algorithm_model\": \"black\",\n" +
                "  \"algorithm\": \"66719e86d82a2a000bfc6912\",\n" +
                "  \"datasets\": [\"66727384d82a2a000ab782a4\"],\n" +
                "  \"description\": \"黑盒-可见光-图像分类-multiple-0618224906\"\n" +
                "}";

        JSONObject jsonObject = new JSONObject(jsonStr);

        // Replace values for specific keys

        jsonObject.put("tested_modality", "New Tested Modality");
        jsonObject.put("tested_task", "New Tested Task");
        jsonObject.put("algorithm_model", "New Algorithm Model");
        jsonObject.put("algorithm", "New Algorithm");
        JSONArray newDatasets = new JSONArray();
        newDatasets.put("1111");

        jsonObject.put("datasets", newDatasets);
        jsonObject.put("description", "New Description");

        // Convert JSONObject back to JSON string
        String updatedJsonStr = jsonObject.toString();
        System.out.println(updatedJsonStr);
    }
}

