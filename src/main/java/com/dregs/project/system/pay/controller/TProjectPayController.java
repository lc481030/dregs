package com.dregs.project.system.pay.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dregs.common.utils.StringUtils;
import com.dregs.project.system.car.domain.Car;
import com.dregs.project.system.car.service.ICarService;
import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.project.service.ITProjectService;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.service.ISlagyardService;
import com.dregs.project.system.transport.domain.CarTransport;
import com.dregs.project.system.transport.service.ICarTransportService;
import com.dregs.project.system.user.domain.User;
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
import com.dregs.project.system.pay.domain.TProjectPay;
import com.dregs.project.system.pay.service.ITProjectPayService;
import com.dregs.framework.web.controller.BaseController;
import com.dregs.framework.web.domain.AjaxResult;
import com.dregs.common.utils.poi.ExcelUtil;
import com.dregs.framework.web.page.TableDataInfo;

/**
 * 项目收支Controller
 * 
 * @author dregs
 * @date 2021-01-20
 */
@Controller
@RequestMapping("/system/pay")
public class TProjectPayController extends BaseController {
    private String prefix = "system/pay";

    @Autowired
    private ITProjectPayService tProjectPayService;

    @Autowired
    private ITProjectService projectService;

    @Autowired
    private ISlagyardService slagyardService;

    @Autowired
    private ICarService carService;

    @RequiresPermissions("system:pay:view")
    @GetMapping()
    public String pay(ModelMap mmap) {
        List<Slagyard> list =  slagyardService.selectSlagyardList(new Slagyard());
        mmap.put("slagyardList",list);

        List<TProject> listProject =  projectService.selectTProjectList(new TProject());
        mmap.put("projectList",listProject);

        List<Car> carList =  carService.selectCarList(new Car());
        mmap.put("carList",carList);

        TProject tProject = new TProject();
        tProject.setId(0L);
        tProject.setName("甲方付款");
        listProject.add(tProject);
        mmap.put("payProjectList",listProject);

        return prefix + "/pay";
    }

    /**
     * 查询项目收支列表
     */
    @RequiresPermissions("system:pay:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TProjectPay tProjectPay) {
        startPage();
        List<TProjectPay> list = tProjectPayService.selectTProjectPayList(tProjectPay);
        for (TProjectPay tp : list) {
            // [{"id":"","text":"所有"},{"id":"1","text":"车运"},{"id":"2","text":"渣费"},{"id":"3","text":"子项目"},{"id":"4","text":"其他"}]
            if (tp.getPayType() == null) {
                tp.setPayTypeName("所有");
            } else if (tp.getPayType() == 1) {
                tp.setPayTypeName("车运");
                Car car = carService.selectCarById(tp.getRelationId());
                if (car != null) {
                    tp.setRelationName(car.getCarNum());
                }
            } else if (tp.getPayType() == 2) {
                tp.setPayTypeName("渣费");
                Slagyard slagyard = slagyardService.selectSlagyardById(tp.getRelationId());
                if (slagyard != null) {
                    tp.setRelationName(slagyard.getTitle());
                }
            } else if (tp.getPayType() == 3) {
                tp.setPayTypeName("子项目");
                TProject tProject = projectService.selectTProjectById(tp.getRelationId());
                if (tProject != null) {
                    tp.setRelationName(tProject.getName());
                }
            } else if (tp.getPayType() == 4) {
                tp.setPayTypeName("其他");
            }
            if (tp.getType() != null && tp.getType().equals("1")) {
                tp.setTypeName("实收");
            } else if (tp.getType() != null && tp.getType().equals("2")) {
                tp.setTypeName("实付");
            }
            String payObjName;
            if (tp.getPayObjId()==null || tp.getPayObjId()==0L){
                payObjName = "甲方付款";
            }else{
                TProject t = projectService.selectTProjectById(tp.getPayObjId());
                payObjName = t.getName();
            }

            tp.setPayObjName(payObjName);
        }
        return getDataTable(list);
    }

