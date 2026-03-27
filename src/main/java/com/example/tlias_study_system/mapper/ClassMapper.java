package com.example.tlias_study_system.mapper;

import com.example.tlias_study_system.pojo.nclass;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {
    @Insert("insert into nclass (class_name,manager,emp_id) values (#{name},#{manager},#{empId})")
    int AddClass(@Param("name")String name,@Param("manager")String manager,@Param("empId")int empId);
    @Select("select id from employee where name=#{name}")
    Integer QueryEmp(@Param("name")String name);
    @Select("select count(*) from nclass where class_name=#{name}")
    int QueryRename(@Param("name")String name);
    @Select("select count(*) from nclass")
    int QueryClassCount();
    @Select("select class_id as classId, class_name as className, manager, count, create_time as createTime, update_time as updateTime from nclass limit #{page},#{size}")
    List<nclass> LoadingList(@Param("page")int page,@Param("size")int size);
    @Select("select class_id as classId,class_name as className,manager,count,create_time as createTime,update_time as updateTime from nclass")
    List<nclass> GetAllClass();
    @Delete("delete from nclass where class_id=#{classId}")
    int DelClass(@Param("classId")int classId);
    @Update("update nclass set class_name=#{name},count=#{count},manager=#{manager},emp_id=#{empId},update_time=now() where class_id=#{classId}")
    int PutClass(@Param("classId")int classId,@Param("name")String name,@Param("count")int count,@Param("manager")String manager,@Param("empId")int empId);
    @Select("select class_id as classId,class_name as className,manager,count,create_time as createTime,update_time as updateTime from nclass where class_id=#{classId} or class_name=#{name} or manager=#{manager}")
    List<nclass> ValueQuery(@Param("classId")int classId,@Param("name")String name,@Param("manager")String manager);
}
