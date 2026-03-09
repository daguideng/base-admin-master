package cn.huanzi.qch.baseadmin.adas.service;


import cn.huanzi.qch.baseadmin.dao.Adas_sceneMapper;
import cn.huanzi.qch.baseadmin.entity.Adas_scene;
import cn.huanzi.qch.baseadmin.entity.One_click;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RunSceneService{


    Log log = LogFactory.get();

    @Resource
    private Adas_sceneMapper adas_sceneMapper ;

    // 1.查询
    public Adas_scene query(Integer id){

        Adas_scene query_result = adas_sceneMapper.selectByPrimaryKey(id);

        return query_result ;

    }




    // 2.保存
    public Map<String,Object> save(Adas_scene adas_scene){
        Date date = DateUtil.date();
        adas_scene.setCreate_time(date);
        adas_scene.setUpdate_time(date);

        //1.查询数据库是否存在(insert)，否则update:
        Adas_scene old_data = this.query(1);
        JSONObject jsonObject = new JSONObject(old_data);
        if(jsonObject.size()==0){  //insert

            int result = adas_sceneMapper.insert(adas_scene);
            if(result >0){
                log.info("数据保存成功!");
                Map<String,Object>   mapout = new HashMap<>();

                Map<String, Adas_scene> mapin = new HashMap<>();
                mapout.put("code",0);
                mapout.put("msg","sucess");
                mapout.put("count",1);

                mapin.put("result",adas_scene);
                mapout.put("data",mapin);

                return mapout ;

            }else {
                log.info("数据保存失败!");
            }


        }else{
            //updata

            Map<String,Object> map = this.update(adas_scene);

            return map ;

        }




        return null ;

    }


    // 3.更新
    public Map<String,Object> update(Adas_scene adas_scene){

        Date date = DateUtil.date();
        adas_scene.setCreate_time(date);
        adas_scene.setUpdate_time(date);
        adas_scene.setId(1);

        int result =  adas_sceneMapper.updateByPrimaryKey(adas_scene);

        if(result >0){
            log.info("数据更新成功!");

            Map<String,Object>   mapout = new HashMap<>();
            Map<String, Object>   mapin = new HashMap<>();

            Adas_scene old_data = this.query(1);

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
    public Map<String,Object> updateFilePath( Map<String, List<String>> map){
        One_click one_click = new One_click();


        Date date = DateUtil.date();
        one_click.setCreate_time(date);
        one_click.setUpdate_time(date);
        one_click.setId(1);



        int result = 0; //one_clickMapper.batchUpdate(map);

        if(result >0){
            log.info("数据更新成功!");

            Map<String,Object>   mapout = new HashMap<>();
            Map<String, Adas_scene>   mapin = new HashMap<>();

            Adas_scene old_data = this.query(1);

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
     * 批量更新第一种方式
     * @param param
     * @return
     */

    /**
    public int batchUpdate(Map<String,Object> param) {

        JSONObject jsonObject = new JSONObject(param);
        log.info("jsonObject:{}",jsonObject);
        return adas_sceneMapper.batchUpdate(param);
    }
     **/







}