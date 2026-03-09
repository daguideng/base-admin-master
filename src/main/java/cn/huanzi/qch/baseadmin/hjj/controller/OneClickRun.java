package cn.huanzi.qch.baseadmin.hjj.controller;

import cn.huanzi.qch.baseadmin.entity.Model_table;
import cn.huanzi.qch.baseadmin.entity.One_click;
import cn.huanzi.qch.baseadmin.hjj.entity.MethodType;
import cn.huanzi.qch.baseadmin.hjj.entity.ModelResponseEntity;
import cn.huanzi.qch.baseadmin.hjj.entity.TableDataResponse;
import cn.huanzi.qch.baseadmin.hjj.entity.User;
import cn.huanzi.qch.baseadmin.hjj.service.ModelService;
import cn.huanzi.qch.baseadmin.hjj.service.ModelTableService;
import cn.huanzi.qch.baseadmin.hjj.service.OneclickService;
import cn.huanzi.qch.baseadmin.hjj.service.OneclickServiceV2;
import cn.huanzi.qch.baseadmin.hjj.util.ReadJson;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcraft.jsch.KnownHosts;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 一键运行上传模型：
 */

@RestController
@RequestMapping("/oneclick")
public class OneClickRun {

    Log log = LogFactory.get();

    @Resource
    private OneclickService oneclickService;



    @Resource
    private HjjUpload hjjUpload;

    @Resource
    private DataSetIncrease dataSetIncrease;

    @Resource
    private ModelService modelService;

    @Resource
    private ModelTableService modelTableService;

    @Resource
    private Testscheme testscheme;

    @GetMapping("/testing")
    public ModelAndView testsetMethodMenu() {

        return new ModelAndView("oneclick/testing");
    }




    @PostMapping("/run")
    public Map<String, Object> oneRun(@RequestBody One_click one_click, HttpServletRequest request) throws Exception {

        JSONObject jsonObject = new JSONObject(one_click);

        log.info("前端请求body数据为:{}", jsonObject);

        //0.做为回归把基本信息保存:
        oneclickService.save(one_click);


        //01.模型上传
        try {
            this.oneClickModel(one_click);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("一键运行：{}异常", "模型上传");
        }


        //02.数据上传
        try {
            this.oneClickDataSet(one_click);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("一键运行：{}异常", "数据上传");
        }


        //03.数据增广
        try {
            this.oneClickDataSetIncrease(one_click);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("一键运行：{}异常", "数据增广");
        }


        //04.测试方案
        try {
            this.oneClickScheme(one_click);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("一键运行：{}异常", "测试方案");
        }


        //1.设置图像的配置文件:

        //2.目标检测的配置文件：

        //3.文本：（情感分析）的配置文件：

        //4.文本：（命名实体识别）配置文件

        //5.语音：（语音识别）配置文件

        //6.目标跟踪：（可见光图像）配置文件

        //7.目标跟踪：（红外光图像）配置文件


        return null;

    }


    /**
     * 默认显示数据：
     */
    @PostMapping("/showdata")
    public Map<String, Object> oneClickShow() {

        log.info("前端请求body数据为:{}");

        //0.做为回归把基本信息保存:
        One_click one_click = oneclickService.query(1);

        Map<String, Object> mapshow = new HashMap<>();

        Map<String, One_click> mapin = new HashMap<>();
        mapshow.put("code", 0);
        mapshow.put("msg", "sucess");
        mapshow.put("count", 1);

        mapin.put("result", one_click);
        mapshow.put("data", mapin);

        return mapshow;


    }


    /**
     * 模型上传：
     */
    public void oneClickModel(One_click one_click) throws Exception {

        // 公共信息：
        String username = one_click.getUsername().trim();
        String password = one_click.getPassword().trim();
        String api = one_click.getApi().trim();
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("api", api);

        //01.模型上传   （图像分类模型上传）       01
        String img_classfication = one_click.getImg_classfication();
        map.put("type", MethodType.Infrared_Ray_Img_Classfication);
        hjjUpload.uploadUnify(null, img_classfication, null, map);


        //02.模型上传   （目标检测模型上传）
        String target_detectio = one_click.getTarget_detection();
        map.put("type", MethodType.Infrared_Ray_Target_Detection);
        hjjUpload.uploadUnify(null, target_detectio, null, map);


        //03.模型上传   （目标跟踪模型上传）
        String target_trace = one_click.getTarget_trace();
        map.put("type", MethodType.Infrared_Ray_Target_trace);
        hjjUpload.uploadUnify(null, target_trace, null, map);

        //03.模型上传   （文本-情感分析上传）
        String text_sen_analysis = one_click.getText_sen_analysis();
        map.put("type", MethodType.Text_Sen_Analysis);
        hjjUpload.uploadUnify(null, text_sen_analysis, null, map);


        //04.模型上传   （文本-实体识别上传）
        String text_entity_recognition = one_click.getText_entity_recognition();
        map.put("type", MethodType.Text_Entity_Recognition);
        hjjUpload.uploadUnify(null, text_entity_recognition, null, map);


        //05.模型上传   （语音-语音识别上传）
        String asr_recognition = one_click.getAsr_asr_recognition();
        map.put("type", MethodType.Asr_Asr_Recognition);
        hjjUpload.uploadUnify(null, asr_recognition, null, map);


    }


