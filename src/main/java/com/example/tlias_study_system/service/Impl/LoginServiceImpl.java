package com.example.tlias_study_system.service.Impl;

import com.example.tlias_study_system.mapper.LoginMapper;
import com.example.tlias_study_system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginmapper;
    @Override
    public int Registers(String accountFlag,String password){return loginmapper.Registers(accountFlag,password);}
    @Override
    public int QueryAccount(String accountFlag,String password){return loginmapper.QueryAccount(accountFlag,password);}
    @Override
    public int QueryOnlyAccount(String accountFlag){return loginmapper.QueryOnlyAccount(accountFlag);}
    @Override
    public int PutAccount(String accountFlag,String password){return loginmapper.PutAccount(accountFlag,password);}
}
