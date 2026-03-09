package cn.huanzi.qch.baseadmin.hjj.controller;


import cn.huanzi.qch.baseadmin.hjj.entity.MethodType;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试集管理
 */


@RestController
@RequestMapping("/testset")

public class TestSetManagement {

    Log log = LogFactory.get();

    @GetMapping("/management")
    public ModelAndView testsetMethodMenu() {
        return new ModelAndView("testset/management");
    }


    //图像目标检测-数据集：
    @PostMapping("/detectiondataset")
    public Map<String, Object> uploaDdetection(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.获取上传文件：
        File uploadfilePath = this.getFile(file);

        //得到上传文件名:
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        log.info("fileName:{}",fileName);
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);


        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(request);

        //3.获取api:
        String api = request.getParameter("api");
        //String api = "http://10.10.12.13:5000";

        //3.目标检测：
        String reNamePrefix = request.getParameter("reDetectionName");

        List<String> method_type = new ArrayList<>();
           method_type.add("");
       //  method_type.add("white");

        List<String> type_list = new ArrayList<>();
        //1.添加图像分类
        type_list.add(MethodType.Visible_Img_Target_Detection);
        type_list.add(MethodType.Infrared_Ray_Target_Detection);


        Map<String, Object> reportMap = new HashMap<>();

        for (String methodtype : method_type) {

            for (String typelist : type_list) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String time = sdf.format(System.currentTimeMillis());

                String reName = null;

                if (reNamePrefix == null || reNamePrefix.equals("")) {
                    reName = methodtype + "-" + fileName + "_" + time;
                } else {
                    reName = methodtype + "-" + reNamePrefix + "-" + fileName + "_" + time;

                }

                reportMap = this.toDoUpload(api, uploadfilePath, reName, methodtype, typelist, heads);
            }

        }

        return reportMap;

    }

    /**
     * 主上传文件
     * @param api
     * @param file
     * @param reName
     * @param methodtype
     * @param typelist
     * @param heads
     * @return
     * @throws IOException
     */
    public Map<String, Object> toDoUpload(String api, File file, String reName, String methodtype, String typelist, Map<String, String> heads
    ) throws IOException {

        Map<String, Object> reportmap = new HashMap<>();

        String uploadURL = api + "/api/data-set/upload";

        //  HashMap<String, Object> paramMap = new HashMap<>();

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", FileUtil.file(file));
        paramMap.put("type", typelist);


        String uploadFileResult = HttpUtil.post(uploadURL, paramMap);
        reportmap.put("uploadFile", uploadFileResult);


        //jsonString需要用大括号包裹
        //JSONObject.class为需要转成的对象类型
        JSONObject upload_id = JSONUtil.toBean(uploadFileResult.toString(), JSONObject.class);
        String file_id = String.valueOf(upload_id.get("id"));
        reportmap.put("file_id", file_id);

        // 拼接请求链接
        String requestUrl = StrUtil.format("{}/api/data-set/upload/{}/status", api, file_id);
        // 发送请求
        try {
            Thread.sleep(9000);

            String returnMsg = HttpUtil.get(requestUrl);
            Map<String, Object> result_paramMap = new HashMap<>();


            //1.如果是目标检测：则提交黑白盒方法:
            result_paramMap.put("name", reName);
            result_paramMap.put("type", typelist);
            result_paramMap.put("file_id", file_id);
            result_paramMap.put("descript", reName);

            Log log = LogFactory.get();
            log.error("test_type {}", "type{}", methodtype, typelist);


            String model_url = api + "/api/model";
            log.info("result_paramMap:{}",result_paramMap);

            HttpResponse result_response = HttpRequest.post(model_url)
                    .headerMap(heads, false)
                    .form(result_paramMap)
                    .timeout(5 * 60 * 1000)
                    .execute();


            reportmap.put("uploadFromResult", result_response);
            reportmap.put("code",0);
            reportmap.put("message","上传成功!");

            log.info("result_response:{}",result_response);


        } catch (Exception e) {
            e.printStackTrace();

        }


        return reportmap;
    }


    public Map<String, String> loginServer(HttpServletRequest request) {

        //公共资源：
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String api = request.getParameter("api");

        log.info("username为:{}", "password为:{}", "api为:{}", username, password, api);

        //2.图像分类：

        Map<String, String> ImgClassficationMap = new HashMap<>();
        //  username = "dengdagui";
        //  password = "abcd1234";
        String base64_name = Base64Encoder.encode(username);
        String base64_pass = Base64Encoder.encode(password);
        ImgClassficationMap.put("username", base64_name);
        ImgClassficationMap.put("password", base64_pass);


        ObjectMapper objectMapper = new ObjectMapper();
        String loginJson = null;
        try {
            loginJson = objectMapper.writeValueAsString(ImgClassficationMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("账户信息为:{}", loginJson);

        // api = "http://10.10.12.13:5000";
        String urlPath = api + "/api/auth/token";

        //1.登录
        String result = HttpUtil.post(urlPath, loginJson);

        log.info("登录结果为:{}", result);


        Map<String, Object> map = JSONUtil.parseObj(result);
        String access_token = (String) map.get("access_token");
        int expires = (int) map.get("expires");
        BigDecimal expires_timestamp = (BigDecimal) map.get("expires_timestamp");
        String id = (String) map.get("id");
        String type = (String) map.get("type");


        // 添加请求头信息
        Map<String, String> heads = new HashMap<>();
        // 使用json发送请求，下面的是必须的
        // heads.put("Content-Type", "application/json;charset=UTF-8");
        heads.put("Authorization", type + " " + access_token);
        return heads;

    }


    /**
     * 获取文件相关
     */
    public File getFile(MultipartFile file) {

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码

        File uploadfilePath = null;
        try {
            uploadfilePath = File.createTempFile(fileName, prefix);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uploadfilePath;
    }



}
