package cn.huanzi.qch.baseadmin.dao;

import cn.huanzi.qch.baseadmin.entity.Model_table;
import org.apache.ibatis.annotations.*;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
public interface Model_tableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Model_table record);

    int insertSelective(Model_table record);

    Model_table selectByPrimaryKey(Integer id);

    @Select("SELECT * FROM model_table WHERE version = #{version} and type = #{type} ")
    List<Model_table>  selectByVersion(@Param("version") String version,@Param("type") String type );


   @Select("SELECT * FROM model_table")
    List<Model_table> allSelect();


    @Select("SELECT * FROM model_table WHERE version = #{version} and model_type = #{model_type} and type = #{type}")
    Model_table  selectByVersionModel_type(@Param("version") String version, @Param("model_type") String model_type,@Param("type") String type);



    @Insert("INSERT INTO model_table (model_type, model_name, sort, version, model_path,update_time,type) VALUES (#{model_type}, #{model_name}, #{sort}, #{version,jdbcType=VARCHAR}, #{model_path}, #{update_time},#{type})")
    int insertModel_table(Model_table record);


    @Update("UPDATE model_table SET model_type = #{model_type}, model_name = #{model_name}, sort = #{sort}, version = #{version,jdbcType=VARCHAR}, model_path = #{model_path}, update_time = #{update_time},type = #{type} WHERE model_type = #{model_type} and version = #{version} and type = #{type}")
    int updateByModel_typeVersion(Model_table record);


    //分页查询
    @Select("SELECT * FROM model_table")
    List<Model_table> selectAll();


    @Select("SELECT * FROM model_table where version=#{version,jdbcType=VARCHAR}")
    List<Model_table> selectByVersin(@Param("version") String version);


    @Select({
            "<script>",
            "SELECT * FROM model_table",
            "WHERE id IN",
            "<foreach item='id' collection='idList' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Model_table> selectByIdList(@Param("idList") List<Integer> idList);


    int updateByPrimaryKeySelective(Model_table record);

    int updateByPrimaryKey(Model_table record);
}