package cn.huanzi.qch.baseadmin.dao;

import cn.huanzi.qch.baseadmin.entity.One_click;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface One_clickMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(One_click record);

    int insertSelective(One_click record);

    One_click selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(One_click record);

    int updateByPrimaryKey(One_click record);

    /*
    int  updateByFilePath(List<String>lisit);
     */
    @Update("update one_click set username = #{username},password = #{password},api = #{api} where id = 1")
    int updateById(@Param("username") String username, @Param("password") String password, @Param("api") String api);



    @Select("SELECT * FROM one_click WHERE id = #{id}")
    One_click selectByPrimaryId(Integer id);


    int batchUpdate(@Param("paramsMap") Map<String,Object> param);

}