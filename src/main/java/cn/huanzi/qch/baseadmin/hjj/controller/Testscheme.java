package cn.huanzi.qch.baseadmin.hjj.controller;


import cn.huanzi.qch.baseadmin.hjj.entity.MethodType;
import cn.huanzi.qch.baseadmin.hjj.entity.SchemePageSubmit;
import cn.huanzi.qch.baseadmin.hjj.service.TestSchemeService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试方案库
 */

@RestController
@RequestMapping("/testscheme")

public class Testscheme {

    Log log = LogFactory.get();

    @Autowired
    private HjjUpload hjjUpload;

    @Resource
    private TestSchemeService testSchemeService;

    @GetMapping("/scheme")
    public ModelAndView schemeMethodMenu() {
        return new ModelAndView("testscheme/scheme");
    }


    @GetMapping("/fromtest")
    public void test() {

        String str = "{\n" + "    \"code\":0,\n" + "    \"msg\":\"执行成功\",\n" + "    \"count\":1,\n" + "    \"data\":[{\n" + "        \"id\":\"10002\",\n" + "        \"username\":\"<div class=\"layui-form\"><input type=\"checkbox\" name=\"BBB\" title=\"选中\" lay-skin=\"tag\" checked></div>\",\n" + "        \"email\":\"test2@email.com\",\n" + "        \"sex\":\"男\",\n" + "        \"city\":\"浙江杭州\",\n" + "        \"sign\":\"舍南舍北皆春水，但见群鸥日日来。花径不曾缘客扫，蓬门今始为君开。盘飧市远无兼味，樽酒家贫只旧醅。肯与邻翁相对饮，隔篱呼取尽余杯。\",\n" + "        \"experience\":\"116\",\n" + "        \"ip\":\"192.168.0.8\",\n" + "        \"checkin\":\"108\",\n" + "        \"joinTime\":\"2016-10-14\"\n" + "    }],\n" + "    \"success\":true\n" + "}";


        Map<String, Object> map = new HashMap<>(str.length());
        log.info("map:{}", map);
        //  System.out.println("map--->"+map.get("msg"));

        //    Map<String, Object> map = jsonObject.toJavaObject(Map.class);

        Map<String, Object> mapX = new HashMap<>();
        Map<String, Object> mapX2 = new HashMap<>();

        mapX2.put("id", "100");
        //  mapX2.put("username","<div class=\"layui-form\"><input type=\"checkbox\" name=\"BBB\" title=\"选中\" lay-skin=\"tag\" checked></div>");
        mapX2.put("username", "<html><body>Hello, World!</body></html>");

        //   mapX2.put("username","11111");

        //  String str1 =  JSON.toJSONString(mapX2);
        mapX.put("code", 0);
        mapX.put("msg", "success");
        mapX.put("count", 1);
        mapX.put("data", mapX2);


        // return mapX ;

        //  return  str ;


    }


