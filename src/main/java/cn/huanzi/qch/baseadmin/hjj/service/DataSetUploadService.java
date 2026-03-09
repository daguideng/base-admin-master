package cn.huanzi.qch.baseadmin.hjj.service;

import cn.huanzi.qch.baseadmin.hjj.util.CalculateMD5;
import cn.huanzi.qch.baseadmin.hjj.util.ReadJson;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataSetUploadService {

    Log log = LogFactory.get();


    public void doScheme(String type, String api, String json_name, String dataset_name, File file, Map<String, String> heads) throws Exception {


        log.info("type:{}", type);

        Map<String, String> replace_map = new HashMap<>();

        //3. 调用upload接口
        String uploadURL = api + "/api/data-set/upload";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", FileUtil.file(file));
        paramMap.put("type", type);  //MethodType.Text_Sen_Analysis
        log.info("fileName:{}", file.getName());

        Map<String, Object> reportmap = new HashMap<>();
        String uploadFileResult = HttpUtil.post(uploadURL, paramMap);
        reportmap.put("uploadFile", uploadFileResult);

        Thread.sleep(2000);

        JSONObject upload_id = JSONUtil.toBean(uploadFileResult, JSONObject.class);
        String file_id = String.valueOf(upload_id.get("id"));
        reportmap.put("file_id", file_id);
        log.info("file_id:{}", file_id);


        String returnMsg = null;
        //4. 调用status接口
        // 拼接请求链接
        String requestUrl = StrUtil.format("{}/api/data-set/upload/{}/status", api, file_id);
        // 发送请求
        try {
            //   Thread.sleep(3000);

            String err = null;
            int total = 0;
            int finished = 10;
            int countsum = 0;
            JSONObject jsonObject = null;

            returnMsg = HttpUtil.get(requestUrl);
            // returnMsg = "{}";
            while (!returnMsg.contains("err")) {
                Thread.sleep(1000);
                returnMsg = HttpUtil.get(requestUrl);

                countsum++;
                if (countsum >= 60) {

                    log.info("type是:{}上传失败,fileName:{}上传失败!", type, file.getName());
                    return;
                }
                log.info("共sleep时间为:{} 毫秒", 1000 * countsum);
                log.info("sleep后status接口返回的结果:{},上传文件名:{}", returnMsg, file.getName());

            }


            log.info("返回上传数据结果为:{}", returnMsg);
            jsonObject = new JSONObject(returnMsg);
            total = (int) jsonObject.get("total");
            finished = (int) jsonObject.get("finished");
            err = (String) jsonObject.get("err");


            if (!err.equals("")) {
                log.error("上传status异常信息为:{},上传模型{}失败", err, type);
                return;
            }


            while (finished < total && err.equals("")) {
                Thread.sleep(1000);
                countsum++;
                try {
                    String speedx = HttpUtil.get(requestUrl);
                    JSONObject jsonObjectx = new JSONObject(speedx);

                    int finishedx = (int) jsonObjectx.get("finished");
                    log.info("finishedx：{}", finishedx);
                    log.info("返回上传数据文件数量:{},fileName:{}", jsonObjectx, file.getName());
                    finished = finishedx;
                    log.info("返回上传数据文件数量:{},fileName:{}", jsonObjectx, file.getName());
                    if (countsum >= 60) {
                        log.info("返回上传数据文件数量:{},fileName:{}上传失败!", jsonObjectx, file.getName());
                        return;
                    }
                } catch (Exception e) {
                    finished = 0;

                }

            }


            //5.调用data-set接口
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(System.currentTimeMillis());
            replace_map.put("name", dataset_name + "_" + time);
            replace_map.put("type", type);   //MethodType.Text_Sen_Analysis
            replace_map.put("file_id", file_id);
            replace_map.put("descript", dataset_name + "_" + time);
            log.info("replace_map:{}", replace_map.toString());
            StringBuffer bodys = ReadJson.getReportContent(json_name, replace_map);


            String dataset_url = api + "/api/data-set";


            HttpResponse result_response = HttpRequest.post(dataset_url)
                    .headerMap(heads, false)
                    .body(bodys.toString())
                    .timeout(5 * 60 * 1000)
                    .execute();


            log.info("status接口返回结果为:{}", returnMsg);
            log.info("上传模型为名为:{}上传成功!", dataset_name + "_" + time);

            reportmap.put("uploadFromResult", result_response);
            reportmap.put("code", 0);
            reportmap.put("message", "上传成功!");


        } catch (Exception e) {
            e.printStackTrace();

        }


    }


    /***
     * 上传数据集
     */

    //String type, String api, String json_name, String dataset_name, File file, Map<String, String> heads
    public void toDoUpload(String api, File file, String reName, String type, Map<String, String> heads
    ) throws IOException, NoSuchAlgorithmException, InterruptedException {


        String md5data = CalculateMD5.calculateMD5(file.getAbsolutePath());

        // 1. 第一个请求：
        // 1.  http://172.26.193.10:30125/api/files/53e419e5c7f760c3cf62244e8e53430d

        String url = StrUtil.format("{}/api/files/{}", api, md5data);
        System.out.println("Formatted URL: " + url);
        String response = HttpUtil.get(url);
        Thread.sleep(2000);
        System.out.println("Response from server: " + response);

        // 等待 60 秒，直到服务器返回结果,有时上传太大比较慢
        for (int i = 0; i < 60; i++) {
            if (!response.contains("hash_value_not_found")) {
                log.info("/api/files/接口response:{}", response);
                break;
            }
            Thread.sleep(500);

            // 如果get不成功则换另一种post请求方式
            //  url = http://10.10.12.62:10088/api/files
            String uploadUrl = StrUtil.format("{}/api/files", api);
            HttpResponse httpResponse = (HttpRequest.post(uploadUrl)
                    .headerMap(heads, false)
                    .form("file", file)
                    .timeout(5 * 60 * 1000)
                    .execute()); // 上传文件
            response = httpResponse.body();
            log.info("上传文件结果:{}", response);
            //  response = HttpUtil.get(url);
        }

        // 将字符串转换为 JSON 对象
        // Thread.sleep(2000);
        log.info("最终response:{}", response);
        JSONObject jsonObjectCheck = JSONUtil.parseObj(response);

        // 获取 JSON 对象中的属性值
        String hash_value = jsonObjectCheck.getStr("hash_value");

        // 2. 第二个请求：
        // 2. http://172.26.193.10:30125/api/files/checker   hutool post body
        String checker_url = StrUtil.format("{}/api/files/checker", api);
        JSONObject checker_paramMap = new JSONObject();
        // {"hash_value":"4fdb49d83a9bb699b655d4cf48cb2d2e","static_name":"data","type":"visible_img.img_classfication"}
        checker_paramMap.put("static_name", "data");
        checker_paramMap.put("type", type);
        checker_paramMap.put("hash_value", hash_value);

        System.out.println("111111111111---->" + type);

        HttpResponse checker_response = HttpRequest.post(checker_url)
                .headerMap(heads, false)
                .body(checker_paramMap.toString())
                .timeout(5 * 60 * 1000)
                .execute();

        log.info("checker_response:{}", checker_response);

        // 3. 第三个请求：
        // 3. http://172.26.193.10:30125/api/files/checker/status?id=664e0a1cf943920122b091cb  hutool post
        JSONObject checker_response_json = JSONUtil.parseObj(checker_response.body());
        String checker_id = checker_response_json.getStr("id");
        String checker_status_url = StrUtil.format("{}/api/files/checker/status?id={}", api, checker_id);
        JSONObject checker_status = new JSONObject();
        checker_status.put("id", checker_id);
        //hutool get
        HttpResponse checker_status_response = HttpRequest.get(checker_status_url)
                .headerMap(heads, false)
                .timeout(5 * 60 * 1000)
                .execute();

        //  log.info("checker_status_response:{}", checker_status_response.body());


        // 4.以快速检测返回的json中 {"ok":true} 为准进判断是否上传文件

        for (int i = 0; i < 100; i++) {
            JSONObject checker_status_json = JSONUtil.parseObj(checker_status_response.body());

            String total = checker_status_json.getStr("total");
            String finished = checker_status_json.getStr("finished");

            log.info("total:{},finished:{}", total,finished);
            if (total.equals(finished) ) {
                log.info("数据集上传文件成功!");
                log.info("上传数据数据集名称:{},reName:{},checker_status_json:{}",file.getName(),reName, checker_status_json);
                break;
            }

            if(i==99){
                log.info("上传数据数据集:{}，reName:{},上传等待60秒,finished:{},total:{}后退出",file.getName(),reName,finished,total);

                JSONObject result = new JSONObject();
                result.put("uploadFromResult", "上传失败!");
                result.put("code", 0);
                result.put("message", "上传失败!");

                log.error("上传结果result:{}", result);
                return;

            }

            Thread.sleep(1000);
            checker_status_response = HttpRequest.get(checker_status_url)
                    .headerMap(heads, false)
                    .timeout(5 * 60 * 1000)
                    .execute();

            log.info("上传原始数据集文件:{}", file.getName());
            log.info("上传数据集名称:{},reName:{},花费时间:{}秒,检查文件数finished:{},total:{}",file.getName(),reName,i,finished,total);




        }


        JSONObject result_paramMap = new JSONObject();
        String file_id = checker_id;

            /**

                 {"name":"test",
                "descript":"test",
                "type":"visible_img.img_classfication",
                "file_id":"666efd9dcfe90b000b1d19e9"}
             ***/

        //1.如果是目标检测：则提交黑白盒方法:
        result_paramMap.put("name", reName);
        result_paramMap.put("descript", reName);
        result_paramMap.put("type", type);
        result_paramMap.put("file_id", file_id);


        //  Log log = LogFactory.get();
        log.info("file_id:{},type:{}", file_id, type);


        String dataset_url = api + "/api/data-set";
        log.info("最终提交的内容是：{}", result_paramMap);


        // 4. 第四个请求：所有内容提交
        HttpResponse result_response = HttpRequest.post(dataset_url)
                .headerMap(heads, false)
                .body(String.valueOf(result_paramMap))
                .timeout(5 * 60 * 1000)
                .execute();


        JSONObject result = new JSONObject();
        result.put("uploadFromResult", result_response);
        result.put("code", 0);
        result.put("message", "上传成功!");

        log.info("上传结果result:{}", result);


    }


}
