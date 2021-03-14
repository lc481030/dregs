package com.dregs.project.system.project.controller;

import com.dregs.common.utils.StringUtils;
import com.dregs.common.utils.poi.ExcelUtil;
import com.dregs.framework.aspectj.lang.annotation.Log;
import com.dregs.framework.aspectj.lang.enums.BusinessType;
import com.dregs.framework.web.controller.BaseController;
import com.dregs.framework.web.domain.AjaxResult;
import com.dregs.framework.web.page.TableDataInfo;
import com.dregs.project.system.car.domain.Car;
import com.dregs.project.system.car.service.ICarService;
import com.dregs.project.system.project.domain.StaCarProject;
import com.dregs.project.system.project.domain.StaProject;
import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.project.excel.ProjectExcel;
import com.dregs.project.system.project.service.ITProjectService;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.service.ISlagyardService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private ICarService carService;

    @Autowired
    private ISlagyardService selectCarList;

    @RequiresPermissions("system:project:view")
    @GetMapping("/staSlaProject")
    public String staSlaProject(String projectId,ModelMap mmap)
    {
        List<Slagyard> slagyards = selectCarList.selectSlagyardList(new Slagyard());
        mmap.put("projectId",projectId);
        mmap.put("slagyards",slagyards);
        return prefix + "/staSlaProject";
    }

    /**
     * 查询项目管理统计
     */
    @RequiresPermissions("system:project:list")
    @PostMapping("/staSlaList")
    @ResponseBody
    public TableDataInfo staSlaList(StaProject staProject)
    {
        startPage();
        List<StaCarProject> list = tProjectService.selectStaSlalist(staProject);
        return getDataTable(list);
    }

    /**
     * 导出项目管理列表
     */
    @RequiresPermissions("system:project:export")
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/staSlaExport")
    @ResponseBody
    public AjaxResult staSlaExport(StaProject staProject)
    {
        List<StaCarProject> list = tProjectService.selectStaSlalist(staProject);
        ExcelUtil<StaCarProject> util = new ExcelUtil<StaCarProject>(StaCarProject.class);
        return util.exportExcel(list, "project");
    }




    @RequiresPermissions("system:project:view")
    @GetMapping("/staCarProject")
    public String staCarProject(String projectId,ModelMap mmap)
    {
        List<Car> cars = carService.selectCarList(new Car());
        mmap.put("projectId",projectId);
        mmap.put("cars",cars);
        return prefix + "/staCarProject";
    }

    /**
     * 查询项目管理统计
     */
    @RequiresPermissions("system:project:list")
    @PostMapping("/staCarList")
    @ResponseBody
    public TableDataInfo staCarlist(StaProject staProject)
    {
        startPage();
        List<StaCarProject> list = tProjectService.selectStaCarlist(staProject);
        return getDataTable(list);
    }

    /**
     * 导出项目管理列表
     */
    @RequiresPermissions("system:project:export")
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/staCarExport")
    @ResponseBody
    public AjaxResult staCarExport(StaProject staProject)
    {
        List<StaCarProject> list = tProjectService.selectStaCarlist(staProject);
        ExcelUtil<StaCarProject> util = new ExcelUtil<StaCarProject>(StaCarProject.class);
        return util.exportExcel(list, "project");
    }

    @RequiresPermissions("system:project:view")
    @GetMapping("/staProject")
    public String staProject(ModelMap mmap)
    {
        List<TProject> projects = tProjectService.selectTProjectList(new TProject());
        mmap.put("projects",projects);
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

        TProject project = new TProject();
        BeanUtils.copyProperties(staProject,project);
        if (StringUtils.isNotEmpty(staProject.getProjectId())){
            project.setId(Long.parseLong(staProject.getProjectId()));
        }
        List<TProject> projects = tProjectService.selectTProjectList(project);
        long total = getDataTable(projects).getTotal();
        TableDataInfo res =  getDataTable(list);
        res.setTotal(total);
        return res;
    }

    @RequiresPermissions("system:project:allExport")
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @GetMapping("/allExport")
    @ResponseBody
    public void allExport(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            List<StaProject> projects = tProjectService.selectStaProjectList(new StaProject());
            Map<String,List<?>> map = new TreeMap<>();
            map.put("汇总",projects);
            List<StaCarProject> allList = new ArrayList<>();
            for (StaProject tp:projects) {
                StaProject staProject = new StaProject();
                staProject.setProjectId(tp.getProjectId());
                List<StaCarProject> list = tProjectService.selectStaCarlist(staProject);
                allList.addAll(list);
            }
            Map<String,List<StaCarProject>>  itemMap =  allList.stream().collect(Collectors.groupingBy(StaCarProject::getCarNum));
            map.putAll(itemMap);

            ProjectExcel.export(response,map);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 导出项目管理列表
     */
    @RequiresPermissions("system:project:export")
    @Log(title = "项目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/staexport")
    @ResponseBody
    public AjaxResult staexport(StaProject staProject)
    {
        List<StaProject> list = tProjectService.selectStaProjectList(staProject);
        ExcelUtil<StaProject> util = new ExcelUtil<StaProject>(StaProject.class);
        return util.exportExcel(list, "project");
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
