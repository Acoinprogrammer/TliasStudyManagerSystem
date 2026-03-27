package com.example.tlias_study_system.service;

import com.example.tlias_study_system.pojo.student;
import com.example.tlias_study_system.pojo.stugroupbygender;

import java.util.List;

public interface StudentService {
    int GetStudentCount();
    List<student> LoadingData();
    int DelStudent(int id);
    List<student> UnionQueryStudent(int id,String name,String fromClass,String manager);
    Integer QueryExitedManager(String manager);
    Integer QueryExitedClass(String fromClass);
    int AddStudent(String name,String gender,String manager,int age,String fromClass,String headImage,int managerId,int classId);
    int PutStudent(String name,String gender,String manager,int age,String fromClass,String headImage,int managerId,int classId,int id);
    String GetHeadImage(int id);
    List<student> StudentLogin(String id,String name);
    List<stugroupbygender> GetGenderCount();
}
