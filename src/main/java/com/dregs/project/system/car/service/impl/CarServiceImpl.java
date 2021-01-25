package com.dregs.project.system.car.service.impl;

import com.dregs.common.utils.DateUtils;
import com.dregs.common.utils.text.Convert;
import com.dregs.project.system.car.domain.Car;
import com.dregs.project.system.car.mapper.CarMapper;
import com.dregs.project.system.car.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 车辆信息Service业务层处理
 * 
 * @author lc
 * @date 2020-11-03
 */
@Service
public class CarServiceImpl implements ICarService 
{
    @Autowired
    private CarMapper carMapper;

    /**
     * 查询车辆信息
     * 
     * @param id 车辆信息ID
     * @return 车辆信息
     */
    @Override
    public Car selectCarById(Long id)
    {
        return carMapper.selectCarById(id);
    }

    /**
     * 查询车辆信息列表
     * 
     * @param car 车辆信息
     * @return 车辆信息
     */
    @Override
    public List<Car> selectCarList(Car car)
    {
        return carMapper.selectCarList(car);
    }

    @Override
    public List<Car> selectCarByPayList() {
        return carMapper.selectCarByPayList();
    }

    /**
     * 新增车辆信息
     * 
     * @param car 车辆信息
     * @return 结果
     */
    @Override
    public int insertCar(Car car)
    {
        car.setDeleted("0");
        car.setCreateTime(DateUtils.getNowDate());
        return carMapper.insertCar(car);
    }

    /**
     * 修改车辆信息
     * 
     * @param car 车辆信息
     * @return 结果
     */
    @Override
    public int updateCar(Car car)
    {
        car.setDeleted("0");
        car.setUpdateTime(DateUtils.getNowDate());
        return carMapper.updateCar(car);
    }

    /**
     * 删除车辆信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCarByIds(String ids)
    {
        return carMapper.deleteCarByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除车辆信息信息
     * 
     * @param id 车辆信息ID
     * @return 结果
     */
    @Override
    public int deleteCarById(Long id)
    {
        return carMapper.deleteCarById(id);
    }
}
