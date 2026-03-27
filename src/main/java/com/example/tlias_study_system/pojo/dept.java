package com.example.tlias_study_system.pojo;

public class dept {
    private int id;
    private String name;
    private String createTime;
    private String updateTime;

    public dept() {}
    public dept(int id, String name, String createTime, String updateTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
    public dept(int id,String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getCreateTime() {return createTime;}
    public String getUpdateTime() {return updateTime;}

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setCreateTime(String createTime) {this.createTime = createTime;}
    public void setUpdateTime(String updateTime) {this.updateTime = updateTime;}

    @Override
    public String toString() {return "id:"+id+",name:"+name+",createTime:"+createTime+",updateTime:"+updateTime+"\n";}
}
