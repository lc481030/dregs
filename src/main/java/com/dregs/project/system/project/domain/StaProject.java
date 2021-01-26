package com.dregs.project.system.project.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class StaProject extends BaseEntity {

    private String carId;

    private String slagyardId;

    /*项目名称*/
    @Excel(name = "项目名称")
    private String name;

    private String projectId;

    @Excel(name = "利润")
    private String pullMoney;/*收益 - 车运 - 渣场*/

    @Excel(name = "收入")
    private String pullTotalMoney;

    @Excel(name = "车运应付")
    private String pushCarTotalMoney;

    @Excel(name = "渣土应付")
    private String pushSlaTotalMoney;

    @Excel(name = "发票数量")
    private String pushTotal;

    @Excel(name = "车运数量")
    private String carTotal;

    @Excel(name = "渣场数量")
    private String  slaTotal;

}
