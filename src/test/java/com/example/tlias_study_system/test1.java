package com.example.tlias_study_system;

import java.util.Date;

public class test1 {
    public static void main(String[] args){
        Date d1 = new Date();
        Date d2 = new Date(d1.getTime()+(long)1000*60*60*24*365);
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d1.getTime()>=d2.getTime()?"d1在后面":"d2在后面");
    }
}