    /**
     * 数据集上传:
     */
    public void oneClickDataSet(One_click one_click) throws Exception {


        // 公共信息：
        String username = one_click.getUsername().trim();
        String password = one_click.getPassword().trim();
        String api = one_click.getApi().trim();
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("api", api);


        //01.数据集上传   （文本-情感分析数据集上传）
        String text_sen_analysis_dataset = one_click.getText_sen_analysis_dataset();
        map.put("type", MethodType.Text_Sen_Analysis);
        hjjUpload.uploadDataset(null, text_sen_analysis_dataset, null, map);

        //02.数据集上传   （文本-命名实体识别数据集上传）
        String text_entity_recognition_dataset = one_click.getText_entity_recognition_dataset();
        map.put("type", MethodType.Text_Entity_Recognition);
        hjjUpload.uploadDataset(null, text_entity_recognition_dataset, null, map);


        //03.数据集上传   （语音-语音识别数据集上传）
        String asr_asr_recognition_dataset = one_click.getAsr_asr_recognition_dataset();
        map.put("type", MethodType.Asr_Asr_Recognition);
        hjjUpload.uploadDataset(null, asr_asr_recognition_dataset, null, map);

        //04.数据集上传   （图像分类数据集上传）
        String img_classfication_dataset = one_click.getImg_classfication_dataset();
        map.put("type", MethodType.Infrared_Ray_Img_Classfication);   //红外线图像分类
        hjjUpload.uploadDataset(null, img_classfication_dataset, null, map);
        map.put("type", MethodType.Visible_Img_Img_Classfication);   //可见光图像分类
        hjjUpload.uploadDataset(null, img_classfication_dataset, null, map);

        //05.数据集上传   （目标检测数据集上传）
        String target_detection_dataset = one_click.getTarget_detection_dataset();
        map.put("type", MethodType.Infrared_Ray_Target_Detection);   //红外线目标检测
        hjjUpload.uploadDataset(null, target_detection_dataset, null, map);
        map.put("type", MethodType.Visible_Img_Target_Detection);   //可见光目标检测
        hjjUpload.uploadDataset(null, target_detection_dataset, null, map);


        //06.数据集上传   （目标跟踪数据集上传）
        String target_trace_dataset = one_click.getTarget_trace_dataset();
        map.put("type", MethodType.Infrared_Ray_Target_trace);   //红外线目标跟踪
        hjjUpload.uploadDataset(null, target_trace_dataset, null, map);
        map.put("type", MethodType.Visible_Img_Target_Trace);   //可见光目标跟踪
        hjjUpload.uploadDataset(null, target_trace_dataset, null, map);


    }


