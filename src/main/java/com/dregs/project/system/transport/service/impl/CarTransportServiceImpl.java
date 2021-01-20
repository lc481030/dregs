package com.dregs.project.system.transport.service.impl;

import java.util.List;
import com.dregs.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dregs.project.system.transport.mapper.CarTransportMapper;
import com.dregs.project.system.transport.domain.CarTransport;
import com.dregs.project.system.transport.service.ICarTransportService;
import com.dregs.common.utils.text.Convert;

/**
 * 车运Service业务层处理
 * 
 * @author dregs
 * @date 2021-01-20
 */
@Service
public class CarTransportServiceImpl implements ICarTransportService 
{
    @Autowired
    private CarTransportMapper carTransportMapper;

    /**
     * 查询车运
     * 
     * @param id 车运ID
     * @return 车运
     */
    @Override
    public CarTransport selectCarTransportById(Long id)
    {
        return carTransportMapper.selectCarTransportById(id);
    }

    /**
     * 查询车运列表
     * 
     * @param carTransport 车运
     * @return 车运
     */
    @Override
    public List<CarTransport> selectCarTransportList(CarTransport carTransport)
    {
        return carTransportMapper.selectCarTransportList(carTransport);
    }

    /**
     * 新增车运
     * 
     * @param carTransport 车运
     * @return 结果
     */
    @Override
    public int insertCarTransport(CarTransport carTransport)
    {
        carTransport.setCreateTime(DateUtils.getNowDate());
        return carTransportMapper.insertCarTransport(carTransport);
    }

    /**
     * 修改车运
     * 
     * @param carTransport 车运
     * @return 结果
     */
    @Override
    public int updateCarTransport(CarTransport carTransport)
    {
        carTransport.setUpdateTime(DateUtils.getNowDate());
        return carTransportMapper.updateCarTransport(carTransport);
    }

    /**
     * 删除车运对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCarTransportByIds(String ids)
    {
        return carTransportMapper.deleteCarTransportByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除车运信息
     * 
     * @param id 车运ID
     * @return 结果
     */
    @Override
    public int deleteCarTransportById(Long id)
    {
        return carTransportMapper.deleteCarTransportById(id);
    }
}
