package com.example.tlias_study_system.service.Impl;

import com.example.tlias_study_system.mapper.EmpMapper;
import com.example.tlias_study_system.pojo.empgroupbygender;
import com.example.tlias_study_system.pojo.employee;
import com.example.tlias_study_system.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empmapper;
    @Override
    public List<employee> GetPageSizeEmp(int page,int size){return empmapper.GetPageSizeEmp((page-1)*size,size);};
    @Override
    public int GetAllEmpCount(){return empmapper.GetAllEmpCount();}
    @Override
    public int DeleteEmp(int id){return empmapper.DeleteEmp(id);}
    @Override
    public int InsertEmp(String name,int deptId,String gender,String phone,String position,String headImg){return empmapper.InsertEmp(name,gender,phone,deptId,position,headImg);}
    @Override
    public Integer SelectDeptId(String deptId){return empmapper.SelectDeptId(deptId);};
    @Override
    public int SelectIsSamePhone(String phone){return empmapper.SelectIsSamePhone(phone);}
    @Override
    public List<employee> QueryEmp(int id,String name,String phone,String position,int deptId,int page,int count,int size){return empmapper.QueryEmp(id,name,phone,deptId,position,(page-1)*size,size);};
    @Override
    public String QueryHeadImg(int id){return empmapper.QueryHeadImg(id);}
    @Override
    public int UpdateEmp(int id,String name,String gender,String phone,int deptId,String position,String headImg){return empmapper.UpdateEmp(id,name,gender,phone,deptId,position,headImg);};
    @Override
    public List<empgroupbygender> GetGenderCount(){return empmapper.GetGenderCount();}
}
