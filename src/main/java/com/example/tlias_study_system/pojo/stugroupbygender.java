package com.example.tlias_study_system.pojo;

public class stugroupbygender {
    private String gender;
    private Integer count;

    public stugroupbygender(String gender, int count) {
        this.gender = gender;
        this.count = count;
    }

    public String getGender() {return gender;}
    public void setGender(String gender){this.gender=gender;}

    public Integer getCount(){return count;}
    public void setCount(Integer count){this.count=count;}
}
