package com.example.tlias_study_system.pojo;

public class employee {
    public static final String RESOURCE_PATH = "https://tlias-manager-system-house.oss-cn-beijing.aliyuncs.com/";
    private int id;
    private String headImg;
    private String name;
    private String gender;
    private String phone;
    private int deptId;
    private String position;
    private String createTime;
    private String updateTime;
    private String deptName;
    private boolean ADD_RESOURCE_PATH;

    public employee() {this.ADD_RESOURCE_PATH=true;}
    public employee(int id, String headImg, String name, String gender, String phone, int deptId, String position, String createTime, String updateTime,String deptName) {
        this.id = id;
        this.headImg = headImg;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.deptId = deptId;
        this.position = position;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deptName= deptName;
    }

    public int getId() {return id;}
    public String getHeadImg() {return headImg;}
    public String getName() {return name;}
    public String getGender() {return gender;}
    public String getPhone() {return phone;}
    public int getDeptId() {return deptId;}
    public String getPosition() {return position;}
    public String getCreateTime() {return createTime;}
    public String getUpdateTime() {return updateTime;}
    public String getDeptName() {return deptName;}

    public void setId(int id) {this.id = id;}
    public void setHeadImg(String headImg) {this.headImg = ADD_RESOURCE_PATH?String.format("%s%s",RESOURCE_PATH,headImg):headImg;}
    public void setName(String name) {this.name = name;}
    public void setGender(String gender) {this.gender = gender;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setDeptId(int deptId) {this.deptId = deptId;}
    public void setPosition(String position) {this.position = position;}
    public void setCreateTime(String createTime) {this.createTime = createTime;}
    public void setUpdateTime(String updateTime) {this.updateTime = updateTime;}
    public void setDeptName(String deptName) {this.deptName = deptName;}
}
