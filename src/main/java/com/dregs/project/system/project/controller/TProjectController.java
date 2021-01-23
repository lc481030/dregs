package com.dregs.project.system.project.controller;

import java.util.List;

import com.dregs.project.system.project.domain.StaProject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dregs.framework.aspectj.lang.annotation.Log;
import com.dregs.framework.aspectj.lang.enums.BusinessType;
import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.project.service.ITProjectService;
import com.dregs.framework.web.controller.BaseController;
import com.dregs.framework.web.domain.AjaxResult;
import com.dregs.common.utils.poi.ExcelUtil;
import com.dregs.framework.web.page.TableDataInfo;

/**
 * 项目管理Controller
 * 
 * @author dregs
 * @date 2021-01-19
 */
@Controller
@RequestMapping("/system/project")
public class TProjectController extends BaseController
{
    private String prefix = "system/project";

    @Autowired
    private ITProjectService tProjectService;

    @RequiresPermissions("system:project:view")
    @GetMapping("/staProject")
    public String staProject()
    {
        return prefix + "/staProject";
    }

    /**
     * 查询项目管理统计
     */
    @RequiresPermissions("system:project:list")
    @PostMapping("/stalist")
    @ResponseBody
    public TableDataInfo stalist(StaProject staProject)
    {
        startPage();
        List<StaProject> list = tProjectService.selectStaProjectList(staProject);
        return getDataTable(list);
    }


    @RequiresPermissions("system:project:view")
    @GetMapping()
    public String project()
    {
        return prefix + "/project";
    }

    /**
     * 查询项目管理列表
     */
    @RequiresPermissions("system:project:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TProject tProject)
    {
        startPage();
        List<TProject> list = tProjectService.selectTProjectList(tProject);
        return getDataTable(list);
    }

    /**
     * 导出项目管理列表
     */
    @RequiresPermissions("system:project:export")
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TProject tProject)
    {
        List<TProject> list = tProjectService.selectTProjectList(tProject);
        ExcelUtil<TProject> util = new ExcelUtil<TProject>(TProject.class);
        return util.exportExcel(list, "project");
    }

    /**
     * 新增项目管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        TProject project = new TProject();
        List<TProject> projects = tProjectService.selectTProjectList(project);
        mmap.put("projects",projects);
        return prefix + "/add";
    }

    /**
     * 新增保存项目管理
     */
    @RequiresPermissions("system:project:add")
    @Log(title = "项目管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TProject tProject)
    {
        return toAjax(tProjectService.insertTProject(tProject));
    }

    /**
     * 修改项目管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TProject project = new TProject();
        List<TProject> projects = tProjectService.selectTProjectList(project);
        mmap.put("projects",projects);
        TProject tProject = tProjectService.selectTProjectById(id);
        mmap.put("tProject", tProject);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目管理
     */
    @RequiresPermissions("system:project:edit")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TProject tProject)
    {
        return toAjax(tProjectService.updateTProject(tProject));
    }

    /**
     * 删除项目管理
     */
    @RequiresPermissions("system:project:remove")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tProjectService.deleteTProjectByIds(ids));
    }
}
