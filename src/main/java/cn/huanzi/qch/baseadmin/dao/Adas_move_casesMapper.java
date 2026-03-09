package cn.huanzi.qch.baseadmin.dao;

import cn.huanzi.qch.baseadmin.entity.Adas_move_cases;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Adas_move_casesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Adas_move_cases record);

    int insertSelective(Adas_move_cases record);

    Adas_move_cases selectByPrimaryKey(Integer id);

    List<Adas_move_cases> selectBySelectCase(String select_case);

    int updateByPrimaryKeySelective(Adas_move_cases record);

    int updateByPrimaryKey(Adas_move_cases record);
}