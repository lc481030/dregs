package com.dregs.project.system.pay.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 项目收支对象 t_project_pay
 * 
 * @author dregs
 * @date 2021-01-20
 */
@Data
public class TProjectPay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 添加人 */
//    @Excel(name = "添加人")
    private Long addUserId;

    /** 添加人名称 */
//    @Excel(name = "添加人名称")
    private String addName;

    /** 更新人 */
//    @Excel(name = "更新人")
    private Long updUserId;

    /** 更新人名称 */
//    @Excel(name = "更新人名称")
    private String udpName;

    /** 支付类型（1车运2渣费3子项目4其他） */
//    @Excel(name = "支付类型", readConverterExp = "1=车运2渣费3子项目4其他")
    private Long payType;

    /** 钱 */
    @Excel(name = "金额")
    private String money;

    /** 关系id（车、渣、项目）其他费用为空 */
//    @Excel(name = "关系id", readConverterExp = "车=、渣、项目")
    private Long relationId;

    /** 1收入2支出 */
//    @Excel(name = "1收入2支出")
    private String type;

    @Excel(name = "收支")
    private String typeName;
    @Excel(name = "关系")
    private String relationName;
    @Excel(name = "支付类型")
    private String payTypeName;

    private String beginTime;
    private String endTime;

    private Long carId;
    private Long projectId;
    private Long slagyardId;

    private String payObjId;
}
