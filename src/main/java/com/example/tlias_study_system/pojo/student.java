package com.example.tlias_study_system.pojo;

public class student {
    private int id;
    private String headImage;
    private String name;
    private String gender;
    private int age;
    private String fromClass;
    private int classId;
    private String manager;
    private int managerId;
    private String createTime;
    private String graduationTime;
    private String updateTime;

    public static final String ImageUrl = "https://tlias-manager-system-house.oss-cn-beijing.aliyuncs.com/";

    public student(){}
    public student(int id, String headImage, String name, String gender, int age, String fromClass, int classId, String manager, int managerId, String createTime, String graduationTime, String updateTime) {
        this.id = id;
        this.headImage = headImage;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.fromClass = fromClass;
        this.classId = classId;
        this.manager = manager;
        this.managerId = managerId;
        this.createTime = createTime;
        this.graduationTime = graduationTime;
        this.updateTime = updateTime;
    }

    public int getId() {return id;}
    public String getHeadImage() {return headImage;}
    public String getName() {return name;}
    public String getGender() {return gender;}
    public int getAge() {return age;}
    public String getFromClass() {return fromClass;}
    public int getClassId() {return classId;}
    public String getManager() {return manager;}
    public int getManagerId() {return managerId;}
    public String getCreateTime() {return createTime;}
    public String getGraduationTime() {return graduationTime;}
    public String getUpdateTime() {return updateTime;}

    public void setId(int id) {this.id = id;}
    public void setHeadImage(String headImage) {this.headImage = String.format("%s%s",ImageUrl,headImage);}
    public void setName(String name) {this.name = name;}
    public void setGender(String gender) {this.gender = gender;}
    public void setAge(int age) {this.age = age;}
    public void setFromClass(String fromClass) {this.fromClass = fromClass;}
    public void setClassId(int classId) {this.classId = classId;}
    public void setManager(String manager) {this.manager = manager;}
    public void setManagerId(int managerId) {this.managerId = managerId;}
    public void setCreateTime(String createTime) {this.createTime = createTime;}
    public void setGraduationTime(String graduationTime) {this.graduationTime = graduationTime;}
    public void setUpdateTime(String updateTime) {this.updateTime = updateTime;}

    @Override
    public String toString() {
        return "student{" +
                "id=" + id +
                ", headImage='" + headImage + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", fromClass='" + fromClass + '\'' +
                ", classId=" + classId +
                ", manager='" + manager + '\'' +
                ", managerId=" + managerId +
                ", createTime='" + createTime + '\'' +
                ", graduationTime='" + graduationTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
