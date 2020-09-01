package com.itcast.actviti7demo.service.impl;

import com.itcast.actviti7demo.mapper.ActRuTaskMapper;
import com.itcast.actviti7demo.mapper.HolidayAuditMapper;
import com.itcast.actviti7demo.model.ActHiProcinst;
import com.itcast.actviti7demo.model.UserLoginDto;
import com.itcast.actviti7demo.service.IActivitiCommonProcessService;
import com.itcast.actviti7demo.utils.WebResult;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ActivitiCommonProcessServiceImpl implements IActivitiCommonProcessService {

    @Autowired
    private HolidayAuditMapper holidayAuditMapper;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ActRuTaskMapper actRuTaskMapper;
    @Override
    public WebResult doTaskWithoutPermissionCheck(String taskId, Map<String, Object> variables){
        taskService.complete(taskId,variables);
        return WebResult.success();
    }
    /**
     * 查询用户的任务列表
     */
    @Override
    public List<Task> taskQueryByWorkId( String workId) {

        TaskQuery taskQuery = taskService.createTaskQuery();
     List<Task> list = taskQuery.taskAssignee(workId).list();
//        }
        //查询当前代理人要执行的任务
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);
            System.out.println("任务id：" + task.getId());
            System.out.println("任务名称：" + task.getName());
            System.out.println("任务执行人：" + task.getAssignee());
        }
        return list;

    }


    @Override
    public ActHiProcinst startProcessInstance(String processDefinitionKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        ActHiProcinst procinst = new ActHiProcinst();
        procinst.setProcDefId(processInstance.getProcessDefinitionId());
        procinst.setProcInstId(processInstance.getProcessInstanceId());
        procinst.setTenantId(processInstance.getTenantId());
        return procinst;
    }

    @Override
    public UserLoginDto login(String user, String pwd) {

        return holidayAuditMapper.login( user, pwd);
    }
    @Override
    @Transactional
    public WebResult doTask(String workId,String taskId, Map<String, Object> variables){
        Task task = taskService.createTaskQuery().taskId(taskId).taskCandidateOrAssigned(workId).singleResult();
        if (null == task){
            throw new RuntimeException("暂无相关任务");
        }
        taskService.setVariables(taskId,variables);
        taskService.complete(taskId);
        return WebResult.success();
    }

}
