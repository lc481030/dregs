package com.dregs.project.system.project.domain;

import com.dregs.framework.aspectj.lang.annotation.Excel;
import com.dregs.framework.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 项目管理对象 t_project
 * 
 * @author dregs
 * @date 2021-01-19
 */
@Data
public class TProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 父项目id */
    private Long pid;

    /** 项目名称 */
    @Excel(name = "父项目名称")
    private String pName;

}
