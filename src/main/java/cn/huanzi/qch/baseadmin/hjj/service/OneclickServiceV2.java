package cn.huanzi.qch.baseadmin.hjj.service;


import cn.huanzi.qch.baseadmin.dao.Model_tableMapper;
import cn.huanzi.qch.baseadmin.dao.One_clickMapper;
import cn.huanzi.qch.baseadmin.entity.Model_table;
import cn.huanzi.qch.baseadmin.entity.One_click;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OneclickServiceV2 {


    Log log = LogFactory.get();

    @Resource
    private Model_tableMapper model_tableMapper ;


    @Resource
    private One_clickMapper one_clickMapper ;



    // 1.查询
    public Model_table query(Model_table model_table){
        String version = model_table.getVersion();
        String model_type = model_table.getModel_type();
        String type = model_table.getType();

        Model_table query_result = model_tableMapper.selectByVersionModel_type(version,model_type,type);

        return query_result ;

    }




    // 2.保存
    public Map<String,Object> save(Model_table model_table){
        Date date = DateUtil.date();
     //   model_table.setCreate_time(date);
        model_table.setUpdate_time(date);


        //1.查询数据库是否存在(insert)，否则update:
        Model_table old_data = this.query(model_table);
        JSONObject  jsonObject = new JSONObject(old_data);
        if(jsonObject.size()==0){  //insert

            int result = model_tableMapper.insertModel_table(model_table);
            if(result >0){
                log.info("数据保存成功!");
                Map<String,Object>   mapout = new HashMap<>();

                Map<String, Model_table>   mapin = new HashMap<>();
                mapout.put("code",0);
                mapout.put("msg","sucess");
                mapout.put("count",1);
                mapin.put("result",model_table);
                mapout.put("data",mapin);

                return mapout ;

            }else {
                log.info("数据保存失败!");
            }

        }else{
                //updata
                Map<String,Object> map = this.update(model_table);
                return map ;
        }


        return null ;

    }


    // 3.更新
    public Map<String,Object> update(Model_table model_table){

        Date date = DateUtil.date();
      //  model_table.setCreate_time(date);
        model_table.setUpdate_time(date);
      //  model_table.setId(1);

        int result =  model_tableMapper.updateByModel_typeVersion(model_table);

        if(result >0){
            log.info("数据更新成功!");

            Map<String,Object>   mapout = new HashMap<>();
            Map<String, Model_table>   mapin = new HashMap<>();

            Model_table old_data = this.query(model_table);

            mapout.put("code",0);
            mapout.put("msg","sucess");
            mapout.put("count",1);

            mapin.put("result",old_data);
            mapout.put("data",mapin);

            return mapout ;

        }else {
            log.info("数据更新失败!");
        }


        return null ;
    }




    // 3.上传模型及数据集时更新模型及数据集的路径
    public Map<String,Object> updateFilePath( Map<String,List<String>> map){
        Model_table model_table = new Model_table();


        Date date = DateUtil.date();
     //   model_table.setCreate_time(date);
        model_table.setUpdate_time(date);
        model_table.setId(1);



        int result = 0; //one_clickMapper.batchUpdate(map);

        if(result >0){
            log.info("数据更新成功!");

            Map<String,Object>   mapout = new HashMap<>();
            Map<String, Model_table>   mapin = new HashMap<>();

            Model_table old_data = this.query(model_table);

            mapout.put("code",0);
            mapout.put("msg","sucess");
            mapout.put("count",1);

            mapin.put("result",old_data);
            mapout.put("data",mapin);

            return mapout ;

        }else {
            log.info("数据更新失败!");
        }


        return null ;
    }


    /**
     * 更新数据 username,password,api wherer id = 1
     * @param username
     * @param password
     * @param api
     * @return
     */

    public Map<String, Object> updateById(String username, String password, String api) {
        Map<String, Object> mapout = new HashMap<>();
        try {
            int result = one_clickMapper.updateById(username, password, api);
            if (result > 0){
                log.info("数据更新成功!");

                One_click one_click = one_clickMapper.selectByPrimaryId(1);
                mapout.put("password", one_click.getPassword());
                mapout.put("username", one_click.getUsername());
                mapout.put("api", one_click.getApi());
                mapout.put("code", "0");
                System.out.printf("mapout:"+mapout);
                return mapout;
            } else {
                log.info("数据更新失败!");
            }
        } catch (Exception e) {
            log.error("数据库更新操作出现异常: " + e.getMessage(), e);
        }
        return mapout;
    }





}
