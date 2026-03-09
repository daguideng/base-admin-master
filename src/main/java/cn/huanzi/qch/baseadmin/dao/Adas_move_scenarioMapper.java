package cn.huanzi.qch.baseadmin.dao;

import cn.huanzi.qch.baseadmin.entity.Adas_move_scenario;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Adas_move_scenarioMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Adas_move_scenario record);

    int insertSelective(Adas_move_scenario record);

    Adas_move_scenario selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Adas_move_scenario record);

    int updateByPrimaryKey(Adas_move_scenario record);
}