package cn.huanzi.qch.baseadmin.hjj.service;

import cn.huanzi.qch.baseadmin.hjj.entity.MethodType;
import cn.huanzi.qch.baseadmin.hjj.util.ReadJson;
import cn.huanzi.qch.baseadmin.hjj.util.ReplaceJsonContent;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSetService {


    @Resource
    private ReplaceJsonContent replaceJsonContent;

    Log log = LogFactory.get();



     //MethodType.Text_Entity_Recognition,black,api,json_name,tager,schemeName,category,heads
    public void doScheme(String type, String test_type, String api, String json_name, Boolean tager, String schemeName, Map<String, String> heads,String Extension_No_Attack ) throws Exception{

        //2.1   查询数据集获取最新数据：

        JSONObject datasetmap = new JSONObject();

        //如果是文本-情感分析则：
        datasetmap.set("usage", "create_task");
        datasetmap.set("tag", "all");
        datasetmap.set("name", "");
        datasetmap.set("page", 1);
        datasetmap.set("per_page", 10);
        datasetmap.set("type", type);

        String url_data_set = api + "/api/data-set";



        String response_data_set = HttpRequest.get(url_data_set)
                .headerMap(heads, false)
                .form(datasetmap).timeout(5 * 60 * 1000)
                .execute()
                .body();


        Thread.sleep(2000);
        JSONObject json = JSONUtil.toBean(response_data_set, JSONObject.class);
        log.info("查询数据集结果为：{}", json);


        log.info("type:{}",type);

        Object[] datasetList = json.getJSONArray("items").toArray();

        String dataset_id_value = null ;
        String labels_count = null;

        for(Object ele : datasetList){

            String linejson = ele.toString();

          //  log.info("各种数据集为：{},type:{}", linejson, type);
            JSONObject jsonObject =  JSONUtil.parseObj(linejson);
            log.info("jsonObject:{}",jsonObject);
            log.info("jsonObject.get(\"type\"):{}",jsonObject.get("type"));
            if (jsonObject.get("type").equals(type)){
                log.info("查询条件是匹配相应条件是：{}且最新的一条数据集为{}:",type,jsonObject);
                dataset_id_value = (String) jsonObject.get("id");
                labels_count = String.valueOf(jsonObject.get("labels_count"));
                log.info("数据集类型为:{},且对应最新id为:{}",type,dataset_id_value);
                break;
            }

        }


        //2.2 查询相应数据集对应的模型最新数据：
        // http://172.26.193.10:31674/api/model?type=text.entity_recognition&test_type=black&name=&page=1&per_page=10
        JSONObject replace_map = new JSONObject();
        if(!Extension_No_Attack.equals(MethodType.Extension_No_Attack)) {  //如果是非对抗则需要执行模型的方法：
            replace_map = this.modelMethod(type, test_type, api, heads, schemeName,labels_count);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = sdf.format(System.currentTimeMillis());
            replace_map.put("name", schemeName+"_" + time);
            replace_map.put("description", schemeName+"_" + time);

        }

        /*
        JSONObject datasetmodel = new JSONObject();
        datasetmodel.set("type", type);
        datasetmodel.set("test_type", test_type);   //black
        datasetmodel.set("name", "");
        datasetmodel.set("page", 1);
        datasetmodel.set("per_page", 10);

        String url_datamodel = api + "/api/model";

        String response_datamodel = HttpRequest.get(url_datamodel)
                .headerMap(heads, false)
                .form(datasetmodel).timeout(5 * 60 * 1000)
                .execute()
                .body();


        JSONObject modeldata_json = JSONUtil.toBean(response_datamodel, JSONObject.class);
        log.info("数据集为:{}对应的模型数据结果为：{}", type,modeldata_json);


        JSONArray model_items = modeldata_json.getJSONArray("items");
        Map<String, String> model_map = (Map<String, String>) model_items.get(0);

        // 模板替换做准备：
        Map<String, String> replace_map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(System.currentTimeMillis());
        replace_map.put("alg_id", model_map.get("id"));
        replace_map.put("name", schemeName+"_" + time);
        replace_map.put("description", schemeName+"_" + time);
         */

        //2.3  模型与算法绑定：   extensionr接口;
        // 获取最新的模板数据：
       // StringBuffer bodys = ReadJson.getReportContent(json_name,replace_map);
        StringBuffer bodys = replaceJsonContent.getDataSetJson(json_name,replace_map);


        String dataset_model_url = StrUtil.format("{}/api/data-set/{}/extension", api, dataset_id_value);
        log.info("dataset_model_url:{},api:{},dataset_id_value:{}",dataset_model_url,api,dataset_id_value);
        log.info("数据集id对应的value结果为:{}"+dataset_id_value);
        log.info("发送body结果为:{}",bodys.toString());


        HttpResponse result_response = HttpRequest.post(dataset_model_url)
                .headerMap(heads, false)
                .body(bodys.toString())
                .timeout(5 * 60 * 1000)
                .execute();

        Thread.sleep(2000);

        log.info("extensionr接口返回结果：{}",result_response.body());



    }


    /**
     * 是否需要提交方案时提交到任务列表：
     * tager为：true时运行，为false不提交到任务例表：
     */

    public void  runTask(Boolean tager,String scheme_from_json, String scheme_respond_body,String api,Map<String,String>heads){

        if(tager == Boolean.TRUE) {
            log.info("提交方案返回body结果：{}", scheme_respond_body);
            Map<String, String> map = JSON.parseObject(scheme_respond_body, HashMap.class);

            String key = null;
            String value = null;

            for (Map.Entry<String, String> entry : map.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
            }

            scheme_from_json = scheme_from_json.replaceFirst("\\{", "{" + "\"" + key + "\"" + ":" + "\"" + value + "\"" + ",");

            log.info("方案提交到运行时body为：{}", scheme_from_json);

            String undefined_Url = api + "/api/task/undefined";

            HttpResponse undefined_resutl = HttpRequest.post(undefined_Url)
                    .headerMap(heads, false)
                    .body(scheme_from_json)
                    .timeout(5 * 60 * 1000)
                    .execute();

            log.info("提交方案到运行任务结果：{}", undefined_resutl.body());

            log.info("运行测试任务！");

        }else {

            log.info("现在设置提交方案时不立即运行测试任务！");
        }

    }


    /**
     * 是否需要请求算法而单独做为一个独立的方法
     */
    public JSONObject modelMethod(String type,String test_type,String api,Map<String, String> heads,String schemeName,String labels_count ) throws InterruptedException {


        //2.2 查询相应数据集对应的模型最新数据：
        // http://172.26.193.10:31674/api/model?type=text.entity_recognition&test_type=black&name=&page=1&per_page=10


        JSONObject datasetmodel = new JSONObject();
        datasetmodel.set("type", type);
        datasetmodel.set("test_type", test_type);   //black
        datasetmodel.set("name", "");
        datasetmodel.set("page", 1);
        datasetmodel.set("per_page", 10);
        datasetmodel.set("labels_count",labels_count);  //?


        Thread.sleep(2000);

        String url_datamodel = api + "/api/model";

        String response_datamodel = HttpRequest.get(url_datamodel)
                .headerMap(heads, false)
                .form(datasetmodel).timeout(5 * 60 * 1000)
                .execute()
                .body();


        JSONObject modeldata_json = JSONUtil.toBean(response_datamodel, JSONObject.class);
        log.info("数据集为:{},对应的模型数据结果为：{}", type,modeldata_json);


        JSONArray model_items = modeldata_json.getJSONArray("items");

       // Map.Entry<JSONArray,Object> entry = model_items.entrySet().iterator().next();
        //Map.Entry<String, Object> entry = model_items.entrySet().iterator().next();

        //
        //location:"upload"

        Map<String, String> model_map = (Map<String, String>) model_items.get(0);

        log.error("alg_id:{}",model_map.get("id"));

        // 模板替换做准备：
        JSONObject replace_map = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(System.currentTimeMillis());
        replace_map.put("alg_id", model_map.get("id"));
        replace_map.put("name", schemeName+"_" + time);
        replace_map.put("description", schemeName+"_" + time);
        replace_map.put("descript", schemeName+"_" + time);



        return replace_map;

    }


}
