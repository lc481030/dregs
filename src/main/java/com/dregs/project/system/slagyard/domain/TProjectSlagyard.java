package com.dregs.project.system.slagyard.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 项目渣场关系对应对象 t_project_slagyard
 * 
 * @author dregs
 * @date 2021-01-19
 */
public class TProjectSlagyard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 项目id */
    private String projectId;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String projectType;

    /** 收益单价 */
    @Excel(name = "收益单价")
    private String pullMoney;

    /** 车运价格 */
    @Excel(name = "车运价格")
    private String pushCarMaoney;

    /** 渣场价格 */
    @Excel(name = "渣场价格")
    private String pushSlagyardMaoney;

    /** 渣场ID */
    private Long slagyardId;

    /** 删除标识 */

    private Long deleted;

    @Excel(name = "项目名称")
    private String projectName;

    @Excel(name = "渣场名称")
    private String slagyardName;


    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    public String getProjectId()
    {
        return projectId;
    }
    public void setProjectType(String projectType)
    {
        this.projectType = projectType;
    }

    public String getProjectType()
    {
        return projectType;
    }
    public void setPullMoney(String pullMoney)
    {
        this.pullMoney = pullMoney;
    }

    public String getPullMoney()
    {
        return pullMoney;
    }
    public void setPushCarMaoney(String pushCarMaoney)
    {
        this.pushCarMaoney = pushCarMaoney;
    }

    public String getPushCarMaoney()
    {
        return pushCarMaoney;
    }
    public void setPushSlagyardMaoney(String pushSlagyardMaoney)
    {
        this.pushSlagyardMaoney = pushSlagyardMaoney;
    }

    public String getPushSlagyardMaoney()
    {
        return pushSlagyardMaoney;
    }
    public void setSlagyardId(Long slagyardId)
    {
        this.slagyardId = slagyardId;
    }

    public Long getSlagyardId()
    {
        return slagyardId;
    }
    public void setDeleted(Long deleted)
    {
        this.deleted = deleted;
    }

    public Long getDeleted()
    {
        return deleted;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSlagyardName() {
        return slagyardName;
    }

    public void setSlagyardName(String slagyardName) {
        this.slagyardName = slagyardName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("projectId", getProjectId())
            .append("projectType", getProjectType())
            .append("pullMoney", getPullMoney())
            .append("pushCarMaoney", getPushCarMaoney())
            .append("pushSlagyardMaoney", getPushSlagyardMaoney())
            .append("slagyardId", getSlagyardId())
            .append("deleted", getDeleted())
            .toString();
    }
}
