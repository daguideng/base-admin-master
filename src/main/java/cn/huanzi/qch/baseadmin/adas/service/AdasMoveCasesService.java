package cn.huanzi.qch.baseadmin.adas.service;

import cn.huanzi.qch.baseadmin.dao.Adas_move_casesMapper;
import cn.huanzi.qch.baseadmin.entity.Adas_move_cases;
import cn.huanzi.qch.baseadmin.entity.Adas_scene;
import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdasMoveCasesService {


    Log log = LogFactory.get();

    @Resource
    private Adas_move_casesMapper adas_move_casesMapper;

    // 1.查询
    public Adas_move_cases queryData(Integer id) {

        Adas_move_cases query_result = adas_move_casesMapper.selectByPrimaryKey(id);

        return query_result;

    }


    // 2.insert
    public Map<String, Object> insertData(Adas_move_cases adas_move_cases) {
        Date date = DateUtil.date();
        adas_move_cases.setCreate_time(date);
        adas_move_cases.setUpdate_time(date);
        String slect_case = adas_move_cases.getSelect_case();


        //1.查询where=select_case 是否存在，存在则更新，否则insert
        List<Adas_move_cases> rows = adas_move_casesMapper.selectBySelectCase(slect_case);
        if (rows.size() == 0) {  //insert

            int count = adas_move_casesMapper.insert(adas_move_cases);
            if (count > 0) {
                log.info("数据保存成功!");
                Map<String, Object> mapout = new HashMap<>();

                Map<String, Adas_move_cases> mapin = new HashMap<>();
                mapout.put("code", 0);
                mapout.put("msg", "sucess");
                mapout.put("count", 1);

                mapin.put("result", adas_move_cases);
                mapout.put("data", mapin);

                return mapout;
            }

        } else {
            // 更新
            log.info("数据保存失败!");
             this.update(adas_move_cases);


        }


        return null;

    }


    // 3.更新
    public Map<String, Object> update(Adas_move_cases adas_move_cases) {

        Date date = DateUtil.date();
        adas_move_cases.setCreate_time(date);
        adas_move_cases.setUpdate_time(date);

        int result = adas_move_casesMapper.updateByPrimaryKey(adas_move_cases);

        if (result > 0) {
            log.info("数据更新成功!");

        } else {
            log.info("数据更新失败!");
        }


        return null;
    }


}
