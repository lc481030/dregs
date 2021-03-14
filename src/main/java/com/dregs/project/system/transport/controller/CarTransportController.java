package com.dregs.project.system.transport.controller;

import com.dregs.common.utils.poi.ExcelUtil;
import com.dregs.framework.aspectj.lang.annotation.Log;
import com.dregs.framework.aspectj.lang.enums.BusinessType;
import com.dregs.framework.web.controller.BaseController;
import com.dregs.framework.web.domain.AjaxResult;
import com.dregs.framework.web.page.TableDataInfo;
import com.dregs.project.system.car.domain.Car;
import com.dregs.project.system.car.service.ICarService;
import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.project.service.ITProjectService;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.domain.TProjectSlagyard;
import com.dregs.project.system.slagyard.service.ISlagyardService;
import com.dregs.project.system.slagyard.service.ITProjectSlagyardService;
import com.dregs.project.system.transport.domain.CarTransport;
import com.dregs.project.system.transport.service.ICarTransportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
    private ISlagyardService slagyardService;

    @Autowired
    private ITProjectService projectService;

    @Autowired
    private ICarTransportService carTransportService;

    @Autowired
    private ITProjectSlagyardService tProjectSlagyardService;

    @Autowired
    private ICarService carService;


    @RequiresPermissions("system:transport:view")
    @GetMapping()
    public String transport(ModelMap mmap)
    {
        List<TProject> projects = projectService.selectTProjectList(new TProject());
        List<Car> cars = carService.selectCarList(new Car());
        List<Slagyard> slagyards = slagyardService.selectSlagyardList(new Slagyard());
        mmap.put("projects",projects);
        mmap.put("cars",cars);
        mmap.put("slagyards",slagyards);
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
    @GetMapping("/addfang")
    public String addFang(ModelMap mmap)
    {
        TProjectSlagyard projectSlagyard = new TProjectSlagyard();
        projectSlagyard.setProjectType("2");
        List<TProjectSlagyard> list = tProjectSlagyardService.selectTProjectSlagyardList(projectSlagyard);
        for (TProjectSlagyard ts:list){
            TProject project = projectService.selectTProjectById(Long.parseLong(ts.getProjectId()));
            if (project!=null){
                ts.setProjectName(project.getName());
            }
            Slagyard s = slagyardService.selectSlagyardById(ts.getSlagyardId());
            if(s!=null){
                ts.setSlagyardName(s.getTitle());
            }

        }

        Car car = new Car();
        List<Car> cars = carService.selectCarList(car);
        Slagyard slagyard = new Slagyard();
        List<Slagyard> slagyards = slagyardService.selectSlagyardList(slagyard);
        mmap.put("cars",cars);
        mmap.put("slagyards",slagyards);
        mmap.put("list",list);
        return prefix + "/addfang";
    }


    /**
     * 新增车运
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        TProjectSlagyard projectSlagyard = new TProjectSlagyard();
        List<TProjectSlagyard> list = tProjectSlagyardService.selectTProjectSlagyardList(projectSlagyard);
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

        Car car = new Car();
        List<Car> cars = carService.selectCarList(car);
        Slagyard slagyard = new Slagyard();
        List<Slagyard> slagyards = slagyardService.selectSlagyardList(slagyard);
        mmap.put("cars",cars);
        mmap.put("slagyards",slagyards);
        mmap.put("list",list);
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
        TProjectSlagyard projectSlagyard = new TProjectSlagyard();
        if (carTransport.getTransportType().toString().equals("3")){
            projectSlagyard.setProjectType("2");
        }else{
            projectSlagyard.setProjectType("1");
        }
        List<TProjectSlagyard> list = tProjectSlagyardService.selectTProjectSlagyardList(projectSlagyard);
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

        Car car = new Car();
        List<Car> cars = carService.selectCarList(car);
        Slagyard slagyard = new Slagyard();
        List<Slagyard> slagyards = slagyardService.selectSlagyardList(slagyard);
        mmap.put("cars",cars);
        mmap.put("slagyards",slagyards);
        mmap.put("list",list);
        if(!carTransport.getTransportType().toString().equals("3")){
            return prefix + "/edit";
        }else{
            return prefix + "/editfang";
        }
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
