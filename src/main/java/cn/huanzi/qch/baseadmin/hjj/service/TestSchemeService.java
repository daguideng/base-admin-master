package cn.huanzi.qch.baseadmin.hjj.service;

import cn.huanzi.qch.baseadmin.hjj.entity.MethodType;
import cn.huanzi.qch.baseadmin.hjj.util.ReplaceJsonContent;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/*
白盒-可见光-图像分类
 */

@Component
public class TestSchemeService {

    Log log = LogFactory.get();

    @Resource
    private ReplaceJsonContent replaceJsonContent;


    public void doScheme(String type, String test_type, String api, String json_name, Boolean tager, String schemeName, String category, Map<String, String> heads) throws Exception {

        //http://10.10.12.62:10088/api/model?type=visible_img.img_classfication&test_type=black&page=1&per_page=10
        //2.1   被测试对象算法数据查询：
        JSONObject paramMap = new JSONObject();
        paramMap.set("type", type);
        paramMap.set("test_type", test_type);
        paramMap.set("page", 1);
        paramMap.set("per_page", 10);

        // String api = "http://10.10.12.13:5000";

        String uploadURL = api + "/api/model";


        //黑盒  --- 可见光图像 请求结果
//        String result2 = HttpRequest.post(uploadURL)
//                .headerMap(heads, false)//头信息，多个头信息多次调用此方法即可
//                .form(paramMap)//表单内容
//                .timeout(20000)//超时，毫秒
//                .execute().body();

        // 2.1  被测试对象算法数据查询：
        String result_response = HttpRequest.get(uploadURL).headerMap(heads, false).form(paramMap).timeout(5 * 60 * 1000).execute().body();

        Thread.sleep(2000);

        JSONObject json = JSONUtil.toBean(result_response, JSONObject.class);
      //  log.info("组合模型组合结果为：{}", json);


        JSONArray model_items = json.getJSONArray("items");

        String model_id = null ;
        String model_name = null ;
        int labels_count = 0 ;

        //只取model是：update上传的model,只取最新上传的一条
        for(int x = 0;x<model_items.size();x++){
            JSONObject model_map = (JSONObject) model_items.get(x);
            String upload =String.valueOf(model_map.get("location"));
            if(upload.equals("upload")){

                model_id = String.valueOf(model_map.get("id"));
                model_name = String.valueOf(model_map.get("name"));
                labels_count = Integer.valueOf(String.valueOf(model_map.get("labels_count")));

                log.info("model_id:{},model_name:{},labels_count:{}", model_id, model_name,labels_count);
                break;
            }
        }



      //  log.info("model_id:{}", model_items);
      //  Map<String, Object> model_map = (Map<String, Object>) model_items.get(0);


      //  String labels_count = String.valueOf(model_map.get("labels_count"));

       // log.info("model_map_id:{}", model_map.get("id"));


        // 2.2  被测试对象数据集查询：
        // http://10.10.12.62:10088/api/data-set?usage=create_task&type=visible_img.img_classfication&labels_count=5&page=1&per_page=10
        Map<String, Object> datasetmap = new HashMap<>();
        datasetmap.put("labels_count", labels_count);
        datasetmap.put("usage", "create_task");
        datasetmap.put("type", type);
        datasetmap.put("page", 1);
        datasetmap.put("per_page", 10);

        String url_data_set = api + "/api/data-set";
      //  String url_data_set = api + "/api/model";

        Thread.sleep(2000);
        String response_data_set = HttpRequest.get(url_data_set).headerMap(heads, false).form(datasetmap).timeout(5 * 60 * 1000).execute().body();


       // Thread.sleep(2000);

        JSONObject response_data_set_json = JSONUtil.toBean(response_data_set, JSONObject.class);
       // log.info("测数据集组合结果为：{}", response_data_set_json);

        JSONArray data_set_items = response_data_set_json.getJSONArray("items");
      //  log.info("data_set_items:{}", data_set_items);
      //  JSONObject deata_set_map = (JSONObject) data_set_items.get(0);


        String deata_set_id = null ;
        String deata_set_name = null ;
        int data_set_labels_count = 0 ;

        //只取model是：update上传的数据集,只取最新上传的一条数据集
        for(int x = 0;x<data_set_items.size();x++){
            JSONObject deata_set_map = (JSONObject) data_set_items.get(x);
            String upload =String.valueOf(deata_set_map.get("source"));
            if(upload.equals("upload")){

                 data_set_labels_count = Integer.valueOf(String.valueOf(deata_set_map.get("labels_count"))) ;

                 if(data_set_labels_count <= labels_count ) {

                     deata_set_id = String.valueOf(deata_set_map.get("id"));
                     deata_set_name = String.valueOf(deata_set_map.get("name"));
                     log.info("deata_set_id:{},deata_set_name:{},data_set_labels_count:{}", deata_set_id, deata_set_name,data_set_labels_count);
                 }



                break;
            }
        }


        /**     只取model是：第一条数据集
        JSONObject deata_set_map = (JSONObject) data_set_items.get(0);
        String deata_set_id = (String) deata_set_map.get("id");
        String deata_set_name = (String) deata_set_map.get("name");
         **/


        JSONObject replace_map = new JSONObject();




        JSONArray datasets = new JSONArray();
        replace_map.put("algorithm", model_id);
        datasets.put(deata_set_id);

      //  log.info("提交真实的:datasets:{}", datasets);
        replace_map.put("datasets", datasets);
        replace_map.put("category", category);
      //  log.info("category:{},json_name:{}", category, json_name);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(System.currentTimeMillis());

        replace_map.put("name", schemeName + "_" + time);
        replace_map.put("description", schemeName + "_" + time);
        replace_map.put("algorithm_model", test_type);

        log.info("提交方案内容:algorithm:{},deata_set_id:{},deata_set_name:{},datasets:{},category:{},name:{},description:{},algorithm_model:{}",
                model_id, deata_set_id, deata_set_name, datasets, category, schemeName + "_" + time, schemeName + "_" + time, test_type);



        //3.0  提交  黑盒-可见光图像-目标检测：
        //替换：
        // StringBuffer sbf = new StringBuffer();


        //4.0 提交方案：
        // StringBuffer bodys = ReadJson.getReportContent(json_name,replace_map);
        StringBuffer bodys = replaceJsonContent.getDataSetJson(json_name, replace_map);

        log.info("提交方案body内容:{}", bodys.toString());

        //http://10.10.12.62:10088/api/projects
        String scheme_url = api + "/api/projects";
        HttpResponse scheme_response = HttpRequest.post(scheme_url).headerMap(heads, false).body(bodys.toString()).timeout(5 * 60 * 1000).execute();

        Thread.sleep(2000);

        log.info("提交方案返回结果:{}", scheme_response.body());



        //5.0  提交方案到运行任务：
        try {
            this.runTask(tager, bodys.toString(), scheme_response.body(), api, heads);
        }catch (Exception e){
            throw new Exception("提交方案到运行任务报异常");
        }


    }


