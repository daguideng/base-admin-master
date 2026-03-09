package cn.huanzi.qch.baseadmin.adas.controller;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;

import cn.huanzi.qch.baseadmin.adas.service.*;
import cn.huanzi.qch.baseadmin.entity.Adas_move_cases;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.mongodb.MongoClientSettings;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.DoubleStream;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.*;
import org.bson.Document;



@RestController
@RequestMapping("/scene")
public class SceneRestore {

    /**
     * /scene/scenario_editor
     * 场景还原到编辑器:
     */

    Log log = LogFactory.get();

    @Resource
    private RunToSceneEditorService RunToSceneEditorService;


    @GetMapping("/scenario_editor")
    public ModelAndView UpalodMethodMenu() {
        return new ModelAndView("scene/scenario_editor");
    }


    @PostMapping("/editor")
    public Map<String, Object> queryMove_scenario(@RequestBody(required = false) Adas_move_cases adas_move_cases) throws Exception {

        String choice_laws = adas_move_cases.getChoice_laws();
        String select_case = adas_move_cases.getSelect_case();
        String sceneEditId = adas_move_cases.getScenario_editor_id();

        Map<String, Object> res = new HashMap<>();
        res.put("code", "0");

        try {
            RunToSceneEditorService.downLoadNfsXosc(choice_laws, select_case, sceneEditId);
            res.put("msg","场景还原成功!");
        }catch (Exception e){
            res.put("msg","场景还原失败!请检查编辑器下xosc文件权限设置正确!");
        }


        return res;
    }


    /**
     * 批量个性ego,GVT的速度，批量生成场景,以便提高效率:
     */
    @PostMapping("/batmodiyspeed")
    public Map<String, Object> updateEgoGvtSpeed(@RequestBody(required = false) Adas_move_cases adas_move_cases) throws Exception {

        String choice_laws = adas_move_cases.getChoice_laws();
        String select_case = adas_move_cases.getSelect_case();
        String sceneEditId = adas_move_cases.getScenario_editor_id();

        Map<String, Object> res = new HashMap<>();
        res.put("code", "0");

        try {
            RunToSceneEditorService.downLoadNfsXosc(choice_laws, select_case, sceneEditId);
            res.put("msg","场景还原成功!");
        }catch (Exception e){
            res.put("msg","场景还原失败!请检查编辑器下xosc文件权限设置正确!");
        }


        return res;
    }



    /**
    修改评分表内容
    ***/

    /***
@GetMapping("/modiyscene")
public class MongoDBExample {

    public Map<String,Object> modiyscene() {
        // 连接到MongoDB
        try (MongoClient mongoClient = MongoClients.create("mongodb://admin:Chushi123@10.10.12.62:17017")) {
            // 选择数据库
            MongoDatabase database = mongoClient.getDatabase("adas");

            // 选择集合
            MongoCollection<Document> collection = database.getCollection("record");

            // 创建要更新的文档条件
            Document filter = new Document("passed", "true");

            // 创建要更新的内容
            Document update = new Document("$set", new Document("passed", "false"));

            // 执行更新操作
            collection.updateOne(filter, update);

            System.out.println("Document updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
  ***/





    /**
    修改评分表内容
    ***/
    @PostMapping("/modiyscene_best/{id}")
    public  Map<String,Object> getExampleById(@PathVariable String id) {
        // MongoDB连接字符串，替换为实际的连接信息
        // 获取自动加密设置



       // AutoEncryptionSettings autoEncryptionSettings = null;
      //  autoEncryptionSettings.builder().disabled(true).build();

        String connectionString = "mongodb://admin:Chushi123@10.10.12.62:17017/";


        // 创建MongoDB客户端设置
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        // 连接到MongoDB
        try {

            MongoClient mongoClient = MongoClients.create(settings);
            // 选择数据库和集合
            MongoDatabase database = mongoClient.getDatabase("adas");
            MongoCollection<Document> collection = database.getCollection("record");


            Map<String,Object> result = new HashMap<>();
            Map<String,Object> map = new HashMap<>();

            // 创建查询条件
            Document query = new Document("id", id);

            // 执行查询操作
            Document document = collection.find(query).first();

            if (document != null) {
                System.out.println("Original Document: " + document);

                // 获取原始文档中的年龄字段值
                boolean originalAge = document.getBoolean("passed", true);

                System.out.println("originalAge-->"+originalAge);
                // 更新文档的年龄字段值
                document.put("passed", false);
                collection.replaceOne(query, document);

                // 查询并输出更新后的文档
                Document updatedDocument = collection.find(query).first();
                System.out.println("Updated Document: " + updatedDocument);


                map.put("update",updatedDocument);
                result.put("code",0000);
                result.put("msg","sucess");
                result.put("result",map);

                return result;

            } else {
                System.out.println("Document not found for query: " + query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null ;
    }









}
