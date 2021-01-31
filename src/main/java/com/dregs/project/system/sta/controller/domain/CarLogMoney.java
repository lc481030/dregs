package com.dregs.project.system.sta.controller.domain;

import com.dregs.framework.web.domain.BaseEntity;
import lombok.Data;

@Data
public class CarLogMoney  extends BaseEntity {

    private String projectId;
    private String carId;
    private String carNum;
    private String totalNum;
    private String totalMoney;
    private String payMoney;
    private String remainMoney;
}
