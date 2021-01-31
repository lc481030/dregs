package com.dregs.project.system.project.service;

import java.util.List;

import com.dregs.project.system.project.domain.StaCarProject;
import com.dregs.project.system.project.domain.StaProject;
import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.sta.controller.domain.CarLogMoney;
import com.dregs.project.system.sta.controller.domain.StaCarDetailMoney;

/**
 * 项目管理Service接口
 * 
 * @author dregs
 * @date 2021-01-19
 */
public interface ITProjectService 
{
    /**
     * 查询项目管理
     * 
     * @param id 项目管理ID
     * @return 项目管理
     */
    public TProject selectTProjectById(Long id);

    /**
     * 查询项目管理列表
     * 
     * @param tProject 项目管理
     * @return 项目管理集合
     */
    public List<TProject> selectTProjectList(TProject tProject);

    /**
     * 新增项目管理
     * 
     * @param tProject 项目管理
     * @return 结果
     */
    public int insertTProject(TProject tProject);

    /**
     * 修改项目管理
     * 
     * @param tProject 项目管理
     * @return 结果
     */
    public int updateTProject(TProject tProject);

    /**
     * 批量删除项目管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTProjectByIds(String ids);

    /**
     * 删除项目管理信息
     * 
     * @param id 项目管理ID
     * @return 结果
     */
    public int deleteTProjectById(Long id);

    List<StaProject> selectStaProjectList(StaProject staProject);

    List<StaCarProject> selectStaCarlist(StaProject staProject);


    List<TProject> selectProjectByPayList();

    List<TProject> selectProjectObjByPayList();

    List<StaCarProject> selectStaSlalist(StaProject staProject);

    List<CarLogMoney> getCarMoneyList(CarLogMoney carLogMoney);

    List<StaCarDetailMoney> getCarDetailList(CarLogMoney carLogMoney);
}
