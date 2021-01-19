package com.dregs.project.system.slagyard.controller;

import java.util.List;

import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.project.service.ITProjectService;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.service.ISlagyardService;
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
import com.dregs.project.system.slagyard.domain.TProjectSlagyard;
import com.dregs.project.system.slagyard.service.ITProjectSlagyardService;
import com.dregs.framework.web.controller.BaseController;
import com.dregs.framework.web.domain.AjaxResult;
import com.dregs.common.utils.poi.ExcelUtil;
import com.dregs.framework.web.page.TableDataInfo;

/**
 * 项目渣场关系对应Controller
 * 
 * @author dregs
 * @date 2021-01-19
 */
@Controller
@RequestMapping("/system/pslagyard")
public class TProjectSlagyardController extends BaseController
{
    private String prefix = "system/pslagyard";

    @Autowired
    private ITProjectSlagyardService tProjectSlagyardService;

    @Autowired
    private ISlagyardService slagyardService;

    @Autowired
    private ITProjectService projectService;

    @RequiresPermissions("system:pslagyard:view")
    @GetMapping()
    public String pslagyard(ModelMap mmap)
    {
        List<Slagyard> list =  slagyardService.selectSlagyardList(new Slagyard());
        mmap.put("slagyardList",list);

        List<TProject> listProject =  projectService.selectTProjectList(new TProject());
        mmap.put("projectList",listProject);
        return prefix + "/slagyard";
    }

    /**
     * 查询项目渣场关系对应列表
     */
    @RequiresPermissions("system:pslagyard:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TProjectSlagyard tProjectSlagyard)
    {
        startPage();
        List<TProjectSlagyard> list = tProjectSlagyardService.selectTProjectSlagyardList(tProjectSlagyard);
        for (TProjectSlagyard ts:list){
            Slagyard s = slagyardService.selectSlagyardById(ts.getSlagyardId());
            if(s!=null){
                ts.setSlagyardName(s.getTitle());
            }
            TProject project = projectService.selectTProjectById(Long.parseLong(ts.getProjectId()));
            if (project!=null){
                ts.setProjectName(project.getName());
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出项目渣场关系对应列表
     */
    @RequiresPermissions("system:pslagyard:export")
    @Log(title = "项目渣场关系对应", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TProjectSlagyard tProjectSlagyard)
    {
        List<TProjectSlagyard> list = tProjectSlagyardService.selectTProjectSlagyardList(tProjectSlagyard);
        for (TProjectSlagyard ts:list){
            Slagyard s = slagyardService.selectSlagyardById(ts.getSlagyardId());
            if(s!=null){
                ts.setSlagyardName(s.getTitle());
            }
            TProject project = projectService.selectTProjectById(Long.parseLong(ts.getProjectId()));
            if (project!=null){
                ts.setProjectName(project.getName());
            }
        }
        ExcelUtil<TProjectSlagyard> util = new ExcelUtil<TProjectSlagyard>(TProjectSlagyard.class);
        return util.exportExcel(list, "pslagyard");
    }

    /**
     * 新增项目渣场关系对应
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        List<Slagyard> list =  slagyardService.selectSlagyardList(new Slagyard());
        mmap.put("slagyardList",list);

        List<TProject> listProject =  projectService.selectTProjectList(new TProject());
        mmap.put("projectList",listProject);
        return prefix + "/add";
    }

    /**
     * 新增保存项目渣场关系对应
     */
    @RequiresPermissions("system:pslagyard:add")
    @Log(title = "项目渣场关系对应", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TProjectSlagyard tProjectSlagyard)
    {
        return toAjax(tProjectSlagyardService.insertTProjectSlagyard(tProjectSlagyard));
    }

    /**
     * 修改项目渣场关系对应
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TProjectSlagyard tProjectSlagyard = tProjectSlagyardService.selectTProjectSlagyardById(id);
        mmap.put("tProjectSlagyard", tProjectSlagyard);

        List<Slagyard> list =  slagyardService.selectSlagyardList(new Slagyard());
        mmap.put("slagyardList",list);

        List<TProject> listProject =  projectService.selectTProjectList(new TProject());
        mmap.put("projectList",listProject);

        return prefix + "/edit";
    }

    /**
     * 修改保存项目渣场关系对应
     */
    @RequiresPermissions("system:pslagyard:edit")
    @Log(title = "项目渣场关系对应", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TProjectSlagyard tProjectSlagyard)
    {
        return toAjax(tProjectSlagyardService.updateTProjectSlagyard(tProjectSlagyard));
    }

    /**
     * 删除项目渣场关系对应
     */
    @RequiresPermissions("system:pslagyard:remove")
    @Log(title = "项目渣场关系对应", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tProjectSlagyardService.deleteTProjectSlagyardByIds(ids));
    }
}
