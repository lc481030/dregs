package com.dregs.project.system.slagyard.controller;

import com.dregs.common.utils.StringUtils;
import com.dregs.common.utils.poi.ExcelUtil;
import com.dregs.framework.aspectj.lang.annotation.Log;
import com.dregs.framework.aspectj.lang.enums.BusinessType;
import com.dregs.framework.web.controller.BaseController;
import com.dregs.framework.web.domain.AjaxResult;
import com.dregs.framework.web.page.TableDataInfo;
import com.dregs.project.system.project.domain.SlagYardStatistics;
import com.dregs.project.system.project.domain.SlagYardStatisticsProject;
import com.dregs.project.system.project.domain.StaProject;
import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.project.service.ITProjectService;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.service.ISlagyardService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 渣场Controller
 * 
 * @author lc
 * @date 2020-11-06
 */
@Controller
@RequestMapping("/system/slagyard")
public class SlagyardController extends BaseController
{
    private String prefix = "system/slagyard";

    @Autowired
    private ISlagyardService slagyardService;


    @Autowired
    private ITProjectService tProjectService;

    @RequiresPermissions("system:slagyard:view")
    @GetMapping()
    public String slagyard()
    {
        return prefix + "/slagyard";
    }

    /**
     * 查询渣场列表
     */
    @RequiresPermissions("system:slagyard:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Slagyard slagyard)
    {
        startPage();
        List<Slagyard> list = slagyardService.selectSlagyardList(slagyard);
        return getDataTable(list);
    }

    /**
     * 导出渣场列表
     */
    @RequiresPermissions("system:slagyard:export")
    @Log(title = "渣场", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Slagyard slagyard)
    {
        List<Slagyard> list = slagyardService.selectSlagyardList(slagyard);
        ExcelUtil<Slagyard> util = new ExcelUtil<Slagyard>(Slagyard.class);
        return util.exportExcel(list, "slagyard");
    }

    /**
     * 新增渣场
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存渣场
     */
    @RequiresPermissions("system:slagyard:add")
    @Log(title = "渣场", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Slagyard slagyard)
    {
        return toAjax(slagyardService.insertSlagyard(slagyard));
    }

    /**
     * 修改渣场
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Slagyard slagyard = slagyardService.selectSlagyardById(id);
        mmap.put("slagyard", slagyard);
        return prefix + "/edit";
    }

    /**
     * 修改保存渣场
     */
    @RequiresPermissions("system:slagyard:edit")
    @Log(title = "渣场", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Slagyard slagyard)
    {
        return toAjax(slagyardService.updateSlagyard(slagyard));
    }

    /**
     * 删除渣场
     */
    @RequiresPermissions("system:slagyard:remove")
    @Log(title = "渣场", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(slagyardService.deleteSlagyardByIds(ids));
    }

    @GetMapping("/slagYardStatistics")
    public String slagYardStatistics(ModelMap mmap){
        List<Slagyard> list = slagyardService.selectSlagyardList(new Slagyard());
        mmap.put("slagyards",list);
        return "system/project/slagYardStatistics";
    }

    @PostMapping("/slagYardStatisticsList")
    @ResponseBody
    public TableDataInfo slagYardStatisticsList(SlagYardStatistics slagYardStatistics){
        startPage();
        List<SlagYardStatistics> list = slagyardService.slagYardStatistics(slagYardStatistics);
        TableDataInfo tableDataInfo = getDataTable(list);

        Slagyard _slagyard = new Slagyard();
        BeanUtils.copyProperties(slagYardStatistics,_slagyard);
        if (StringUtils.isNotEmpty(slagYardStatistics.getSlagyardId())){
            _slagyard.setId(Long.parseLong(slagYardStatistics.getSlagyardId()));
        }

        List<Slagyard> _list = slagyardService.selectSlagyardList(_slagyard);
        TableDataInfo _tableDataInfo = getDataTable(_list);
        tableDataInfo.setTotal(_tableDataInfo.getTotal());
        return tableDataInfo;
    }


    /**
     * 导出渣场列表
     */
    @Log(title = "渣场", businessType = BusinessType.EXPORT)
    @PostMapping("/slagYardStatisticsListexport")
    @ResponseBody
    public AjaxResult slagYardStatisticsListexport(SlagYardStatistics slagYardStatistics)
    {
        List<SlagYardStatistics> list = slagyardService.slagYardStatistics(slagYardStatistics);
        ExcelUtil<SlagYardStatistics> util = new ExcelUtil<SlagYardStatistics>(SlagYardStatistics.class);
        return util.exportExcel(list, "渣场统计");
    }

    @GetMapping("/slagYardProjectStatistics")
    public String slagYardProjectStatistics(ModelMap mmap,String slagyardId){
        List<TProject> list = tProjectService.selectTProjectList(new TProject());
        mmap.put("projects",list);
        mmap.put("slagyardId",slagyardId);
        return "system/project/slagYardProjectStatistics";
    }

    @Log(title = "渣场统计导出", businessType = BusinessType.EXPORT)
    @PostMapping("/slagYardProjectStatisticsList")
    @ResponseBody
    public TableDataInfo slagYardProjectStatisticsList(SlagYardStatisticsProject slagYardStatisticsProject){
        List<SlagYardStatisticsProject> list = slagyardService.slagYardStatisticsProject(slagYardStatisticsProject);
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }


    @Log(title = "渣场项目统计导出", businessType = BusinessType.EXPORT)
    @PostMapping("/slagYardProjectStatisticsListexport")
    @ResponseBody
    public AjaxResult slagYardProjectStatisticsListexport(SlagYardStatisticsProject slagYardStatisticsProject)
    {
        List<SlagYardStatisticsProject> list = slagyardService.slagYardStatisticsProject(slagYardStatisticsProject);
        ExcelUtil<SlagYardStatisticsProject> util = new ExcelUtil<SlagYardStatisticsProject>(SlagYardStatisticsProject.class);
        return util.exportExcel(list, "渣场项目统计");
    }
}
