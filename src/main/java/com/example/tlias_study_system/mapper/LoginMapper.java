package com.example.tlias_study_system.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginMapper {
    @Insert("insert into account values(#{accountFlag},#{password})")
    int Registers(@Param("accountFlag")String accountFlag,@Param("password")String password);
    @Select("select count(*) from account where account_flag=#{accountFlag} and password=#{password}")
    int QueryAccount(@Param("accountFlag")String accountFlag,@Param("password")String password);
    @Select("select count(*) from account where account_flag=#{accountFlag}")
    int QueryOnlyAccount(@Param("accountFlag")String accountFlag);
    @Update("update account set password=#{password} where account_flag=#{accountFlag}")
    int PutAccount(@Param("accountFlag")String accountFlag,@Param("password")String password);
}
