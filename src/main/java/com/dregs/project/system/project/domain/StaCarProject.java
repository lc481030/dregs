package com.dregs.project.system.project.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class StaCarProject extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long carId;

    /** 车辆主键或者渣场主键 */
    @Excel(name = "车辆/渣场")
    private String carNum;


    /** 车辆主键或者渣场主键 */
    @Excel(name = "已付")
    private String payMoney;

    /** 运输车数 */
    @Excel(name = "运输车数")
    private Long transportNum;

    /** 项目渣场关系表ID */
    @Excel(name = "项目")
    private String projectName;


    @Excel(name = "支出单价")
    private String pushCarMoney;

    @Excel(name = "支出总价")
    private String totalMoney;


    @Excel(name = "渣场")
    private String slaName;

}
