package cn.huanzi.qch.baseadmin.hjj.controller;

import cn.huanzi.qch.baseadmin.entity.Model_table;
import cn.huanzi.qch.baseadmin.entity.One_click;
import cn.huanzi.qch.baseadmin.hjj.entity.*;
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
import com.github.pagehelper.PageInfo;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

/**
 * 一键运行上传模型：
 */

@RestController
@RequestMapping("/oneclick")
public class Model {

    Log log = LogFactory.get();


    @Resource
    private OneclickServiceV2 oneclickServiceV2;

    @Resource
    private OneclickService oneclickService;

    @Resource
    private ModelTableService modelTableService;


    @Resource
    private HjjUpload hjjUpload;

    @Resource
    private DataSetIncrease dataSetIncrease;

    @Resource
    private ModelService modelService;





    @GetMapping("/modeltable")
    public ModelAndView testsetMethodMenu() {

        return new ModelAndView("oneclick/modeltable");
    }

    /***
     *展现算法模型的表格的数据
     */

    @GetMapping("/showmodel")
    public Map<String,Object> getEntities(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        PageInfo<Model_table> pageInfo = modelTableService.getEntities(page, limit);
//        List<Model_table> modeltable = modelTableService.selectAllQuery();
        Map<String, Object> resultMap = new HashMap<>();
//        ModelResponseEntity modelResponseEntity = new ModelResponseEntity();
//        modelResponseEntity.setCode(0); // 或者其他适当的状态码
//        modelResponseEntity.setMsg("Success");
//        modelResponseEntity.setCount(modeltable.size());
//        modelResponseEntity.setData(pageInfo);
        

        resultMap.put("code", 0);
        resultMap.put("msg", "Success");
        resultMap.put("count", pageInfo.getTotal());
        resultMap.put("data", pageInfo.getList());

        return resultMap;

    }


