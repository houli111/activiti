package com.itcast.actviti7demo.service;

import com.itcast.actviti7demo.model.ActHiProcinst;

import java.util.Map;

public interface IActivitiProcessService {
    /**
     * 流程接入和审批 (个人任务)
     * @param workId
     * @param nodeCode 通过nodeCode做验证，防止同一任务 多人重复点击,后面流程如果操作人都被注入，流程会被推动多步
     * @param processInstanceId
     * @param paramMap
     * @return
     * @throws Exception
     */
    boolean confirmNodeProcessAssignee(String workId, String nodeCode, String processInstanceId, Map<String, Object> paramMap) throws Exception;

    /**
     * 流程接入和审批 （组任务）针对 并行网关 使用（不需要指定下一步nodeNode）,
     *                           非并行网关，可能造成多次点击流程被推动多步
     * @param workId
     * @param processInstanceId
     * @param paramMap
     * @return
     * @throws Exception
     */
    boolean confirmNodeProcess(String workId, String processInstanceId, Map<String, Object> paramMap) throws Exception;


    /**
     * 流程接入和审批 (组任务)
     * @param workId
     * @param nodeCode 通过nodeCode做验证，防止同一任务 多人重复点击，后面流程如果操作人都被注入，流程会被推动多步
     * @param processInstanceId
     * @param paramMap （流程变量)下一步执行人，判断条件等
     * @return
     * @throws Exception
     */
    boolean confirmNodeProcess(String workId, String nodeCode, String processInstanceId, Map<String, Object> paramMap) throws Exception;


    /**
     * 启动流程
     * @description TODO
     * @author wanghanbin
     * @date 2020/8/27 13:45
     **/
    ActHiProcinst startProcessInstance(String processDefinitionKey, String businessId, String permissionUserIds);


//    /**
//     * 查询个人任务或相关组任务
//     * @param workId
//     * @return
//     * @throws Exception
//     */
//    R queryTaskByWorkId(String processDefinitionKey, String workId) throws Exception;
}