    /**
     * 是否需要提交方案时提交到任务列表：
     * tager为：true时运行，为false不提交到任务例表：
     */

    public void runTask(Boolean tager, String scheme_from_json, String scheme_respond_body, String api, Map<String, String> heads) throws Exception {

        if (tager == Boolean.TRUE) {
           // log.info("提交方案返回body结果：{}", scheme_respond_body);


            JSONObject taskjsonObject = new JSONObject(scheme_from_json);
            JSONObject project_id_vaule = new JSONObject(scheme_respond_body);
            String project_id = (String) project_id_vaule.get("project_id");
            taskjsonObject.put("project_id",project_id);


          //  log.info("提交方案到运行时body为:{}", taskjsonObject.toString());


            //  log.info("方案提交到运行时body为：{}", scheme_from_json);

            String task_Url = api + "/api/tasks";

            HttpResponse task_resutl = HttpRequest.post(task_Url).headerMap(heads, false).body(taskjsonObject.toString()).timeout(5 * 60 * 1000).execute();

            Thread.sleep(1000);
            log.info("提交方案到运行任务结果:{},提交方案到运行时body为:{}", task_resutl.body(),taskjsonObject.toString());

            JSONObject task_res = new JSONObject(task_resutl.body());
            log.info("task_res:{}",task_res.toString());
            String task_run_error = (String) task_res.get("code");

            log.info("task_run_error:{}",task_run_error);

            //出错则异常向上抛：
            if(null !=task_run_error){

                log.error("message:{},提交方案到运行时body为:{}",task_res.get("message"),taskjsonObject.toString());
                throw new Exception("提交任务时报异常!");
            }



            log.info("运行测试任务！");

        } else {

            log.info("现在设置提交方案时不立即运行测试任务！");
        }

    }


