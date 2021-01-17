package com.dregs.project.system.slagyard.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 渣场对象 t_slagyard
 * 
 * @author lc
 * @date 2020-11-06
 */
public class Slagyard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String title;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("title", getTitle())
            .toString();
    }
}
