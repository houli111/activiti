package com.itcast.actviti7demo.mapper;

import com.itcast.actviti7demo.model.Holiday;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HolidayMapper {
    int deleteByPrimaryKey(String holidayId);

    int insert(Holiday record);

    int insertSelective(Holiday record);

    Holiday selectByPrimaryKey(String holidayId);

    int updateByPrimaryKeySelective(Holiday record);

    int updateByPrimaryKey(Holiday record);
}