    /**
     * 数据集增方：
     */
    public void oneClickDataSetIncrease(One_click one_click) throws Exception {

        log.info("一键测试数据集开始");
        String path = "oneclickJson/oneclickDataSetIncrease.json";
        String dataSetConfig = ReadJson.getMapValue(path).toString();
        JSONObject jsonConfig = new JSONObject(dataSetConfig);

        Map<String, String> map = new HashMap<>();

        map.put("username", one_click.getUsername().trim());
        map.put("password", one_click.getPassword().trim());
        map.put("api", one_click.getApi().trim());

        //1.可见光图像分类
        String visible_light_img_increase = one_click.getVisible_light_img_increase();
        if (StrUtil.isNotEmpty(visible_light_img_increase)) {
            String title = "可见光图像分类";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(visible_light_img_increase);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Visible_Img_Img_Classfication);
                map.put("radio_type", radio_type);
                dataSetIncrease.datasetRun(null, map);
            }
        }


        //2.红外线图像分类
        String infrared_ray_img_classfication_increase = one_click.getInfrared_ray_img_classfication_increase();
        if (StrUtil.isNotEmpty(infrared_ray_img_classfication_increase)) {
            String title = "红外线图像分类";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(infrared_ray_img_classfication_increase);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Infrared_Ray_Img_Classfication);
                map.put("radio_type", radio_type);
                dataSetIncrease.datasetRun(null, map);
            }
        }

        //3.可见光目标检测
        String visible_light_detection_increase = one_click.getVisible_light_detection_increase();
        if (StrUtil.isNotEmpty(visible_light_detection_increase)) {
            String title = "可见光目标检测";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(visible_light_detection_increase);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Visible_Img_Target_Detection);
                map.put("radio_type", radio_type);
                dataSetIncrease.datasetRun(null, map);
            }
        }

        //4.红外线目标检测
        String infrared_ray_target_detection_increase = one_click.getInfrared_ray_target_detection_increase();
        if (StrUtil.isNotEmpty(infrared_ray_target_detection_increase)) {
            String title = "红外线目标检测";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(infrared_ray_target_detection_increase);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Infrared_Ray_Target_Detection);
                map.put("radio_type", radio_type);
                dataSetIncrease.datasetRun(null, map);
            }
        }

        //5.可见光目标跟踪
        String visible_light_tracking_increase = one_click.getVisible_light_tracking_increase();
        if (StrUtil.isNotEmpty(visible_light_tracking_increase)) {
            String title = "可见光目标跟踪";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(visible_light_tracking_increase);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Visible_Img_Img_Classfication);
                map.put("radio_type", radio_type);
                dataSetIncrease.datasetRun(null, map);
            }
        }

        //6.红外线目标跟踪
        String infrared_ray_target_trace_increase = one_click.getInfrared_ray_target_trace_increase();
        if (StrUtil.isNotEmpty(infrared_ray_target_trace_increase)) {
            String title = "红外线目标跟踪";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(infrared_ray_target_trace_increase);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Infrared_Ray_Img_Classfication);
                map.put("radio_type", radio_type);
                dataSetIncrease.datasetRun(null, map);
            }
        }

        //7.文本-情感分析
        String text_analysis_increase = one_click.getText_analysis_increase();
        if (StrUtil.isNotEmpty(text_analysis_increase)) {
            String title = "文本-情感分析";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(text_analysis_increase);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Text_Sen_Analysis);
                map.put("radio_type", radio_type);
                dataSetIncrease.datasetRun(null, map);
            }
        }

        //8.文本-实体识别
        String text_named_entity_increase = one_click.getText_named_entity_increase();
        if (StrUtil.isNotEmpty(text_named_entity_increase)) {
            String title = "文本-实体识别";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(text_named_entity_increase);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Text_Entity_Recognition);
                map.put("radio_type", radio_type);
                dataSetIncrease.datasetRun(null, map);
            }
        }

        //9.语音-识别
        String asr_asr_recognition_increase = one_click.getAsr_asr_recognition_increase();
        if (StrUtil.isNotEmpty(asr_asr_recognition_increase)) {
            String title = "语音-识别";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(asr_asr_recognition_increase);   //asr_asr_recognition_increase
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Asr_Asr_Recognition);
                map.put("radio_type", radio_type);
                dataSetIncrease.datasetRun(null, map);
            }
        }


    }


    /**
     * 测试方案：
     */
    public void oneClickScheme(One_click one_click) throws Exception {

        log.info("一键测试测试方案开始");
        String path = "oneclickJson/oneclickScheme.json";
        String dataSetConfig = ReadJson.getMapValue(path).toString();
        JSONObject jsonConfig = new JSONObject(dataSetConfig);
        log.info("jsonConfig:{}", jsonConfig);

        Map<String, String> map = new HashMap<>();

        map.put("username", one_click.getUsername().trim());
        map.put("password", one_click.getPassword().trim());
        map.put("api", one_click.getApi().trim());
        map.put("category", one_click.getCategory());
        map.put("isTask", one_click.getIstask());


        //1.可见光图像分类
        String visible_img_img_classfication_scheme = one_click.getVisible_img_img_classfication_scheme();
        if (StrUtil.isNotEmpty(visible_img_img_classfication_scheme)) {
            String title = "可见光图像分类-测试方案";
            log.info("可见光图像分类:{}", visible_img_img_classfication_scheme);
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(visible_img_img_classfication_scheme);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Visible_Img_Img_Classfication);
                map.put("radio_type", radio_type);
                testscheme.testScheme(null, map);
            }
        }


        //2.红外线图像分类
        String infrared_ray_img_classfication_scheme = one_click.getInfrared_ray_img_classfication_scheme();
        if (StrUtil.isNotEmpty(infrared_ray_img_classfication_scheme)) {
            String title = "红外线图像分类";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(infrared_ray_img_classfication_scheme);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Infrared_Ray_Img_Classfication);
                map.put("radio_type", radio_type);
                testscheme.testScheme(null, map);
            }
        }

        //3.可见光目标检测
        String visible_img_target_detection_scheme = one_click.getVisible_img_target_detection_scheme();
        if (StrUtil.isNotEmpty(visible_img_target_detection_scheme)) {
            String title = "可见光目标检测";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(visible_img_target_detection_scheme);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Visible_Img_Target_Detection);
                map.put("radio_type", radio_type);
                testscheme.testScheme(null, map);
            }
        }

        //4.红外线目标检测
        String infrared_ray_target_detection_scheme = one_click.getInfrared_ray_target_detection_scheme();
        if (StrUtil.isNotEmpty(infrared_ray_target_detection_scheme)) {
            String title = "红外线目标检测";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(infrared_ray_target_detection_scheme);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Infrared_Ray_Target_Detection);
                map.put("radio_type", radio_type);
                testscheme.testScheme(null, map);
            }
        }

        //5.可见光目标跟踪
        String visible_img_target_trace_scheme = one_click.getVisible_img_target_trace_scheme();
        if (StrUtil.isNotEmpty(visible_img_target_trace_scheme)) {
            String title = "可见光目标跟踪";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(visible_img_target_trace_scheme);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Visible_Img_Target_Trace);
                map.put("radio_type", radio_type);
                testscheme.testScheme(null, map);
            }
        }

        //6.红外线目标跟踪
        String infrared_ray_target_trace_scheme = one_click.getInfrared_ray_target_trace_scheme();
        if (StrUtil.isNotEmpty(infrared_ray_target_trace_scheme)) {
            String title = "红外线目标跟踪";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(infrared_ray_target_trace_scheme);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Infrared_Ray_Target_Detection);
                map.put("radio_type", radio_type);
                testscheme.testScheme(null, map);
            }
        }

        //7.文本-情感分析
        String text_sen_analysis_scheme = one_click.getText_sen_analysis_scheme();
        log.info("text_sen_analysis_scheme:{}", text_sen_analysis_scheme);
        if (StrUtil.isNotEmpty(text_sen_analysis_scheme)) {
            String title = "文本-情感分析";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(text_sen_analysis_scheme);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Text_Sen_Analysis);
                map.put("radio_type", radio_type);
                testscheme.testScheme(null, map);
            }
        }

        //8.文本-实体识别
        String text_entity_recognition_scheme = one_click.getText_entity_recognition_scheme();
        if (StrUtil.isNotEmpty(text_entity_recognition_scheme)) {
            String title = "文本-实体识别";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(text_entity_recognition_scheme);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Text_Entity_Recognition);
                map.put("radio_type", radio_type);
                testscheme.testScheme(null, map);
            }
        }

        //9.语音-识别
        String asr_recognition_scheme = one_click.getAsr_recognition_scheme();
        if (StrUtil.isNotEmpty(asr_recognition_scheme)) {
            String title = "语音-识别";
            log.info("一键测试：{}", title);
            List<String> value = (List) jsonConfig.get(asr_recognition_scheme);
            for (String radio_type : value) {
                log.info("radio_type:{}", radio_type);
                map.put("type", MethodType.Asr_Asr_Recognition);
                map.put("radio_type", radio_type);
                testscheme.testScheme(null, map);
            }
        }


    }


    /**
     * 手动上传模型及算法
     *
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadfile")
    public Map<String, Object> oneClickUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {

        //1.得到上传的文件
        File uploadFile = hjjUpload.getFile(file);
        String uploadFilePath = uploadFile.getAbsolutePath();
        log.info("uploadFilePath:{}", uploadFilePath);

        //  List< One_click> list = new ArrayList<>();
        One_click one_click = new One_click();

        Map<String, Object> result = new HashMap<>();

        //  Map<String,Object> data = new HashMap<>();

        Map<String, Object> param = new HashMap<>();

        //2.对模型及数据集进行update:

        //  List<String> list = new ArrayList<>();
        //基本信息：模型
        String img_classfication = request.getParameter("img_classfication");

        if (null != img_classfication) {
            // update one_click set img_classfication=#img_classfication where id = 1

            one_click.setImg_classfication(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("模型：{}上传成功!", img_classfication);

            result.put("code", 0);
            result.put("file", uploadFilePath);

            return result;
        }
        String target_detection = request.getParameter("target_detection");


        if (null != target_detection) {
            one_click.setTarget_detection(uploadFilePath);

            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("模型：{}上传成功!", target_detection);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;
        }
        String target_trace = request.getParameter("target_trace");

        if (null != target_trace) {
            one_click.setTarget_trace(uploadFilePath);

            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("模型：{}上传成功!", target_trace);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;

        }
        String text_sen_analysis = request.getParameter("text_sen_analysis");

        if (null != text_sen_analysis) {


            one_click.setText_sen_analysis(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("模型：{}上传成功!", text_sen_analysis);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;

        }

        String text_entity_recognition = request.getParameter("text_entity_recognition");

        if (null != text_entity_recognition) {
            //    data.put("text_entity_recognition",uploadFilePath);
            one_click.setText_entity_recognition(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("模型：{}上传成功!", text_entity_recognition);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;

        }
        String asr_asr_recognition = request.getParameter("asr_asr_recognition");

        if (null != asr_asr_recognition) {
            one_click.setAsr_asr_recognition(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("模型：{}上传成功!", asr_asr_recognition);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;

        }


        //基本信息：数据集
        String img_classfication_dataset = request.getParameter("img_classfication_dataset");

        if (null != img_classfication_dataset) {
            //    data.put("img_classfication_dataset",uploadFilePath);
            one_click.setImg_classfication_dataset(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("数据集：{}上传成功!", img_classfication_dataset);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;

        }
        String target_detection_dataset = request.getParameter("target_detection_dataset");

        if (null != target_detection_dataset) {
            one_click.setTarget_detection_dataset(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("数据集：{}上传成功!", target_detection_dataset);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;

        }
        String target_trace_dataset = request.getParameter("target_trace_dataset");

        if (null != target_trace_dataset) {
            one_click.setTarget_trace_dataset(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("数据集：{}上传成功!", target_trace_dataset);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;

        }
        String text_sen_analysis_dataset = request.getParameter("text_sen_analysis_dataset");

        if (null != text_sen_analysis_dataset) {
            one_click.setText_sen_analysis_dataset(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("数据集：{}上传成功!", text_sen_analysis_dataset);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;
        }
        String text_entity_recognition_dataset = request.getParameter("text_entity_recognition_dataset");

        if (null != text_entity_recognition_dataset) {
            one_click.setText_entity_recognition_dataset(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("数据集：{}上传成功!", text_entity_recognition_dataset);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;

        }
        String asr_asr_recognition_dataset = request.getParameter("asr_asr_recognition_dataset");

        if (null != asr_asr_recognition_dataset) {
            one_click.setAsr_asr_recognition_dataset(uploadFilePath);
            param.put("paramsMap", one_click);
            oneclickService.batchUpdate(param);
            log.info("数据集：{}上传成功!", asr_asr_recognition_dataset);

            result.put("code", 0);
            result.put("file", uploadFilePath);
            return result;


        }

        // list.add(one_click);

        //  oneclickService.updateFilePath(uploadFilePath, list);


        return null;

    }


    /**
     * 一键测试定时器
     * https://blog.csdn.net/qq_31122833/article/details/82783479
     */
    @Scheduled(cron = "0 0 23 * * ?")    //每天23点定时运行一键测试    "0 0 23 * * ?"    "*/5 * * * * ?"
    public void autoCronTabRun() throws Exception {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(System.currentTimeMillis());


        log.info("一键测试定时器开始时间:{}", time);

        //1. 定时读取定时配置文件
        String path = "oneclickJson/oneclickCron.json";
        String dataSetConfig = ReadJson.getMapValue(path).toString();
        JSONObject jsonConfig = new JSONObject(dataSetConfig);
        log.info("定时器运行jsonConfig:{}", jsonConfig);


        //2. 定时运行
        One_click one_click = JSONUtil.toBean(dataSetConfig, One_click.class);
        log.info("one_click.getUsername:{}", one_click.getUsername());

        log.info("定时器运行一键测试开始：****");
        long begin = System.currentTimeMillis();
        this.oneRun(one_click, null);
        long end = System.currentTimeMillis();
        log.info("一键测试总耗时:{} 毫秒", (end - begin));


    }


    @GetMapping("/testXXX")
    public TableDataResponse getTableData() {
        List<User> users = new ArrayList<>();
        users.add(new User(1001, "user1", "user1@example.com", 28, "2023-01-01"));
        users.add(new User(1002, "user2", "user2@example.com", 32, "2022-11-15"));
        users.add(new User(1003, "user3", "user3@example.com", 25, "2023-02-20"));

        return new TableDataResponse(0, "", users.size(), users);
    }








}
