package com.dregs.project.system.slagyard.controller;

import com.dregs.common.utils.poi.ExcelUtil;
import com.dregs.framework.aspectj.lang.annotation.Log;
import com.dregs.framework.aspectj.lang.enums.BusinessType;
import com.dregs.framework.web.controller.BaseController;
import com.dregs.framework.web.domain.AjaxResult;
import com.dregs.framework.web.page.TableDataInfo;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.service.ISlagyardService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
}
