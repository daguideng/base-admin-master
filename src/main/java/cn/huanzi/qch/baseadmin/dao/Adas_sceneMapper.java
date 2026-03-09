package cn.huanzi.qch.baseadmin.dao;

import cn.huanzi.qch.baseadmin.entity.Adas_scene;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

@Mapper
public interface Adas_sceneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Adas_scene record);

    int insertSelective(Adas_scene record);

    Adas_scene selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Adas_scene record);

    int updateByPrimaryKey(Adas_scene record);
}