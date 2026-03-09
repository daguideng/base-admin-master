package cn.huanzi.qch.baseadmin.adas.service;

import cn.huanzi.qch.baseadmin.dao.Adas_move_scenarioMapper;
import cn.huanzi.qch.baseadmin.entity.Adas_move_scenario;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MoveScenario {


    Log log = LogFactory.get();

    @Resource
    private Adas_move_scenarioMapper adas_move_scenarioMapper ;

    // 1.查询
    public Adas_move_scenario query(Integer id){

        Adas_move_scenario query_result = adas_move_scenarioMapper.selectByPrimaryKey(id);
        log.info("Adas_move_scenario查询返回结果:{}",query_result);
        return query_result ;

    }
}
