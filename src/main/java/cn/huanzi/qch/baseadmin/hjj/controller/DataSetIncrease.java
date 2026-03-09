package cn.huanzi.qch.baseadmin.hjj.controller;

import cn.huanzi.qch.baseadmin.hjj.entity.DataSetPageSubmit;
import cn.huanzi.qch.baseadmin.hjj.entity.MethodType;
import cn.huanzi.qch.baseadmin.hjj.service.DataSetService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;


@RestController
@RequestMapping("/dataset")
public class DataSetIncrease {


    Log log = LogFactory.get();


    @Resource
    private HjjUpload hjjUpload;

    @Resource
    private DataSetService dataSetService;


    @GetMapping("/increase")
    public ModelAndView testsetMethodMenu() {
        return new ModelAndView("dataset/increase");
    }


    @PostMapping("/runincrease")
    public void datasetRun(@RequestBody DataSetPageSubmit dataSetPageSubmit, Map<String,String> map) throws Exception {

        //1. 登录
        /**
         String username = "dengdagui";
         String password = "abcd1234";
         String api = "http://172.26.193.10:31674";
         **/
        log.info("数据集提交表单入口开始");
        // 0.登录信息
        String api = null;
        String username = null;
        String password = null;


        Boolean tager = Boolean.FALSE;


        String text_named_entity = null;
        String text_analysis = null;
        String voicle_recognition = null;
        String visible_light_img = null;
        String red_outer_img = null;
        String visible_light_tracking = null;
        String red_outer_tracking = null;
        String red_outer_detection = null ;
        String radio_type = null ;
        String visible_light_detection = null;
        //姿态估计
        String visible_img_pose_estimation = null ;  //姿态估计
        //雷达-信号分类
        String radar_signal_classification = null ;  //雷达-信号分类
        String visible_img_img_reid = null ;  //行人重识别
        String visible_img_img_segmentation = null ; //语义分割

        String video_target_detection = null ; //视频-目标检测
        String video_target_trace = null ; //视频-目标跟踪
        String structured_data_classification = null ; //结构化数据分类
        String structured_data_regression = null ; //结构化数据回归


        if (1 == map.size()) {

            // 0.登录信息
             api = dataSetPageSubmit.getApi().trim();
             username = dataSetPageSubmit.getUsername().trim();
             password = dataSetPageSubmit.getPassword().trim();

             //1. 增广  -雷达-信号分类
            radar_signal_classification = dataSetPageSubmit.getRadar_signal_classification();
            //2 .增广  -可见光-姿态估计
            visible_img_pose_estimation = dataSetPageSubmit.getVisible_img_pose_estimation();

            //2. 增广  -文本-命名实体识别
            text_named_entity = dataSetPageSubmit.getText_named_entity();
            //3. 增广  -文本-情感分析
            text_analysis = dataSetPageSubmit.getText_analysis();
            //4. 增广  -语音-语音识别
            voicle_recognition = dataSetPageSubmit.getVoicle_recognition();
            //5. 增广  -可见光-图像分类-白盒对抗增广
            visible_light_img = dataSetPageSubmit.getVisible_light_img();
            //8. 增广  -红外-图像分类-白盒对抗增广
            red_outer_img = dataSetPageSubmit.getRed_outer_img();
            //17. 增广  -可见光图像-目标跟踪-非对抗增广
            visible_light_tracking = dataSetPageSubmit.getVisible_light_tracking();
            //18. 增广  -红外线图像-目标跟踪-非对抗增广
            red_outer_tracking = dataSetPageSubmit.getRed_outer_tracking();
            //14. 增广  -红外线-目标检测-白盒对抗增广
            red_outer_detection = dataSetPageSubmit.getRed_outer_detection();
            //11. 增广  -可见光-目标检测-白盒对抗增广
            visible_light_detection = dataSetPageSubmit.getVisible_light_detection();
            //类型：黑、白、非对抗
             radio_type = dataSetPageSubmit.getRadio_type();

             //12  增广  可见光-行人重识别
            visible_img_img_reid = dataSetPageSubmit.getVisible_img_img_reid();
            //13  增广  可见光-语义分割
            visible_img_img_segmentation = dataSetPageSubmit.getVisible_img_img_segmentation();
            //12  增广  视频-目标检测
            video_target_detection = dataSetPageSubmit.getVideo_target_detection();
            //12  增广  视频-目标跟踪
            video_target_trace = dataSetPageSubmit.getVideo_target_trace();
            //12  增广  结构化数据-分类
            structured_data_classification = dataSetPageSubmit.getStructured_data_classification();
            //12  增广  结构化数据-回归
            structured_data_regression = dataSetPageSubmit.getStructured_data_regression();




        }else {
            username = map.get("username");
            password = map.get("password");
            api = map.get("api");
            String type = map.get("type");
            radio_type = map.get("radio_type") ;
            if(StrUtil.equals(type,MethodType.Text_Entity_Recognition)){  //实体识别
                text_named_entity = type;
            }else if(StrUtil.equals(type,MethodType.Text_Sen_Analysis)){  //情感分析
                text_analysis = type;
            }else if(StrUtil.equals(type,MethodType.Asr_Asr_Recognition)){  //语音识别
                voicle_recognition = type;
            }else if(StrUtil.equals(type,MethodType.Visible_Img_Img_Classfication)){  //可见光图像分类
                visible_light_img = type;
            }else if(StrUtil.equals(type,MethodType.Infrared_Ray_Img_Classfication)){  //红外线图像分类
                red_outer_img = type;
            }else if(StrUtil.equals(type,MethodType.Visible_Img_Target_Detection)){  //可见光目标检测
                visible_light_detection = type;
            }else if(StrUtil.equals(type,MethodType.Infrared_Ray_Target_Detection)){  //红外线目标检测
                red_outer_detection = type;
            }else if(StrUtil.equals(type,MethodType.Visible_Img_Img_Classfication)){  //可见光目标跟踪
                visible_light_tracking = type;
            }else if(StrUtil.equals(type,MethodType.Infrared_Ray_Img_Classfication)){  //红外线目标跟踪
                red_outer_tracking = type;
            }else if(StrUtil.equals(type,MethodType.Visible_Img_Pose_Estimation)) {    //可见光-姿态估计
                visible_img_pose_estimation = type;
            }else if(StrUtil.equals(type,MethodType.Visible_Img_Img_Reid)){  //可见光-行人重识别
                visible_img_img_reid = type;
            }else if(StrUtil.equals(type,MethodType.Visible_Img_Img_Segmentation)){  //可见光-语义分割
                visible_img_img_segmentation = type;
            }else if(StrUtil.equals(type,MethodType.Radar_Signal_Classification)) {    //雷达-信号分类
                radar_signal_classification = type;
            }else if(StrUtil.equals(type,MethodType.Video_Detection)){  //视频-目标检测
                video_target_detection = type;
            }else if(StrUtil.equals(type,MethodType.Video_Target_Trace)){  //视频-目标跟踪
                video_target_trace = type;
            }else if(StrUtil.equals(type,MethodType.Structured_Data_Classification)){  //结构化数据-分类
                structured_data_classification = type;
            }else if(StrUtil.equals(type,MethodType.Structured_Data_Regression)){  //结构化数据-回归
                structured_data_regression = type;
            }
        }


        // 1. 根据登录信息获取认证：
        Map<String, String> heads = hjjUpload.loginServer(username, password, api);
        log.info("登录结果为：{}", heads);


        /**    以下为所有数据增广   ***/
        //2. 增广  黑盒对抗-文本-命名实体识别

            if (StrUtil.isNotEmpty(text_named_entity) && radio_type.equals(MethodType.Black)) {
                String black_Text_Entity_Recognition_JsonFileName = "datasetIncreaseJson/black/black-Text_Entity_Recognition.json";
                String black_Text_Entity_Recognition_schemeName = "黑盒增广-文本-命名实体识别";
                log.info("数据增广运行：{}", black_Text_Entity_Recognition_schemeName);
                try {
                    dataSetService.doScheme(MethodType.Text_Entity_Recognition, MethodType.Black, api, black_Text_Entity_Recognition_JsonFileName, tager, black_Text_Entity_Recognition_schemeName, heads, "");
                }catch (Exception e){
                    log.info("数据集增广:{}异常",black_Text_Entity_Recognition_schemeName);
                    e.printStackTrace();
                }
            }


        //增广  -雷达-信号分类-白盒对抗增广
        if (StrUtil.isNotEmpty(radar_signal_classification) && radio_type.equals(MethodType.White)) {
            String white_Radar_Signal_Classification_JsonFileName = "datasetIncreaseJson/white/white-Radar_Signal_Classification.json";
            String white_Radar_Signal_Classification_schemeName = "白盒对抗增广-雷达-信号分类";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", white_Radar_Signal_Classification_schemeName);
            try {
                dataSetService.doScheme(MethodType.Radar_Signal_Classification, MethodType.White, api, white_Radar_Signal_Classification_JsonFileName, tager, white_Radar_Signal_Classification_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",white_Radar_Signal_Classification_schemeName);
                e.printStackTrace();
            }
        }


        //增广  -雷达-信号分类-黑盒对抗增广
        if (StrUtil.isNotEmpty(radar_signal_classification) && radio_type.equals(MethodType.Black)) {
            String JsonFileName = "datasetIncreaseJson/black/black-Radar_Signal_Classification.json";
            String schemeName = "黑盒对抗增广-雷达-信号分类";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", schemeName);
            try {
                dataSetService.doScheme(MethodType.Radar_Signal_Classification, MethodType.Black, api, JsonFileName, tager, schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",schemeName);
                e.printStackTrace();
            }
        }



        //增广  -雷达-信号分类-非对抗增广
        if (StrUtil.isNotEmpty(radar_signal_classification) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_Radar_Signal_Classification_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Radar_Signal_Classification.json";
            String Extension_No_Attack_Radar_Signal_Classification_schemeName = "非对抗增广-雷达-信号分类";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Radar_Signal_Classification_schemeName);
            try {
                dataSetService.doScheme(MethodType.Radar_Signal_Classification, MethodType.Black, api, Extension_No_Attack_Radar_Signal_Classification_JsonFileName, tager, Extension_No_Attack_Radar_Signal_Classification_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Radar_Signal_Classification_schemeName);
                e.printStackTrace();
            }
        }



        // 可见光图像-姿态估计-白盒对抗增广
        if (StrUtil.isNotEmpty(visible_img_pose_estimation) && radio_type.equals(MethodType.White)) {
            String white_Visible_Img_Pose_Estimation_JsonFileName = "datasetIncreaseJson/white/white-Visible_Img_Pose_Estimation.json";
            String white_Visible_Img_Pose_Estimation_schemeName = "白盒对抗增广-可见光图像-姿态估计";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", white_Visible_Img_Pose_Estimation_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Pose_Estimation, MethodType.White, api, white_Visible_Img_Pose_Estimation_JsonFileName, tager, white_Visible_Img_Pose_Estimation_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",white_Visible_Img_Pose_Estimation_schemeName);
                e.printStackTrace();
            }
        }

