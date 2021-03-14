package com.dregs.project.system.project.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class SlagYardStatisticsProject extends BaseEntity {


    private String slagyardId;

    /*项目名称*/
    @Excel(name = "渣场名称")
    private String name;

    /*项目名称*/
    @Excel(name = "项目名称")
    private String pname;

    @Excel(name = "票（数）")
    private String slaTotal;

    private String projectId;

    @Excel(name = "应付")
    private String pushSlaTotalMoney;

    @Excel(name = "已付")
    private String  pullEndMoney;


}