    /**
     * 按版本查询模型数据
     */
    @GetMapping("/versinquery")
    public Map<String, Object> query(@RequestParam(value = "searchversion") String searchversion,@RequestParam(value = "mySlectType") String mySlectType) {
        log.info("按版本查询模型数据");



       // String searchversion = request.getParameter("searchversion");

        log.info("searchversion:{}",searchversion);
        List<Model_table> pageInfo = modelTableService.queryVersion(searchversion,mySlectType);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "查询成功");
        map.put("count", pageInfo.size());
        map.put("data", pageInfo);
        return map;
    }


    /**
     * 批量上传模型
     * username:$('#username').val(),password:$('#password').val(),api:$('#api').val()
     */
    @PostMapping("/batchuploadmodel")
    public Map<String, Object> batUpdateModel(@Validated @RequestBody OneClickUpload oneClickUpload,HttpServletRequest request) throws Exception {
        log.info("按版本查询模型数据");


        String ids[] = oneClickUpload.getIds();
        String username = oneClickUpload.getUsername();
        String password = oneClickUpload.getPassword();
        String api = oneClickUpload.getApi();

        log.info("ids:{}",ids);
        log.info("username:{}",username);
        log.info("password:{}",password);
        log.info("api:{}",api);


        Map<String, Object> map = new HashMap<>();


        log.info("ids:{}",ids);


        List<Integer> list = new ArrayList<>();

        for(String id: ids){
            list.add(Integer.parseInt(id));
            log.info("id:{}",id);
        }


        List<Model_table> Model_paths = modelTableService.getModelPathById(list);


        Map<String,String> info = new HashMap<>();
       // String api = "http://172.26.193.10:30125";
        info.put("username",username);
        info.put("password",password);
        info.put("api",api);

        /**
        String api = "http://172.26.193.10:30125";
        userinfo.put("username","test");
        userinfo.put("password","123456");
        userinfo.put("api",api);
         **/

        for(Model_table  model_table: Model_paths){
            String model_path = model_table.getModel_path();
            String model_type = model_table.getModel_type();
           // model_type = model_type.replace("_",".");
            String type = model_table.getType();

            //类型如：visible_img.img_classfication
            info.put("type", model_type);

            //如果是算法则调算法上传,否则调数据集上传
            if("model".equals(type)) {


                log.info("model_path:{}", model_path);
                hjjUpload.uploadUnify(null, model_path, request, info);
            }else {
                hjjUpload.uploadDataset(null, model_path, request,  info);

            }
        }


        map.put("code", 0);
        map.put("msg", "查询成功");
//        map.put("count", pageInfo.size());
//        map.put("data", pageInfo);
        return map;
    }



    /**
     * 新增上传模型数据
     */
    @PostMapping("/addmodel")
    public Map<String, Object> addmodel(@RequestBody Model_table model_table) {


        log.info("新增模型数据");
        Map<String, Object> map =modelTableService.save(model_table);


      //  map.put("code", 0);
      //  map.put("msg", "新增成功");
       // map.put("data", model_table);
        return map;
    }



    /**
     * 单独上传算法
     */
    @PostMapping("/v2uploadfile")
    public Map<String, Object> oneClickUploadV2(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {


        //0.得到上传的文件参数：
        String versin = request.getParameter("version");
        log.info("version:{}", versin);

        String type = request.getParameter("type");
        log.info("type:{}", type);

        String model_type = request.getParameter("methodtype").split("\\|")[0];
        log.info("model_type:{}", model_type);

        String type_format = request.getParameter("methodtype").split("\\|")[0];
        log.info("type_format:{}", type_format);
        String type_name = request.getParameter("methodtype").split("\\|")[1];
        log.info("type_name:{}", type_name);
        String model_name = type_name.split("-")[0];
        log.info("model_name:{}", model_name);
        String model_sort = type_name.split("-")[1];
        log.info("model_sort:{}", model_sort);

        log.info("type_format:{}", type_format);

        type_format = type_format.replace(".","_");
        log.info("type_format:{}", type_format);


        //1.得到上传的文件
      //  File uploadFile = hjjUpload.getFile(file);
        //2. 创建文件夹名称：
     //   String uploadFilePath_Dirct = uploadFile.getAbsolutePath();

    //    String directoryPathParent = Paths.get(uploadFilePath_Dirct).getParent().toString();
     //   log.info("directoryPathParent:{}", directoryPathParent);


   //     uploadFilePath_Dirct = directoryPathParent+"/"+versin;
    //    log.info("uploadFilePath_Dirct:{}", uploadFilePath_Dirct);

        //java创建文件夹：/Users/dengdagui/Documents/install/java/base-admin-master/temp/v1
   //     Path directoryPath = Paths.get(uploadFilePath_Dirct);
   //     try {
            // 创建文件夹
   //         Files.createDirectories(directoryPath);
   //         System.out.println("文件夹创建成功！");
   //     } catch (IOException e) {
    //        System.out.println("文件夹创建失败！");
     //       e.printStackTrace();
    //    }


        //3.得到新的文件路径：
      //  String uploadFilePath = uploadFile.getAbsolutePath();
      //  log.info("uploadFilePath:{}", uploadFilePath);


        String model_pathDir = "/temp/"+versin+"/"+type_format+"/";

        String model_path = this.getFile(file,model_pathDir).getAbsolutePath();

      //  String model_path = uploadFilePath.replace("temp","temp/v1/"+type);
        log.info("model_path:{}", model_path);



        //4.设置更新时间：
      //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      //  String nowtime = sdf.format(System.currentTimeMillis());


        //  List< One_click> list = new ArrayList<>();
        Model_table model_table = new Model_table();

        Map<String, Object> result = new HashMap<>();

        //  Map<String,Object> data = new HashMap<>();

        Map<String, Object> param = new HashMap<>();

        //2.对模型及数据集进行update:

        //  List<String> list = new ArrayList<>();
        //基本信息：模型
        //   String img_classfication = request.getParameter("img_classfication");

        // if (null != img_classfication) {
        // update one_click set img_classfication=#img_classfication where id = 1
        model_table.setModel_path(model_path);
        model_table.setVersion(versin);
        model_table.setType(type);
        model_table.setModel_type(model_type);
        model_table.setModel_name(model_name);
        model_table.setSort(model_sort);
        //model_table.setUpdate_time(nowtime);
        //  param.put("paramsMap", model_table);
        oneclickServiceV2.save(model_table);
        //      log.info("模型：{}上传成功!", img_classfication);

        result.put("code", 0);
        result.put("file", model_path);

        return result;
        //     }


        //     return null;

    }





    /**
     * 获取文件第三种方式
     */
    public File getFile(MultipartFile file,String filePathDirct) {

        Long begin = System.currentTimeMillis();

        File uploadfile = null;
     //   String filePath = System.getProperty("user.dir") + "/temp/";
        String filePath = System.getProperty("user.dir") + filePathDirct;
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


    /***
     * 更新数据 username,password,api wherer id = 1
     */
    @PostMapping("/updateById")
    public Map<String, Object> updateById(@RequestBody OneClickUpload oneClickUpload) {
        log.info("更新数据 username,password,api wherer id = 1");

        String username = oneClickUpload.getUsername().trim();
        String password = oneClickUpload.getPassword().trim();
        String api = oneClickUpload.getApi().trim();

 //       log.info("username:{}", username);
//        log.info("password:{}", password);
//        log.info("api:{}", api);

        Map<String, Object> map = oneclickServiceV2.updateById(username, password, api);

        log.info("mapxxxx:{}", map);

        return map;
    }




}
