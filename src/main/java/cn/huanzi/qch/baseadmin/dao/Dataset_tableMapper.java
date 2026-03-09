package cn.huanzi.qch.baseadmin.dao;

import cn.huanzi.qch.baseadmin.entity.Dataset_table;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface Dataset_tableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dataset_table record);

    int insertSelective(Dataset_table record);

    Dataset_table selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dataset_table record);

    int updateByPrimaryKey(Dataset_table record);
}