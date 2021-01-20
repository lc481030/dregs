package com.dregs.project.system.pay.service;

import java.util.List;
import com.dregs.project.system.pay.domain.TProjectPay;

/**
 * 项目收支Service接口
 * 
 * @author dregs
 * @date 2021-01-20
 */
public interface ITProjectPayService 
{
    /**
     * 查询项目收支
     * 
     * @param id 项目收支ID
     * @return 项目收支
     */
    public TProjectPay selectTProjectPayById(Long id);

    /**
     * 查询项目收支列表
     * 
     * @param tProjectPay 项目收支
     * @return 项目收支集合
     */
    public List<TProjectPay> selectTProjectPayList(TProjectPay tProjectPay);

    /**
     * 新增项目收支
     * 
     * @param tProjectPay 项目收支
     * @return 结果
     */
    public int insertTProjectPay(TProjectPay tProjectPay);

    /**
     * 修改项目收支
     * 
     * @param tProjectPay 项目收支
     * @return 结果
     */
    public int updateTProjectPay(TProjectPay tProjectPay);

    /**
     * 批量删除项目收支
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTProjectPayByIds(String ids);

    /**
     * 删除项目收支信息
     * 
     * @param id 项目收支ID
     * @return 结果
     */
    public int deleteTProjectPayById(Long id);
}