    //  黑盒-可见光-目标检测
    @PostMapping("/schemfromsubmit")
    public Map<String, Object> testScheme(@RequestBody SchemePageSubmit schemePageSubmit, Map<String, String> map) throws Exception {


        log.info("方案提交表单入口开始");

        // 1.得到重要登录信息
        String api = null;
        String username = null;
        String password = null;
        String category = null;
        String isTask_from = null;

        // 3.1可见光-目标检测    黑白判断执行：
        String isible_light_detection = null;
        // 3.2 可见光-图像分类  黑白判断执行：
        String isible_light_img = null;
        // 3.3  可见光-目标跟踪
        String isible_light_tracking = null;
        // 3.4 红外线-图像分类
        String red_outer_img = null;
        // 3.5 红外线-目标检测
        String red_outer_detection = null;
        // 3.6 红外线-目标跟踪
        String red_outer_tracking = null;
        // 雷达-信号分类
        String radar_signal_classification = null;
        // 可见光-姿态估计
        String visible_img_pose_estimation = null;
        // 3.7 文本-情感分析
        String text_analysis = null;
        // 3.8 文本-命名实体识别
        String text_named_entity = null;
        // 3.9 语音-语音识别
        String voicle_recognition = null;
        // 3.10 可见光-行人重识别
        String visible_img_img_reid = null;
        // 3.11 可见光-图像-语义分割
        String visible_img_img_segmentation = null;
        // 3.12 视频-目标检测
        String video_target_detection = null;
        // 3.13 视频-目标跟踪
        String video_target_trace = null;
        // 3.14 结构数据化分类
        String structured_data_classification = null;
        // 3.15 结构数据化回归
        String structured_data_regression = null;
        // 3.16 结构数据化聚类
        String structured_data_cluster = null;




        //模型类型：(黑、白、数据集)
        String radio_type = null;

        if (1 == map.size()) {
            // 3.1可见光-目标检测
            isible_light_detection = schemePageSubmit.getIsible_light_detection();
            // 3.2 可见光-图像分类  黑白判断执行：
            isible_light_img = schemePageSubmit.getIsible_light_img();
            // 3.3  可见光-目标跟踪
            isible_light_tracking = schemePageSubmit.getIsible_light_tracking();
            // 3.4 红外线-图像分类
            red_outer_img = schemePageSubmit.getRed_outer_img();
            // 3.5 红外线-目标检测
            red_outer_detection = schemePageSubmit.getRed_outer_detection();
            // 3.6 红外线-目标跟踪
            red_outer_tracking = schemePageSubmit.getRed_outer_tracking();
            // 雷达-信号分类
            radar_signal_classification = schemePageSubmit.getRadar_signal_classification();

            // 可见光-姿态估计
            visible_img_pose_estimation = schemePageSubmit.getVisible_img_pose_estimation();

            // 3.7 文本-情感分析
            text_analysis = schemePageSubmit.getText_analysis();
            // 3.8 文本-命名实体识别
            text_named_entity = schemePageSubmit.getText_named_entity();
            // 3.9 语音-语音识别
            voicle_recognition = schemePageSubmit.getVoicle_recognition();
            // 3.10 可见光-行人重识别
            visible_img_img_reid = schemePageSubmit.getVisible_img_img_reid();
            // 3.11 可见光-图像-语义分割
            visible_img_img_segmentation = schemePageSubmit.getVisible_img_img_segmentation();

            // 3.12 视频-目标检测
            video_target_detection = schemePageSubmit.getVideo_target_detection();

            // 3.13 视频-目标跟踪
            video_target_trace = schemePageSubmit.getVideo_target_trace();

            //3.14 结构数据化-分类
            structured_data_classification = schemePageSubmit.getStructured_data_classification();
            //3.15 结构数据化-回归
            structured_data_regression = schemePageSubmit.getStructured_data_regression();
            structured_data_cluster = schemePageSubmit.getStructured_data_cluster();
            //3.17 雷达-信号分类
            radar_signal_classification = schemePageSubmit.getRadar_signal_classification();


            //模型类型：(黑、白、数据集)
            radio_type = schemePageSubmit.getRadio_type().trim();

            // 1.得到重要登录信息
            api = schemePageSubmit.getApi().trim();
            username = schemePageSubmit.getUsername().trim();
            password = schemePageSubmit.getPassword().trim();
            category = schemePageSubmit.getCategory().trim();
            try {
                isTask_from = schemePageSubmit.getIsTask().trim();
            } catch (Exception e) {
                isTask_from = null;
            }


        } else {

            username = map.get("username");
            password = map.get("password");
            api = map.get("api");
            String type = map.get("type");
            category = map.get("category");
            try {
                isTask_from = map.get("isTask");
            } catch (Exception e) {
                isTask_from = null;
            }
            radio_type = map.get("radio_type");
            if (StrUtil.equals(type, MethodType.Text_Entity_Recognition)) {  //实体识别
                text_named_entity = type;
            } else if (StrUtil.equals(type, MethodType.Text_Sen_Analysis)) {  //情感分析
                text_analysis = type;
            } else if (StrUtil.equals(type, MethodType.Asr_Asr_Recognition)) {  //语音识别
                voicle_recognition = type;
            } else if (StrUtil.equals(type, MethodType.Visible_Img_Img_Classfication)) {  //可见光图像分类
                isible_light_img = type;
            } else if (StrUtil.equals(type, MethodType.Infrared_Ray_Img_Classfication)) {  //红外线图像分类
                red_outer_img = type;
            } else if (StrUtil.equals(type, MethodType.Visible_Img_Target_Detection)) {  //可见光目标检测
                isible_light_detection = type;
            } else if (StrUtil.equals(type, MethodType.Infrared_Ray_Target_Detection)) {  //红外线目标检测
                red_outer_detection = type;
            } else if (StrUtil.equals(type, MethodType.Visible_Img_Img_Classfication)) {  //可见光目标跟踪
                isible_light_tracking = type;
            } else if (StrUtil.equals(type, MethodType.Infrared_Ray_Img_Classfication)) {  //红外线目标跟踪
                red_outer_tracking = type;
            }


        }


        Boolean boolean_isTask = Boolean.FALSE;

        if (StrUtil.isNotEmpty(isTask_from)) {
            boolean_isTask = Boolean.TRUE;

        }


        //    boolean boolean_isTask = Boolean.parseBoolean(isTask);
        if (StrUtil.isEmpty(category)) {
            category = "666beed5280f56000a273892";
        }

        // 2. 根据登录信息获取认证：
        Map<String, String> heads = hjjUpload.loginServer(username, password, api);
        log.info("登录结果为：{}", heads);
        // 3. 根据提交信息分别进行方案提交


        Map<String, Object> result = new HashMap<>();


        log.info("模型类型为:{}", radio_type);

        // 3.1可见光-目标检测    黑白判断执行：
        if (StrUtil.isNotEmpty(isible_light_detection) && radio_type.equals(MethodType.Black)) {
            //1.黑盒-可见光-目标检测
            log.info("运行：{}-可见光-目标检测", radio_type);
            String black_Visible_Img_Target_Detection_JsonFileName = "schemeJson/black/black-Visible_Img_Target_Detection.json";
            String black_Visible_Img_Target_Detection_schemeName = "黑盒-可见光-目标检测";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Target_Detection, MethodType.Black, api, black_Visible_Img_Target_Detection_JsonFileName, boolean_isTask, black_Visible_Img_Target_Detection_schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-可见光-目标检测任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-可见光-目标检测执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-可见光-目标检测任务执行失败");
                e.printStackTrace();
            }

        }

