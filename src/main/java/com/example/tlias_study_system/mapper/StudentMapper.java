package com.example.tlias_study_system.mapper;

import com.example.tlias_study_system.pojo.student;
import com.example.tlias_study_system.pojo.stugroupbygender;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select count(*) from student")
    int GetStudentCount();
    @Select("select id, " +
            "head_image as headImage, name, gender, age, " +
            "from_class as fromClass, manager, " +
            "create_time as createTime, graduation_time as graduationTime, " +
            "update_time as updateTime from student")
    List<student> GetAllStudent();
    @Delete("delete from student where id=#{id}")
    int DelStudent(@Param("id")int id);
    @Select("select id, " +
            "head_image as headImage, name, gender, age, " +
            "from_class as fromClass, manager, " +
            "create_time as createTime, graduation_time as graduationTime, " +
            "update_time as updateTime from student where id=#{id} or name=#{name} or from_class=#{fromClass} or manager=#{manager} ")
    List<student> UnionQueryStudent(@Param("id")int id, @Param("name")String name,@Param("fromClass")String fromClass ,@Param("manager")String manager);
    @Select("select id from employee where name=#{manager}")
    Integer QueryExitedManager(@Param("manager")String manager);
    @Select("select class_id from nclass where class_name=#{fromClass}")
    Integer QueryExitedClass(@Param("fromClass")String fromClass);
    @Insert("insert into student(name,head_image ,gender, age, from_class, manager,class_id,manager_id) " +
            "values(#{name},#{headImage}, #{gender}, #{age}, #{fromClass}, #{manager},#{classId},#{managerId})")
    int AddStudent(@Param("name")String name,@Param("headImage")String headImage,@Param("gender")String gender,@Param("age")int age,@Param("fromClass")String fromClass,@Param("manager")String manager,@Param("classId")Integer classId,@Param("managerId")Integer managerId);
    @Select("select head_image from student where id=#{id}")
    String GetHeadImage(@Param("id")int id);
    @Update("update student set name=#{name},gender=#{gender},manager=#{manager},age=#{age},from_class=#{fromClass},head_image=#{filename},manager_id=#{managerId},class_id=#{classId} where id=#{id}")
    int PutStudent(@Param("name")String name,@Param("gender")String gender,@Param("manager")String manager,@Param("age")int age,@Param("fromClass")String fromClass,@Param("filename")String filename,@Param("managerId")Integer managerId,@Param("classId")Integer classId,@Param("id")int id);
    @Select("select id,head_image as headImage,name,gender,age,from_class as fromClass,manager,create_time as createTime" +
            ",graduation_time as graduationTime,update_time as updateTime from student where id=#{id} and name=#{name}")
    List<student> StudentLogin(@Param("id")String id,@Param("name")String name);
    @Select("select gender,count(*) from student group by gender")
    List<stugroupbygender> GetGenderCount();
}
