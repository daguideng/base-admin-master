package cn.huanzi.qch.baseadmin.hjj.controller;

import cn.huanzi.qch.baseadmin.hjj.entity.MethodType;
import cn.huanzi.qch.baseadmin.hjj.service.DataSetUploadService;
import cn.huanzi.qch.baseadmin.hjj.service.FileDownloadServlet;
import cn.huanzi.qch.baseadmin.hjj.service.UploadModelService;
import cn.huanzi.qch.baseadmin.hjj.util.FolderFileManagement;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.server.SimpleServer;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 登录用户访问
 */
@RestController
@RequestMapping("/upload")

public class HjjUpload {

    Log log = LogFactory.get();


    @Resource
    private DataSetUploadService dataSetUploadService;

    @Resource
    private UploadModelService uploadModelService;


    @Resource
    private FileDownloadServlet fileDownloadServlet;


    @GetMapping("/method")
    public ModelAndView UpalodMethodMenu() {
        return new ModelAndView("upload/method");
    }


    public Map<String, String> loginServer(String username, String password, String api) {

        log.info("登录账号:{},密码:{}", username, password);

        // 添加请求头信息
        Map<String, String> heads = new HashMap<>();

        //1.如果不用最新版登录则用最旧版登录
        try {
            this.loginServer_2(username, password, api);
        } catch (Exception e) {

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
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
            }

            log.info("账户信息为:{}", loginJson);

            // api = "http://10.10.12.13:5000";
            String urlPath = api + "/api/auth/token";

            //1.登录
            HttpResponse result = HttpRequest.post(urlPath).body(loginJson).execute();
            log.info("登录结果为:{}", result.body());
            Map<String, Object> map = JSONUtil.parseObj(result.body());
            String access_token = (String) map.get("access_token");
            int expires = (int) map.get("expires");
            BigDecimal expires_timestamp = (BigDecimal) map.get("expires_timestamp");
            String id = (String) map.get("id");
            String type = (String) map.get("type");


            // 使用json发送请求，下面的是必须的
            // heads.put("Content-Type", "application/json;charset=UTF-8");
            heads.put("Authorization", type + " " + access_token);
            //  heads.put("Content-Type", "application/json");


        }


