package cn.huanzi.qch.baseadmin.hjj.service;

import cn.huanzi.qch.baseadmin.hjj.util.CalculateMD5;
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
import org.springframework.web.multipart.MultipartFile;
import cn.hutool.core.util.StrUtil;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UploadModelService {


    Log log = LogFactory.get();


    /**
     * 上传
     */
    //MultipartFile file
    //  public void uploadMidle(List<String> method_type,List<String> type_list,String reUplodName,String fileName,String api,File uploadfilePath,Map<String, String> heads)throws Exception{
    public void uploadMidle(List<String> method_type, List<String> type_list, String reUplodName, String fileName, String api, File file, Map<String, String> heads,String type) throws Exception {

        log.info("fileName:{}",  fileName);
        String feilei_name = "test";
        //   String  type_name = null ;
        String task_name_big = "test";
        String task_name_small = "test";
        //  1. 根据文件名判断是二分类还是多分类：
        if (fileName.contains("binary")) {   //二分类
            feilei_name = "binary";

        }
        if (fileName.contains("multi")) {   //多分类
            feilei_name = "multiple";
        }

        // 2. 根据文件名判断是可见光还是红外线：
        if (fileName.contains("visible") || type.contains("visible")) {   //可见光
            task_name_big = "可见光";

        }
        if (fileName.contains("infrared") ||type.contains("infrared")) {   //红外线
            task_name_big = "红外线";
        }

        // 视频
        if (fileName.contains("video") ||type.contains("video")) {   //视频
            task_name_big = "视频";
        }


        // 语音
        if (fileName.contains("asr")||type.contains("asr")) {   //语音
            task_name_big = "语音";
        }
        // 结构化数据
        if (fileName.contains("struct")||type.contains("struct")) {   //结构化数据
            task_name_big = "结构化数据";
        }

        // 雷达-信号分类
        if (fileName.contains("radar")||type.contains("radar")) {   //雷达
            task_name_big = "雷达";
        }


        // 雷达-信号分类
        if (fileName.contains("signal_classification")||type.contains("signal_classification")) {   //雷达
            task_name_small = "信号分类";
        }



        // 文本-命名实体识别
        if (fileName.contains("entity")||type.contains("entity")) {   //文本
            task_name_small = "文本-命名实体识别";
        }


        // 文本-情感
        if (fileName.contains("sen_analysis")||type.contains("sen_analysis")) {   //文本
            task_name_small = "文本-情感分析";
        }

        //3. 根据文件名判断是目标检测还是图像分类：
        if (fileName.contains("target")||type.contains("target")) {   //目标检测
            task_name_small = "目标检测";

        }
        //图像分类
        if (fileName.contains("classfication")||type.contains("classfication")) {   //图像分类
            task_name_small = "图像分类";
        }
        // 目标跟踪
        if (fileName.contains("target_trace")||type.contains("target_trace")) {   //目标跟踪
            task_name_small = "目标跟踪";
        }

        // 行人重识别
        if (fileName.contains("reid") ||type.contains("reid")) {   //行人重识别
            task_name_small = "行人重识别";
        }

        // 语义分割
        if (fileName.contains("segmentation") || type.contains("segmentation")) {   //语义分割
            task_name_small = "语义分割";
        }

        // 语音识别
        if (fileName.contains("asr")|| type.contains("asr")) {   //语音识别
            task_name_small = "语音识别";
        }

        // 结构化数据-分类
        if (fileName.contains("structured_data.classification")|| type.contains("structured_data.classification")) {   //结构化数据-分类
            task_name_small = "分类";
        }
        // 结构化数据-回归
        if (fileName.contains("regression") || type.contains("regression")) {   //分类
            task_name_small = "回归";
        }


        Map<String, Object> reportMap = new HashMap<>();

        for (String methodtype : method_type) {

            for (String typelist : type_list) {

               // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
                String time = sdf.format(System.currentTimeMillis());

                String reName = null;
                String tyep_name = null;

                if (methodtype.equals("white")) {
                    tyep_name = "白盒";
                } else {
                    tyep_name = "黑盒";
                }



                if (reUplodName == null || reUplodName.equals("")) {

                    if(feilei_name.contains("test")) {
                        reName = tyep_name + "-" + task_name_big + "-" + task_name_small  + "-" + time;
                    }else{
                        reName = tyep_name + "-" + task_name_big + "-" + task_name_small + "-" + feilei_name +"-"+time;
                    }
                    log.info("重命名为：{}", reName);
                } else {
                    reName = reUplodName + "-" + tyep_name + "-" + task_name_big + "-" + task_name_small + "-" + feilei_name +"-"+time;
                    log.info("重命名为：{}", reName);
                }

                //    reportMap = this.toDoUpload(api, uploadfilePath, reName, methodtype, typelist, heads);
                System.out.println("222222----->" + typelist);
                this.toDoUpload(api, file, reName, methodtype, typelist, heads);

            }

        }


    }


    /**
     * 主上传文件
     *
     * @param api
     * @param file
     * @param reName
     * @param methodtype
     * @param typelist
     * @param heads
     * @throws IOException
     */


    public void toDoUpload(String api, File file, String reName, String methodtype, String typelist, Map<String, String> heads
    ) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InterruptedException {


        String md5data = CalculateMD5.calculateMD5(file.getAbsolutePath());


        // 1. 第一个请求：
        // 1.  http://172.26.193.10:30125/api/files/53e419e5c7f760c3cf62244e8e53430d

        String url = StrUtil.format("{}/api/files/{}", api, md5data);
        System.out.println("Formatted URL: " + url);
        String response = HttpUtil.get(url);
        Thread.sleep(2000);
        System.out.println("Response from server: " + response);

        // 等待 60 秒，直到服务器返回结果,有时上传太大比较慢
        for(int i = 0 ; i < 60 ; i++){
            if(!response.contains("hash_value_not_found")){
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
        // {"hash_value":"53e419e5c7f760c3cf62244e8e53430d","static_name":"model","type":"visible_img.target_detection"}
        checker_paramMap.set("static_name", "model");
        checker_paramMap.set("type", typelist);
        checker_paramMap.set("hash_value", hash_value);

        System.out.println("111111111111---->" + typelist);

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

        for (int i = 0; i < 120; i++) {
            JSONObject checker_status_json = JSONUtil.parseObj(checker_status_response.body());

           // String quick_test_res = checker_status_json.getStr("quick_test_res");
            log.info("reName:{},花费时间:{}秒,checker_status_json:{}",reName,i, checker_status_json);
            if (!checker_status_json.equals("{}")) {
                JSONObject ok_status = JSONUtil.parseObj(checker_status_json);
                String  finished =  ok_status.get("finished").toString();
                String  total =  ok_status.get("total").toString();
                if (finished.equals(total)) {
                    log.info("上传文件成功!");
                    log.info("上传算算法文件名:{},reName:{},花费时间:{}秒,checker_status_json结果:{}",file.getName(),reName,i, checker_status_json);
                    break;
                }

            }

            Thread.sleep(1000);
            checker_status_response = HttpRequest.get(checker_status_url)
                    .headerMap(heads, false)
                    .timeout(5 * 60 * 1000)
                    .execute();


            log.info("上传算算法文件名:{},reName:{},花费时间:{}秒,checker_status_json结果:{}",file.getName(),reName,i, checker_status_json);



        }


        Map<String, Object> result_paramMap = new HashMap<>();
        String file_id = checker_id;


        //1.如果是目标检测：则提交黑白盒方法:
        result_paramMap.put("name", reName);
        result_paramMap.put("test_type", methodtype);
        result_paramMap.put("type", typelist);
        result_paramMap.put("version", "v1.0");
        result_paramMap.put("file_id", file_id);
        result_paramMap.put("descript", reName);
        result_paramMap.put("location", "upload");


        //  Log log = LogFactory.get();
        log.info("test_type:{},type:{}", methodtype, typelist);


        String model_url = api + "/api/model";
        log.info("最终提交的内容是：{}", result_paramMap);


        // 4. 第四个请求：所有内容提交
        HttpResponse result_response = HttpRequest.post(model_url)
                .headerMap(heads, false)
                .form(result_paramMap)
                .timeout(5 * 60 * 1000)
                .execute();


        JSONObject result = new JSONObject();
        result.put("uploadFromResult", result_response);
        result.put("code", 0);
        result.put("message", "上传成功!");

        log.info("上传结果result:{}", result);


    }


    public Object byteToObject(byte[] bytes) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        bis.close();


        return obj;

    }


    /**
     * 将byte数组转化为Object对象
     *
     * @return
     */
    private Object toObject(byte[] bytes) {
        Object object = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);// 创建ByteArrayInputStream对象
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);// 创建ObjectInputStream对象
            object = objectInputStream.readObject();// 从objectInputStream流中读取一个对象
            byteArrayInputStream.close();// 关闭输入流
            objectInputStream.close();// 关闭输入流
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;// 返回对象
    }


}
