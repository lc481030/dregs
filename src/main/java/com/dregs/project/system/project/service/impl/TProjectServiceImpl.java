package com.dregs.project.system.project.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.dregs.common.utils.DateUtils;
import com.dregs.project.system.project.domain.StaCarProject;
import com.dregs.project.system.project.domain.StaProject;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.mapper.SlagyardMapper;
import com.dregs.project.system.sta.controller.domain.CarLogMoney;
import com.dregs.project.system.sta.controller.domain.StaCarDetailMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dregs.project.system.project.mapper.TProjectMapper;
import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.project.service.ITProjectService;
import com.dregs.common.utils.text.Convert;

/**
 * 项目管理Service业务层处理
 * 
 * @author dregs
 * @date 2021-01-19
 */
@Service
public class TProjectServiceImpl implements ITProjectService 
{
    @Autowired
    private TProjectMapper tProjectMapper;

    @Autowired
    private SlagyardMapper slagyardMapper;


    /**
     * 查询项目管理
     * 
     * @param id 项目管理ID
     * @return 项目管理
     */
    @Override
    public TProject selectTProjectById(Long id)
    {
        return tProjectMapper.selectTProjectById(id);
    }

    /**
     * 查询项目管理列表
     * 
     * @param tProject 项目管理
     * @return 项目管理
     */
    @Override
    public List<TProject> selectTProjectList(TProject tProject)
    {
        return tProjectMapper.selectTProjectList(tProject);
    }

    /**
     * 新增项目管理
     * 
     * @param tProject 项目管理
     * @return 结果
     */
    @Override
    public int insertTProject(TProject tProject)
    {
        tProject.setCreateTime(DateUtils.getNowDate());
        return tProjectMapper.insertTProject(tProject);
    }

    /**
     * 修改项目管理
     * 
     * @param tProject 项目管理
     * @return 结果
     */
    @Override
    public int updateTProject(TProject tProject)
    {
        tProject.setUpdateTime(DateUtils.getNowDate());
        return tProjectMapper.updateTProject(tProject);
    }

    /**
     * 删除项目管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTProjectByIds(String ids)
    {
        return tProjectMapper.deleteTProjectByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除项目管理信息
     * 
     * @param id 项目管理ID
     * @return 结果
     */
    @Override
    public int deleteTProjectById(Long id)
    {
        return tProjectMapper.deleteTProjectById(id);
    }

    @Override
    public List<StaProject> selectStaProjectList(StaProject staProject) {
        List<StaProject> list = tProjectMapper.selectStaProjectList(staProject);
        return list;
    }

    @Override
    public List<StaCarProject> selectStaCarlist(StaProject staProject) {


        TProject project = tProjectMapper.selectTProjectById(Long.parseLong(staProject.getProjectId()));
        List<StaCarProject> list = tProjectMapper.selectStaCarByProjectId(staProject);
        List<StaCarProject> list1 = tProjectMapper.selectPayCarListByProjectId(staProject);
        /*有记录增加付款金额*/
        list.forEach(staCarProject -> {
            Optional<StaCarProject> _staCarProject = list1.stream().filter(item->item.getCarId().toString().equals(staCarProject.getCarId().toString())).findFirst();
            if (_staCarProject.isPresent()){
                StaCarProject a = _staCarProject.get();
                staCarProject.setPayMoney(a.getPayMoney());
                list1.remove(a);
            }
        });
        /*已付款未运输增加付款记录*/
        if (null != list1 && list1.size()>0){
            list1.forEach(staCarProject -> {
                Optional<StaCarProject> _staCarProject = list.stream().filter(item->item.getCarId().toString().equals(staCarProject.getCarId().toString())).findFirst();
                if (!_staCarProject.isPresent()){
                    staCarProject.setProjectName(project.getName());
                    list.add(staCarProject);
                }
            });
        }


        return list;
    }

    @Override
    public List<TProject> selectProjectByPayList() {
        return tProjectMapper.selectProjectByPayList();
    }

    @Override
    public List<TProject> selectProjectObjByPayList() {
        return tProjectMapper.selectProjectObjByPayList();
    }

