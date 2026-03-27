package com.example.tlias_study_system.pojo;

public class empgroupbygender {

    private String gender;
    private Integer count;

    public empgroupbygender(String gender, Integer count) {
        this.gender = gender;
        this.count = count;
    }

    public String getGender() {return gender;}
    public Integer getCount() {return count;}

    public void setGender(String gender) {this.gender = gender;}
    public void setCount(Integer count) {this.count = count;}
}
