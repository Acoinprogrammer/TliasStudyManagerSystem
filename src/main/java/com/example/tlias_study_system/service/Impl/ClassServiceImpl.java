package com.example.tlias_study_system.service.Impl;

import com.example.tlias_study_system.mapper.ClassMapper;
import com.example.tlias_study_system.pojo.nclass;
import com.example.tlias_study_system.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassMapper classmapper;
    @Override
    public int AddClass(String name,String manager,int empId){return classmapper.AddClass(name,manager,empId);}
    @Override
    public Integer QueryEmp(String name){return classmapper.QueryEmp(name);}
    @Override
    public int QueryRename(String name){return classmapper.QueryRename(name);}
    @Override
    public int QueryClassCount(){return classmapper.QueryClassCount();}
    @Override
    public List<nclass> LoadingList(int page, int size){return classmapper.LoadingList(page,size);}
    @Override
    public List<nclass> GetAllClass(){return classmapper.GetAllClass();}
    @Override
    public int DelClass(int classId){return classmapper.DelClass(classId);}
    @Override
    public int PutClass(int classId,String name,int count,String manager,int empId){return classmapper.PutClass(classId,name,count,manager,empId);}
    @Override
    public List<nclass> ValueQuery(int classId,String name,String manager){return classmapper.ValueQuery(classId,name,manager);}
}
