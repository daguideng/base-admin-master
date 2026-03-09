package cn.huanzi.qch.baseadmin.adas.controller;

import cn.huanzi.qch.baseadmin.adas.service.*;
import cn.huanzi.qch.baseadmin.entity.Adas_move_cases;
import cn.huanzi.qch.baseadmin.entity.Adas_move_scenario;
import cn.huanzi.qch.baseadmin.entity.Adas_scene;
import cn.huanzi.qch.baseadmin.hjj.util.ReadJson;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 运行指定场景：
 */
@RestController
@RequestMapping("/scene")
public class RunScene {


    Log log = LogFactory.get();

    @Resource
    private RunSceneService runSceneService;

    @Resource
    private MoveScenario moveScenario;

    @Resource
    private ReadExcelUtil ReadExcelUtil ;

    @Resource
    private AdasMoveCasesService adasMoveCasesService ;

    @Resource
    private SFTPUtils sFTPUtils;

    @GetMapping("/run")
    public ModelAndView UpalodMethodMenu() {
        return new ModelAndView("scene/run");
    }


    @GetMapping("/querymovescen")
    public JSONObject queryMove_scenario() throws IOException {

        Adas_move_scenario adas_move_scenario = moveScenario.query(1);

        Map<String, Object> map = new HashMap<>();

        String[] choice_laws = adas_move_scenario.getChoice_laws().split(",");


        Map<String, String> choice_laws_map = new LinkedHashMap<>();
        List<Object> choice_laws_list = new ArrayList<>();
        choice_laws_map.put("id", "");
        choice_laws_map.put("name", "请选择");
        choice_laws_list.add(choice_laws_map);
        for (String value : choice_laws) {
            Map<String, String> choice_laws2_map = new LinkedHashMap<>();
            choice_laws2_map.put("id", value);
            choice_laws2_map.put("name", value);
            choice_laws_list.add(choice_laws2_map);

        }

        map.put("choice_laws", choice_laws_list);

        JSONObject jsonObject = new JSONObject(map);
        log.info("前端:move_scenario查询结果:{}", jsonObject);

        return jsonObject;

    }


    /**
     * api与直播地址绑定
     * @param api
     * @return
     * @throws IOException
     */
    @GetMapping("/bindquery")
    public Map<String,Object> queryLiveUrl(@RequestParam String api ) throws IOException {

        Adas_scene adas_scene = runSceneService.query(1);
        String[] apis = adas_scene.getApi().split(",");
        String[] PresetIds = adas_scene.getPreset_id().split(",");
        String[] livesUrls = adas_scene.getRemark().split(",");
        Map<String,Object> map = new HashMap<>();

        for(int i = 0 ; i<apis.length; i++ ){
            if(apis[i].equals(api)){
                map.put("preset_id",PresetIds[i]);
                map.put("url",livesUrls[i]);
                return map;
            }
        }
        return map ;
    }

    @GetMapping("/query")
    public JSONObject queryAdas_scence() throws IOException {

        Adas_scene adas_scene = runSceneService.query(1);

        Map<String, Object> map = new HashMap<>();

        String[] apis = adas_scene.getApi().split(",");
        String[] scene_ids = adas_scene.getScene_ids().split(",");
        String[] preset_ids = adas_scene.getPreset_id().split(",");
        String[] sources = adas_scene.getSource().split(",");




        Map<String, String> api_map1 = new LinkedHashMap<>();
        List<Object> api_list = new ArrayList<>();
        api_map1.put("id", "");
        api_map1.put("name", "请选择");
        api_list.add(api_map1);
        for (String value : apis) {
            Map<String, String> api_map = new LinkedHashMap<>();
            Map<String, String> lives_map = new LinkedHashMap<>();
            api_map.put("id", value);
            api_map.put("name", value);
        //    lives_map.put("")

            api_list.add(api_map);

        }

        Map<String, String> preset_ids_map1 = new LinkedHashMap<>();
        List<Object> preset_ids_list = new ArrayList<>();
        preset_ids_map1.put("id", "");
        preset_ids_map1.put("name", "请选择");
        preset_ids_list.add(preset_ids_map1);
        for (String value : preset_ids) {
            Map<String, String> preset_ids_map = new LinkedHashMap<>();
            preset_ids_map.put("id", value);
            preset_ids_map.put("name", value);
            preset_ids_list.add(preset_ids_map);
        }


        Map<String, String> source_map1 = new LinkedHashMap<>();
        List<Object> source_list = new ArrayList<>();
        source_map1.put("id", "");
        source_map1.put("name", "请选择");
        source_list.add(source_map1);
        for (String value : sources) {
            Map<String, String> source_map = new LinkedHashMap<>();
            source_map.put("id", value);
            source_map.put("name", value);
            source_list.add(source_map);
        }


        map.put("api", api_list);
        map.put("preset_id", preset_ids_list);
        map.put("source", source_list);


        JSONObject jsonObject = new JSONObject(map);
        log.info("前端:adas_scene查询结果:{}", jsonObject);

        return jsonObject;

    }


