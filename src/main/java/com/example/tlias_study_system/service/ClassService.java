package com.example.tlias_study_system.service;

import com.example.tlias_study_system.pojo.nclass;

import java.util.List;

public interface ClassService {
    int AddClass(String name,String manager,int empId);
    Integer QueryEmp(String name);
    int QueryRename(String name);
    int QueryClassCount();
    List<nclass> LoadingList(int page,int size);
    List<nclass> GetAllClass();
    int DelClass(int classId);
    int PutClass(int classId,String name,int count,String manager,int empId);
    List<nclass> ValueQuery(int classId,String name,String manager);

}
