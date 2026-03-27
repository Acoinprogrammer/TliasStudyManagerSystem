package com.example.tlias_study_system.mapper;

import com.example.tlias_study_system.pojo.dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
//    @Results(id = "dept", value = {
//            @Result(property = "id", column = "id"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "createTime", column = "create_time"),
//            @Result(property = "updateTime", column = "update_time")
//    })
    @Select("select id as id,name as name,create_time as createTime,update_time as updateTime from dept order by update_time desc")
    List<dept> GetAllDept();
    @Select("select count(*) from dept")
    int GetAllDeptCount();
    @Delete("delete from dept where name=#{del_name}")
    int DeleteDept(@Param("del_name")String del_name);
    @Select("select id as id,name as name,create_time as createTime,update_time as updateTime from dept order by update_time desc limit #{offset},#{size}")
    List<dept> GetPageSizeDept(@Param("offset")int offset,@Param("size")int size);
    @Insert("insert into dept (name,create_time,update_time) values(#{add_name},now(),now())")
    int InsertDept(@Param("add_name")String add_name);
    @Select("Select id as id,name as name,create_time as createTime,update_time as updateTime from dept where (id=#{id} || name=#{name})")
    dept SelectConditionQuery(@Param("id")int id,@Param("name")String name);
    @Update("update dept set name=#{name},update_time=now() where id=#{id}")
    int UpdateDept(@Param("id")int id,@Param("name")String name);
}
