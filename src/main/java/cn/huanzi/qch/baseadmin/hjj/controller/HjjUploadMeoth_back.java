package cn.huanzi.qch.baseadmin.hjj.controller;


import cn.huanzi.qch.baseadmin.hjj.entity.MethodType;
import cn.huanzi.qch.baseadmin.hjj.service.DataSetUploadService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录用户访问
 */
@RestController
@RequestMapping("/upload")

public class HjjUploadMeoth_back {

    Log log = LogFactory.get();


    @Resource
    private DataSetUploadService dataSetUploadService;


    @GetMapping("/methodX")
    public ModelAndView UpalodMethodMenu() {
        return new ModelAndView("upload/method");
    }


    //图像分类：
    @PostMapping("/imgclassficationX")
    public Map<String, Object> uploadImgClassfication(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //1.获取上传文件：
        File uploadfilePath = this.getFile(file);

        //得到上传文件名:
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);



        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String api = request.getParameter("api");

        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(username,password,api);

        //3.获取api:
      //  String api = request.getParameter("api");
        //String api = "http://10.10.12.13:5000";

        //3.图像分类：
        String reNamePrefix = request.getParameter("reImgClassficationName");

        List<String> method_type = new ArrayList<>();
        method_type.add("black");
        method_type.add("white");

        List<String> type_list = new ArrayList<>();
        //1.添加图像分类
        type_list.add(MethodType.Visible_Img_Img_Classfication);
        type_list.add(MethodType.Infrared_Ray_Img_Classfication);


        Map<String, Object> reportMap = new HashMap<>();

