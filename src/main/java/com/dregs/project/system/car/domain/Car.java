package com.dregs.project.system.car.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * 车辆信息对象 t_car
 * 
 * @author lc
 * @date 2020-11-03
 */
public class Car extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 车辆编号 */
    @Excel(name = "车辆编号")
    private String number;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carNum;

    /** 司机姓名 */
    @Excel(name = "司机姓名")
    private String driver;

    /** 身份证 */
    @Excel(name = "身份证")
    private String cardNum;

    /** 电话号码 */
    @Excel(name = "电话号码")
    private String tel;

    /** 拥有人 */
    @Excel(name = "拥有人")
    private String alluser;

    /** 车辆类型 */
    @Excel(name = "车辆类型")
    private String carType;

    /** 年审审核日期 */
    @Excel(name = "年审审核日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditDate;

    /** 删除标识 */
    @Excel(name = "删除标识")
    private String deleted;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getNumber()
    {
        return number;
    }
    public void setCarNum(String carNum)
    {
        this.carNum = carNum;
    }

    public String getCarNum()
    {
        return carNum;
    }
    public void setDriver(String driver)
    {
        this.driver = driver;
    }

    public String getDriver()
    {
        return driver;
    }
    public void setCardNum(String cardNum)
    {
        this.cardNum = cardNum;
    }

    public String getCardNum()
    {
        return cardNum;
    }
    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getTel()
    {
        return tel;
    }
    public void setAlluser(String alluser)
    {
        this.alluser = alluser;
    }

    public String getAlluser()
    {
        return alluser;
    }
    public void setCarType(String carType)
    {
        this.carType = carType;
    }

    public String getCarType()
    {
        return carType;
    }
    public void setAuditDate(Date auditDate)
    {
        this.auditDate = auditDate;
    }

    public Date getAuditDate()
    {
        return auditDate;
    }
    public void setDeleted(String deleted)
    {
        this.deleted = deleted;
    }

    public String getDeleted()
    {
        return deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("number", getNumber())
            .append("carNum", getCarNum())
            .append("driver", getDriver())
            .append("cardNum", getCardNum())
            .append("tel", getTel())
            .append("alluser", getAlluser())
            .append("carType", getCarType())
            .append("auditDate", getAuditDate())
            .append("deleted", getDeleted())
            .toString();
    }
}
