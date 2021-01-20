package com.dregs.project.system.transport.service;

import java.util.List;
import com.dregs.project.system.transport.domain.CarTransport;

/**
 * 车运Service接口
 * 
 * @author dregs
 * @date 2021-01-20
 */
public interface ICarTransportService 
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
     * 批量删除车运
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCarTransportByIds(String ids);

    /**
     * 删除车运信息
     * 
     * @param id 车运ID
     * @return 结果
     */
    public int deleteCarTransportById(Long id);
}
