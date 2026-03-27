package com.example.tlias_study_system.service;

import com.example.tlias_study_system.pojo.dept;
import java.util.List;

public interface DeptService {
    List<dept> GetAllDept();
    int GetAllDeptCount();
    List<dept> GetPageSizeDept(int page,int count,int size);
    int DeleteDept(String del_name);
    int InsertDept(String add_name);
    dept SelectConditionQuery(int id,String name);
    int UpdateDept(int id,String name);
}