    @PostMapping("/movescene")
    public Map<String, Object> moveScene(@RequestBody(required = false) Adas_move_cases adas_move_cases, StringBuffer sb) throws IOException {

        String scenario_editor_id = adas_move_cases.getScenario_editor_id().trim();
        String choice_laws = adas_move_cases.getChoice_laws().trim();
        String select_case = adas_move_cases.getSelect_case().trim();

        Map<String, Object> res = new HashMap<>();
        res.put("code", "0");


        try {
            Long begin = System.currentTimeMillis();
            String result = sFTPUtils.downXoscFile(scenario_editor_id, choice_laws, select_case);
            Long end = System.currentTimeMillis();
            // log.info("上传花费时间: ",(end-start) + "毫秒");
            log.info("BufferedOutputStream生成文件耗时:{} 毫秒", (end - begin));

            adasMoveCasesService.insertData(adas_move_cases);
            JSONObject jsonObject = new JSONObject(adas_move_cases);
            log.info("保存数据为:{}",jsonObject);

            res.put("msg", result + " 场景拷备成功!");
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject(adas_move_cases);
            sb.append(jsonObject);
            log.error("拷备失败的场景信息:{}",jsonObject);
            res.put("msg", "场景拷备失败!确认场景id是否存在!");
            e.printStackTrace();
        }

        log.info("返回前端json结果:{}", res);

        return res;

    }


    /**
     * 批量上传
     * @param
     * @return
     * @throws IOException
     */
    @PostMapping("/batchupload")
    public Map<String, Object> batchUploadExcel(@RequestParam("file") MultipartFile file) throws Exception {

        Map<String,Object>  map = new HashMap<>();

        File excelPath = ReadExcelUtil.getFile(file);
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(excelPath.toString()), 0);
        /**
         *  第一个参数是表头所在行；第二个参数是读取数据开始行，会自动过滤掉表头行；第三个参数是实体类
         *  如果行记录所有列都为空，则会跳过该行记录，不创建对象。只要有一个列是不为空的，就会创建对象
         **/
        List<Adas_move_cases> read = reader.read(0, 0, Adas_move_cases.class);
        reader.close();
        StringBuffer sb = new StringBuffer();
        for (Adas_move_cases adas_move_cases : read) {

            JSONObject  jsonObject = new JSONObject(adas_move_cases);
            log.info("jsonObject返回结果:{}",jsonObject);
            this.moveScene(adas_move_cases,sb);

        }

        if(sb.length() == 0) {
            map.put("code",0);
            map.put("msg", "全部数据批量导入成功!");
        }else {
            map.put("code",800);
            map.put("msg",sb.toString());
            log.info("导入失败的case:{}",sb.toString());
        }