        if (StrUtil.isNotEmpty(isible_light_detection) && radio_type.equals(MethodType.White)) {
            //2.白盒-可见光-目标检测    --需要检查运行
            log.info("运行：{}-可见光-目标检测", radio_type);
            String white_Visible_Img_Target_Detection_JsonFileName = "schemeJson/white/white-Visible_Img_Target_Detection.json";
            String white_Visible_Img_Target_Detection_schemeName = "白盒-可见光-目标检测";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Target_Detection, MethodType.White, api, white_Visible_Img_Target_Detection_JsonFileName, boolean_isTask, white_Visible_Img_Target_Detection_schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-可见光-目标检测任务任务执行成功");
            } catch (Exception e) {
                log.error("白盒-可见光-目标检测执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-可见光-目标检测任务执行失败");
                e.printStackTrace();
            }

        }


        // 3.2 可见光-图像分类  黑白判断执行：
        if (StrUtil.isNotEmpty(isible_light_img) && radio_type.equals(MethodType.Black)) {
            //3.黑盒-可见光-图像分类    --需要检查运行
            log.info("运行：{}-可见光-图像分类", radio_type);
            String black_Visible_Img_Img_Classfication_JsonFileName = "schemeJson/black/black-Visible_Img_Img_Classfication.json";
            String black_Visible_Img_Img_Classfication_schemeName = "黑盒-可见光-图像分类";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Img_Classfication, MethodType.Black, api, black_Visible_Img_Img_Classfication_JsonFileName, boolean_isTask, black_Visible_Img_Img_Classfication_schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-可见光-图像分任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-可见光-图像分类执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-可见光-图像分类任务执行失败");
                e.printStackTrace();
            }

        }

        // 3.2 白盒-可见光-图像分类  黑白判断执行：
        if (StrUtil.isNotEmpty(isible_light_img) && radio_type.equals(MethodType.White)) {
            //4.白盒-可见光-图像分类
            log.info("运行：{}-可见光-图像分类", radio_type);
            String JsonFileName = "schemeJson/white/white-Visible_Img_Img_Classfication.json";
            String schemeName = "白盒-可见光-图像分类";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Img_Classfication, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-可见光-图像分类任务执行成功");
            } catch (Exception e) {
                log.error("白盒-可见光-图像分类执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-可见光-图像分类任务执行失败");
                e.printStackTrace();
            }
        }


        // 3.3  黑盒-可见光-目标跟踪
        if (StrUtil.isNotEmpty(isible_light_tracking) && radio_type.equals(MethodType.Black)) {

            /***     以下是json中无数据   ***/
            //16.黑盒-可见光-目标跟踪   (json中无数据)
            log.info("运行：{}-可见光-目标跟踪", radio_type);
            String black_Visible_Img_Target_Trace_JsonFileName = "schemeJson/black/black-Visible_Img_Target_Trace.json";
            String black_Visible_Img_Target_Trace_schemeName = "黑盒-可见光-目标跟踪";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Target_Trace, MethodType.Black, api, black_Visible_Img_Target_Trace_JsonFileName, boolean_isTask, black_Visible_Img_Target_Trace_schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-可见光-目标跟踪任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-可见光-目标跟踪执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-可见光-目标跟踪任务执行失败");
                e.printStackTrace();
            }

        }

        //白盒-可见光-目标跟踪
        if (StrUtil.isNotEmpty(isible_light_tracking) && radio_type.equals(MethodType.White)) {
            //17.白盒-可见光-目标跟踪   (json中无数据)
            log.info("运行：{}-可见光-目标跟踪", radio_type);
            String JsonFileName = "schemeJson/white/white-Visible_Img_Target_Trace.json";
            String schemeName = "白盒-可见光-目标跟踪";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Target_Trace, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-可见光-目标跟踪任务执行成功");
            } catch (Exception e) {
                log.error("白盒-可见光-目标跟踪执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-可见光-目标跟踪任务执行失败");
                e.printStackTrace();
            }

        }

        // 3.4 黑盒-红外线-图像分类
        if (StrUtil.isNotEmpty(red_outer_img) && radio_type.equals(MethodType.Black)) {
            //7.黑盒-红外图像-图像分类
            log.info("运行：{}-红外线-图像分类", radio_type);
            String JsonFileName = "schemeJson/black/black-Infrared_Ray_Img_Classfication.json";
            String schemeName = "黑盒-红外图像-图像分类";
            try {
                testSchemeService.doScheme(MethodType.Infrared_Ray_Img_Classfication, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-红外图像-图像分任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-红外图像-图像分类执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-红外图像-图像分类任务执行失败");
                e.printStackTrace();
            }

        }

        if (StrUtil.isNotEmpty(red_outer_img) && radio_type.equals(MethodType.White)) {
            //8.白盒-红外图像-图像分类
            log.info("运行：{}-红外线-图像分类", radio_type);
            String JsonFileName = "schemeJson/white/white-Infrared_Ray_Img_Classfication.json";
            String schemeName = "白盒-红外图像-图像分类";
            try {
                testSchemeService.doScheme(MethodType.Infrared_Ray_Img_Classfication, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-红外图像-图像分类任务执行成功");
            } catch (Exception e) {
                log.error("白盒-红外图像-图像分类执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-红外图像-图像分类任务执行失败");
                e.printStackTrace();
            }
        }


        // 3.5 黑盒-红外线-目标检测
        if (StrUtil.isNotEmpty(red_outer_detection) && radio_type.equals(MethodType.Black)) {
            //5.黑盒-红外图像-目标检测
            log.info("运行：{}-红外图像-目标检测", radio_type);
            String JsonFileName = "schemeJson/black/black-Infrared_Ray_Target_Detection.json";
            String schemeName = "黑盒-红外图像-目标检测";
            try {
                testSchemeService.doScheme(MethodType.Infrared_Ray_Target_Detection, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-红外图像-目标检测任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-红外图像-目标检测执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-红外图像-目标检测任务执行失败");
                e.printStackTrace();
            }

        }
        if (StrUtil.isNotEmpty(red_outer_detection) && radio_type.equals(MethodType.White)) {
            //6.白盒-红外图像-目标检测
            log.info("运行：{}-红外图像-目标检测", radio_type);
            String JsonFileName = "schemeJson/white/white-Infrared_Ray_Target_Detection.json";
            String schemeName = "白盒-红外图像-目标检测";
            try {
                testSchemeService.doScheme(MethodType.Infrared_Ray_Target_Detection, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-红外图像-目标检测任务执行成功");
            } catch (Exception e) {
                log.error("白盒-红外图像-目标检测执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-红外图像-目标检测任务执行失败");
                e.printStackTrace();
            }
        }

        // 3.6 黑盒-红外线-目标跟踪
        if (StrUtil.isNotEmpty(red_outer_tracking) && radio_type.equals(MethodType.Black)) {
            //18.黑盒-红外-目标跟踪   (json中无数据)
            log.info("运行：{}-红外-目标跟踪", radio_type);
            String JsonFileName = "schemeJson/black/black-Infrared_Ray_Target_trace.json";
            String schemeName = "黑盒-红外-目标跟踪";
            try {
                testSchemeService.doScheme(MethodType.Infrared_Ray_Target_trace, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-红外-目标跟踪任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-红外-目标跟踪执行失败");
                result.put("code", 0);
                result.put("msg", "黑盒-红外-目标跟踪任务执行失败");
                e.printStackTrace();
            }

        }
        //  3.6 白盒-红外线-目标跟踪
        if (StrUtil.isNotEmpty(red_outer_tracking) && radio_type.equals(MethodType.White)) {
            //19.白盒-红外-目标跟踪   (json中无数据)
            log.info("运行：{}-红外-目标跟踪", radio_type);
            String JsonFileName = "schemeJson/white/white-Infrared_Ray_Target_trace.json";
            String schemeName = "白盒-红外-目标跟踪";
            try {
                testSchemeService.doScheme(MethodType.Infrared_Ray_Target_trace, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-红外-目标跟踪任务执行成功");
            } catch (Exception e) {
                log.error("白盒-红外-目标跟踪执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-红外-目标跟踪任务执行失败");
                e.printStackTrace();
            }
        }


        // 3.7 黑盒-视频-目标检测
        if (StrUtil.isNotEmpty(video_target_detection) && radio_type.equals(MethodType.Black)) {
            //18.黑盒-视频-目标检测   (json中无数据)
            log.info("运行：{}-视频-目标检测", radio_type);
            String JsonFileName = "schemeJson/black/black-Video_Target_Detection.json";
            String schemeName = "黑盒-视频-目标检测";
            try {
                testSchemeService.doScheme(MethodType.Video_Detection, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-视频-目标检测任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-视频-目标检测执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-视频-目标检测任务执行失败");
                e.printStackTrace();
            }

        }

        //  3.8 白盒-视频-目标检测
        if (StrUtil.isNotEmpty(video_target_detection) && radio_type.equals(MethodType.White)) {
            //19.白盒-视频-目标检测   (json中无数据)
            log.info("运行：{}-视频-目标检测", radio_type);
            String JsonFileName = "schemeJson/white/white-Video_Target_Detection.json";
            String schemeName = "白盒-视频-目标检测";
            try {
                testSchemeService.doScheme(MethodType.Video_Detection, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-视频-目标检测任务执行成功");
            } catch (Exception e) {
                log.error("白盒-视频-目标检测执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-视频-目标检测任务执行失败");
                e.printStackTrace();
            }
        }


        // 3.9 黑盒-视频-目标跟踪
        if (StrUtil.isNotEmpty(video_target_trace) && radio_type.equals(MethodType.Black)) {
            //18.黑盒-视频-目标跟踪   (json中无数据)
            log.info("运行：{}-视频-目标跟踪", radio_type);
            String JsonFileName = "schemeJson/black/black-Video_Target_Trace.json";
            String schemeName = "黑盒-视频-目标跟踪";
            try {
                testSchemeService.doScheme(MethodType.Video_Target_Trace, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-视频-目标跟踪任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-视频-目标跟踪执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-视频-目标跟踪任务执行失败");
                e.printStackTrace();
            }

        }
        //  3.10 白盒-视频-目标跟踪
        if (StrUtil.isNotEmpty(video_target_trace) && radio_type.equals(MethodType.White)) {
            //19.白盒-视频-目标跟踪   (json中无数据)
            log.info("运行：{}-视频-目标跟踪", radio_type);
            String JsonFileName = "schemeJson/white/white-Video_Target_Trace.json";
            String schemeName = "白盒-视频-目标跟踪";
            try {
                testSchemeService.doScheme(MethodType.Video_Target_Trace, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-视频-目标跟踪任务执行成功");
            } catch (Exception e) {
                log.error("白盒-视频-目标跟踪执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-视频-目标跟踪任务执行失败");
                e.printStackTrace();
            }
        }


        // 3.5 黑盒-可见光-行人重识别
        if (StrUtil.isNotEmpty(visible_img_img_reid) && radio_type.equals(MethodType.Black)) {
            //5.黑盒-红外图像-目标检测
            log.info("运行：{}-可见光-行人重识别", radio_type);
            String JsonFileName = "schemeJson/black/black-Visible_Img_Img_Reid.json";
            String schemeName = "黑盒-可见光-行人重识别";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Img_Reid, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-可见光-行人重识别任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-可见光-行人重识别执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-可见光-行人重识别任务执行失败");
                e.printStackTrace();
            }
        }

        // 3.6 白盒-可见光-行人重识别
        if (StrUtil.isNotEmpty(visible_img_img_reid) && radio_type.equals(MethodType.White)) {
            //5.黑盒-红外图像-目标检测
            log.info("运行：{}-可见光-行人重识别", radio_type);
            String JsonFileName = "schemeJson/white/white-Visible_Img_Img_Reid.json";
            String schemeName = "白盒-可见光-行人重识别";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Img_Reid, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-可见光-行人重识别任务执行成功");
            } catch (Exception e) {
                log.error("白盒-可见光-行人重识别执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-可见光-行人重识别任务执行失败");
                e.printStackTrace();
            }
        }


        // 3.7 黑盒-可见光-语义分割
        if (StrUtil.isNotEmpty(visible_img_img_segmentation) && radio_type.equals(MethodType.Black)) {
            //5.黑盒-可见光-语义分割
            log.info("运行：{}-可见光-语义分割", radio_type);
            String JsonFileName = "schemeJson/black/black-Visible_Img_Img_Segmentation.json";
            String schemeName = "黑盒-可见光-语义分割";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Img_Segmentation, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-可见光-语义分割任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-可见光-语义分割执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-可见光-语义分割任务执行失败");
                e.printStackTrace();
            }
        }

        // 3.8 白盒-可见光-语义分割
        if (StrUtil.isNotEmpty(visible_img_img_segmentation) && radio_type.equals(MethodType.White)) {
            //5.黑盒-红外图像-目标检测
            log.info("运行：{}-可见光-语义分割", radio_type);
            String JsonFileName = "schemeJson/white/white-Visible_Img_Img_Segmentation.json";
            String schemeName = "白盒-可见光-语义分割";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Img_Segmentation, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-可见光-语义分割任务执行成功");
            } catch (Exception e) {
                log.error("白盒-可见光-语义分割执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-可见光-语义分割任务执行失败");
                e.printStackTrace();
            }
        }

        // 雷达-信号分类
        if (StrUtil.isNotEmpty(radar_signal_classification) && radio_type.equals(MethodType.Black)) {
            //  黑盒-雷达-信号分类
            log.info("运行：{}-雷达-信号分类", radio_type);
            String JsonFileName = "schemeJson/black/black-Radar-Signal_Classfication.json";
            String schemeName = "黑盒-雷达-信号分类";
            try {
                testSchemeService.doScheme(MethodType.Radar_Signal_Classification, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-可见光-语义分割任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-可见光-语义分割执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-可见光-语义分割任务执行失败");
                e.printStackTrace();
            }

        }


        // 雷达-信号分类
        if (StrUtil.isNotEmpty(radar_signal_classification) && radio_type.equals(MethodType.White)) {
            //  白盒-雷达-信号分类
            log.info("运行：{}-雷达-信号分类", radio_type);
            String JsonFileName = "schemeJson/white/white-Radar-Signal_Classfication.json";
            String schemeName = "白盒-雷达-信号分类";
            try {
                testSchemeService.doScheme(MethodType.Radar_Signal_Classification, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-雷达-信号分类任务执行成功");
            } catch (Exception e) {
                log.error("白盒-雷达-信号分类执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-雷达-信号分类任务执行失败");
                e.printStackTrace();
            }

        }

        // 雷达-信号分类
        if (StrUtil.isNotEmpty(radar_signal_classification) && radio_type.equals(MethodType.DataSet)) {
            //  数据集-雷达-信号分类
            log.info("运行：{}-雷达-信号分类", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Radar-Signal_Classfication.json";
            String schemeName = "数据集-雷达-信号分类";
            try {
                testSchemeService.doScheme(MethodType.Radar_Signal_Classification, MethodType.DataSet, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-雷达-信号分类任务执行成功");
            } catch (Exception e) {
                log.error("数据集-雷达-信号分类执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-雷达-信号分类任务执行失败");
                e.printStackTrace();
            }
        }


        // 可见光-姿态估计
        if (StrUtil.isNotEmpty(visible_img_pose_estimation) && radio_type.equals(MethodType.Black)) {
            //  黑盒-可见光-姿态估计
            log.info("运行：{}-可见光-姿态估计", radio_type);
            String JsonFileName = "schemeJson/black/black-Visible_Img_Pose_Estimation.json";
            String schemeName = "黑盒-可见光-姿态估计";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Pose_Estimation, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-可见光-姿态估计任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-可见光-姿态估计执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-可见光-姿态估计任务执行失败");
                e.printStackTrace();
            }

        }


        // 可见光-姿态估计
        if (StrUtil.isNotEmpty(visible_img_pose_estimation) && radio_type.equals(MethodType.White)) {
            //  白盒-可见光-姿态估计
            log.info("运行：{}-可见光-姿态估计", radio_type);
            String JsonFileName = "schemeJson/white/white-Visible_Img_Pose_Estimation.json";
            String schemeName = "白盒-可见光-姿态估计";
            try {
                testSchemeService.doScheme(MethodType.Visible_Img_Pose_Estimation, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-可见光-姿态估计任务执行成功");
            } catch (Exception e) {
                log.error("白盒-可见光-姿态估计执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-可见光-姿态估计任务执行失败");
                e.printStackTrace();
            }

        }

        // 可见光-姿态估计
      //  if (StrUtil.isNotEmpty(visible_img_pose_estimation) && radio_type.equals(MethodType.DataSet)) {
            //  数据集-可见光-姿态估计
      //  }




        // 3.7 文本-情感分析
        if (StrUtil.isNotEmpty(text_analysis) && radio_type.equals(MethodType.Black)) {
            //10.黑盒-文本-情感分析
            log.info("运行：{}-文本-情感分析", radio_type);
            String JsonFileName = "schemeJson/black/black-Text_Sen_Analysis.json";
            String schemeName = "黑盒-文本-情感分析";
            try {
                testSchemeService.doScheme(MethodType.Text_Sen_Analysis, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-文本-情感分析任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-文本-情感分析执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-文本-情感分析任务执行失败");
                e.printStackTrace();
            }
        }
        if (StrUtil.isNotEmpty(text_analysis) && radio_type.equals(MethodType.White)) {
            //  文本-情感分析 现无白盒
        }

        // 3.8 文本-命名实体识别
        if (StrUtil.isNotEmpty(text_named_entity) && radio_type.equals(MethodType.Black)) {

            //11.黑盒-文本-命名实体识别
            log.info("运行：{}-文本-命名实体识别", radio_type);
            String JsonFileName = "schemeJson/black/black-Text_Entity_Recognition.json";
            String schemeName = "黑盒-文本-命名实体识别";
            try {
                testSchemeService.doScheme(MethodType.Text_Entity_Recognition, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-文本-命名实体识别任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-文本-命名实体识别执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-文本-命名实体识别任务执行失败");
                e.printStackTrace();
            }
        }
        if (StrUtil.isNotEmpty(text_named_entity) && radio_type.equals(MethodType.White)) {
            //  文本-命名实体识别 现无白盒
        }

        // 3.9 语音-语音识别
        if (StrUtil.isNotEmpty(voicle_recognition) && radio_type.equals(MethodType.Black)) {
            //  语音-语音识别  现无黑盒
        }
        if (StrUtil.isNotEmpty(voicle_recognition) && radio_type.equals(MethodType.White)) {
            //9.白盒-语音识别
            log.info("运行：{}-白盒-语音识别", radio_type);
            String JsonFileName = "schemeJson/white/white-Asr_Asr_Recognition.json";
            String schemeName = "白盒-语音识别";
            try {
                testSchemeService.doScheme(MethodType.Asr_Asr_Recognition, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-语音识别任务执行成功");
            } catch (Exception e) {
                log.error("白盒-语音识别执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-语音识别任务执行失败");
                e.printStackTrace();
            }
        }


        // 3.10 黑盒-结构化数据-分类
        if (StrUtil.isNotEmpty(structured_data_classification) && radio_type.equals(MethodType.Black)) {
            //5.黑盒-结构化数据-分类
            log.info("运行：{}-结构化数据-分类", radio_type);
            String JsonFileName = "schemeJson/black/black-Structured_Data_Classification.json";
            String schemeName = "黑盒-结构化数据-分类";
            try {
                testSchemeService.doScheme(MethodType.Structured_Data_Classification, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-结构化数据-分类任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-结构化数据-分类执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-结构化数据-分类任务执行失败");
                e.printStackTrace();
            }

        }

        // 3.11 白盒-结构化数据-分类
        if (StrUtil.isNotEmpty(structured_data_classification) && radio_type.equals(MethodType.White)) {
            //5 白盒-结构化数据-分类
            log.info("运行：{}-结构化数据-分类", radio_type);
            String JsonFileName = "schemeJson/white/white-Structured_Data_Classification.json";
            String schemeName = "白盒-结构化数据-分类";
            try {
                testSchemeService.doScheme(MethodType.Structured_Data_Classification, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-结构化数据-分类任务执行成功");
            } catch (Exception e) {
                log.error("白盒-结构化数据-分类执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-结构化数据-分类任务执行失败");
                e.printStackTrace();
            }
        }


        // 3.12 黑盒-结构化数据-回归
        if (StrUtil.isNotEmpty(structured_data_regression) && radio_type.equals(MethodType.Black)) {
            //5.黑盒-结构化数据-回归
            log.info("运行：{}-结构化数据-回归", radio_type);
            String JsonFileName = "schemeJson/black/black-Structured_Data_Regression.json";
            String schemeName = "黑盒-结构化数据-回归";
            try {
                testSchemeService.doScheme(MethodType.Structured_Data_Regression, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-结构化数据-回归任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-结构化数据-回归执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-结构化数据-回归任务执行失败");
                e.printStackTrace();
            }
        }

        // 3.13 白盒-结构化数据-回归
        if (StrUtil.isNotEmpty(structured_data_regression) && radio_type.equals(MethodType.White)) {
            //5 白盒-结构化数据-回归
            log.info("运行：{}-结构化数据-回归", radio_type);
            String JsonFileName = "schemeJson/white/white-Structured_Data_Regression.json";
            String schemeName = "白盒-结构化数据-回归";
            try {
                testSchemeService.doScheme(MethodType.Structured_Data_Regression, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-结构化数据-回归任务执行成功");
            } catch (Exception e) {
                log.error("白盒-结构化数据-回归执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-结构化数据-回归任务执行失败");
                e.printStackTrace();
            }
        }


        // 3.14 黑盒-结构化数据-聚类
        if (StrUtil.isNotEmpty(structured_data_cluster) && radio_type.equals(MethodType.Black)) {
            //5.黑盒-结构化数据-聚类
            log.info("运行：{}-结构化数据-聚类", radio_type);
            String JsonFileName = "schemeJson/black/black-Structured_Data_Cluster.json";
            String schemeName = "黑盒-结构化数据-聚类";
            try {
                testSchemeService.doScheme(MethodType.Structured_Data_Cluster, MethodType.Black, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "黑盒-结构化数据-聚类任务执行成功");
            } catch (Exception e) {
                log.error("黑盒-结构化数据-聚类执行失败");
                result.put("code", 100);
                result.put("msg", "黑盒-结构化数据-聚类任务执行失败");
                e.printStackTrace();
            }
        }

        // 3.15 白盒-结构化数据-聚类
        if (StrUtil.isNotEmpty(structured_data_cluster) && radio_type.equals(MethodType.White)) {
            //5 白盒-结构化数据-聚类
            log.info("运行：{}-结构化数据-聚类", radio_type);
            String JsonFileName = "schemeJson/white/white-Structured_Data_Cluster.json";
            String schemeName = "白盒-结构化数据-聚类";
            try {
                testSchemeService.doScheme(MethodType.Structured_Data_Cluster, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "白盒-结构化数据-聚类任务执行成功");
            } catch (Exception e) {
                log.error("白盒-结构化数据-聚类执行失败");
                result.put("code", 100);
                result.put("msg", "白盒-结构化数据-聚类任务执行失败");
                e.printStackTrace();
            }

        }


        // 以下是数据集类型：
        // 数据集-可见光-目标检测
        if (StrUtil.isNotEmpty(isible_light_detection) && radio_type.equals(MethodType.DataSet)) {
            //12.数据集-可见光-目标检测
            log.info("运行：{}-数据集-可见光-目标检测", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Visible_Img_Target_Detection.json";
            String schemeName = "数据集-可见光-目标检测";
            try {
                testSchemeService.doDataSetScheme(MethodType.Visible_Img_Target_Detection, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-可见光-目标检测任务执行成功");
            } catch (Exception e) {
                log.error("数据集-可见光-目标检测执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-可见光-目标检测任务执行失败");
                e.printStackTrace();
            }
        }


        //  数据集-可见光-图像分类
        if (StrUtil.isNotEmpty(isible_light_img) && radio_type.equals(MethodType.DataSet)) {
            //13.数据集-可见光-图像分类
            log.info("运行：{}-数据集-可见光-图像分类", radio_type);
            String dataset_Visible_Img_Img_Classfication_JsonFileName = "schemeJson/dataset/dataset-Visible_Img_Img_Classfication.json";
            String dataset_Visible_Img_Img_Classfication_schemeName = "数据集-可见光-图像分类";
            try {
                testSchemeService.doDataSetScheme(MethodType.Visible_Img_Img_Classfication, MethodType.White, api, dataset_Visible_Img_Img_Classfication_JsonFileName, boolean_isTask, dataset_Visible_Img_Img_Classfication_schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-可见光-图像分类任务执行成功");
            } catch (Exception e) {
                log.error("数据集-可见光-图像分类执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-可见光-图像分类任务执行失败");
                e.printStackTrace();
            }
        }

        //  数据集-可见光-目标跟踪
        if (StrUtil.isNotEmpty(isible_light_tracking) && radio_type.equals(MethodType.DataSet)) {
            //13.数据集-可见光-目标跟踪
            log.info("运行：{}-数据集-可见光-目标跟踪", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Visible_Img_Target_Trace.json";
            String schemeName = "数据集-可见光-目标跟踪";
            try {
                testSchemeService.doDataSetScheme(MethodType.Visible_Img_Target_Trace, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-可见光-目标跟踪分类任务执行成功");
            } catch (Exception e) {
                log.error("数据集-可见光-目标跟踪执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-可见光-目标跟踪任务执行失败");
                e.printStackTrace();
            }
        }


        // 数据集-红外-目标检测
        if (StrUtil.isNotEmpty(red_outer_detection) && radio_type.equals(MethodType.DataSet)) {
            //14.数据集-红外-目标检测
            log.info("运行：{}-数据集-红外-目标检测", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Infrared_Ray_Target_Detection.json";
            String schemeName = "数据集-红外-目标检测";
            try {
                testSchemeService.doDataSetScheme(MethodType.Infrared_Ray_Target_Detection, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-红外-目标检测任务执行成功");
            } catch (Exception e) {
                log.error("数据集-红外-目标检测执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-红外-目标检测任务执行失败");
                e.printStackTrace();
            }
        }


        // 数据集-红外-图像分类
        if (StrUtil.isNotEmpty(red_outer_img) && radio_type.equals(MethodType.DataSet)) {
            //15.数据集-红外-图像分类
            log.info("运行：{}-数据集-红外-图像分类", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Infrared_Ray_Img_Classfication.json";
            String schemeName = "数据集-红外-图像分类";
            try {
                testSchemeService.doDataSetScheme(MethodType.Infrared_Ray_Img_Classfication, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-红外-图像分类任务执行成功");
            } catch (Exception e) {
                log.error("数据集-红外-图像分类执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-红外-图像分类任务执行失败");
                e.printStackTrace();
            }

        }

        // 数据集-红外-目标跟踪
        if (StrUtil.isNotEmpty(red_outer_tracking) && radio_type.equals(MethodType.DataSet)) {
            //15.数据集-红外-目标跟踪
            log.info("运行：{}-数据集-红外图像-目标跟踪", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Infrared_Ray_Target_Trace.json";
            String schemeName = "数据集-红外图像-目标跟踪";
            try {
                testSchemeService.doDataSetScheme(MethodType.Infrared_Ray_Target_trace, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-红外图像-目标跟踪任务执行成功");
            } catch (Exception e) {
                log.error("数据集-红外图像-目标跟踪执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-红外图像-目标跟踪任务执行失败");
                e.printStackTrace();
            }

        }


        // 数据集-视频-目标检测
        if (StrUtil.isNotEmpty(video_target_detection) && radio_type.equals(MethodType.DataSet)) {
            //15.数据集-视频-目标检测
            log.info("运行：{}-数据集-视频-目标检测", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Video_Target_Detection.json";
            String schemeName = "数据集-视频-目标检测";
            try {
                testSchemeService.doDataSetScheme(MethodType.Video_Detection, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-视频-目标检测任务执行成功");
            } catch (Exception e) {
                log.error("数据集-视频-目标检测执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-视频-目标检测任务执行失败");
                e.printStackTrace();
            }
        }

        // 数据集-视频-目标跟踪
        if (StrUtil.isNotEmpty(video_target_trace) && radio_type.equals(MethodType.DataSet)) {
            //15.数据集-红外-目标跟踪
            log.info("运行：{}-数据集-视频-目标跟踪", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Video_Target_Trace.json";
            String schemeName = "数据集-视频-目标跟踪";
            try {
                testSchemeService.doDataSetScheme(MethodType.Video_Target_Trace, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-视频-目标跟踪任务执行成功");
            } catch (Exception e) {
                log.error("数据集-视频-目标跟踪执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-视频-目标跟踪任务执行失败");
                e.printStackTrace();
            }

        }


        // 数据集-结构数据化-分类
        if (StrUtil.isNotEmpty(structured_data_classification) && radio_type.equals(MethodType.DataSet)) {
            //15.数据集-结构数据化-分类
            log.info("运行：{}-数据集-结构数据化-分类", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Structured_Data_Classification.json";
            String schemeName = "数据集-结构数据化-分类";
            try {
                testSchemeService.doDataSetScheme(MethodType.Structured_Data_Classification, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-结构数据化-分类任务执行成功");
            } catch (Exception e) {
                log.error("数据集-结构数据化-分类执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-结构数据化-分类任务执行失败");
                e.printStackTrace();
            }
        }

        // 数据集-结构数据化-回归
        if (StrUtil.isNotEmpty(structured_data_regression) && radio_type.equals(MethodType.DataSet)) {
            //15.数据集-结构数据化-回归
            log.info("运行：{}-数据集-结构数据化-回归", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Structured_Data_Regression.json";
            String schemeName = "数据集-结构数据化-回归";
            try {
                testSchemeService.doDataSetScheme(MethodType.Structured_Data_Regression, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-结构数据化-回归任务执行成功");
            } catch (Exception e) {
                log.error("数据集-结构数据化-回归执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-结构数据化-回归任务执行失败");
                e.printStackTrace();
            }

        }

        // 数据集-结构数据化-聚类
        /**
        if (StrUtil.isNotEmpty(structured_data_cluster) && radio_type.equals(MethodType.DataSet)) {
            //15.数据集-结构数据化-聚类
            log.info("运行：{}-数据集-结构数据化-聚类", radio_type);
            String JsonFileName = "schemeJson/dataset/dataset-Structured_Data_Cluster.json";
            String schemeName = "数据集-结构数据化-聚类";
            try {
                testSchemeService.doScheme(MethodType.Structured_Data_Cluster, MethodType.White, api, JsonFileName, boolean_isTask, schemeName, category, heads);
                result.put("code", 0);
                result.put("msg", "数据集-结构数据化-聚类任务执行成功");
            } catch (Exception e) {
                log.error("数据集-结构数据化-聚类执行失败");
                result.put("code", 100);
                result.put("msg", "数据集-结构数据化-聚类任务执行失败");
                e.printStackTrace();
            }

        }
         ***/


        // 数据集-行人重识别    数据集-语义分割
        if (StrUtil.isNotEmpty(visible_img_img_reid) || StrUtil.isNotEmpty(visible_img_img_segmentation) && radio_type.equals(MethodType.DataSet)) {
            //15.数据集-红外-目标跟踪
            log.info("运行：{}-数据集-视频-目标跟踪", radio_type);
            result.put("code", 0);
            result.put("msg", "行人重识别或语义分割没有数据集任务方案");

        }


        return result;
    }


}

