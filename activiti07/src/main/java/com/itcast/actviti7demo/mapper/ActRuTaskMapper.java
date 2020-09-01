package com.itcast.actviti7demo.mapper;

import com.itcast.actviti7demo.model.ActRuTask;
import com.itcast.actviti7demo.model.ActRuTaskExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface ActRuTaskMapper {
    List<ActRuTask> selectByExample(ActRuTaskExample example);


    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table act_ru_task
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<ActRuTask> list);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table act_ru_task
     *
     * @mbg.generated
     * @author hewei
     */
    ActRuTask selectOneByExample(ActRuTaskExample example);
}