    /**
     * 数据集的方案提交：
     */
    public void doDataSetScheme(String type, String test_type, String api, String json_name, Boolean tager, String schemeName, String category, Map<String, String> heads) throws Exception {

        //http://10.10.12.62:10088/api/model?type=visible_img.img_classfication&test_type=black&page=1&per_page=10


        // 2.2  被测试对象数据集查询：
        // http://172.17.24.152:9000/api/data-set?usage=create_task&type=visible_img.target_detection&page=1&per_page=10


        JSONObject datasetmap = new JSONObject();
     //   datasetmap.put("labels_count", labels_count);
        datasetmap.put("usage", "create_task");
        datasetmap.put("type", type);
        datasetmap.put("page", 1);
        datasetmap.put("per_page", 10);

        String url_data_set = api + "/api/data-set";
        //  String url_data_set = api + "/api/model";

        Thread.sleep(2000);
        String response_data_set = HttpRequest.get(url_data_set).headerMap(heads, false).form(datasetmap).timeout(5 * 60 * 1000).execute().body();


     //   Thread.sleep(2000);

        JSONObject response_data_set_json = JSONUtil.toBean(response_data_set, JSONObject.class);
      //  log.info("测数据集组合结果为：{}", response_data_set_json);

        JSONArray data_set_items = response_data_set_json.getJSONArray("items");
      //  log.info("data_set_items:{}", data_set_items);



        String deata_set_id = null ;
        String deata_set_name = null ;
        int labels_count = 0 ;

        //只取dataset是：upload上传的数据集,只取最新的一条数据集
        for(int x = 0;x<data_set_items.size();x++){
            JSONObject deata_set_map = (JSONObject) data_set_items.get(x);
            String upload =String.valueOf(deata_set_map.get("source"));
            if(upload.equals("upload")){

                deata_set_id = String.valueOf(deata_set_map.get("id"));
                deata_set_name = String.valueOf( deata_set_map.get("name"));
                labels_count =  Integer.valueOf(String.valueOf(deata_set_map.get("labels_count")));

                log.info("source:{},deata_set_id:{},deata_set_name:{},labels_count:{}",upload,deata_set_id, deata_set_name,labels_count);
                break;
            }
        }



        /***
        JSONObject deata_set_map = (JSONObject) data_set_items.get(0);

        String deata_set_id = String.valueOf(deata_set_map.get("id")) ;
        String deata_set_name = String.valueOf(deata_set_map.get("name"));
        String labels_count =  String.valueOf(deata_set_map.get("labels_count"));
         **/




      //  log.info("deata_set_id:{},deata_set_name:{}", deata_set_id, deata_set_name);
        //  replace_map.put("datasets", "8888888");




  //这是重写-------------------------------------------------------------------------
        //http://172.17.24.152:9000/api/model?type=visible_img.target_detection&test_type=white&page=1&per_page=10
        //2.1   被测试对象算法数据查询：  first

        /**
        JSONObject paramfistMap = new JSONObject();
        paramfistMap.set("type", type);
        paramfistMap.set("test_type", test_type);
        paramfistMap.set("page", 1);
        paramfistMap.set("per_page", 10);

        String uploadURL = api + "/api/model";

        String result_fist_response = HttpRequest.get(uploadURL).headerMap(heads, false).form(paramfistMap).timeout(5 * 60 * 1000).execute().body();

        log.info("result_fist_response:{}",result_fist_response);
         ***/

     //   String labels_count = result_fist_response.g


       //http://172.17.24.152:9000/api/model?type=visible_img.target_detection&test_type=white&labels_count=1&page=1&per_page=10

        JSONObject paramMap = new JSONObject();
        paramMap.set("type", type);
        paramMap.set("test_type", test_type);
        paramMap.set("page", 1);
        paramMap.set("per_page", 10);
        paramMap.set("labels_count", labels_count);



        // String api = "http://10.10.12.13:5000";

         String uploadURL = api + "/api/model";


        // 2.1  被测试对象算法数据查询：
        String result_response = HttpRequest.get(uploadURL).headerMap(heads, false).form(paramMap).timeout(5 * 60 * 1000).execute().body();

        Thread.sleep(1000);

        JSONObject json = JSONUtil.toBean(result_response, JSONObject.class);
       // log.info("组合模型组合结果为：{}", json);


        JSONArray model_items = json.getJSONArray("items");
       // log.info("model_id:{}", model_items);



        String model_id = null ;
        String model_name = null ;
        int model_id_labels_count = 0 ;
        //取最新一条算法：
        for(int x = 0;x<model_items.size();x++){
            JSONObject model_map = (JSONObject) model_items.get(x);
            String upload =String.valueOf(model_map.get("location"));
            if(upload.equals("upload")){


                model_id_labels_count =  Integer.valueOf(String.valueOf(model_map.get("labels_count")));

                if(model_id_labels_count >= labels_count){
                    model_id = String.valueOf(model_map.get("id")) ;
                    model_name = String.valueOf(model_map.get("name"));
                    log.info("location:{},model_id:{},model_name:{},model_id_labels_count:{}",upload, model_id, model_name,model_id_labels_count);
                    break;
                }




            }
        }


        /**
        Map<String, Object> model_map = (Map<String, Object>) model_items.get(0);
        JSONObject replace_map = new JSONObject();

       // replace_map.put("algorithm", model_map.get("id"));
       // String labels_count = String.valueOf(model_map.get("labels_count"));

      //  log.info("model_map_id:{}", model_map.get("id"));
       ****/





        /**
        JSONObject replace_map = new JSONObject();

        JSONObject paramMap2 = new JSONObject();
        paramMap2.set("type", type);
        paramMap2.set("test_type", test_type);
        paramMap2.set("page", 1);
        paramMap2.set("per_page", 10);
        paramMap2.put("labels_count", labels_count);


        Thread.sleep(2000);
        String response_model = HttpRequest.get(uploadURL).headerMap(heads, false).form(datasetmap).timeout(5 * 60 * 1000).execute().body();


        Thread.sleep(2000);

        JSONObject response_model_json = JSONUtil.toBean(response_model, JSONObject.class);
        log.info("测数据集组合结果为：{}", response_model_json);

        JSONArray get_model_items = response_model_json.getJSONArray("items");
        log.info("get_model_items:{}", get_model_items);


        String algorithm = null ;
        String algorithm_name = null ;

        for(int x = 0;x<get_model_items.size();x++){
            JSONObject get_model_second_items = (JSONObject) get_model_items.get(x);
            String upload =String.valueOf(get_model_second_items.get("source"));
            if(upload.equals("upload")){


                model_id_labels_count =  Integer.valueOf(String.valueOf(get_model_second_items.get("labels_count")));

                if(labels_count == model_id_labels_count){
                    algorithm = String.valueOf(get_model_second_items.get("id")) ;
                    algorithm_name = String.valueOf(get_model_second_items.get("name"));
                }

                log.info("algorithm:{},algorithm_name:{},model_id_labels_count:{}", algorithm, algorithm_name,model_id_labels_count);
            }
        }

         **/



        //3.projects
        // http://172.17.24.152:9000/api/projects

        JSONObject replace_map = new JSONObject();

        replace_map.set("algorithm", model_id);
        replace_map.set("algorithm_name", model_name);
        replace_map.set("algorithm_model", "");


        JSONArray datasets = new JSONArray();
        datasets.put(deata_set_id);

      //  log.info("提交真实的:datasets:{}", datasets);
        replace_map.set("datasets", datasets);
        replace_map.set("category", category);
     //   log.info("category:{},json_name:{}", category, json_name);
      //  log.info("deata_set_id:{},deata_set_name:{}", deata_set_id, deata_set_name);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(System.currentTimeMillis());

        replace_map.put("name", schemeName + "_" + time);
        replace_map.put("description", schemeName + "_" + time);

        log.info("提交数据集到方案的内容:algorithm:{},deata_set_id:{},deata_set_name:{},datasets:{},category:{},name:{},description:{},algorithm_model:{}",
                model_id, deata_set_id, deata_set_name, datasets, category, schemeName + "_" + time, schemeName + "_" + time, test_type);




        //3.0  提交  黑盒-可见光图像-目标检测：
        //替换：
        // StringBuffer sbf = new StringBuffer();


        //4.0 提交方案：
        // StringBuffer bodys = ReadJson.getReportContent(json_name,replace_map);
        StringBuffer bodys = replaceJsonContent.getDataSetJson(json_name, replace_map);

        log.info("提交方案body内容:{}", bodys.toString());

        //http://10.10.12.62:10088/api/projects
        String scheme_url = api + "/api/projects";
        HttpResponse scheme_response = HttpRequest.post(scheme_url).headerMap(heads, false).body(bodys.toString()).timeout(5 * 60 * 1000).execute();

        Thread.sleep(2000);

        log.info("提交方案返回结果:{}", scheme_response.body());



        //5.0  提交方案到运行任务：
        try {
            this.runTask(tager, bodys.toString(), scheme_response.body(), api, heads);
        }catch (Exception e){
            throw new Exception("提交方案到运行任务报异常");
        }


    }


}
