package com.itcast.actviti7demo.controller;

import com.google.common.base.Preconditions;
import com.itcast.actviti7demo.model.ActHiProcinst;
import com.itcast.actviti7demo.model.ParamVO;
import com.itcast.actviti7demo.model.UserLoginDto;
import com.itcast.actviti7demo.service.IActivitiCommonProcessService;
import com.itcast.actviti7demo.service.IActivitiProcessService;
import com.itcast.actviti7demo.utils.R;
import com.itcast.actviti7demo.utils.WebResult;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 处理流程相关的类
 */
@Controller
@RequestMapping("/pages")
public class ProcessController {

    @Autowired
    private IActivitiCommonProcessService activitiCommonProcessService;

    @Autowired
    private TaskService taskService;
    //注入相关的流程处理的runtime,service
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private RepositoryService repositoryService;


    @Autowired
    private IActivitiCommonProcessService commonProcessService;
    @Autowired
    private IActivitiProcessService activitiProcessService;
    UserLoginDto  login= null;

    @PostMapping("/deployment")
    public String deployment(@RequestParam("bpmn") MultipartFile file) throws IOException{
        //1.得到上传文件的文件名
        String fileName = file.getOriginalFilename();
        String substring = fileName.substring(0, fileName.indexOf("."));
        //2.实现流程部署
        Deployment deployment = repositoryService.createDeployment()
                .addBytes(fileName, file.getBytes()).name(substring).key(substring).deploy();
        //3.输出部署的ID
        String deploymentID = deployment.getId();
        System.out.println("======================================="+deploymentID);

        return "ablank";
    }

    // 查询全部流程
    @RequestMapping("/search-Process")
    public String search(Model model){
        //1.查询流程定义信息
        //得到流程定义的查询器
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.list();
        //2.将查询结果放入model中
        model.addAttribute("list",list);
        System.out.println("================="+list.size());
        //3.跳转页面
        return "searchProcess";
    }
    //16.13.   查询任务
    @RequestMapping("/queryTaskByWorkId")
    public String  queryTaskByWorkId(String user, Model model){

        Preconditions.checkNotNull(user,"workId can not be null");
        List<Task> list = commonProcessService.taskQueryByWorkId(user);
        model.addAttribute("list",list);
        System.out.println("========你还有"+list.size()+"个任务需处理");

        return "disposeProcess";
    }


    @RequestMapping("/dispose_Process")
    public String disposeProcess(@RequestBody ParamVO paramVO) throws Exception{
        activitiProcessService.confirmNodeProcess(paramVO.getWorkId(), paramVO.getProcessInstanceId(), paramVO.getParamMap());
        return "disposeProcess";
    }

    @RequestMapping("/viewBpmn")
    public void view(String processDefinitionId, String resourceType, HttpServletResponse response) throws Exception {
        //1.得到ProcessDefinitionQuery对象
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //2.根据processDefinitionId，查询得到具体的一个ProcessDefinition对象
        ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionId(processDefinitionId).singleResult();
        //3.通过processDefinition对象，得到deploymentID
        String deploymentId = processDefinition.getDeploymentId();
        //4.获取资源文件的名称
        InputStream is =null;
        if("bpmn".equals(resourceType)){
            String resourceName=processDefinition.getResourceName();//得到资源文件名称
            //5.获取bpmn文件输入流
             is = repositoryService.getResourceAsStream(deploymentId,resourceName);
        }

        //6.由response对象，得到outputStream
        ServletOutputStream outputStream = response.getOutputStream();
        //7.实现文件复制
        IOUtils.copy(is,outputStream);
        //8.关闭流对象
        outputStream.close();
        is.close();
    }

    @RequestMapping("/deleteDeployment")
    public String deleteDeployment(String deploymentId){
        //1.调用repositoryService的方法，实现删除操作
        repositoryService.deleteDeployment(deploymentId,true);

        //2.跳页面
        return "forward:search-Process";
    }


    /**
     * 启动流程实例
     * 根据流程定义 启动一个流程实例
     * 根据流程定义的一次具体执行过程，就是一个流程实例
     * businessId  业务id  比如：请假流程的业务id就是具体人的请假的单号
     * permissionUserIds 下一步执行人  可以提前注入，也可以每一步的时候在判断，(提前注入的话下一步只能注入的人才可以操作)
     */
    @GetMapping("/startProcessInstance")
    @ResponseBody
   public WebResult startProcessInstance(@RequestParam String processDefinitionKey,
                                          @RequestParam String businessId,@RequestParam String permissionUserIds) {
        ActHiProcinst actHiProcinst = activitiProcessService.startProcessInstance(processDefinitionKey, businessId, permissionUserIds);
        return WebResult.success().withData(actHiProcinst);
    }

//    /**
//     * @description 查看当前用户的任务列表
//     * processDefinitionKey 流程key
//     * workId 用户id
//     * @date 2020/8/27 13:43
//     **/
//    @GetMapping("/queryUserInstance")
//    @ResponseBody
//    public  WebResult queryUserInstance(@RequestParam String processDefinitionKey,@RequestParam String workId) throws Exception {
//        return WebResult.success().withData(activitiProcessService.queryTaskByWorkId(processDefinitionKey, workId));
//    }

    /**
     * 处理流程 任务处理---并行网关组任务使用（不需要指定下一步nodeNode）--非并行网关，可能造成多次点击流程被推动多步
     * @date 2020/8/27 13:09
     **/
    @PostMapping("/parallelConfirmNodeProcess")
    public WebResult parallelConfirmNodeProcess(@RequestBody ParamVO paramVO) throws Exception{
        activitiProcessService.confirmNodeProcess(paramVO.getWorkId(), paramVO.getProcessInstanceId(), paramVO.getParamMap());
        return WebResult.success();
    }
        //任务处理 组任务 通过nodeCode做验证，防止同一任务 多人重复点击
    @PostMapping("/confirmNodeProcess")
    //http://localhost:9020/activiti/prdocess/confirmNodeProcess
    @ResponseBody
    public void confirmNodeProcess(@RequestBody ParamVO paramVO) throws Exception{

        activitiProcessService.confirmNodeProcess(paramVO.getWorkId(), paramVO.getNodeCode(), paramVO.getProcessInstanceId(), paramVO.getParamMap());
//        return WebResult.success();
    }

    @RequestMapping("/login")
    @ResponseBody
    public R login(@RequestParam String user , @RequestParam String pwd){
         login = activitiCommonProcessService.login(user, pwd);
        if(login == null ){
            return R.error("用户为空");
        }
        System.out.println("登录成功");
        return R.ok().put("data",login);
    }


}
