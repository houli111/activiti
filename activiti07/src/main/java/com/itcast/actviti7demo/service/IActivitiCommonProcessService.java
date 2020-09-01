package com.itcast.actviti7demo.service;

import com.itcast.actviti7demo.model.ActHiProcinst;
import com.itcast.actviti7demo.model.UserLoginDto;
import com.itcast.actviti7demo.utils.WebResult;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface IActivitiCommonProcessService {

    WebResult doTaskWithoutPermissionCheck(String taskId, Map<String, Object> variables);

    List<Task> taskQueryByWorkId(  String workId);

    /**
     * 启动流程
     * @param processDefinitionKey
     * @param variables
     * @return
     * @throws Exception
     */
    ActHiProcinst startProcessInstance(String processDefinitionKey, Map<String, Object> variables);

    UserLoginDto login(String user , String pwd);

    WebResult doTask(String workId,String taskId, Map<String, Object> variables);
}
