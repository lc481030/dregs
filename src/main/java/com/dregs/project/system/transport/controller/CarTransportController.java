package com.dregs.project.system.transport.controller;

import com.dregs.common.utils.poi.ExcelUtil;
import com.dregs.framework.aspectj.lang.annotation.Log;
import com.dregs.framework.aspectj.lang.enums.BusinessType;
import com.dregs.framework.web.controller.BaseController;
import com.dregs.framework.web.domain.AjaxResult;
import com.dregs.framework.web.page.TableDataInfo;
import com.dregs.project.system.transport.domain.CarTransport;
import com.dregs.project.system.transport.service.ICarTransportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车运Controller
 * 
 * @author dregs
 * @date 2021-01-20
 */
@Controller
@RequestMapping("/system/transport")
public class CarTransportController extends BaseController
{
    private String prefix = "system/transport";

    @Autowired
    private ICarTransportService carTransportService;

    @RequiresPermissions("system:transport:view")
    @GetMapping()
    public String transport()
    {
        return prefix + "/transport";
    }

    /**
     * 查询车运列表
     */
    @RequiresPermissions("system:transport:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CarTransport carTransport)
    {
        startPage();
        List<CarTransport> list = carTransportService.selectCarTransportList(carTransport);
        return getDataTable(list);
    }

    /**
     * 导出车运列表
     */
    @RequiresPermissions("system:transport:export")
    @Log(title = "车运", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CarTransport carTransport)
    {
        List<CarTransport> list = carTransportService.selectCarTransportList(carTransport);
        ExcelUtil<CarTransport> util = new ExcelUtil<CarTransport>(CarTransport.class);
        return util.exportExcel(list, "transport");
    }

    /**
     * 新增车运
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存车运
     */
    @RequiresPermissions("system:transport:add")
    @Log(title = "车运", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CarTransport carTransport)
    {
        return toAjax(carTransportService.insertCarTransport(carTransport));
    }

    /**
     * 修改车运
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        CarTransport carTransport = carTransportService.selectCarTransportById(id);
        mmap.put("carTransport", carTransport);
        return prefix + "/edit";
    }

    /**
     * 修改保存车运
     */
    @RequiresPermissions("system:transport:edit")
    @Log(title = "车运", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CarTransport carTransport)
    {
        return toAjax(carTransportService.updateCarTransport(carTransport));
    }

    /**
     * 删除车运
     */
    @RequiresPermissions("system:transport:remove")
    @Log(title = "车运", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(carTransportService.deleteCarTransportByIds(ids));
    }
}
