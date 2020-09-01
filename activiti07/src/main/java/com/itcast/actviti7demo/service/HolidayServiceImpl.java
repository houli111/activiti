package com.itcast.actviti7demo.service;

import com.itcast.actviti7demo.mapper.HolidayMapper;
import com.itcast.actviti7demo.model.Holiday;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by Administrator on 2019/5/1.
 */
@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    private HolidayMapper holidayMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void saveHoliday(Holiday holiday) {
        String key = ResourceBundle.getBundle("bpmnFile").getString("processDefinitionKey");
        System.out.println(key);
        String holiday_id = UUID.randomUUID().toString();
        String businessKey = holiday_id;



        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, businessKey);

        holiday.setProcessinstanceid(processInstance.getId());
        holiday.setHolidayId(holiday_id);


        holidayMapper.insertSelective(holiday);
    }
}
