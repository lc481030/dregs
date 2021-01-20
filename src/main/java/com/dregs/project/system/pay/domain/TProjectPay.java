package com.dregs.project.system.pay.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 项目收支对象 t_project__pay
 * 
 * @author dregs
 * @date 2021-01-20
 */
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

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSlagyardId() {
        return slagyardId;
    }

    public void setSlagyardId(Long slagyardId) {
        this.slagyardId = slagyardId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setAddUserId(Long addUserId)
    {
        this.addUserId = addUserId;
    }

    public Long getAddUserId()
    {
        return addUserId;
    }
    public void setAddName(String addName)
    {
        this.addName = addName;
    }

    public String getAddName()
    {
        return addName;
    }
    public void setUpdUserId(Long updUserId)
    {
        this.updUserId = updUserId;
    }

    public Long getUpdUserId()
    {
        return updUserId;
    }
    public void setUdpName(String udpName)
    {
        this.udpName = udpName;
    }

    public String getUdpName()
    {
        return udpName;
    }
    public void setPayType(Long payType)
    {
        this.payType = payType;
    }

    public Long getPayType()
    {
        return payType;
    }
    public void setMoney(String money)
    {
        this.money = money;
    }

    public String getMoney()
    {
        return money;
    }
    public void setRelationId(Long relationId)
    {
        this.relationId = relationId;
    }

    public Long getRelationId()
    {
        return relationId;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("addUserId", getAddUserId())
            .append("addName", getAddName())
            .append("updUserId", getUpdUserId())
            .append("udpName", getUdpName())
            .append("payType", getPayType())
            .append("money", getMoney())
            .append("relationId", getRelationId())
            .append("type", getType())
            .toString();
    }
}
