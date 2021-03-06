package com.dregs.project.system.slagyard.mapper;

import com.dregs.project.system.slagyard.domain.Slagyard;

import java.util.List;

/**
 * 渣场Mapper接口
 * 
 * @author lc
 * @date 2020-11-06
 */
public interface SlagyardMapper 
{
    /**
     * 查询渣场
     * 
     * @param id 渣场ID
     * @return 渣场
     */
    public Slagyard selectSlagyardById(Long id);

    /**
     * 查询渣场列表
     * 
     * @param slagyard 渣场
     * @return 渣场集合
     */
    public List<Slagyard> selectSlagyardList(Slagyard slagyard);

    /**
     * 新增渣场
     * 
     * @param slagyard 渣场
     * @return 结果
     */
    public int insertSlagyard(Slagyard slagyard);

    /**
     * 修改渣场
     * 
     * @param slagyard 渣场
     * @return 结果
     */
    public int updateSlagyard(Slagyard slagyard);

    /**
     * 删除渣场
     * 
     * @param id 渣场ID
     * @return 结果
     */
    public int deleteSlagyardById(Long id);

    /**
     * 批量删除渣场
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSlagyardByIds(String[] ids);

    List<Slagyard> selectSlagyardByPayList();
}
