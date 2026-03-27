package com.example.tlias_study_system.pojo;

public class nclass {
    private int classId;
    private String className;
    private String manager;
    private int count;
    private String createTime;
    private String updateTime;
    private int empId;
    public nclass() {}
    public nclass(int id, String name, String manager, int count, String createTime, String updateTime,int empId) {
        this.classId = id;
        this.className = name;
        this.manager = manager;
        this.count = count;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.empId=empId;
    }

    public int getClassId() {return classId;}
    public void setClassId(int classId) {this.classId = classId;}

    public String getClassName() {return className;}
    public void setClassName(String className) {this.className = className;}

    public String getManager() {return manager;}
    public void setManager(String manager) {this.manager = manager;}

    public int getCount() {return count;}
    public void setCount(int count) {this.count = count;}

    public String getCreateTime() {return createTime;}
    public void setCreateTime(String createTime) {this.createTime = createTime;}
    public String getUpdateTime() {return updateTime;}
    public void setUpdateTime(String updateTime) {this.updateTime = updateTime;}

    public int getEmpId() {return empId;}
    public void setEmpId(int empId) {this.empId = empId;}

    @Override
    public String toString() {
        return "nclass{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", manager='" + manager + '\'' +
                ", count=" + count +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", empId=" + empId +
                '}';
    }
}