    /**
     * 导出项目收支列表
     */
    @RequiresPermissions("system:pay:export")
    @Log(title = "项目收支", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TProjectPay tProjectPay) {
        List<TProjectPay> list = tProjectPayService.selectTProjectPayList(tProjectPay);
        for (TProjectPay tp : list) {
            // [{"id":"","text":"所有"},{"id":"1","text":"车运"},{"id":"2","text":"渣费"},{"id":"3","text":"子项目"},{"id":"4","text":"其他"}]
            if (tp.getPayType() == null) {
                tp.setPayTypeName("所有");
            } else if (tp.getPayType() == 1) {
                tp.setPayTypeName("车运");
                Car car = carService.selectCarById(tp.getRelationId());
                if (car != null) {
                    tp.setRelationName(car.getCarNum());
                }

            } else if (tp.getPayType() == 2) {
                tp.setPayTypeName("渣费");
                Slagyard slagyard = slagyardService.selectSlagyardById(tp.getRelationId());
                if (slagyard != null) {
                    tp.setRelationName(slagyard.getTitle());
                }
            } else if (tp.getPayType() == 3) {
                tp.setPayTypeName("子项目");
                TProject tProject = projectService.selectTProjectById(tp.getRelationId());
                if (tProject != null) {
                    tp.setRelationName(tProject.getName());
                }
            } else if (tp.getPayType() == 4) {
                tp.setPayTypeName("其他");
            }
            if (tp.getType() != null && tp.getType().equals("1")) {
                tp.setTypeName("实收");
            } else if (tp.getType() != null && tp.getType().equals("2")) {
                tp.setTypeName("实付");
            }
            String payObjName;
            if (tp.getPayObjId()==null || tp.getPayObjId()==0L){
                payObjName = "甲方付款";
            }else{
                TProject t = projectService.selectTProjectById(tp.getPayObjId());
                payObjName = t.getName();
            }

            tp.setPayObjName(payObjName);
        }
        ExcelUtil<TProjectPay> util = new ExcelUtil<TProjectPay>(TProjectPay.class);
        return util.exportExcel(list, "pay");
    }

    /**
     * 新增项目收支
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        List<TProject> listProject =  projectService.selectTProjectList(new TProject());
        TProject tProject = new TProject();
        tProject.setId(0L);
        tProject.setName("甲方付款");
        listProject.add(0,tProject);
        mmap.put("payProjectList",listProject);
        return prefix + "/add";
    }

    /**
     * 新增保存项目收支
     */
    @RequiresPermissions("system:pay:add")
    @Log(title = "项目收支", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TProjectPay tProjectPay) {
        User user = getSysUser();

        tProjectPay.setAddUserId(user.getUserId());
        tProjectPay.setAddName(user.getUserName());

        return toAjax(tProjectPayService.insertTProjectPay(tProjectPay));
    }

    /**
     * 修改项目收支
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        TProjectPay tProjectPay = tProjectPayService.selectTProjectPayById(id);
        mmap.put("tProjectPay", tProjectPay);

        List<TProject> listProject =  projectService.selectTProjectList(new TProject());
        TProject tProject = new TProject();
        tProject.setId(0L);
        tProject.setName("甲方付款");
        listProject.add(0,tProject);
        mmap.put("payProjectList",listProject);

        return prefix + "/edit";
    }

    /**
     * 修改保存项目收支
     */
    @RequiresPermissions("system:pay:edit")
    @Log(title = "项目收支", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TProjectPay tProjectPay) {
        User user = getSysUser();
        tProjectPay.setUpdUserId(user.getUserId());
        tProjectPay.setUdpName(user.getUserName());
        return toAjax(tProjectPayService.updateTProjectPay(tProjectPay));
    }

    /**
     * 删除项目收支
     */
    @RequiresPermissions("system:pay:remove")
    @Log(title = "项目收支", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(tProjectPayService.deleteTProjectPayByIds(ids));
    }

    /**
     * 查询下拉框
     */
    @PostMapping("/selectItem")
    @ResponseBody
    public List<Map<String, Object>> selectItem(String id, String rid) {
        // [{"id":"","text":"所有"},{"id":"1","text":"车运"},{"id":"2","text":"渣费"},{"id":"3","text":"子项目"},{"id":"4","text":"其他"}]

        List<Map<String, Object>> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(id) && id.equals("1")) {
           List<Car> carList = carService.selectCarList(new Car());
            for (Car tp : carList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", tp.getId().toString());
                if (StringUtils.isNotEmpty(rid) && rid.equals(tp.getId().toString())) {
                    map.put("selected", true);
                }
                map.put("text", tp.getCarNum());
                list.add(map);
            }
        } else if (StringUtils.isNotEmpty(id) && id.equals("2")) {

            List<Slagyard> slagyardList = slagyardService.selectSlagyardList(new Slagyard());
            for (Slagyard tp : slagyardList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", tp.getId().toString());
                if (StringUtils.isNotEmpty(rid) && rid.equals(tp.getId().toString())) {
                    map.put("selected", true);
                }
                map.put("text", tp.getTitle());
                list.add(map);
            }

        } else if (StringUtils.isNotEmpty(id) && id.equals("3")) {
            List<TProject> projectList = projectService.selectTProjectList(new TProject());
            for (TProject tp : projectList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", tp.getId().toString());
                if (StringUtils.isNotEmpty(rid) && rid.equals(tp.getId().toString())) {
                    map.put("selected", true);
                }
                map.put("text", tp.getName());
                list.add(map);
            }

        } else if (StringUtils.isNotEmpty(id) && id.equals("4")) {

        }

        return list;
    }
}
