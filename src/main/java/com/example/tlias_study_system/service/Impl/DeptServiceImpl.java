package com.example.tlias_study_system.service.Impl;


import com.example.tlias_study_system.mapper.DeptMapper;
import com.example.tlias_study_system.pojo.dept;
import com.example.tlias_study_system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptmapper;
    @Override
    public List<dept> GetAllDept(){return deptmapper.GetAllDept();}
    @Override
    public int GetAllDeptCount(){return deptmapper.GetAllDeptCount();}
    @Override
    public List<dept> GetPageSizeDept(int page,int count,int size){return deptmapper.GetPageSizeDept((page-1)*size,size);};
    @Override
    public int DeleteDept(String del_name){return deptmapper.DeleteDept(del_name);}
    @Override
    public int InsertDept(String add_name){return deptmapper.InsertDept(add_name);};
    @Override
    public dept SelectConditionQuery(int id,String name){return deptmapper.SelectConditionQuery(id,name);};
    @Override
    public int UpdateDept(int id,String name){return deptmapper.UpdateDept(id,name);}
}
