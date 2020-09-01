package com.itcast.actviti7demo.common.netvo.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/11/19 13:51
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskPointVo implements Serializable {
    private static final long serialVersionUID = 5532476421234360833L;
    /**
     * 节点名称
     */
    private  String id;

    private String name;
    /**
     * 用户ID列表
     */
    private List<String> users;
}
