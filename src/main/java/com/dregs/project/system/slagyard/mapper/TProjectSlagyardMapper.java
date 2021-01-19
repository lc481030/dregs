package com.dregs.project.system.slagyard.mapper;

import java.util.List;
import com.dregs.project.system.slagyard.domain.TProjectSlagyard;

/**
 * 项目渣场关系对应Mapper接口
 * 
 * @author dregs
 * @date 2021-01-19
 */
public interface TProjectSlagyardMapper 
{
    /**
     * 查询项目渣场关系对应
     * 
     * @param id 项目渣场关系对应ID
     * @return 项目渣场关系对应
     */
    public TProjectSlagyard selectTProjectSlagyardById(Long id);

    /**
     * 查询项目渣场关系对应列表
     * 
     * @param tProjectSlagyard 项目渣场关系对应
     * @return 项目渣场关系对应集合
     */
    public List<TProjectSlagyard> selectTProjectSlagyardList(TProjectSlagyard tProjectSlagyard);

    /**
     * 新增项目渣场关系对应
     * 
     * @param tProjectSlagyard 项目渣场关系对应
     * @return 结果
     */
    public int insertTProjectSlagyard(TProjectSlagyard tProjectSlagyard);

    /**
     * 修改项目渣场关系对应
     * 
     * @param tProjectSlagyard 项目渣场关系对应
     * @return 结果
     */
    public int updateTProjectSlagyard(TProjectSlagyard tProjectSlagyard);

    /**
     * 删除项目渣场关系对应
     * 
     * @param id 项目渣场关系对应ID
     * @return 结果
     */
    public int deleteTProjectSlagyardById(Long id);

    /**
     * 批量删除项目渣场关系对应
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTProjectSlagyardByIds(String[] ids);
}
