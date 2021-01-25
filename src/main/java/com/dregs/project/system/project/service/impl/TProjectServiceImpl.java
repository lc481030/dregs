package com.dregs.project.system.project.service.impl;

import java.util.List;
import com.dregs.common.utils.DateUtils;
import com.dregs.project.system.project.domain.StaProject;
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
        return tProjectMapper.selectStaProjectList(staProject);
    }

    @Override
    public List<StaProject> selectStaCarlist(StaProject staProject) {
        return tProjectMapper.selectStaCarByProjectId(staProject);
    }
}
