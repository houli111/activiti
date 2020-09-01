package com.itcast.actviti7demo.service.impl;

import com.google.common.base.Preconditions;
import com.itcast.actviti7demo.model.ActHiProcinst;
import com.itcast.actviti7demo.service.IActivitiCommonProcessService;
import com.itcast.actviti7demo.service.IActivitiProcessService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActivitiProcessServiceImpl implements IActivitiProcessService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private IActivitiCommonProcessService commonProcessService;
    //16.16.   任务处理 个人任务 通过nodeCode做验证，防止同一任务 多人重复点击
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean confirmNodeProcessAssignee(String workId,String nodeCode, String processInstanceId, Map<String, Object> paramMap) throws Exception{
        Preconditions.checkNotNull(workId, "workId can not be null");
        Preconditions.checkNotNull(nodeCode,"nodeCode can not be null");
        Preconditions.checkNotNull(processInstanceId, "processInstanceId can not be null");
        //Preconditions.checkNotNull(paramMap, "paramMap can not be null");
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(workId).processInstanceId(processInstanceId).list();
        if(CollectionUtils.isEmpty(taskList)){
            throw new Exception("没有您的对应审批任务");
        }
        List<Task> tasks = taskList.stream().filter(task -> nodeCode.equals(task.getName())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tasks)){
            throw new Exception("任务进度不匹配");
        }

        //Set<String> taskIds = taskList.stream().map(TaskInfo::getId).collect(Collectors.toSet());
        for (Task task : tasks){
            String taskId = task.getId();
            //taskService.addComment(taskId,processInstanceId ,nodeCode );
            //处理任务
            commonProcessService.doTaskWithoutPermissionCheck(taskId,paramMap);
        }
        return true;
    }

    //16.16.   任务处理---并行网关组任务使用（不需要指定下一步nodeNode）--非并行网关，可能造成多次点击流程被推动多步
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean confirmNodeProcess(String workId, String processInstanceId, Map<String, Object> paramMap) throws Exception{
        Preconditions.checkNotNull(workId, "workId can not be null");
        Preconditions.checkNotNull(processInstanceId, "processInstanceId can not be null");
        //Preconditions.checkNotNull(paramMap, "paramMap can not be null");
//        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(workId).processInstanceId(processInstanceId).list();
        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(workId).list();
        for (Task task : list) {
            System.out.println(task.toString());
        }

        if(CollectionUtils.isEmpty(list)){
            throw new Exception("没有您的对应审批任务");
        }

        //Set<String> taskIds = taskList.stream().map(TaskInfo::getId).collect(Collectors.toSet());
        for (Task task : list){
            String taskId = task.getId();
            //任务拾取
            taskService.claim(taskId, workId);
            // taskId,流程实例 id ,批注内容
            //Authentication.setAuthenticatedUserId(workId);
            taskService.addComment(taskId,processInstanceId ,"拾取组任务 并行网关无nodeCode操作" );
            //处理任务
            taskService.complete(taskId, paramMap);
        }
        return true;
    }


    //16.16.   任务处理 组任务 通过nodeCode做验证，防止同一任务 多人重复点击
    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public boolean confirmNodeProcess(String workId,String nodeCode, String processInstanceId, Map<String, Object> paramMap) throws Exception{
        Preconditions.checkNotNull(workId, "workId can not be null");
        Preconditions.checkNotNull(nodeCode,"nodeCode can not be null");
        Preconditions.checkNotNull(processInstanceId, "processInstanceId can not be null");
        //Preconditions.checkNotNull(paramMap, "paramMap can not be null");
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        List<Task> tasks = taskList.stream().filter(task -> nodeCode.equals(task.getName())).collect(Collectors.toList());

//        Map<String,Object> paramMap = new HashMap<>();// map 提前注入下一步处理人流程信息
//        paramMap.put("agree","true");
        if (CollectionUtils.isEmpty(tasks)){
            throw new Exception("任务进度不匹配");
        }
        for (Task task : tasks){
            String taskId = task.getId();
            //taskService.addComment(taskId,processInstanceId ,nodeCode );
            //处理任务
            commonProcessService.doTask(workId,taskId,paramMap);
        }
        return true;
    }


    /**
     * 启动一个实例
     */
    @Override
    public ActHiProcinst startProcessInstance(String processDefinitionKey, String businessId, String permissionUserIds) {
        Map<String,Object> map = new HashMap<>();// map 提前注入下一步处理人流程信息
        map.put("businessId",businessId);
        map.put("user",permissionUserIds);
        return commonProcessService.startProcessInstance(processDefinitionKey,map);
    }

//    //16.13.   查询任务
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
//    public R queryTaskByWorkId(String processDefinitionKey, String workId) throws Exception{
//        Preconditions.checkNotNull(workId,"workId can not be null");
//
//
//        return commonProcessService.taskQueryByWorkId(processDefinitionKey,workId);
//    }

}
