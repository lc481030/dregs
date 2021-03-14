package com.dregs.project.system.transport.mapper;

import java.util.List;
import com.dregs.project.system.transport.domain.CarTransport;

/**
 * 车运Mapper接口
 * 
 * @author dregs
 * @date 2021-01-20
 */
public interface CarTransportMapper 
{
    /**
     * 查询车运
     * 
     * @param id 车运ID
     * @return 车运
     */
    public CarTransport selectCarTransportById(Long id);

    /**
     * 查询车运列表
     * 
     * @param carTransport 车运
     * @return 车运集合
     */
    public List<CarTransport> selectCarTransportList(CarTransport carTransport);

    /**
     * 新增车运
     * 
     * @param carTransport 车运
     * @return 结果
     */
    public int insertCarTransport(CarTransport carTransport);

    /**
     * 修改车运
     * 
     * @param carTransport 车运
     * @return 结果
     */
    public int updateCarTransport(CarTransport carTransport);

    /**
     * 删除车运
     * 
     * @param id 车运ID
     * @return 结果
     */
    public int deleteCarTransportById(Long id);

    /**
     * 批量删除车运
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarTransportByIds(String[] ids);

    Integer getTransportNumByProjectSlagyardId(CarTransport carTransport);
}
