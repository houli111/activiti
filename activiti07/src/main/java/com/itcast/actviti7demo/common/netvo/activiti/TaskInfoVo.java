package com.itcast.actviti7demo.common.netvo.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/11/20 16:58
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfoVo implements Serializable {
    private static final long serialVersionUID = -7676363937173114756L;
    /**
     * 开始人ID
     */
    private String ownerId;
    /**
     * 表单数据
     */
    private String data;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 审批类型
     */
    private String processType;
    /**
     * 创建时间
     */
    private Date createTime;
}
