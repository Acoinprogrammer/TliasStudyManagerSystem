package com.example.tlias_study_system.mapper;

import com.example.tlias_study_system.pojo.empgroupbygender;
import com.example.tlias_study_system.pojo.employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmpMapper {
    @Select("select em.id as id,em.name as name,ifnull(de.name,'无') as deptName,head_img as headImg, gender,phone,dept_id as deptId, position, em.create_time as createTime, em.update_time as updateTime " +
            "from employee as em left join dept as de on em.dept_id = de.id order by de.create_time desc limit #{offset},#{size}")
    List<employee> GetPageSizeEmp(@Param("offset")int offset,@Param("size")int size);
    @Select("select count(*) from employee")
    int GetAllEmpCount();
    @Delete("delete from employee where id=#{id}")
    int DeleteEmp(@Param("id")int id);
    @Insert("insert into employee (name,gender,phone,dept_id,position,head_img,create_time,update_time) values (" +
            "#{name},#{gender},#{phone},#{deptId},#{position},#{headImg},now(),now())")
    int InsertEmp(@Param("name")String name,
                  @Param("gender")String gender,
                  @Param("phone")String phone,
                  @Param("deptId")int deptId,
                  @Param("position")String position,
                  @Param("headImg")String headImg
    );
    @Select("select id from dept where name=#{deptId}")
    Integer SelectDeptId(@Param("deptId")String deptId);
    @Select("select count(*) from employee where phone=#{phone}")
    int SelectIsSamePhone(@Param("phone")String phone);
    @Select("select emp.id as id,emp.name as name,ifnull(de.name,'无') as deptName,head_img as headImg, gender,phone,dept_id as deptId, position, emp.create_time as createTime, emp.update_time as updateTime " +
            "from employee as emp left join dept as de on dept_id=de.id where (emp.id=#{id} or emp.name=#{name} or emp.phone=#{phone} or emp.dept_id=#{deptId} or emp.position=#{position}) " +
            "order by emp.create_time desc limit #{offset},#{size}")
    List<employee> QueryEmp(@Param("id")int id, @Param("name")String name, @Param("phone")String phone,@Param("deptId")int deptId,@Param("position")String position,@Param("offset")int offset,@Param("size")int size);
    @Select("select head_img as headImg from employee where id=#{id}")
    String QueryHeadImg(@Param("id")int id);
    @Update("update employee set name=#{name},gender=#{gender},phone=#{phone},dept_id=#{deptId},position=#{position},head_img=#{headImg},update_time=now() where id=#{id} ")
    int UpdateEmp(@Param("id")int id,@Param("name")String name,@Param("gender")String gender,@Param("phone")String phone,@Param("deptId")int deptId,@Param("position")String position,@Param("headImg")String headImg);
    @Select("select gender,count(*) from employee group by gender")
    List<empgroupbygender> GetGenderCount();
}
