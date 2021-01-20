package com.dregs.project.system.transport.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 车运对象 t_car_transport
 * 
 * @author dregs
 * @date 2021-01-20
 */
@Data
public class CarTransport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 添加人 */
    @Excel(name = "添加人")
    private Long addUserId;

    /** 添加人名称 */
    @Excel(name = "添加人名称")
    private String addName;

    /** 更新人 */
    @Excel(name = "更新人")
    private Long updUserId;

    /** 更新人名称 */
    @Excel(name = "更新人名称")
    private String udpName;

    /** 车辆主键或者渣场主键 */
    @Excel(name = "车辆主键或者渣场主键")
    private Long relationId;

    /** 运输车数 */
    @Excel(name = "运输车数")
    private Long transportNum;

    /** 项目id */
    @Excel(name = "项目id")
    private Long projectId;

    /** 类型1车运2渣场 */
    @Excel(name = "类型1车运2渣场")
    private Long transportType;

    /** 项目渣场关系表ID */
    @Excel(name = "项目渣场关系表ID")
    private Long projectSlagyardId;


}