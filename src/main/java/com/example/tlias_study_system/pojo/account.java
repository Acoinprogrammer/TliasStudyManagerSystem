package com.example.tlias_study_system.pojo;

public class account {
    private String accountFlag;
    private String password;

    public account(String accountFlag, String password) {
        this.accountFlag = accountFlag;
        this.password = password;
    }
    public account(){}

    public String getAccountFlag() {return accountFlag;}
    public String getPassword() {return password;}

    public void setAccountFlag(String accountFlag) {this.accountFlag = accountFlag;}
    public void setPassword(String password) {this.password = password;}
}
