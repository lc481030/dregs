package com.dregs.project.system.slagyard.service.impl;

import com.dregs.common.utils.DateUtils;
import com.dregs.common.utils.text.Convert;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.mapper.SlagyardMapper;
import com.dregs.project.system.slagyard.service.ISlagyardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 渣场Service业务层处理
 * 
 * @author lc
 * @date 2020-11-06
 */
@Service
public class SlagyardServiceImpl implements ISlagyardService 
{
    @Autowired
    private SlagyardMapper slagyardMapper;

    /**
     * 查询渣场
     * 
     * @param id 渣场ID
     * @return 渣场
     */
    @Override
    public Slagyard selectSlagyardById(Long id)
    {
        return slagyardMapper.selectSlagyardById(id);
    }

    /**
     * 查询渣场列表
     * 
     * @param slagyard 渣场
     * @return 渣场
     */
    @Override
    public List<Slagyard> selectSlagyardList(Slagyard slagyard)
    {
        return slagyardMapper.selectSlagyardList(slagyard);
    }

    /**
     * 新增渣场
     * 
     * @param slagyard 渣场
     * @return 结果
     */
    @Override
    public int insertSlagyard(Slagyard slagyard)
    {
        slagyard.setCreateTime(DateUtils.getNowDate());
        return slagyardMapper.insertSlagyard(slagyard);
    }

    /**
     * 修改渣场
     * 
     * @param slagyard 渣场
     * @return 结果
     */
    @Override
    public int updateSlagyard(Slagyard slagyard)
    {
        slagyard.setUpdateTime(DateUtils.getNowDate());
        return slagyardMapper.updateSlagyard(slagyard);
    }

    /**
     * 删除渣场对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSlagyardByIds(String ids)
    {
        return slagyardMapper.deleteSlagyardByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除渣场信息
     * 
     * @param id 渣场ID
     * @return 结果
     */
    @Override
    public int deleteSlagyardById(Long id)
    {
        return slagyardMapper.deleteSlagyardById(id);
    }
}
