package com.itcast.actviti7demo.service;

import com.itcast.actviti7demo.common.constants.ApprovalTypeEnum;
import com.itcast.actviti7demo.common.exception.CommonException;
import com.itcast.actviti7demo.common.netvo.activiti.ProcessDefinitionVo;
import com.itcast.actviti7demo.common.netvo.activiti.TaskPointVo;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/11/19 15:13
 * Description:
 */
@Service
public class ActivitiService {
    //private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 保存流程定义
     *
     * @param companyId 企业ID
     * @param vo        流程定义信息
     */
    public void saveProcess(String companyId, ProcessDefinitionVo vo) {
        String processName = ApprovalTypeEnum.getByCode(vo.getProcessType()).getValue() + "-" + companyId;
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId(processName);
        process.setName(processName);
        StartEvent startEvent = createStartEvent();
        EndEvent endEvent = createEndEvent();
        process.addFlowElement(startEvent);
        List<String> pointIds = new ArrayList<>();
        pointIds.add(startEvent.getId());
        for (TaskPointVo pointVo : vo.getPoints()) {
            process.addFlowElement(createUserTask(pointVo.getId(), pointVo.getName(), pointVo.getUsers()));
            pointIds.add(pointVo.getName());
        }
        process.addFlowElement(endEvent);
        pointIds.add(endEvent.getId());
        for (int i = 0; i < pointIds.size(); i++) {
            if (i + 1 < pointIds.size()) {
                process.addFlowElement(createSequenceFlow("_"+i,pointIds.get(i), pointIds.get(i + 1)));
            }
        }
        List<String> groupList = new ArrayList<>();
        groupList.add(companyId);
        process.setCandidateStarterGroups(groupList);
        Deployment deployment = repositoryService.createDeployment().addBpmnModel(processName + ".bpmn", model).deploy();
        vo.setProcessDefinitionId(deployment.getId());
        vo.setProcessDefinitionKey(deployment.getKey());
        System.out.println("完成...");
    }

    public ProcessDefinition findProcessByKey(String companyId, String processType) {
        String processKey = ApprovalTypeEnum.getByCode(processType).getValue() + "-" + companyId;
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionKeyLike(processKey).singleResult();
        return definition;
    }

    public void startProcess(String processDefinitionId, Map<String, Object> mapVariables) {
        runtimeService.startProcessInstanceById(processDefinitionId, mapVariables);
    }

    public List<Task> getMyTaskList(String userId) {
        List<Task> taskList = taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
        return taskList;
    }

    public VariableInstance getVariable(String executionId, String variable) {
        VariableInstance instance = runtimeService.getVariableInstance(executionId, variable);
        return instance;
    }

    public void executeTask(String taskId,String userId,Integer opType) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task==null){
            throw new CommonException("当前审批任务不存在!");
        }
        taskService.setAssignee(taskId,userId);
        if (opType==1){
            taskService.complete(taskId);
        }else{
            // TODO: 2018/11/20 回退流程
        }
    }

    //创建task
    private UserTask createUserTask(String id, String name, List<String> users) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateUsers(users);
//        userTask.setOwner("#{userId}");
        return userTask;
    }


    //创建箭头
    private SequenceFlow createSequenceFlow(String id,String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setId(id);
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    //创建开始事件
    private StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent1");
        startEvent.setName("Start");
        return startEvent;
    }

    //创建结束事件
    private EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent1");
        endEvent.setName("End");
        return endEvent;
    }
}
