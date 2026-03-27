package com.example.tlias_study_system.service;

public interface LoginService {
    int Registers(String accountFlag,String password);
    int QueryAccount(String accountFlag,String password);
    int QueryOnlyAccount(String accountFlag);
    int PutAccount(String accountFlag,String password);
}
