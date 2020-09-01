package com.itcast.actviti7demo.mapper;

import com.itcast.actviti7demo.model.HolidayAudit;
import com.itcast.actviti7demo.model.UserLoginDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HolidayAuditMapper {
    int insert(HolidayAudit record);

    int insertSelective(HolidayAudit record);


    UserLoginDto login(@Param("user")String user, @Param("pwd") String pwd);
}