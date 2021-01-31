package com.dregs.project.system.sta.controller.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class StaCarDetailMoney extends BaseEntity {
    /**/

    private String carId;
    @Excel(name="车牌")
    private String carNum;
    @Excel(name="车数")
    private String totalNum;
    @Excel(name="应付")
    private String totalMoney;
    @Excel(name="已付")
    private String payMoney;
    @Excel(name="待付")
    private String remainMoney;
    private String projectId;
    @Excel(name="项目")
    private String projectName;
    @Excel(name="渣场")
    private String slagyardName;
    private String slagyardId;
}
