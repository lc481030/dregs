package com.dregs.project.system.pay.service.impl;

import java.util.List;
import com.dregs.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dregs.project.system.pay.mapper.TProjectPayMapper;
import com.dregs.project.system.pay.domain.TProjectPay;
import com.dregs.project.system.pay.service.ITProjectPayService;
import com.dregs.common.utils.text.Convert;

/**
 * 项目收支Service业务层处理
 * 
 * @author dregs
 * @date 2021-01-20
 */
@Service
public class TProjectPayServiceImpl implements ITProjectPayService 
{
    @Autowired
    private TProjectPayMapper tProjectPayMapper;

    /**
     * 查询项目收支
     * 
     * @param id 项目收支ID
     * @return 项目收支
     */
    @Override
    public TProjectPay selectTProjectPayById(Long id)
    {
        return tProjectPayMapper.selectTProjectPayById(id);
    }

    /**
     * 查询项目收支列表
     * 
     * @param tProjectPay 项目收支
     * @return 项目收支
     */
    @Override
    public List<TProjectPay> selectTProjectPayList(TProjectPay tProjectPay)
    {
        return tProjectPayMapper.selectTProjectPayList(tProjectPay);
    }

    /**
     * 新增项目收支
     * 
     * @param tProjectPay 项目收支
     * @return 结果
     */
    @Override
    public int insertTProjectPay(TProjectPay tProjectPay)
    {
        tProjectPay.setCreateTime(DateUtils.getNowDate());
        return tProjectPayMapper.insertTProjectPay(tProjectPay);
    }

    /**
     * 修改项目收支
     * 
     * @param tProjectPay 项目收支
     * @return 结果
     */
    @Override
    public int updateTProjectPay(TProjectPay tProjectPay)
    {
        tProjectPay.setUpdateTime(DateUtils.getNowDate());
        return tProjectPayMapper.updateTProjectPay(tProjectPay);
    }

    /**
     * 删除项目收支对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTProjectPayByIds(String ids)
    {
        return tProjectPayMapper.deleteTProjectPayByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除项目收支信息
     * 
     * @param id 项目收支ID
     * @return 结果
     */
    @Override
    public int deleteTProjectPayById(Long id)
    {
        return tProjectPayMapper.deleteTProjectPayById(id);
    }
}