    @Override
    public List<StaCarProject> selectStaSlalist(StaProject staProject) {
        TProject project = tProjectMapper.selectTProjectById(Long.parseLong(staProject.getProjectId()));
        List<StaCarProject> list = tProjectMapper.selectStaSlaByProjectId(staProject);
        List<StaCarProject> list1 = tProjectMapper.selectPaySlaListByProjectId(staProject);
        list.forEach(staCarProject -> {
            Optional<StaCarProject> _staCarProject = list1.stream().filter(item->item.getSlagyardId().toString().equals(staCarProject.getSlagyardId().toString())).findFirst();
            if (_staCarProject.isPresent()){
                StaCarProject a = _staCarProject.get();
                staCarProject.setPayMoney(a.getPayMoney());
                list1.remove(a);
            }
        });
        /*已付款未运输增加付款记录*/
        if(null != list1 && list1.size()>0){
            list1.forEach(staCarProject -> {
                Optional<StaCarProject> _staCarProject = list.stream().filter(item->item.getSlagyardId().toString().equals(staCarProject.getSlagyardId().toString())).findFirst();
                if (!_staCarProject.isPresent()){
                    staCarProject.setProjectName(project.getName());
                    list.add(staCarProject);
                }
            });
        }

        return list;
    }

    @Override
    public List<CarLogMoney> getCarMoneyList(CarLogMoney carLogMoneyp) {
        /*查询所有车辆车运应付总金额*/
        List<CarLogMoney> list = tProjectMapper.getAllCarLogMoney(carLogMoneyp);

        /*查询支付明细记录*/
        List<CarLogMoney> paylist = tProjectMapper.getAllCarPayMoney(carLogMoneyp);

        list.forEach(carLogMoney -> {
            Optional<CarLogMoney> _carLogMoney = paylist.stream().filter(item->item.getCarId().equals(carLogMoney.getCarId())).findFirst();
            if (_carLogMoney.isPresent()){
                CarLogMoney a = _carLogMoney.get();
                carLogMoney.setPayMoney(a.getPayMoney());
                BigDecimal totalMoney = new BigDecimal(carLogMoney.getTotalMoney());
                BigDecimal payMoney = new BigDecimal(a.getPayMoney());
                carLogMoney.setRemainMoney(totalMoney.subtract(payMoney).toString());
                paylist.remove(a);
            }else{
                carLogMoney.setPayMoney("0");
                carLogMoney.setRemainMoney(carLogMoney.getTotalMoney());
            }
        });
        /*已付款未运输增加付款记录*/
        if(null != paylist && paylist.size()>0){
            paylist.forEach(carLogMoney -> {
                carLogMoney.setRemainMoney("-"+carLogMoney.getPayMoney());
                list.add(carLogMoney);
            });
        }
        return list;
    }

    @Override
    public List<StaCarDetailMoney> getCarDetailList(CarLogMoney carLogMoney) {
        List<TProject> projects = tProjectMapper.selectTProjectList(new TProject());
        List<Slagyard> slagyards =  slagyardMapper.selectSlagyardList(new Slagyard());
        List<StaCarDetailMoney> list = tProjectMapper.getCarDetailList(carLogMoney);
        List<StaCarDetailMoney> paylist = tProjectMapper.getCarPayDetailList(carLogMoney);

        list.forEach(staCarDetailMoney -> {

            Optional<TProject> pname = projects.stream().filter(item->item.getId().toString().equals(staCarDetailMoney.getProjectId())).findFirst();
            if (pname.isPresent()){
                staCarDetailMoney.setProjectName(pname.get().getName());
            }

            Optional<Slagyard> sname = slagyards.stream().filter(item->item.getId().toString().equals(staCarDetailMoney.getSlagyardId())).findFirst();
            if (pname.isPresent()){
                staCarDetailMoney.setSlagyardName(sname.get().getTitle());
            }

            Optional<StaCarDetailMoney> _staCarDetailMoney = paylist.stream().filter(
                    item->item.getCarId().equals(staCarDetailMoney.getCarId())
                    && item.getProjectId().equals(staCarDetailMoney.getProjectId())
            ).findFirst();
            if (_staCarDetailMoney.isPresent()){
                StaCarDetailMoney _a =_staCarDetailMoney.get();
                staCarDetailMoney.setPayMoney(_a.getPayMoney());
                paylist.remove(_a);
            }else{
                carLogMoney.setPayMoney("0");
                carLogMoney.setRemainMoney(carLogMoney.getTotalMoney());
            }
        });

        /*已付款未运输增加付款记录*/
        if(null != paylist && paylist.size()>0){
            paylist.forEach(staCarDetailMoney -> {

                Optional<TProject> pname = projects.stream().filter(item->item.getId().toString().equals(staCarDetailMoney.getProjectId())).findFirst();
                if (pname.isPresent()){
                    staCarDetailMoney.setProjectName(pname.get().getName());
                }

                staCarDetailMoney.setRemainMoney("-"+carLogMoney.getPayMoney());
                list.add(staCarDetailMoney);
            });
        }
        return list;
    }
}