        // 可见光图像-姿态估计-黑盒对抗增广
        if (StrUtil.isNotEmpty(visible_img_pose_estimation) && radio_type.equals(MethodType.Black)) {
            String black_Visible_Img_Pose_Estimation_JsonFileName = "datasetIncreaseJson/black/black-Visible_Img_Pose_Estimation.json";
            String black_Visible_Img_Pose_Estimation_schemeName = "黑盒对抗增广-可见光图像-姿态估计";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", black_Visible_Img_Pose_Estimation_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Pose_Estimation, MethodType.Black, api, black_Visible_Img_Pose_Estimation_JsonFileName, tager, black_Visible_Img_Pose_Estimation_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",black_Visible_Img_Pose_Estimation_schemeName);
                e.printStackTrace();
            }
        }

        // 可见光图像-姿态估计-非对抗增广
        if (StrUtil.isNotEmpty(visible_img_pose_estimation) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_Visible_Img_Pose_Estimation_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Visible_Img_Pose_Estimation.json";
            String Extension_No_Attack_Visible_Img_Pose_Estimation_schemeName = "非对抗增广-可见光图像-姿态估计";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Visible_Img_Pose_Estimation_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Pose_Estimation, MethodType.Black, api, Extension_No_Attack_Visible_Img_Pose_Estimation_JsonFileName, tager, Extension_No_Attack_Visible_Img_Pose_Estimation_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Visible_Img_Pose_Estimation_schemeName);
                e.printStackTrace();
            }
        }






        //3. 增广  黑盒对抗-文本-情感分析
        if (StrUtil.isNotEmpty(text_analysis) && radio_type.equals(MethodType.Black)) {
            String black_Text_Sen_Analysis_JsonFileName = "datasetIncreaseJson/black/black-Text_Sen_Analysis.json";
            String black_Text_Sen_Analysis_schemeName = "黑盒对抗增广-文本-情感分析";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", black_Text_Sen_Analysis_schemeName);
            try {
                dataSetService.doScheme(MethodType.Text_Sen_Analysis, MethodType.Black, api, black_Text_Sen_Analysis_JsonFileName, tager, black_Text_Sen_Analysis_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",black_Text_Sen_Analysis_schemeName);
                e.printStackTrace();
            }
        }


        //4. 增广  -语音-语音识别
        if (StrUtil.isNotEmpty(voicle_recognition) && radio_type.equals(MethodType.White)) {
            String whitle_Asr_Asr_Recognition_JsonFileName = "datasetIncreaseJson/white/white-Asr_Asr_Recognition.json";
            String whitle_Asr_Asr_Recognition_schemeName = "白盒增广-语音-语音识别";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", whitle_Asr_Asr_Recognition_schemeName);
            try {
                dataSetService.doScheme(MethodType.Asr_Asr_Recognition, MethodType.White, api, whitle_Asr_Asr_Recognition_JsonFileName, tager, whitle_Asr_Asr_Recognition_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",whitle_Asr_Asr_Recognition_schemeName);
                e.printStackTrace();
            }
        }



        //5. 增广  -可见光-图像分类-白盒对抗增广
      //  log.info("visible_light_img:{},MethodType.White:{}",visible_light_img,MethodType.White);

        if (StrUtil.isNotEmpty(visible_light_img) && radio_type.equals(MethodType.White)) {
            String whitle_AVisible_Img_Img_Classfication_JsonFileName = "datasetIncreaseJson/white/white-Img_Classfication.json";
            String whitle_Visible_Img_Img_Classfication_schemeName = "白盒对抗增广-可见光-图像分类";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", whitle_Visible_Img_Img_Classfication_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Img_Classfication, MethodType.White, api, whitle_AVisible_Img_Img_Classfication_JsonFileName, tager, whitle_Visible_Img_Img_Classfication_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",whitle_Visible_Img_Img_Classfication_schemeName);
                e.printStackTrace();
            }
        }


        //6. 增广  -可见光-图像分类-黑盒对抗增广
        if (StrUtil.isNotEmpty(visible_light_img) && radio_type.equals(MethodType.Black)) {
            String black_AVisible_Img_Img_Classfication_JsonFileName = "datasetIncreaseJson/black/black-Img_Classfication.json";
            String black_Visible_Img_Img_Classfication_schemeName = "黑盒对抗增广-可见光-图像分类";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", black_Visible_Img_Img_Classfication_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Img_Classfication, MethodType.Black, api, black_AVisible_Img_Img_Classfication_JsonFileName, tager, black_Visible_Img_Img_Classfication_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",black_Visible_Img_Img_Classfication_schemeName);
                e.printStackTrace();
            }

        }


        //7. 增广  -可见光-图像分类-非对抗增广
        if (StrUtil.isNotEmpty(visible_light_img) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_AVisible_Img_Img_Classfication_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Img_Classfication.json";
            String Extension_No_Attack_Visible_Img_Img_Classfication_schemeName = "非对抗增广-可见光-图像分类";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Visible_Img_Img_Classfication_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Img_Classfication, MethodType.Black, api, Extension_No_Attack_AVisible_Img_Img_Classfication_JsonFileName, tager, Extension_No_Attack_Visible_Img_Img_Classfication_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Visible_Img_Img_Classfication_schemeName);
                e.printStackTrace();
            }
        }


        /*   -----------------------------*/
        //8. 增广  -红外-图像分类-白盒对抗增广
        if (StrUtil.isNotEmpty(red_outer_img) && radio_type.equals(MethodType.White)) {
            String whitle_Infrared_Ray_Img_Classfication_JsonFileName = "datasetIncreaseJson/white/white-Img_Classfication.json";
            String whitle_Infrared_Ray_Img_Classfication_schemeName = "白盒对抗增广-红外-图像分类";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", whitle_Infrared_Ray_Img_Classfication_schemeName);
            try {
                dataSetService.doScheme(MethodType.Infrared_Ray_Img_Classfication, MethodType.White, api, whitle_Infrared_Ray_Img_Classfication_JsonFileName, tager, whitle_Infrared_Ray_Img_Classfication_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",whitle_Infrared_Ray_Img_Classfication_schemeName);
                e.printStackTrace();
            }
        }


        //9. 增广  -红外-图像分类-黑盒对抗增广
        if (StrUtil.isNotEmpty(red_outer_img) && radio_type.equals(MethodType.Black)) {
            String black_Infrared_Ray_Img_Classfication_JsonFileName = "datasetIncreaseJson/black/black-Img_Classfication.json";
            String black_Infrared_Ray_Img_Classfication_schemeName = "黑盒对抗增广-红外-图像分类";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", black_Infrared_Ray_Img_Classfication_schemeName);
            try {
                dataSetService.doScheme(MethodType.Infrared_Ray_Img_Classfication, MethodType.Black, api, black_Infrared_Ray_Img_Classfication_JsonFileName, tager, black_Infrared_Ray_Img_Classfication_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",black_Infrared_Ray_Img_Classfication_schemeName);
                e.printStackTrace();
            }
        }


        //10. 增广  -红外-图像分类-非对抗增广  (所有非对抗暂时不做)
        if (StrUtil.isNotEmpty(red_outer_img) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_Infrared_Ray_Img_Classfication_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Img_Classfication.json";
            String Extension_No_Attack_Infrared_Ray_Img_Classfication_schemeName = "非对抗增广-红外-图像分类";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Img_Classfication_schemeName);
            try {
                dataSetService.doScheme(MethodType.Infrared_Ray_Img_Classfication, MethodType.Black, api, Extension_No_Attack_Infrared_Ray_Img_Classfication_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Img_Classfication_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Img_Classfication_schemeName);
                e.printStackTrace();
            }
        }



        /*      ------目标检测--可见光---          */
        //11. 增广  -可见光-目标检测-白盒对抗增广
        if (StrUtil.isNotEmpty(visible_light_detection) && radio_type.equals(MethodType.White)) {
            String whitle_Visible_Img_Target_Detection_JsonFileName = "datasetIncreaseJson/white/white-Target_Detection.json";
            String whitle_Visible_Img_Target_Detection_schemeName = "白盒对抗增广-可见光-目标检测";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", whitle_Visible_Img_Target_Detection_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Target_Detection, MethodType.White, api, whitle_Visible_Img_Target_Detection_JsonFileName, tager, whitle_Visible_Img_Target_Detection_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",whitle_Visible_Img_Target_Detection_schemeName);
                e.printStackTrace();
            }

        }


        //12. 增广  -可见光-目标检测-黑盒对抗增广
        if (StrUtil.isNotEmpty(visible_light_detection) && radio_type.equals(MethodType.Black)) {
            String black_Visible_Img_Target_Detection_JsonFileName = "datasetIncreaseJson/black/black-Target_Detection.json";
            String black_Visible_Img_Target_Detection_schemeName = "黑盒对抗增广-可见光-目标检测";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", black_Visible_Img_Target_Detection_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Target_Detection, MethodType.Black, api, black_Visible_Img_Target_Detection_JsonFileName, tager, black_Visible_Img_Target_Detection_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",black_Visible_Img_Target_Detection_schemeName);
                e.printStackTrace();
            }
        }


        //13. 增广  -可见光-目标检测-非对抗增广
        if (StrUtil.isNotEmpty(visible_light_detection) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_Visible_Img_Target_Detection_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Target_Detection.json";
            String Extension_No_Attack_Visible_Img_Target_Detection_schemeName = "非对抗增广-可见光-目标检测";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Visible_Img_Target_Detection_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Target_Detection, MethodType.Black, api, Extension_No_Attack_Visible_Img_Target_Detection_JsonFileName, tager, Extension_No_Attack_Visible_Img_Target_Detection_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Visible_Img_Target_Detection_schemeName);
                e.printStackTrace();
            }
        }




        /*      ------目标检测--红外线---          */
        //14. 增广  -红外线-目标检测-白盒对抗增广
        if (StrUtil.isNotEmpty(red_outer_detection) && radio_type.equals(MethodType.White)) {
            String whitle_Infrared_Ray_Target_Detection_JsonFileName = "datasetIncreaseJson/white/white-Target_Detection.json";
            String whitle_Infrared_Ray_Target_Detection_schemeName = "白盒对抗增广-红外线-目标检测";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", whitle_Infrared_Ray_Target_Detection_schemeName);
            try {
                dataSetService.doScheme(MethodType.Infrared_Ray_Target_Detection, MethodType.White, api, whitle_Infrared_Ray_Target_Detection_JsonFileName, tager, whitle_Infrared_Ray_Target_Detection_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",whitle_Infrared_Ray_Target_Detection_schemeName);
                e.printStackTrace();
            }
        }


        //15. 增广  -红外线-目标检测-黑盒对抗增广
        if (StrUtil.isNotEmpty(red_outer_detection) && radio_type.equals(MethodType.Black)) {
            String black_Infrared_Ray_Target_Detection_JsonFileName = "datasetIncreaseJson/black/black-Target_Detection.json";
            String black_Infrared_Ray_Target_Detection_schemeName = "黑盒对抗增广-红外线-目标检测";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", black_Infrared_Ray_Target_Detection_schemeName);
            try {
                dataSetService.doScheme(MethodType.Infrared_Ray_Target_Detection, MethodType.Black, api, black_Infrared_Ray_Target_Detection_JsonFileName, tager, black_Infrared_Ray_Target_Detection_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",black_Infrared_Ray_Target_Detection_schemeName);
                e.printStackTrace();
            }
        }


        //16. 增广  -红外线-目标检测-非对抗增广
        if (StrUtil.isNotEmpty(red_outer_detection) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_Infrared_Ray_Target_Detection_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Target_Detection.json";
            String Extension_No_Attack_Infrared_Ray_Target_Detection_schemeName = "非对抗增广-红外线-目标检测";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Target_Detection_schemeName);
            try {
                dataSetService.doScheme(MethodType.Infrared_Ray_Target_Detection, MethodType.Black, api, Extension_No_Attack_Infrared_Ray_Target_Detection_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Target_Detection_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Target_Detection_schemeName);
                e.printStackTrace();
            }
        }


        //17 .未对目标跟踪进行编写：

        //17. 增广  -可见光图像-目标跟踪-非对抗增广
        if (StrUtil.isNotEmpty(visible_light_tracking) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_Visible_Img_Target_Trace_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Target_Trace.json";
            String Extension_No_Attack_Visible_Img_Target_Trace_schemeName = "非对抗增广-可见光图像-目标跟踪";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Visible_Img_Target_Trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Target_Trace, MethodType.Black, api, Extension_No_Attack_Visible_Img_Target_Trace_JsonFileName, tager, Extension_No_Attack_Visible_Img_Target_Trace_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Visible_Img_Target_Trace_schemeName);
                e.printStackTrace();
            }

        }

        // 增广  -可见光图像-目标跟踪-黑盒对抗增广
        if (StrUtil.isNotEmpty(visible_light_tracking) && radio_type.equals(MethodType.Black)) {
            String Extension_No_Attack_Visible_Img_Target_Trace_JsonFileName = "datasetIncreaseJson/black/black-Target_Trace.json";
            String Extension_No_Attack_Visible_Img_Target_Trace_schemeName = "黑盒对抗增广-可见光图像-目标跟踪";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Visible_Img_Target_Trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Visible_Img_Target_Trace, MethodType.Black, api, Extension_No_Attack_Visible_Img_Target_Trace_JsonFileName, tager, Extension_No_Attack_Visible_Img_Target_Trace_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Visible_Img_Target_Trace_schemeName);
                e.printStackTrace();
            }

        }


        //18. 增广  -红外线图像-目标跟踪-非对抗增广
        if (StrUtil.isNotEmpty(red_outer_tracking) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Target_Trace.json";
            String Extension_No_Attack_Infrared_Ray_Target_trace_schemeName = "非对抗增广-红外线图像-目标跟踪";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Infrared_Ray_Target_trace, MethodType.Black, api, Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Target_trace_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
                e.printStackTrace();
            }
        }

        //18. 增广  -红外线图像-目标跟踪-黑盒对抗增广
        if (StrUtil.isNotEmpty(red_outer_tracking) && radio_type.equals(MethodType.Black)) {
            String Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName = "datasetIncreaseJson/black/black-Target_Trace.json";
            String Extension_No_Attack_Infrared_Ray_Target_trace_schemeName = "黑盒对抗增广-红外线图像-目标跟踪";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Infrared_Ray_Target_trace, MethodType.Black, api, Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Target_trace_schemeName, heads,"");
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
                e.printStackTrace();
            }
        }


        // 新增加
        //18. 增广  视频-目标检测-白盒对抗增广
        if (StrUtil.isNotEmpty(video_target_detection) && radio_type.equals(MethodType.White)) {
            String Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName = "datasetIncreaseJson/white/white-Target_Detection.json";
            String Extension_No_Attack_Infrared_Ray_Target_trace_schemeName = "白盒对抗增广-视频-目标检测";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Video_Detection, MethodType.White, api, Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Target_trace_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
                e.printStackTrace();
            }
        }


        //18. 增广  视频-目标检测-黑盒对抗增广
        if (StrUtil.isNotEmpty(video_target_detection) && radio_type.equals(MethodType.Black)) {
            String Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName = "datasetIncreaseJson/black/black-Target_Detection.json";
            String Extension_No_Attack_Infrared_Ray_Target_trace_schemeName = "黑盒对抗增广-视频-目标检测";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Video_Detection, MethodType.Black, api, Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Target_trace_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
                e.printStackTrace();
            }
        }


        //18. 增广  视频-目标检测-非对抗增广
        if (StrUtil.isNotEmpty(video_target_detection) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Target_Detection.json";
            String Extension_No_Attack_Infrared_Ray_Target_trace_schemeName = "非对抗增广-视频-目标检测";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Video_Detection, MethodType.Black, api, Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Target_trace_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
                e.printStackTrace();
            }
        }



        //18. 增广  视频-目标跟踪-白盒对抗增广
        /**
        if (StrUtil.isNotEmpty(video_target_detection) && radio_type.equals(MethodType.White)) {
            String Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName = "datasetIncreaseJson/white/white-Target_Detection.json";
            String Extension_No_Attack_Infrared_Ray_Target_trace_schemeName = "白盒对抗增广-视频-目标跟踪";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Video_Detection, MethodType.White, api, Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Target_trace_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
                e.printStackTrace();
            }
        }
         **/


        //18. 增广  视频-目标跟踪-黑盒对抗增广
        if (StrUtil.isNotEmpty(video_target_trace) && radio_type.equals(MethodType.Black)) {
            String Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName = "datasetIncreaseJson/black/black-Target_Trace.json";
            String Extension_No_Attack_Infrared_Ray_Target_trace_schemeName = "黑盒对抗增广-视频-目标跟踪";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Video_Target_Trace, MethodType.Black, api, Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Target_trace_schemeName, heads, "");
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
                e.printStackTrace();
            }
        }


        //18. 增广  视频-目标跟踪测-非对抗增广
        if (StrUtil.isNotEmpty(video_target_trace) && radio_type.equals(MethodType.Extension_No_Attack)) {
            String Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName = "datasetIncreaseJson/extension_no_attack/extension_no_attack-Target_Trace.json";
            String Extension_No_Attack_Infrared_Ray_Target_trace_schemeName = "非对抗增广-视频-目标跟踪";
            // tager = Boolean.FALSE;
            log.info("数据增广运行：{}", Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
            try {
                dataSetService.doScheme(MethodType.Video_Target_Trace, MethodType.Black, api, Extension_No_Attack_Infrared_Ray_Target_trace_JsonFileName, tager, Extension_No_Attack_Infrared_Ray_Target_trace_schemeName, heads, MethodType.Extension_No_Attack);
            }catch (Exception e){
                log.info("数据集增广:{}异常",Extension_No_Attack_Infrared_Ray_Target_trace_schemeName);
                e.printStackTrace();
            }
        }


    }

}
