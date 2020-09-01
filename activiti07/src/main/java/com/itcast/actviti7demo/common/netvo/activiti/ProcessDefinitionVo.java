package com.itcast.actviti7demo.common.netvo.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/11/19 13:50
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDefinitionVo implements Serializable {
    private static final long serialVersionUID = 4080685481067557387L;
    /**
     * 流程定义ID
     */
    private String processDefinitionId;
    /**
     * 流程定义Key
     */
    private String processDefinitionKey;
    /**
     * 审批设置类型ID
     */
    private String processType;
    /**
     * 任务节点列表
     */
    List<TaskPointVo> points;
}
