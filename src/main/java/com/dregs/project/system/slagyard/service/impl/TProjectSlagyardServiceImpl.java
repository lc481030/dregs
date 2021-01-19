package com.dregs.project.system.slagyard.service.impl;

import java.util.List;
import com.dregs.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dregs.project.system.slagyard.mapper.TProjectSlagyardMapper;
import com.dregs.project.system.slagyard.domain.TProjectSlagyard;
import com.dregs.project.system.slagyard.service.ITProjectSlagyardService;
import com.dregs.common.utils.text.Convert;

/**
 * 项目渣场关系对应Service业务层处理
 * 
 * @author dregs
 * @date 2021-01-19
 */
@Service
public class TProjectSlagyardServiceImpl implements ITProjectSlagyardService 
{
    @Autowired
    private TProjectSlagyardMapper tProjectSlagyardMapper;

    /**
     * 查询项目渣场关系对应
     * 
     * @param id 项目渣场关系对应ID
     * @return 项目渣场关系对应
     */
    @Override
    public TProjectSlagyard selectTProjectSlagyardById(Long id)
    {
        return tProjectSlagyardMapper.selectTProjectSlagyardById(id);
    }

    /**
     * 查询项目渣场关系对应列表
     * 
     * @param tProjectSlagyard 项目渣场关系对应
     * @return 项目渣场关系对应
     */
    @Override
    public List<TProjectSlagyard> selectTProjectSlagyardList(TProjectSlagyard tProjectSlagyard)
    {
        return tProjectSlagyardMapper.selectTProjectSlagyardList(tProjectSlagyard);
    }

    /**
     * 新增项目渣场关系对应
     * 
     * @param tProjectSlagyard 项目渣场关系对应
     * @return 结果
     */
    @Override
    public int insertTProjectSlagyard(TProjectSlagyard tProjectSlagyard)
    {
        tProjectSlagyard.setCreateTime(DateUtils.getNowDate());
        return tProjectSlagyardMapper.insertTProjectSlagyard(tProjectSlagyard);
    }

    /**
     * 修改项目渣场关系对应
     * 
     * @param tProjectSlagyard 项目渣场关系对应
     * @return 结果
     */
    @Override
    public int updateTProjectSlagyard(TProjectSlagyard tProjectSlagyard)
    {
        tProjectSlagyard.setUpdateTime(DateUtils.getNowDate());
        return tProjectSlagyardMapper.updateTProjectSlagyard(tProjectSlagyard);
    }

    /**
     * 删除项目渣场关系对应对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTProjectSlagyardByIds(String ids)
    {
        return tProjectSlagyardMapper.deleteTProjectSlagyardByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除项目渣场关系对应信息
     * 
     * @param id 项目渣场关系对应ID
     * @return 结果
     */
    @Override
    public int deleteTProjectSlagyardById(Long id)
    {
        return tProjectSlagyardMapper.deleteTProjectSlagyardById(id);
    }
}
