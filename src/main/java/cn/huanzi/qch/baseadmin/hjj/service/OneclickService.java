package cn.huanzi.qch.baseadmin.hjj.service;


import cn.huanzi.qch.baseadmin.dao.Model_tableMapper;
import cn.huanzi.qch.baseadmin.dao.One_clickMapper;
import cn.huanzi.qch.baseadmin.entity.Model_table;
import cn.huanzi.qch.baseadmin.entity.One_click;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OneclickService {


    Log log = LogFactory.get();

    @Resource
    private Model_tableMapper model_tableMapper ;

    @Resource
    private One_clickMapper one_clickMapper ;

    // 1.查询
    public One_click query(Integer id){

        One_click query_result = one_clickMapper.selectByPrimaryKey(id);

        return query_result ;

    }




    // 2.保存
    public Map<String,Object> save(One_click one_click){
        Date date = DateUtil.date();
        one_click.setCreate_time(date);
        one_click.setUpdate_time(date);

        //1.查询数据库是否存在(insert)，否则update:
        One_click old_data = this.query(1);
        JSONObject  jsonObject = new JSONObject(old_data);
        if(jsonObject.size()==0){  //insert

            int result = one_clickMapper.insert(one_click);
            if(result >0){
                log.info("数据保存成功!");
                Map<String,Object>   mapout = new HashMap<>();

                Map<String, One_click>   mapin = new HashMap<>();
                mapout.put("code",0);
                mapout.put("msg","sucess");
                mapout.put("count",1);

                mapin.put("result",one_click);
                mapout.put("data",mapin);

                return mapout ;

            }else {
                log.info("数据保存失败!");
            }


        }else{
                //updata
                Map<String,Object> map = this.update(one_click);
                return map ;

        }


        return null ;

    }


    // 3.更新
    public Map<String,Object> update(One_click one_click){

        Date date = DateUtil.date();
        one_click.setCreate_time(date);
        one_click.setUpdate_time(date);
        one_click.setId(1);

        int result =  one_clickMapper.updateByPrimaryKey(one_click);

        if(result >0){
            log.info("数据更新成功!");

            Map<String,Object>   mapout = new HashMap<>();
            Map<String, One_click>   mapin = new HashMap<>();

            One_click old_data = this.query(1);

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
        One_click one_click = new One_click();


        Date date = DateUtil.date();
        one_click.setCreate_time(date);
        one_click.setUpdate_time(date);
        one_click.setId(1);



        int result = 0; //one_clickMapper.batchUpdate(map);

        if(result >0){
            log.info("数据更新成功!");

            Map<String,Object>   mapout = new HashMap<>();
            Map<String, One_click>   mapin = new HashMap<>();

            One_click old_data = this.query(1);

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
    public int batchUpdate(Map<String,Object> param) {

        JSONObject jsonObject = new JSONObject(param);
        log.info("jsonObject:{}",jsonObject);
        return one_clickMapper.batchUpdate(param);
    }










}
