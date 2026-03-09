package cn.huanzi.qch.baseadmin.hjj.service;


import cn.huanzi.qch.baseadmin.dao.Model_tableMapper;
import cn.huanzi.qch.baseadmin.entity.Model_table;
import cn.huanzi.qch.baseadmin.entity.One_click;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModelTableService {


    Log log = LogFactory.get();

    @Resource
    private Model_tableMapper model_tableMapper;

    // 1.查询
    public List<Model_table> query(Model_table model_table) {

      //  List<Model_table> query_result = model_tableMapper.selectByVersion(model_table.getVersion());

        return null;

    }


    // 1.查询版本号
    public List<Model_table> queryVersion(String version,String type) {
        List<Model_table> list = model_tableMapper.selectByVersion(version,type);
        return list;
    }


    //2.分页查询
    public PageInfo<Model_table> getEntities(int page, int size) {
        PageHelper.startPage(page, size);
        List<Model_table> list = model_tableMapper.selectAll();
        return new PageInfo<>(list);
    }

    //3.根据id查询出model_path
    public List<Model_table> getModelPathById(List<Integer> idList) {

        List<Model_table> model_table = model_tableMapper.selectByIdList(idList);
        return model_table;

    }


    /**
     * 全部查询
     *
     * @return
     */
    public List<Model_table> selectAllQuery() {

        List<Model_table> query_result = model_tableMapper.allSelect();

        return query_result;

    }

    // 2.保存
    public Map<String, Object> save(Model_table model_table) {

        // 获取当前时间的字符串表示，自定义格式
        String customFormat = DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH:mm:ss");
        // System.out.println("当前时间（自定义格式）：" + customFormat);

        Date date = DateUtil.parse(customFormat);
        model_table.setUpdate_time(date);
        model_table.setModel_path("model_path");
        model_table.setModel_type("visible_img.img_classfication");

        //1.查询数据库是否存在(insert)，否则update:
        List<Model_table> old_data = this.query(model_table);
        JSONObject jsonObject = new JSONObject(old_data);
        if (jsonObject.size() == 0) {  //insert

            int result = model_tableMapper.insert(model_table);
            if (result > 0) {
                log.info("数据保存成功!");

                Map<String, Object> mapin = new HashMap<>();
                mapin.put("code", 0);
                mapin.put("msg", "sucess");
                mapin.put("update", date);
                mapin.put("model_path", "model_path");
                return mapin;
            } else {
                log.info("数据保存失败!");
                Map<String, Object> mapout = new HashMap<>();
                mapout.put("code", 1);
                mapout.put("msg", "fail");
                return mapout;
            }
        } else {  //update
            int result = model_tableMapper.updateByPrimaryKey(model_table);
            if (result > 0) {
                log.info("数据更新成功!");
                Map<String, Object> mapout = new HashMap<>();
                Map<String, Model_table> mapin = new HashMap<>();
                mapout.put("code", 0);
                mapout.put("msg", "sucess");
                return mapout;
            } else {
                log.info("数据更新失败!");
                Map<String, Object> mapout = new HashMap<>();
                mapout.put("code", 1);
                mapout.put("msg", "fail");
                return mapout;
            }
        }
    }


}
