package com.example.tlias_study_system.service.Impl;

import com.example.tlias_study_system.mapper.StudentMapper;
import com.example.tlias_study_system.pojo.student;
import com.example.tlias_study_system.pojo.stugroupbygender;
import com.example.tlias_study_system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentmapper;
    @Override
    public int GetStudentCount(){return studentmapper.GetStudentCount();}
    @Override
    public List<student> LoadingData(){return studentmapper.GetAllStudent();}
    @Override
    public int DelStudent(int id){return studentmapper.DelStudent(id);}
    @Override
    public List<student> UnionQueryStudent(int id,String name,String fromClass,String manager){return studentmapper.UnionQueryStudent(id,name,fromClass,manager);}
    @Override
    public Integer QueryExitedManager(String manager){return studentmapper.QueryExitedManager(manager);}
    @Override
    public Integer QueryExitedClass(String fromClass){return studentmapper.QueryExitedClass(fromClass);}
    @Override
    public int AddStudent(String name,String gender,String manager,int age,String fromClass,String headImage,int managerId,int classId){return studentmapper.AddStudent(name,headImage,gender,age,fromClass,manager,classId,managerId);}
    @Override
    public String GetHeadImage(int id){return studentmapper.GetHeadImage(id);};
    @Override
    public int PutStudent(String name,String gender,String manager,int age,String fromClass,String filename,int managerId,int classId,int id){return studentmapper.PutStudent(name,gender,manager,age,fromClass,filename,managerId,classId,id);}
    @Override
    public List<student> StudentLogin(String id,String name){return studentmapper.StudentLogin(id,name);}
    @Override
    public List<stugroupbygender> GetGenderCount(){return studentmapper.GetGenderCount();}
}