        for (String methodtype : method_type) {

            for (String typelist : type_list) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String time = sdf.format(System.currentTimeMillis());

                String reName = null;

                if (reNamePrefix == null || reNamePrefix.equals("")) {
                    reName = methodtype + "-" + fileName + "_" + time;
                    log.info("重命名为：{}",reName);
                } else {
                    reName = methodtype + "-" + reNamePrefix + "-" + fileName + "_" + time;
                    log.info("重命名为：{}",reName);

                }

                reportMap = this.toDoUpload(api, uploadfilePath, reName, methodtype, typelist, heads);
            }

        }

        return reportMap;

    }


    //目标检测：
    @PostMapping("/detectionX")
    public Map<String, Object> uploaDdetection(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.获取上传文件：
        File uploadfilePath = this.getFile(file);

        //得到上传文件名:
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        log.info("fileName:{}",fileName);
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String api = request.getParameter("api");

        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(username,password,api);


        //3.获取api:
        //String api = "http://10.10.12.13:5000";

        //3.目标检测：
        String reNamePrefix = request.getParameter("reDetectionName");

        List<String> method_type = new ArrayList<>();
        method_type.add("black");
        method_type.add("white");

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


    //文本-情感：
    @PostMapping("/chineseattackNameX")
    public Map<String, Object> uploadchineseattack(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.获取上传文件：
        File uploadfilePath = this.getFile(file);

        //得到上传文件名:
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String api = request.getParameter("api");

        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(username,password,api);


        //3.获取api:


        //3.文本-情感：
        String reNamePrefix = request.getParameter("reChinese_attackName");

        List<String> method_type = new ArrayList<>();
        method_type.add("black");


        List<String> type_list = new ArrayList<>();
        //1.添加文本
        type_list.add(MethodType.Text_Sen_Analysis);



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


    //文本-命名实体识别:
    @PostMapping("/reseqattackNameX")
    public Map<String, Object> uploadArseqattack(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.获取上传文件：
        File uploadfilePath = this.getFile(file);

        //得到上传文件名:
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);


        //2.获取登录相关认证头：
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String api = request.getParameter("api");

        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(username,password,api);



        //String api = "http://10.10.12.13:5000";

        //3.文本-情感：
        String reNamePrefix = request.getParameter("reSeq_AttackName");

        List<String> method_type = new ArrayList<>();
        method_type.add("black");


        List<String> type_list = new ArrayList<>();
        //1.添加文本命名实体识别：
        type_list.add(MethodType.Text_Entity_Recognition);


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


    //语音：
    @PostMapping("/voiceX")
    public Map<String, Object> uploaVoice(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.获取上传文件：
        File uploadfilePath = this.getFile(file);

        //得到上传文件名:
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        System.out.printf("fileName--->" + fileName);
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String api = request.getParameter("api");

        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(username,password,api);


        //3.获取api:


        //3.语音：
        String reNamePrefix = request.getParameter("reVoiceName");

        List<String> method_type = new ArrayList<>();
        method_type.add("white");


        List<String> type_list = new ArrayList<>();
        //1.添加语音:
        type_list.add(MethodType.Asr_Asr_Recognition);

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


    //目标跟踪：
    @PostMapping("/trackdownX")
    public Map<String, Object> uploaTrackdown(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.获取上传文件：
        File uploadfilePath = this.getFile(file);

        //得到上传文件名:
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        System.out.printf("fileName--->" + fileName);
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String api = request.getParameter("api");

        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(username,password,api);



        //3.目标跟踪：
        String reNamePrefix = request.getParameter("reTrackdownName");

        List<String> method_type = new ArrayList<>();
        method_type.add("white");
        method_type.add("black");


        List<String> type_list = new ArrayList<>();
        //1.目标跟踪:
        type_list.add(MethodType.Visible_Img_Target_Trace);
        type_list.add(MethodType.Infrared_Ray_Target_trace);

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

        String uploadURL = api + "/api/model/upload";

        //  HashMap<String, Object> paramMap = new HashMap<>();
        JSONObject param = JSONUtil.createObj();
        param.put("file", FileUtil.file(file));
        param.put("type", typelist);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", FileUtil.file(file));
        paramMap.put("type", typelist);


        JSONObject paramx = JSONUtil.createObj();
        paramx.put("file", FileUtil.file(file));
        paramx.put("type", typelist);


        String uploadFileResult = HttpUtil.post(uploadURL, paramMap);
        reportmap.put("uploadFile", uploadFileResult);


        //jsonString需要用大括号包裹
        //JSONObject.class为需要转成的对象类型
        JSONObject upload_id = JSONUtil.toBean(uploadFileResult.toString(), JSONObject.class);
        String file_id = String.valueOf(upload_id.get("id"));
        reportmap.put("file_id", file_id);

        // 拼接请求链接
        String requestUrl = StrUtil.format("{}/api/model/upload/{}/status", api, file_id);
        // 发送请求
        try {
            Thread.sleep(9000);

            String returnMsg = HttpUtil.get(requestUrl);
            Map<String, Object> result_paramMap = new HashMap<>();


            //1.如果是目标检测：则提交黑白盒方法:
            result_paramMap.put("name", reName);
            result_paramMap.put("test_type", methodtype);
            result_paramMap.put("type", typelist);
            result_paramMap.put("version", "v1.0");
            result_paramMap.put("file_id", file_id);
            result_paramMap.put("descript", reName);

            Log log = LogFactory.get();
            log.info("test_type {}", "type{}", methodtype, typelist);


            String model_url = api + "/api/model";
            log.info("最终提交的内容是：{}",result_paramMap);

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


    public Map<String, String> loginServer(String username,String password,String api) {

        log.info("username为:{}", "password为:{}", "api为:{}", username, password, api);
        log.info("登录账号:{},密码:{}",username,password);

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
      //  heads.put("Content-Type", "application/json");

        return heads;

    }


    @GetMapping("/login2X")
    public String  loginServer_2X(String user_name,String password,String api){
        log.info("登录账号:{},密码:{}",user_name,password);
          api = "http://172.26.193.10:31674/";
          user_name = "dengdagui";
          password = "abcd1234";

        //2.图像分类：
        JSONObject  ImgClassficationMap = new JSONObject();

        ImgClassficationMap.set("user_name", user_name);
        ImgClassficationMap.set("password", password);

        log.info("账户信息为:{}", ImgClassficationMap);

        // api = "http://10.10.12.13:5000";
        String urlPath = api + "/api/login";

        //1.登录
        HttpResponse result = HttpRequest.post(urlPath)
                        .body(ImgClassficationMap.toString()).execute();

        log.info("登录结果为:{}", result);


        // 添加请求头信息
        Map<String, String> heads = new HashMap<>();


        return result.body();


    }


    /**
     * 获取文件相关
     */
    public File getFile(MultipartFile file) {

        //文件上传前的名称
        String fileName = file.getOriginalFilename();
        File uploadfile = new File(fileName);
        OutputStream out = null;
        try{
            //获取文件流，以文件流的方式输出到新文件
            //    InputStream in = multipartFile.getInputStream();
            out = new FileOutputStream(uploadfile);
            byte[] ss = file.getBytes();
            for(int i = 0; i < ss.length; i++){
                out.write(ss[i]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }









        return uploadfile;
    }







    /**
     *  数据集上传
     */
    @PostMapping("/uploaddatasetX")
    public Map<String, Object>  uploadDataset(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {

        //1.获取上传文件：
        File uploadfilePath = this.getFile(file);
      //  File file = new File(uploadfilePath);

        //得到上传文件名:
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);


        String username = request.getParameter("username_dataset");
        String password = request.getParameter("password_dataset");
        String api = request.getParameter("api_dataset");
        String reDdatasetName = request.getParameter("reDdatasetName");

        String visible_img_img_classfication = request.getParameter("isible_light_img");
        String visible_img_target_detection = request.getParameter("isible_light_detection");
        String visible_img_target_trace = request.getParameter("isible_light_tracking");
        String infrared_ray_img_classfication = request.getParameter("red_outer_img");
        String infrared_ray_target_detection = request.getParameter("red_outer_detection");
        String infrared_ray_target_trace = request.getParameter("red_outer_tracking");
        String text_sen_analysis = request.getParameter("text_analysis");
        String text_entity_recognition = request.getParameter("text_named_entity");
        String asr_asr_recognition = request.getParameter("voicle_recognition");




        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(username, password, api);
        log.info("登录信息：{}", heads);



        //3.文本-情感分析
        if(StrUtil.equals(text_sen_analysis,MethodType.Text_Sen_Analysis)) {
            String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String Text_Sen_Analysis_schemeName = null ;
            if(StrUtil.isNotEmpty(reDdatasetName)){
                Text_Sen_Analysis_schemeName = reDdatasetName ;
            }else {
                Text_Sen_Analysis_schemeName = "文本-情感分析";
            }
            log.info("数据集上传运行：{}", Text_Sen_Analysis_schemeName);
            dataSetUploadService.doScheme(MethodType.Text_Sen_Analysis, api, Text_Sen_Analysis_JsonFileName, Text_Sen_Analysis_schemeName, uploadfilePath, heads);
        }


        //3.文本-命名实体识别
        if(StrUtil.equals(text_entity_recognition,MethodType.Text_Entity_Recognition)) {
            String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String Text_Sen_Analysis_schemeName = null ;
            if(StrUtil.isNotEmpty(reDdatasetName)){
                Text_Sen_Analysis_schemeName = reDdatasetName ;
            }else {
                Text_Sen_Analysis_schemeName = "文本-命名实体识别";
            }
            log.info("数据集上传运行：{}", Text_Sen_Analysis_schemeName);
            dataSetUploadService.doScheme(MethodType.Text_Entity_Recognition, api, Text_Sen_Analysis_JsonFileName, Text_Sen_Analysis_schemeName, uploadfilePath, heads);
        }


        //4.语音-语音识别
        if(StrUtil.equals(asr_asr_recognition,MethodType.Asr_Asr_Recognition)) {
            String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String Text_Sen_Analysis_schemeName = null ;
            if(StrUtil.isNotEmpty(reDdatasetName)){
                Text_Sen_Analysis_schemeName = reDdatasetName ;
            }else {
                Text_Sen_Analysis_schemeName = "语音-语音识别";
            }
            log.info("数据集上传运行：{}", Text_Sen_Analysis_schemeName);
            dataSetUploadService.doScheme(MethodType.Asr_Asr_Recognition, api, Text_Sen_Analysis_JsonFileName, Text_Sen_Analysis_schemeName, uploadfilePath, heads);
        }


        //5.红外线-图像分类
        if(StrUtil.equals(infrared_ray_img_classfication,MethodType.Infrared_Ray_Img_Classfication)) {
            String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String Text_Sen_Analysis_schemeName = null ;
            if(StrUtil.isNotEmpty(reDdatasetName)){
                Text_Sen_Analysis_schemeName = reDdatasetName ;
            }else {
                Text_Sen_Analysis_schemeName = "红外线-图像分类";
            }
            log.info("数据集上传运行：{}", Text_Sen_Analysis_schemeName);
            dataSetUploadService.doScheme(MethodType.Infrared_Ray_Img_Classfication, api, Text_Sen_Analysis_JsonFileName, Text_Sen_Analysis_schemeName, uploadfilePath, heads);
        }



        //6.红外线-目标检测
        if(StrUtil.equals(infrared_ray_target_detection,MethodType.Infrared_Ray_Target_Detection)) {
            String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String Text_Sen_Analysis_schemeName = null ;
            if(StrUtil.isNotEmpty(reDdatasetName)){
                Text_Sen_Analysis_schemeName = reDdatasetName ;
            }else {
                Text_Sen_Analysis_schemeName = "红外线-目标检测";
            }
            log.info("数据集上传运行：{}", Text_Sen_Analysis_schemeName);
            dataSetUploadService.doScheme(MethodType.Infrared_Ray_Target_Detection, api, Text_Sen_Analysis_JsonFileName, Text_Sen_Analysis_schemeName, uploadfilePath, heads);
        }



        //7.红外线-目标跟踪
        if(StrUtil.equals(infrared_ray_target_trace,MethodType.Infrared_Ray_Target_trace)) {
            String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String Text_Sen_Analysis_schemeName = null ;
            if(StrUtil.isNotEmpty(reDdatasetName)){
                Text_Sen_Analysis_schemeName = reDdatasetName ;
            }else {
                Text_Sen_Analysis_schemeName = "红外线-目标跟踪";
            }
            log.info("数据集上传运行：{}", Text_Sen_Analysis_schemeName);
            dataSetUploadService.doScheme(MethodType.Infrared_Ray_Target_trace, api, Text_Sen_Analysis_JsonFileName, Text_Sen_Analysis_schemeName, uploadfilePath, heads);
        }



        //8.可见光-图像分类
        if(StrUtil.equals(visible_img_img_classfication,MethodType.Visible_Img_Img_Classfication)) {
            String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String Text_Sen_Analysis_schemeName = null ;
            if(StrUtil.isNotEmpty(reDdatasetName)){
                Text_Sen_Analysis_schemeName = reDdatasetName ;
            }else {
                Text_Sen_Analysis_schemeName = "可见光-图像分类";
            }
            log.info("数据集上传运行：{}", Text_Sen_Analysis_schemeName);
            dataSetUploadService.doScheme(MethodType.Visible_Img_Img_Classfication, api, Text_Sen_Analysis_JsonFileName, Text_Sen_Analysis_schemeName, uploadfilePath, heads);
        }



        //9.可见光-目标检测
        if(StrUtil.equals(visible_img_target_detection,MethodType.Visible_Img_Target_Detection)) {
            String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String Text_Sen_Analysis_schemeName = null ;
            if(StrUtil.isNotEmpty(reDdatasetName)){
                Text_Sen_Analysis_schemeName = reDdatasetName ;
            }else {
                Text_Sen_Analysis_schemeName = "可见光-目标检测";
            }
            log.info("数据集上传运行：{}", Text_Sen_Analysis_schemeName);
            dataSetUploadService.doScheme(MethodType.Visible_Img_Target_Detection, api, Text_Sen_Analysis_JsonFileName, Text_Sen_Analysis_schemeName, uploadfilePath, heads);
        }


        //9.可见光-目标跟踪
        if(StrUtil.equals(visible_img_target_trace,MethodType.Visible_Img_Target_Trace)) {
            String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String Text_Sen_Analysis_schemeName = null ;
            if(StrUtil.isNotEmpty(reDdatasetName)){
                Text_Sen_Analysis_schemeName = reDdatasetName ;
            }else {
                Text_Sen_Analysis_schemeName = "可见光-目标跟踪";
            }
            log.info("数据集上传运行：{}", Text_Sen_Analysis_schemeName);
            dataSetUploadService.doScheme(MethodType.Visible_Img_Target_Trace, api, Text_Sen_Analysis_JsonFileName, Text_Sen_Analysis_schemeName, uploadfilePath, heads);
        }


       /**
        //删除本地刚上传文件：
        File f = new File(uploadfilePath.toURI());
        if (f.delete()){
            System.out.println("filepath--->"+uploadfilePath.toURI());
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }
        **/



        return null;
    }



        /**
        //3. 调用upload接口
        String uploadURL = api + "/api/data-set/upload";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("file", FileUtil.file(file));
        paramMap.put("type", MethodType.Text_Sen_Analysis);


        Map<String, Object> reportmap = new HashMap<>();
        String uploadFileResult = HttpUtil.post(uploadURL, paramMap);
        reportmap.put("uploadFile", uploadFileResult);


        JSONObject upload_id = JSONUtil.toBean(uploadFileResult.toString(), JSONObject.class);
        String file_id = String.valueOf(upload_id.get("id"));
        reportmap.put("file_id", file_id);

        System.out.println("file_id--->"+file_id);


        //4. 调用status接口
        // 拼接请求链接
        String requestUrl = StrUtil.format("{}/api/data-set/upload/{}/status", api, file_id);
        // 发送请求
        try {
            Thread.sleep(9000);

            String returnMsg = HttpUtil.get(requestUrl);
            Map<String,String> replace_map = new HashMap<>();
            System.out.println("returnMsg---->"+returnMsg);


            //5.调用data-set接口
            replace_map.put("name", "情感分析上传11xxxxxxxx");
            replace_map.put("type", MethodType.Text_Sen_Analysis);
            replace_map.put("file_id", file_id);
            replace_map.put("descript", "情感分析上传11xxxxxxxx");
            String json_name = "datasetupload/datasetModel.json";
            StringBuffer bodys = ReadJson.getReportContent(json_name,replace_map);


            String dataset_url = api + "/api/data-set";
            System.out.println("bodys---->" + bodys.toString());
            System.out.println("dataset_url---->" + dataset_url);
            System.out.println("heads--->"+heads);

            HttpResponse result_response = HttpRequest.post(dataset_url)
                    .headerMap(heads, false)
                    .body(bodys.toString())
                    .timeout(5 * 60 * 1000)
                    .execute();


            reportmap.put("uploadFromResult", result_response);
            reportmap.put("code",0);
            reportmap.put("message","上传成功!");


            System.out.println("result_response---->" + result_response);


        } catch (Exception e) {
            e.printStackTrace();

        }


         **/






}
