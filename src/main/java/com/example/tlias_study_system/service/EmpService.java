package com.example.tlias_study_system.service;


import com.example.tlias_study_system.pojo.empgroupbygender;
import com.example.tlias_study_system.pojo.employee;

import java.util.List;

public interface EmpService {
    List<employee> GetPageSizeEmp(int page,int size);
    int GetAllEmpCount();
    int DeleteEmp(int id);
    int InsertEmp(String name,int dept_id,String gender,String phone,String position,String headImg);
    Integer SelectDeptId(String deptId);
    int SelectIsSamePhone(String phone);
    List<employee> QueryEmp(int id,String name,String phone,String position,int deptId,int page,int count,int size);
    String QueryHeadImg(int id);
    int UpdateEmp(int id,String name,String gender,String phone,int deptId,String position,String headImg);
    List<empgroupbygender> GetGenderCount();
}
