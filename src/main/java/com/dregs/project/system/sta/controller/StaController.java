package com.dregs.project.system.sta.controller;

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
import com.dregs.project.system.sta.controller.domain.CarLogMoney;
import com.dregs.project.system.sta.controller.domain.StaCarDetailMoney;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 项目管理Controller
 * 
 * @author dregs
 * @date 2021-01-19
 */
@Controller
@RequestMapping("/system/sta")
public class StaController extends BaseController
{
    private String prefix = "system/sta";

    @Autowired
    private ITProjectService tProjectService;

    @Autowired
    private ICarService carService;

    @Autowired
    private ISlagyardService selectCarList;

    @GetMapping("/car")
    public String staSlaCatPage(ModelMap mmap)
    {

        List<Car> carList = carService.selectCarList(new Car());
        mmap.put("cars",carList);
        return prefix + "/staCar";
    }

    /**
     * 查询项目管理统计
     */
    @PostMapping("/carList")
    @ResponseBody
    public TableDataInfo carList(CarLogMoney carLogMoney)
    {
        //统计车辆总的该收多少钱，和付多少钱
        List<CarLogMoney> list = tProjectService.getCarMoneyList(carLogMoney);
        return getDataTable(list);
    }

    @GetMapping("/carDetail")
    public String staSlaProject(ModelMap mmap,String carId)
    {

        List<TProject> projects = tProjectService.selectTProjectList(new TProject());
        mmap.put("projects",projects);
        mmap.put("carId",carId);
        return prefix + "/staCarDetail";
    }

    /**
     * 查询项目管理统计
     */
    @PostMapping("/carDetailList")
    @ResponseBody
    public TableDataInfo carDetailList(CarLogMoney carLogMoney)
    {
        //统计车辆总的该收多少钱，和付多少钱
        List<StaCarDetailMoney> list = tProjectService.getCarDetailList(carLogMoney);
        return getDataTable(list);
    }

}
