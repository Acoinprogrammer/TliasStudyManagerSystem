package com.example.tlias_study_system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test2 {
    public static void main(String[] args) throws ParseException {
        System.out.println(new SimpleDateFormat("yyyy年MM月dd日").format(new SimpleDateFormat("yyyy-MM-dd").parse("2006-1-14")));
    }
}
