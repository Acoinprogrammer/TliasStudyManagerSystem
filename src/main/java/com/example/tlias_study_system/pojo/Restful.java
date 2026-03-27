package com.example.tlias_study_system.pojo;

import com.sun.istack.NotNull;

public class Restful {
    private Integer code;
    private String message;
    private Object data;
    private String token;

    public Integer getCode() {return code;}
    public String getMessage() {return message;}
    public Object getData() {return data;}
    public String getToken() {return token;}

    public void setCode(Integer code) {this.code = code;}
    public void setMessage(String message) {this.message = message;}
    public void setData(Object data) {this.data = data;}
    public void setToken(String token){this.token=token;}

    public static Restful success(){
        Restful res = new Restful();
        res.code=200;
        res.message="成功";
        return res;
    }
    public static Restful success(Object data){
        Restful res = new Restful();
        res.code=200;
        res.message="成功";
        res.data=data;
        return res;
    }
    public static Restful success(Object data, boolean Flag){
        Restful res = new Restful();
        res.code=200;
        res.message="成功";
        res.data=data;
        return res;
    }
    public static Restful success(Object data, String message){
        Restful res = new Restful();
        res.code = 200;
        res.message = message;
        res.data = data;
        return res;
    }
    public static Restful success(@NotNull Object data, @NotNull String token, String message){
        Restful res = new Restful();
        res.code = 200;
        res.message=message;
        res.data=data;
        res.token=token;
        return res;
    }
    public static Restful success(String token){
        Restful res = new Restful();
        res.code=200;
        res.token=token;
        return res;
    }
    public static Restful error(String str){
        Restful res = new Restful();
        res.code=500;
        res.message=str;
        return res;
    }
    public static Restful error(){
        Restful res = new Restful();
        res.code=500;
        return res;
    }

    public static Restful warning(){
        Restful res = new Restful();
        res.code=400;
        res.message="warning";
        return res;
    }
    public static Restful warning(String str){
        Restful res = new Restful();
        res.code=400;
        res.message=str;
        return res;
    }

    @Override
    public String toString() {
        return "restful{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", token='" + token + '\'' +
                '}';
    }
}
