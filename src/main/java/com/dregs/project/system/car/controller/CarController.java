package com.dregs.project.system.car.controller;

import com.dregs.common.utils.poi.ExcelUtil;
import com.dregs.framework.aspectj.lang.annotation.Log;
import com.dregs.framework.aspectj.lang.enums.BusinessType;
import com.dregs.framework.web.controller.BaseController;
import com.dregs.framework.web.domain.AjaxResult;
import com.dregs.framework.web.page.TableDataInfo;
import com.dregs.project.system.car.domain.Car;
import com.dregs.project.system.car.service.ICarService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车辆信息Controller
 * 
 * @author lc
 * @date 2020-11-03
 */
@Controller
@RequestMapping("/system/car")
public class CarController extends BaseController
{
    private String prefix = "system/car";

    @Autowired
    private ICarService carService;

    @RequiresPermissions("system:car:view")
    @GetMapping()
    public String car()
    {
        return prefix + "/car";
    }

    /**
     * 查询车辆信息列表
     */
    @RequiresPermissions("system:car:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Car car)
    {
        startPage();
        List<Car> list = carService.selectCarList(car);
        return getDataTable(list);
    }

    /**
     * 导出车辆信息列表
     */
    @RequiresPermissions("system:car:export")
    @Log(title = "车辆信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Car car)
    {
        List<Car> list = carService.selectCarList(car);
        ExcelUtil<Car> util = new ExcelUtil<Car>(Car.class);
        return util.exportExcel(list, "car");
    }

    /**
     * 新增车辆信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存车辆信息
     */
    @RequiresPermissions("system:car:add")
    @Log(title = "车辆信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Car car)
    {
        return toAjax(carService.insertCar(car));
    }

    /**
     * 修改车辆信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Car car = carService.selectCarById(id);
        mmap.put("car", car);
        return prefix + "/edit";
    }

    /**
     * 修改保存车辆信息
     */
    @RequiresPermissions("system:car:edit")
    @Log(title = "车辆信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Car car)
    {
        return toAjax(carService.updateCar(car));
    }

    /**
     * 删除车辆信息
     */
    @RequiresPermissions("system:car:remove")
    @Log(title = "车辆信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(carService.deleteCarByIds(ids));
    }
}