        return heads;

    }


    @GetMapping("/login2")
    public String loginServer_2(String user_name, String password, String api) {
        log.info("登录账号:{},密码:{}", user_name, password);

        /*
        api = "http://172.26.193.10:31674/";
        user_name = "dengdagui";
        password = "abcd1234";
         */

        //2.图像分类：
        JSONObject ImgClassficationMap = new JSONObject();

        ImgClassficationMap.set("user_name", user_name);
        ImgClassficationMap.set("password", password);

        log.info("账户信息为:{}", ImgClassficationMap);

        // api = "http://10.10.12.13:5000";
        String urlPath = api + "/api/login";

        //1.登录

        HttpResponse result = HttpRequest.post(urlPath).body(ImgClassficationMap.toString()).execute();

        log.info("登录结果为:{}", result.body());


        return result.body();


    }


    /**
     * 获取文件第三种方式
     */
    public File getFile(MultipartFile file) {

        Long begin = System.currentTimeMillis();

        File uploadfile = null;
        String filePath = System.getProperty("user.dir") + "/temp/";
        log.info("filePath:{}", filePath);

        if (!file.isEmpty()) {
            try {
                // 保存文件
                String fileName = file.getOriginalFilename();

                uploadfile = new File(filePath + fileName);
                if (!uploadfile.getParentFile().exists()) {
                    uploadfile.getParentFile().mkdirs();
                }
                file.transferTo(uploadfile);
                return uploadfile;
            } catch (IOException e) {
                log.info("上传失败");
                e.printStackTrace();

            }
        }

        long end = System.currentTimeMillis();
        log.info("BufferedOutputStream生成文件耗时:{} 毫秒", (end - begin));

        return uploadfile;
    }


    /**
     * 获取文件相关
     */
    public File getFileX(MultipartFile file) throws IOException {


        //文件上传前的名称
        String fileName = file.getOriginalFilename();
        File uploadfile = new File(fileName);
        OutputStream out = null;

        FileOutputStream outSTr = null;
        BufferedOutputStream buff = null;

        try {
            //获取文件流，以文件流的方式输出到新文件
            // InputStream in = multipartFile.getInputStream();
            long begin = System.currentTimeMillis();
            outSTr = new FileOutputStream(uploadfile);
            buff = new BufferedOutputStream(outSTr);
            byte[] ss = file.getBytes();

            for (int i = 0; i < ss.length; i++) {
                buff.write(ss[i]);
            }
            buff.flush();

            long end = System.currentTimeMillis();
            log.info("BufferedOutputStream生成文件耗时:{} 毫秒", (end - begin));

            /**
             out = new FileOutputStream(uploadfile);
             byte[] ss = file.getBytes();
             for (int i = 0; i < ss.length; i++) {
             out.write(ss[i]);
             }
             **/
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outSTr != null) {
                    outSTr.close();
                }
                if (buff != null) {
                    buff.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();

            }

        }

        return uploadfile;
    }


    /**
     * 数据集上传
     */
    @PostMapping("/uploaddataset")
    public Map<String, Object> uploadDataset(@RequestParam("file") MultipartFile file, String filePath, HttpServletRequest request, Map<String, String> map) throws Exception {


        /*
        //1.获取上传文件：
        File uploadfilePath = this.getFile(file);
        //  File file = new File(uploadfilePath);

        //得到上传文件名:
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);
         */

        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        String time = sdf.format(System.currentTimeMillis());


        //公共变量
        File uploadfilePath = null;
        String username = null;
        String password = null;
        String api = null;
        String type = null;
        String reDdatasetName = null;
        String fileName = null;


        //单个数据集上传变量：
        String visible_img_img_classfication = null;
        String visible_img_target_detection = null;
        String visible_img_target_trace = null;
        String infrared_ray_img_classfication = null;
        String infrared_ray_target_detection = null;
        String infrared_ray_target_trace = null;
        //雷达-信号分类
        String radar_signal_classification = null;
        String text_sen_analysis = null;
        String text_entity_recognition = null;
        String asr_asr_recognition = null;

        //姿态估计
        String visible_img_pose_estimation = null;
        //行人重识别
        String visible_img_img_reid = null;
        //语义分割
        String visible_img_img_segmentation = null;
        //视频-目标检测
        String video_target_detection = null;
        //视频-目标跟踪
        String video_target_trace = null;
        //结构化数据分类
        String structured_data_classification = null;
        //结构化数据回归
        String structured_data_regression = null;
        //结构化数据聚类
        String structured_data_cluster = null;


        //得到上传文件名:   对一键化测试进行兼容则进行判断
        if (null == filePath) {  //单独算法上传
            //1.获取上传文件：
            uploadfilePath = this.getFile(file);
            File temp = new File(uploadfilePath.toURI());
            log.info("生成临时文件路径为:{}", temp.getAbsolutePath());
            fileName = file.getOriginalFilename();

            username = request.getParameter("username_dataset");
            password = request.getParameter("password_dataset");
            api = request.getParameter("api_dataset");
            reDdatasetName = request.getParameter("reDdatasetName");
            // type = request.getParameter("type");
            // log.info("上传类型:{}", type);
            log.info("重命名value:{}", reDdatasetName);

            //基本信息：
            visible_img_img_classfication = request.getParameter("visible_img_img_classfication_dataset");
            visible_img_target_detection = request.getParameter("visible_img_target_detection_dataset");
            visible_img_target_trace = request.getParameter("visible_img_target_trace_dataset");
            infrared_ray_img_classfication = request.getParameter("infrared_ray_img_classfication_dataset");
            infrared_ray_target_detection = request.getParameter("infrared_ray_target_detection_dataset");
            infrared_ray_target_trace = request.getParameter("infrared_ray_target_trace_dataset");
            radar_signal_classification = request.getParameter("radar_signal_classification_dataset");
            text_sen_analysis = request.getParameter("text_sen_analysis_dataset");
            text_entity_recognition = request.getParameter("text_entity_recognition_dataset");
            asr_asr_recognition = request.getParameter("asr_asr_recognition_dataset");
            //行人重识别
            visible_img_img_reid = request.getParameter("visible_img_img_reid_dataset");
            //语义分割
            visible_img_img_segmentation = request.getParameter("visible_img_img_segmentation_dataset");
            //视频-目标检测
            video_target_detection = request.getParameter("video_target_detection_dataset");
            //视频-目标跟踪
            video_target_trace = request.getParameter("video_target_trace_dataset");
            //结构化数据分类
            structured_data_classification = request.getParameter("structured_data_classification_dataset");
            //结构化数据回归
            structured_data_regression = request.getParameter("structured_data_regression_dataset");
            //结构化数据聚类
            structured_data_cluster = request.getParameter("structured_data_cluster_dataset");
            //姿态估计
            visible_img_pose_estimation = request.getParameter("visible_img_pose_estimation_dataset");


        } else {   // 一键化
            uploadfilePath = new File(filePath);
            fileName = uploadfilePath.getName();

            username = map.get("username");
            password = map.get("password");
            api = map.get("api");
            type = map.get("type");

            if (StrUtil.equals(type, MethodType.Radar_Signal_Classification)) {   //可见光图像-姿态估计
                radar_signal_classification = MethodType.Radar_Signal_Classification;
            } else if (StrUtil.equals(type, MethodType.Text_Sen_Analysis)) {   //文本-情感分析    tex   text.sen_analysis
                text_sen_analysis = MethodType.Text_Sen_Analysis;  //text.sen_analysis    text.sen_analysis
            } else if (StrUtil.equals(type, MethodType.Text_Entity_Recognition)) {  //文本-命名实体识别
                text_entity_recognition = MethodType.Text_Entity_Recognition;
            } else if (StrUtil.equals(type, MethodType.Asr_Asr_Recognition)) {   //语音-语音识别
                asr_asr_recognition = MethodType.Asr_Asr_Recognition;
            } else if (StrUtil.equals(type, MethodType.Infrared_Ray_Img_Classfication)) {   //红外线-图像分类
                infrared_ray_img_classfication = MethodType.Infrared_Ray_Img_Classfication;
            } else if (StrUtil.equals(type, MethodType.Infrared_Ray_Target_Detection)) {   //红外线-目标检测
                infrared_ray_target_detection = MethodType.Infrared_Ray_Target_Detection;
            } else if (StrUtil.equals(type, MethodType.Infrared_Ray_Target_trace)) {   //红外线-目标跟踪
                infrared_ray_target_trace = MethodType.Infrared_Ray_Target_trace;
            } else if (StrUtil.equals(type, MethodType.Visible_Img_Img_Classfication)) {   //可见光-图像分类
                visible_img_img_classfication = MethodType.Visible_Img_Img_Classfication;
            } else if (StrUtil.equals(type, MethodType.Visible_Img_Target_Detection)) {  //可见光-目标检测
                visible_img_target_detection = MethodType.Visible_Img_Target_Detection;
            } else if (StrUtil.equals(type, MethodType.Visible_Img_Target_Trace)) {    //可见光-目标跟踪
                visible_img_target_trace = MethodType.Visible_Img_Target_Trace;
            } else if (StrUtil.equals(type, MethodType.Visible_Img_Pose_Estimation)) {    //可见光-姿态估计
                visible_img_pose_estimation = MethodType.Visible_Img_Pose_Estimation;
            } else if (StrUtil.equals(type, MethodType.Visible_Img_Img_Reid)) {    //可见光-行人重识别
                visible_img_img_reid = MethodType.Visible_Img_Img_Reid;
            } else if (StrUtil.equals(type, MethodType.Visible_Img_Img_Segmentation)) {    //可见光-语义分割
                visible_img_img_segmentation = MethodType.Visible_Img_Img_Segmentation;
            } else if (StrUtil.equals(type, MethodType.Video_Detection)) {    //视频-目标检测
                video_target_detection = MethodType.Video_Detection;
            } else if (StrUtil.equals(type, MethodType.Video_Target_Trace)) {    //视频-目标跟踪
                video_target_trace = MethodType.Video_Target_Trace;
            } else if (StrUtil.equals(type, MethodType.Structured_Data_Classification)) {    //结构化数据-分类
                structured_data_classification = MethodType.Structured_Data_Classification;
            } else if (StrUtil.equals(type, MethodType.Structured_Data_Regression)) {    //结构化数据-回归
                structured_data_regression = MethodType.Structured_Data_Regression;
            }

            log.info("上传类型:{}", type);
            reDdatasetName = null;
            log.info("重命名value:{}", reDdatasetName);
        }
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);


        //根据上传文件名对数据集重命名，当文件名含有multiple或binary或single时重定义名称：
        String type_name = null;
        if (fileName.contains("multiple")) {

            type_name = "-multiple";
        } else if (fileName.contains("binary")) {
            type_name = "-binary";
        } else if (fileName.contains("single")) {
            type_name = "-single";
        } else {
            type_name = "";
        }


        //如果未登录则登录,只登录一次：
        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(username, password, api);
        log.info("登录信息：{}", heads);
        Map<String, Object> result = new HashMap<>();


        //1.可见光-图像分类
        if (StrUtil.equals(visible_img_img_classfication, MethodType.Visible_Img_Img_Classfication) || StrUtil.equals(MethodType.Visible_Img_Img_Classfication,type)) { //可见光-图像分类
            //   String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "可见光-图像分类" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Visible_Img_Img_Classfication, heads);
                result.put("code", 0);
                result.put("msg", "可见光-图像分类" + " 上传数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(visible_img_target_detection, MethodType.Visible_Img_Target_Detection)||StrUtil.equals(MethodType.Visible_Img_Target_Detection,type)) { //可见光-目标检测
            //  String Text_Sen_Analysis_JsonFileName = "datasetupload/datasetModel.json";
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "可见光-目标检测" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);

            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Visible_Img_Target_Detection, heads);
                result.put("code", 0);
                result.put("msg", "可见光-目标检测" + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(visible_img_target_trace, MethodType.Visible_Img_Target_Trace)||StrUtil.equals(MethodType.Visible_Img_Target_Trace,type)) {  //可见光-目标跟踪
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "可见光-目标跟踪" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Visible_Img_Target_Trace, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }
        } else if (StrUtil.equals(visible_img_pose_estimation, MethodType.Visible_Img_Pose_Estimation)||StrUtil.equals(MethodType.Visible_Img_Pose_Estimation,type)){     //可见光-姿态估计
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "可见光-姿态估计" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Visible_Img_Pose_Estimation, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

      }else if (StrUtil.equals(visible_img_img_reid, MethodType.Visible_Img_Img_Reid)||StrUtil.equals(MethodType.Visible_Img_Img_Reid,type)) { //可见光-行人重识别
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "可见光-行人重识别" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Visible_Img_Img_Reid, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }
        } else if (StrUtil.equals(visible_img_img_segmentation, MethodType.Visible_Img_Img_Segmentation)||StrUtil.equals(MethodType.Visible_Img_Img_Segmentation,type)) { //可见光-语义分割
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "可见光-语义分割" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Visible_Img_Img_Segmentation, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(infrared_ray_img_classfication, MethodType.Infrared_Ray_Img_Classfication)||StrUtil.equals(MethodType.Infrared_Ray_Img_Classfication,type)) { //红外线-图像分类
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "红外线-图像分类" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);

            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Infrared_Ray_Img_Classfication, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(infrared_ray_target_detection, MethodType.Infrared_Ray_Target_Detection)||StrUtil.equals(MethodType.Infrared_Ray_Target_Detection,type)) { //红外线-目标检测
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "红外线-目标检测" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);

            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Infrared_Ray_Target_Detection, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(infrared_ray_target_trace, MethodType.Infrared_Ray_Target_trace)||StrUtil.equals(MethodType.Infrared_Ray_Target_trace,type)) { //红外线-目标跟踪
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "红外线-目标跟踪" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Infrared_Ray_Target_trace, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(video_target_detection, MethodType.Video_Detection)||StrUtil.equals(MethodType.Video_Detection,type)) {  //视频-目标检测
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "视频-目标检测" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Video_Detection, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(video_target_trace, MethodType.Video_Target_Trace)||StrUtil.equals(MethodType.Video_Target_Trace,type)) { //视频-目标跟踪
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "视频-目标跟踪" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Video_Target_Trace, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if(StrUtil.equals(radar_signal_classification, MethodType.Radar_Signal_Classification)||StrUtil.equals(MethodType.Radar_Signal_Classification,type)) {   //雷达-信号分类
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "雷达-信号分类" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Radar_Signal_Classification, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }


        }else if (StrUtil.equals(text_sen_analysis, MethodType.Text_Sen_Analysis)||StrUtil.equals(MethodType.Text_Sen_Analysis,type)) { //文本-情感分析
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "文本-情感分析" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);

            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Text_Sen_Analysis, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }
        } else if (StrUtil.equals(text_entity_recognition, MethodType.Text_Entity_Recognition)||StrUtil.equals(MethodType.Text_Entity_Recognition,type)) { //文本-命名实体识别
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "文本-命名实体识别" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Text_Entity_Recognition, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(asr_asr_recognition, MethodType.Asr_Asr_Recognition)||StrUtil.equals(MethodType.Asr_Asr_Recognition,type)) { //语音-语音识别
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "语音-语音识别" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);

            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Asr_Asr_Recognition, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }


        } else if (StrUtil.equals(structured_data_classification, MethodType.Structured_Data_Classification)||StrUtil.equals(MethodType.Structured_Data_Classification,type)) { //结构化数据-分类
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "结构化数据-分类" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);
            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Structured_Data_Classification, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(structured_data_regression, MethodType.Structured_Data_Regression)||StrUtil.equals(MethodType.Structured_Data_Regression,type)) { //结构化数据-回归
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "结构化数据-回归" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);

            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Structured_Data_Regression, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else if (StrUtil.equals(structured_data_cluster, MethodType.Structured_Data_Cluster)||StrUtil.equals(MethodType.Structured_Data_Cluster,type)) { //结构化数据-聚类
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "结构化数据-聚类" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);

            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Structured_Data_Cluster, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        }else if (StrUtil.equals(visible_img_pose_estimation, MethodType.Visible_Img_Pose_Estimation)||StrUtil.equals(MethodType.Visible_Img_Pose_Estimation,type)) { //可见光-姿态估计
            String rename_schemeName = null;
            if (StrUtil.isNotEmpty(reDdatasetName)) {
                rename_schemeName = reDdatasetName;
            } else {
                rename_schemeName = "可见光-姿态估计" + type_name + "-" + time;
            }
            log.info("数据集上传运行：{}", rename_schemeName);

            try {
                dataSetUploadService.toDoUpload(api, uploadfilePath, rename_schemeName, MethodType.Visible_Img_Pose_Estimation, heads);
                result.put("code", 0);
                result.put("msg", rename_schemeName + " 数据集成功");
            } catch (Exception e) {
                result.put("code", 100);
                result.put("msg", rename_schemeName + " 数据集失败");
                e.printStackTrace();
            }

        } else {
            result.put("code", 100);
            result.put("msg", "数据集上传失败");
        }


        return result;


        //删除本地刚上传文件：

        /*
        if (map.size() == 0) {     //只针对单个上传模型与数据集才删除
            //删除本地刚上传文件：
            File f = new File(uploadfilePath.toURI());
            if (f.delete()) {
                log.info("上传文件{},删除成功!", uploadfilePath.toURI());
            } else {
                log.info("上传文件{},删除失败!", uploadfilePath.toURI());
            }

        }
         */


    }


    /**
     * 算法上传修改成统一接口:
     */
    //图像分类：
    @PostMapping("/uploadunify")
    public Map<String, Object> uploadUnify(@RequestParam("file") MultipartFile file, String filePath, HttpServletRequest request, Map<String, String> map) throws Exception {

        log.info("算法上传修改成统一接口");

        File uploadfilePath = null;
        String username = null;
        String password = null;
        String api = null;
        String type = null;
        String reUplodName = null;
        String fileName = null;

        //单个算法上传：
        String visible_img_img_classfication = request.getParameter("visible_img_img_classfication");
        //目标检测
        String visible_img_target_detection = request.getParameter("visible_img_target_detection");
        //目标跟踪
        String visible_img_target_trace = request.getParameter("visible_img_target_trace");
        //行人重识别
        String visible_img_img_reid = request.getParameter("visible_img_img_reid");
        //语义分割
        String visible_img_img_segmentation = request.getParameter("visible_img_img_segmentation");
        //红外线-图像分类
        String infrared_ray_img_classfication = request.getParameter("infrared_ray_img_classfication");
        //红外线-目标检测
        String infrared_ray_target_detection = request.getParameter("infrared_ray_target_detection");
        //可见光-图像分类、姿态估计
        String visible_img_pose_estimation = request.getParameter("visible_img_pose_estimation");
        //红外线-目标跟踪
        String infrared_ray_target_trace = request.getParameter("infrared_ray_target_trace");
        //视频-目标检测
        String video_target_detection = request.getParameter("video_target_detection");
        //视频-目标跟踪
        String video_target_trace = request.getParameter("video_target_trace");
        //雷达 信号分类
        String radar_signal_classification = request.getParameter("radar_signal_classification");

        //文本-情感分析
        String text_sen_analysis = request.getParameter("text_sen_analysis");
        //文本-命名实体识别
        String text_entity_recognition = request.getParameter("text_entity_recognition");
        //语音-语音识别
        String asr_asr_recognition = request.getParameter("asr_asr_recognition");
        //结构化数据-分类
        String structured_data_classification = request.getParameter("structured_data_classification");
        //结构化数据-回归
        String structured_data_regression = request.getParameter("structured_data_regression");
        //结构化数据-聚类
        String structured_data_cluster = request.getParameter("structured_data_cluster");



        //得到上传文件名:   对一键化测试进行兼容则进行判断
        if (null == filePath) {  //单独算法上传
            //1.获取上传文件：
            uploadfilePath = this.getFile(file);
            File temp = new File(uploadfilePath.toURI());
            log.info("生成临时文件路径为:{}", temp.getAbsolutePath());
            fileName = file.getOriginalFilename();

            username = request.getParameter("username_model");
            password = request.getParameter("password_model");
            api = request.getParameter("api_model");


            if (StrUtil.isNotEmpty(radar_signal_classification)) {  //雷达-信号分类
                type = radar_signal_classification;
            } else if (StrUtil.isNotEmpty(visible_img_img_classfication)) { //可见光-图像分类
                type = visible_img_img_classfication;
            } else if (StrUtil.isNotEmpty(visible_img_target_detection)) { //目标检测
                type = visible_img_target_detection;
            } else if (StrUtil.isNotEmpty(visible_img_target_trace)) { //目标跟踪
                type = visible_img_target_trace;
            } else if (StrUtil.isNotEmpty(visible_img_img_reid)) { //行人重识别
                type = visible_img_img_reid;
            } else if (StrUtil.isNotEmpty(visible_img_img_segmentation)) { //语义分割
                type = visible_img_img_segmentation;
            } else if (StrUtil.isNotEmpty(infrared_ray_img_classfication)) { //红外线-图像分类
                type = infrared_ray_img_classfication;
            } else if (StrUtil.isNotEmpty(infrared_ray_target_detection)) { //红外线-目标检测
                type = infrared_ray_target_detection;
            } else if (StrUtil.isNotEmpty(infrared_ray_target_trace)) { //红外线-目标跟踪
                type = infrared_ray_target_trace;
            } else if (StrUtil.isNotEmpty(video_target_detection)) { //视频-目标检测
                type = video_target_detection;
            } else if (StrUtil.isNotEmpty(video_target_trace)) { //视频-目标跟踪
                type = video_target_trace;
            } else if (StrUtil.isNotEmpty(text_sen_analysis)) {//文本-情感分析
                type = text_sen_analysis;
            } else if (StrUtil.isNotEmpty(text_entity_recognition)) { //文本-命名实体识别
                type = text_entity_recognition;
            } else if (StrUtil.isNotEmpty(asr_asr_recognition)) { //语音-语音识别
                type = asr_asr_recognition;
            } else if (StrUtil.isNotEmpty(structured_data_classification)) { //结构化数据-分类
                type = structured_data_classification;
            } else if (StrUtil.isNotEmpty(structured_data_regression)) {  //结构化数据-回归
                type = structured_data_regression;
            } else if (StrUtil.isNotEmpty(structured_data_cluster)) { //结构化数据-聚类
                type = structured_data_cluster;
            } else if(StrUtil.isNotEmpty(visible_img_pose_estimation)) {  //可见光-姿态估计
                type = visible_img_pose_estimation;
            }


            // type = request.getParameter("type");
            log.info("上传类型:{}", type);
            reUplodName = request.getParameter("reModelName");
            log.info("重命名value:{}", reUplodName);

        } else {   // 一键化
            uploadfilePath = new File(filePath);
            fileName = uploadfilePath.getName();

            username = map.get("username");
            password = map.get("password");
            api = map.get("api");
            type = map.get("type");
            log.info("上传类型:{}", type);
            reUplodName = null;
            log.info("重命名value:{}", reUplodName);
        }
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index);
        log.info("上传文件名:{}", fileName);


        //2.获取登录相关认证头：
        Map<String, String> heads = this.loginServer(username, password, api);

        Map<String, Object> result = new HashMap<>();

        log.info("type:{}", type);

        //3.图像分类： img_classfication (可见光-图像分类、红外图线-图像分类 都有相同结尾)
        if (StrUtil.equals(MethodType.Visible_Img_Img_Classfication, visible_img_img_classfication)||StrUtil.equals(MethodType.Visible_Img_Img_Classfication, type)) {  //可见光-图像分类
            log.info("可见光-图像分类上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Visible_Img_Img_Classfication);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", "可见光-图像分类算法上传成功");
            } catch (Exception e) {
                log.error("可见光-图像分类上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Visible_Img_Target_Detection, visible_img_target_detection)||StrUtil.equals(MethodType.Visible_Img_Target_Detection, type)) {  //可见光-目标检测
            log.info("可见光-目标检测上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Visible_Img_Target_Detection);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("可见光-目标检测上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }


        }

        if (StrUtil.equals(MethodType.Visible_Img_Target_Trace, visible_img_target_trace)||StrUtil.equals(MethodType.Visible_Img_Target_Trace, type)) { //可见光-目标跟踪
            log.info("可见光-目标跟踪上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Visible_Img_Target_Trace);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("可见光-目标跟踪上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        //可见光-姿态估计
        if (StrUtil.equals(MethodType.Visible_Img_Pose_Estimation, visible_img_pose_estimation)||StrUtil.equals(MethodType.Visible_Img_Pose_Estimation, type)) { //可见光-姿态估计
            log.info("可见光-姿态估计上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Visible_Img_Pose_Estimation);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("可见光-姿态估计上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Visible_Img_Img_Reid, visible_img_img_reid)||StrUtil.equals(MethodType.Visible_Img_Img_Reid, type)) {  //可见光-行人重识别
            log.info("可见光-行人重识别上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Visible_Img_Img_Reid);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("可见光-行人重识别上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }


        }

        if (StrUtil.equals(MethodType.Visible_Img_Img_Segmentation, visible_img_img_segmentation)||StrUtil.equals(MethodType.Visible_Img_Img_Segmentation, type)) { //可见光-语义分割
            log.info("可见光-语义分割上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Visible_Img_Img_Segmentation);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("可见光-语义分割上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }

        if (StrUtil.equals(MethodType.Infrared_Ray_Img_Classfication, infrared_ray_img_classfication)||StrUtil.equals(MethodType.Infrared_Ray_Img_Classfication, type)) { //红外图线-图像分类
            log.info("红外图线-图像分类上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Infrared_Ray_Img_Classfication);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("红外图线-图像分类上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }

        if (StrUtil.equals(MethodType.Infrared_Ray_Target_Detection, infrared_ray_target_detection)||StrUtil.equals(MethodType.Infrared_Ray_Target_Detection, type)) { //红外图像-目标检测
            log.info("红外图像-目标检测上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Infrared_Ray_Target_Detection);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("红外图像-目标检测上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Infrared_Ray_Target_trace, infrared_ray_target_trace)||StrUtil.equals(MethodType.Infrared_Ray_Target_trace, type)) { //红外图像-目标跟踪
            log.info("红外图像-目标跟踪上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Infrared_Ray_Target_trace);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("红外图像-目标跟踪上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Video_Detection, video_target_detection)||StrUtil.equals(MethodType.Video_Detection, type)) { //视频-目标检测
            log.info("视频-目标检测上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Video_Detection);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("视频-目标检测上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Video_Target_Trace, video_target_trace)||StrUtil.equals(MethodType.Video_Target_Trace, type)) { //视频-目标跟踪
            log.info("视频-目标跟踪上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Video_Target_Trace);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("视频-目标跟踪上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }

        //雷达 信号分类
        if (StrUtil.equals(MethodType.Radar_Signal_Classification, radar_signal_classification)||StrUtil.equals(MethodType.Radar_Signal_Classification, type)) { //雷达 信号分类
            log.info("雷达 信号分类上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Radar_Signal_Classification);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("雷达 信号分类上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Text_Sen_Analysis, text_sen_analysis)||StrUtil.equals(MethodType.Text_Sen_Analysis, type)) { //文本-情感分析
            log.info("文本-情感分析上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            // method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Text_Sen_Analysis);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("文本-情感分析上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Text_Entity_Recognition, text_entity_recognition)||StrUtil.equals(MethodType.Text_Entity_Recognition, type)) { //文本-命名实体识别
            log.info("文本-命名实体识别上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            //  method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Text_Entity_Recognition);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("文本-命名实体识别上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }

        }


        if (StrUtil.equals(MethodType.Asr_Asr_Recognition, asr_asr_recognition)||StrUtil.equals(MethodType.Asr_Asr_Recognition, type)) { //语音-语音识别
            log.info("语音-语音识别上传");
            List<String> method_type = new ArrayList<>();
            //  method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Asr_Asr_Recognition);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("语音-语音识别上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Structured_Data_Classification, structured_data_classification)||StrUtil.equals(MethodType.Structured_Data_Classification, type)) { //结构化数据-分类    structured_data_classification
            log.info("结构化数据-分类上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Structured_Data_Classification);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("结构化数据-分类上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Structured_Data_Regression, structured_data_regression)||StrUtil.equals(MethodType.Structured_Data_Regression, type)) { //结构化数据-回归    structured_data_regression
            log.info("结构化数据-回归上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Structured_Data_Regression);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("结构化数据-回归上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        if (StrUtil.equals(MethodType.Structured_Data_Cluster, structured_data_cluster)||StrUtil.equals(MethodType.Structured_Data_Cluster, type)) { //结构化数据-聚类    structured_data_cluster
            log.info("结构化数据-聚类上传");
            List<String> method_type = new ArrayList<>();
            method_type.add("black");
            method_type.add("white");
            List<String> type_list = new ArrayList<>();
            type_list.add(MethodType.Structured_Data_Cluster);
            try {
                uploadModelService.uploadMidle(method_type, type_list, reUplodName, fileName, api, uploadfilePath, heads, type);
                result.put("code", 0);
                result.put("msg", fileName + " 算法上传成功");
            } catch (Exception e) {
                log.error("结构化数据-聚类上传失败");
                result.put("code", 100);
                result.put("msg", fileName + " 算法上传失败");
                e.printStackTrace();
            }
        }


        return result;


        //删除本地刚上传文件：

        /*
        if (map.size() == 0) {     //只针对单个上传模型与数据集才删除

            //删除本地刚上传文件：
            File f = new File(uploadfilePath.toURI());
            if (f.delete()) {
                log.info("上传文件{},删除成功!", uploadfilePath.toURI());
            } else {
                log.info("上传文件{},删除失败!", uploadfilePath.toURI());
            }

        }
         */


    }


    Map<String, Object> map = new HashMap<>();

    /***
     * 删除数据集文件而写的接口
     */
    @PostMapping("/deleteFile")
    @ResponseBody
    public Map<String, Object> deleteFile(@RequestParam("file") MultipartFile file, String filePath, HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("删除数据集文件而写的接口");

        // String directoryPath = "/Users/dengdagui/Documents/work/me/项目/项目原型及需求/算法平台/数据集分割测试/图像-目标检测/数据集/multiple_infrared_ray_target_detection_data"; // 替换为实际的目录路径
        String directoryPath = filePath;
        //  int number = 20;

        //1.上传压缩文件   /delete/datasetfile


        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        int number = 20;

        String reDdatasetName = request.getParameter("reDdatasetName");

        if ("".equals(reDdatasetName)) {
            reDdatasetName = "20";
        } else {
            number = Integer.valueOf(reDdatasetName);
        }


        log.info("reDdatasetName:{},number:{}", reDdatasetName, number);

        File uploadfilePath = this.getFile(file);
        File temp = new File(uploadfilePath.toURI());
        log.info("生成临时文件路径为:{}", temp.getAbsolutePath());

        String fileName = file.getOriginalFilename();

        //2.解压文件
        // 使用Hutool进行解压
        String sourceFileDir = temp.getAbsolutePath();
        // 获取当前文件目录
        File tempFile = new File(sourceFileDir);
        String currentDirectory = tempFile.getParent();


        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = currentDateTime.format(formatter);

        String tagerFileDir = currentDirectory + "/" + formattedDateTime + "_unzip";

        // String tagerFileDir = currentDirectory + "/" + "unzip";
        //强制删除文件夹
        FolderFileManagement.forceDelete(tagerFileDir);
        FolderFileManagement.forceDeleteFolder(tagerFileDir);

        ZipUtil.unzip(sourceFileDir, tagerFileDir);
        System.out.println("ZIP file extracted successfully.");

        //3.删除解压后的指定的文件数
        Map<String, Object> info = FolderFileManagement.deleteDataSetFile(tagerFileDir, number);

        //4.删除文件后重新压缩文件
        // 打包操作
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        String zipFilePath = currentDirectory + "/zip/" + fileName + "_" + number + ".zip";
        log.info("zipFilePath:{}", zipFilePath);
        // String tagerFileDir =  currentDirectory + "/" + "zip";
        ZipUtil.zip(tagerFileDir, zipFilePath);
        log.info("tagerFileDir:{},zipFilePath:{}", tagerFileDir, zipFilePath);
        System.out.println("Directory zipped successfully.");


        //5.返回压缩文件路径并下载
        map.clear();
        map.put("path", zipFilePath);

        Map<String, Object> result = new HashMap();
        result.put("code", 0);
        result.put("msg", "将上传文件数为:" + number + " 文件总数为:" + info.get("sumfiles"));

        return result;


    }


    /**
     * 向客户浏览器推送下载文件
     */
    @GetMapping("/downfileXX")
    public ResponseEntity<FileSystemResource> downloadZipFiles(HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        // 创建要下载的ZIP文件资源
        String zipPath = (String) map.get("path");

        log.info("zipPath: {}", zipPath);
        File file = new File(zipPath);
        FileSystemResource resource = new FileSystemResource(file);

        // 设置HTTP头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(file.length());

        // 构建ResponseEntity并返回
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

    }


    /***
     * 为点击错误页的Total可以下载jpllog文件：
     */
    @RequestMapping(value = "/downfile", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void DownloadJplLog(HttpServletResponse res) throws Exception {

        res.setCharacterEncoding("UTF-8");
        // 一般文件
        // res.setContentType("text/html;charset=utf-8");
        //是通用的二进制流类型
        res.setContentType("application/octet-stream;charset=UTF-8");


        String zipPath = (String) map.get("path");

        log.info("zipPath: {}", zipPath);
        File file = new File(zipPath);

        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        res.setHeader("Content-Type", "application/octet-stream");

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;

        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, i);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