        return map ;
    }



    @GetMapping("/downexcel")
    public void downLoadExcelModel(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {


        Map<String,Object>  map = new HashMap<>();

       // String filePathx = "adas/download/test.xlsx";

        ClassPathResource classPathResource = new ClassPathResource("adas/download/test.xlsx");
        InputStream inputStream = classPathResource.getStream();
        File file = ReadJson.asFileExcel(inputStream);//手动转换：InputStream To File
        String filePathx = file.toString();

       // String filePathx = "/Users/dengdagui/Documents/install/test/test.xlsx" ;
        File f = new File(filePathx);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            map.put("code",500);
            map.put("msg","服务器模板不存在,请联系管理员！");
            JSONObject jsonObject = new JSONObject(map);
            log.info("下载模板信息:{}",jsonObject);
            return ;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;

        isOnLine = Boolean.TRUE ;
        response.reset(); // 非常重要
        if (isOnLine) { // 在线打开方式
            URL u = new URL("file:///" + filePathx);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
            // 文件名应该编码成UTF-8
        } else { // 纯下载方式
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        br.close();
        out.close();


        map.put("code",0);
        map.put("msg","下载模板成功!");

        JSONObject jsonObject = new JSONObject(map);
        log.info("下载模板信息:{}",jsonObject);


    }








    @PostMapping("/comment")
    public Map<String, Object> uploadImgClassfication(@RequestBody(required = false) Adas_scene scene) throws IOException {


       // 以下以取巧的方式以前端显示api与user_id相对应
        String api = scene.getApi().trim();
        String []user_ids ={"10.10.12.128","192.168.0.88","192.169.1.66","192.168.100.188","192.168.88.166","198.168.888.168"};
        Adas_scene adas_scene = runSceneService.query(1);
        String [] apis = adas_scene.getApi().split(",");
        List<String>listindex = Arrays.asList(apis);
        int index =listindex.indexOf(api);

        String user_id =  user_ids[index];


        // 不管前端得到api是多少ip，强制都是最后一个可用的后端ip
        api = apis[apis.length-1];


        // api  /record/execute
        String scene_ids = scene.getScene_ids().trim();
        String preset_id = scene.getPreset_id().trim();
        String source = scene.getSource().trim();

        JSONObject submap = new JSONObject();
        submap.set("api", api);
        submap.set("scene_ids", scene_ids);
        submap.set("preset_id", preset_id);
        submap.set("source", source);


        log.info("测试提交信息为:{}", submap);


        try {
            this.getbatch_id(api, user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  Map<String, Object> jsonx = new HashMap<>(10);
        JSONObject json = JSONUtil.createObj();

        // scene_ids    = "[\"AEB056\"]";

        String[] scene_idss = scene_ids.split(",");

        List<String> list = new ArrayList<>();
        for (String scene_id : scene_idss) {
            list.add(scene_id);
        }


        json.set("scene_ids", list);
        json.set("preset_id", preset_id);
        json.set("source", source);
        json.set("user_id", user_id);


        //  System.out.println("json---->"+json);


        // 添加请求头信息
        Map<String, String> heads = new HashMap<>(10);
        // 使用json发送请求，下面的是必须的
        heads.put("Content-Type", "application/json;charset=UTF-8");
        heads.put("accept", "application/json");

        /**
         ** headerMap是添加的请求头，
         body是传入的参数，这里选择json，后端使用@RequestBody接收
         */

        String response = null;
        try {

            response = HttpRequest.post(api + "/record/execute")
                    .body(String.valueOf(json))
                    .headerMap(heads, false)
                    .execute().body();

            log.info("执行任务运行execute结果:{}", response);

            //有个坑超过21秒会导致失效，注意
        } catch (Exception e) {
            e.printStackTrace();
        }


        // log.info("运行task返回结果:{}", response);


        Map<String, Object> res = new HashMap<>();
        res.put("code", "0");
        res.put("msg", response);


        log.info("返回前端json结果:{}", res);


        return res;

    }


    /**
     * adas定时器运行
     * https://blog.csdn.net/qq_31122833/article/details/82783479
     */



   // @Scheduled(cron = "*/6000 * * * * ?")    //每天23点定时运行一键测试    "0 0 23 * * ?"    "*/5 * * * * ?"
    /***
    public void crontabRun() throws Exception {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(System.currentTimeMillis());
        log.info("一键测试定时器开始时间:{}", time);


        log.info("定时器运行一键测试开始：****");
        long begin = System.currentTimeMillis();

        String api = "http://172.26.193.10:31838";
        String url = api + "/api/record/execute";
        Map<String, String> map = new HashMap<>();
        map.put("preset_id", "64db3f1a53529ca687eae97f");
        map.put("user_id", "31f3-5092-a4b5-53a9-a9e2");

        String json_name = "adas/crontabTime/";




        


        //1. LKA 运行
        String LKA_Path = json_name + "LKA.json";
        StringBuffer lka_bodys = ReadJson.adasSendInfo(LKA_Path, map);
        log.info("LKA发送body数据为:{}", lka_bodys);
        Long sleepTime = Long.valueOf(3600000);      //1小时
        Long sleepwhile_Time = Long.valueOf(50000);  //5分钟

        HttpResponse lka_response = HttpRequest.post(url)
                .body(lka_bodys.toString())
                .timeout(5 * 60 * 1000)
                .execute();


        while (lka_response.body().contains("\"code\":5002")) {

            lka_response = HttpRequest.post(url)
                    .body(lka_bodys.toString())
                    .timeout(5 * 60 * 1000)
                    .execute();

            Thread.sleep(sleepwhile_Time);

            log.error("lka_response_exception:{}", lka_response.body());

        }


        log.info("lka_response:{}", lka_response.body());

        Thread.sleep(sleepTime);


        //2. LDW 运行
        String LDW_Path = json_name + "LDW.json";
        StringBuffer ldw_bodys = ReadJson.adasSendInfo(LDW_Path, map);
        log.info("LDW发送body数据为:{}", ldw_bodys);


        HttpResponse ldw_response = HttpRequest.post(url)
                .body(ldw_bodys.toString())
                .timeout(5 * 60 * 1000)
                .execute();


        while (ldw_response.body().contains("\"code\":5002")) {

            ldw_response = HttpRequest.post(url)
                    .body(ldw_bodys.toString())
                    .timeout(5 * 60 * 1000)
                    .execute();

            Thread.sleep(sleepwhile_Time);

            log.error("ldw_response_exception:{}", ldw_response.body());

        }


        log.info("ldw_response:{}", ldw_response.body());
        Thread.sleep(sleepTime);


        //3. SAS 运行
        String SAS_Path = json_name + "SAS.json";
        StringBuffer sas_bodys = ReadJson.adasSendInfo(SAS_Path, map);
        log.info("SAS发送body数据为:{}", sas_bodys);


        HttpResponse sas_response = HttpRequest.post(url)
                .body(sas_bodys.toString())
                .timeout(5 * 60 * 1000)
                .execute();


        while (sas_response.body().contains("\"code\":5002")) {

            sas_response = HttpRequest.post(url)
                    .body(sas_bodys.toString())
                    .timeout(5 * 60 * 1000)
                    .execute();

            Thread.sleep(sleepwhile_Time);

            log.error("sas_response_exception:{}", sas_response.body());

        }


        log.info("sas_response:{}", sas_response.body());
        Thread.sleep(sleepTime);


        //4. BSD 运行
        String BSD_Path = json_name + "BSD.json";
        StringBuffer bsd_bodys = ReadJson.adasSendInfo(BSD_Path, map);
        log.info("BSD发送body数据为:{}", bsd_bodys);

        HttpResponse bsd_response = HttpRequest.post(url)
                .body(bsd_bodys.toString())
                .timeout(5 * 60 * 1000)
                .execute();


        while (bsd_response.body().contains("\"code\":5002")) {

            bsd_response = HttpRequest.post(url)
                    .body(bsd_bodys.toString())
                    .timeout(5 * 60 * 1000)
                    .execute();

            Thread.sleep(sleepwhile_Time);

            log.error("bsd_response_exception:{}", bsd_response.body());

        }


        log.info("bsd_response:{}", bsd_response.body());
        Thread.sleep(sleepTime);


        //5. AEB 运行
        String AEB_Path = json_name + "AEB.json";
        StringBuffer aeb_bodys = ReadJson.adasSendInfo(AEB_Path, map);
        log.info("AEB发送body数据为:{}", aeb_bodys);


        HttpResponse aeb_response = HttpRequest.post(url)
                .body(aeb_bodys.toString())
                .timeout(5 * 60 * 1000)
                .execute();


        while (aeb_response.body().contains("\"code\":5002")) {

            aeb_response = HttpRequest.post(url)
                    .body(aeb_bodys.toString())
                    .timeout(5 * 60 * 1000)
                    .execute();

            Thread.sleep(sleepwhile_Time);

            log.error("bsd_response_exception:{}", aeb_response.body());

        }


        log.info("aeb_response:{}", aeb_response.body());


        long end = System.currentTimeMillis();
        log.info("ADAS一键测试总耗时:{} 毫秒", (end - begin));


    }

     ***/


    /**
     * 查询info
     */
    public void getbatch_id(String api, String user_id) {

        //    String url = api + "/record/task/info" ;

        String url = api + "/record/task/info?user_id=" + user_id;


        HttpResponse response = null;
        try {
            response = HttpRequest.get(url).timeout(20000)
                    .header("Content-Type", "application/json").execute();


            String res = response.body();

            log.info("查询info返回结果：{}", res);

            JSONObject jsonObject = new JSONObject(res);


            String batch_id = (String) jsonObject.get("batch_id");


            log.info("查询info返回batch_id：{}", batch_id);


            String url_kill = api + "/record/stop";

            JSONObject map = new JSONObject();


            map.set("batch_id", batch_id);

            if (!"".equals(batch_id)) {

                String response_stop = HttpRequest.post(url_kill)
                        .body(String.valueOf(map))
                        .execute().body();

                log.info("kill掉response_stop:{}", response_stop);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * test
     */
    @GetMapping("/test")
    public List<Object> getTest() {

        List<Object> list = new ArrayList<>();
        Map<Object, Object> map = new HashMap<>();

        map.put("name", "menu item 1");
        map.put("id", "1");


        list.add(map);
        //  String result = "[{name: 'menu item 1', id: 1}]";

        return list;

    }


    /**
     * 批量自定场景运行：
     */
    /**
     * 批量上传
     * @param
     * @return
     * @throws IOException
     */
    @PostMapping("/customizebatupload")
    public Map<String, Object> customizebatuploadExcel(@RequestParam("file") MultipartFile file) throws Exception {

        Map<String,Object>  map = new HashMap<>();

        File excelPath = ReadExcelUtil.getFile(file);
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(excelPath.toString()), 0);
        /**
         *  第一个参数是表头所在行；第二个参数是读取数据开始行，会自动过滤掉表头行；第三个参数是实体类
         *  如果行记录所有列都为空，则会跳过该行记录，不创建对象。只要有一个列是不为空的，就会创建对象
         **/
        List<Adas_scene> read = reader.read(0, 0, Adas_scene.class);
        reader.close();
        StringBuffer sb = new StringBuffer();



        String user_id = "10.10.12.188" ;
        String api = read.get(0).getApi().trim();
        String preset_id = read.get(0).getPreset_id().trim();
        //1.执行之前把正在运行的对列都给删除掉：
        this.getbatch_id(api, user_id);



        Map<String,String>  source_map = new LinkedHashMap(50);

        // 1.为了得到：source是key:key 进行分组：
        for (Adas_scene adas_scene : read) {

            JSONObject  jsonObject = new JSONObject(adas_scene);
            log.info("jsonObject返回结果:{}",jsonObject);

            String source = adas_scene.getSource();
            source_map.put(source,source);

        }



        Map<String,Object>  msg_map = new LinkedHashMap(50);


        // 2.循环编历：
        for (Map.Entry<String, String> entry : source_map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 在循环中使用key和value进行必要的操作

            for (Adas_scene adas_scene : read) {

                JSONObject  jsonObject = new JSONObject(adas_scene);
             //   log.info("jsonObject返回结果:{}",jsonObject);

                String source = adas_scene.getSource();
                List<String>  list = new ArrayList<>();

                if(source.equals(key)){
                    String scene_ids = adas_scene.getScene_ids();
                    list.add(scene_ids);
                    msg_map.put("api",api);
                    msg_map.put("scene_ids",list);
                    msg_map.put("preset_id",preset_id);
                    msg_map.put("source",source);
                }
            }

        }


        this.DorecordExecute(msg_map,api);

        if(sb.length() == 0) {
            map.put("code",0);
            map.put("msg", "自定义批量上传场景运行成功!");
        }else {
            map.put("code",800);
            map.put("msg",sb.toString());
            log.info("自定义批量上传场景运行失败:{}",sb.toString());
        }


        return map ;
    }


    public Map<String,Object> DorecordExecute(Map<String,Object> map,String api){


        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // 在循环中使用key和value进行必要的操作


            JSONObject json = JSONUtil.createObj();
            json.set(key, value);



            // 添加请求头信息
            Map<String, String> heads = new HashMap<>(50);
            // 使用json发送请求，下面的是必须的
            heads.put("Content-Type", "application/json;charset=UTF-8");
            heads.put("accept", "application/json");

            /**
             ** headerMap是添加的请求头，
             body是传入的参数，这里选择json，后端使用@RequestBody接收
             */

            String response = null;
            try {
                response = HttpRequest.post(api + "/record/execute")
                        .body(String.valueOf(json))
                        .headerMap(heads, false)
                        .execute().body();

                log.info("场景运行成功:{}",json);
                log.info("执行任务运行execute结果:{}", response);


                //有个坑超过21秒会导致失效，注意
            } catch (Exception e) {
                e.printStackTrace();
            }



        }



        /**
        String user_id = "10.10.12.188," ;

        String api = (String) map.get("api");
        List scene_ids =  (List) map.get("scene_ids");
        String preset_id = (String) map.get("preset_id");
        String source = (String) map.get("source");

        log.info("api:{},scene_ids:{},preset_id:{},source:{}",api,scene_ids,preset_id,source);

        System.out.printf("11111111111");
         ***/


        /**
        String[] scene_idss = scene_ids.split(",");

        List<String> list = new ArrayList<>();
        for (String scene_id : scene_idss) {
            list.add(scene_id);
        }
         **/





        Map<String,Object>  m = new HashMap<>();
       // m.put("scene_ids",scene_ids);
        return null ;



    }



}
