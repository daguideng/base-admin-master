package cn.huanzi.qch.baseadmin.dao;

import cn.huanzi.qch.baseadmin.entity.Test_user;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface Test_userMapper {
    int deleteByPrimaryKey(String userId);

    int insert(Test_user record);

    int insertSelective(Test_user record);

    Test_user selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(Test_user record);

    int updateByPrimaryKey(Test_user record);
}