package com.dregs.project.system.transport.service.impl;

import java.util.List;
import java.util.Optional;

import com.dregs.common.utils.DateUtils;
import com.dregs.common.utils.security.ShiroUtils;
import com.dregs.project.system.car.domain.Car;
import com.dregs.project.system.car.mapper.CarMapper;
import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.project.mapper.TProjectMapper;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.domain.TProjectSlagyard;
import com.dregs.project.system.slagyard.mapper.SlagyardMapper;
import com.dregs.project.system.slagyard.mapper.TProjectSlagyardMapper;
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

    @Autowired
    private TProjectSlagyardMapper tProjectSlagyardMapper;

    @Autowired
    private TProjectMapper tProjectMapper;
    @Autowired
    private SlagyardMapper slagyardMapper;

    @Autowired
    private CarMapper carMapper;

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
        List<CarTransport> list = carTransportMapper.selectCarTransportList(carTransport);

        TProjectSlagyard projectSlagyard = new TProjectSlagyard();
        List<TProjectSlagyard> list1 = tProjectSlagyardMapper.selectTProjectSlagyardList(projectSlagyard);
        TProject project = new TProject();
        List<TProject> projects = tProjectMapper.selectTProjectList(project);

        List<Slagyard> slagyards = slagyardMapper.selectSlagyardList(new Slagyard());

        List<Car> cars = carMapper.selectCarList(new Car());


        for (CarTransport carTransp:list){

            Optional<TProject> _project = projects.stream().filter(item->item.getId().toString().equals(carTransp.getProjectId().toString())).findFirst();
            if (_project.isPresent()){
                carTransp.setProjectName(_project.get().getName());
            }

            if (carTransp.getTransportType().toString().equals("1") || carTransp.getTransportType().toString().equals("3")){
//                Optional<Car> _car = cars.stream().filter(item->item.getId().toString().equals(carTransp.getCarId().toString())).findFirst();
//                Car c = _car.get();
//                carTransp.setRelationName(c.getCarNum()+"["+c.getDriver()+"]");
            }else if (carTransp.getTransportType().toString().equals("2")){
                Optional<Slagyard> _slagyard = slagyards.stream().filter(item->item.getId().toString().equals(carTransp.getSlagyardId().toString())).findFirst();
                Slagyard c = _slagyard.get();
                carTransp.setSlagyardName(c.getTitle());
                carTransp.setRelationName(c.getTitle());
            }else if (carTransp.getTransportType().toString().equals("0")){
                carTransp.setRelationName(carTransp.getProjectName());
            }else{
                carTransp.setRelationName("数据错误");
            }



            Optional<TProjectSlagyard> option = list1.stream().filter(item->item.getId().equals(carTransp.getProjectSlagyardId())).findFirst();
            if (option.isPresent()){

                TProjectSlagyard tProjectSlagyard = option.get();

                if (carTransp.getTransportType().toString().equals("1") || carTransp.getTransportType().toString().equals("3")){
                    carTransp.setTotalMoney((carTransp.getTransportNum() * Long.parseLong(tProjectSlagyard.getPushCarMaoney()))+"");
                    carTransp.setMoney(tProjectSlagyard.getPushCarMaoney());
                }else if (carTransp.getTransportType().toString().equals("2")){
                    carTransp.setTotalMoney((carTransp.getTransportNum() * Long.parseLong(tProjectSlagyard.getPushSlagyardMaoney()))+"");
                    carTransp.setMoney(tProjectSlagyard.getPushSlagyardMaoney());
                }else if (carTransp.getTransportType().toString().equals("0")){
                    carTransp.setTotalMoney((carTransp.getTransportNum() * Long.parseLong(tProjectSlagyard.getPullMoney()))+"");
                    carTransp.setMoney(tProjectSlagyard.getPullMoney());
                }

                Optional<Slagyard> _slagyard = slagyards.stream().filter(item->item.getId().toString().equals(tProjectSlagyard.getSlagyardId().toString())).findFirst();
                if (_slagyard.isPresent()){
                    carTransp.setSlagyardName(_slagyard.get().getTitle());
                }
            }

        }
        return list;
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
        carTransport.setAddUserId(ShiroUtils.getUserId());
        carTransport.setAddName(ShiroUtils.getLoginName());
        TProjectSlagyard projectSlagyard = tProjectSlagyardMapper.selectTProjectSlagyardById(carTransport.getProjectSlagyardId());
        carTransport.setProjectId(projectSlagyard.getProjectId());
        carTransport.setSlagyardId(projectSlagyard.getSlagyardId().toString());
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
        carTransport.setUpdateTime(DateUtils.getNowDate());
        carTransport.setUpdUserId(ShiroUtils.getUserId());
        carTransport.setUdpName(ShiroUtils.getLoginName());
        TProjectSlagyard projectSlagyard = tProjectSlagyardMapper.selectTProjectSlagyardById(carTransport.getProjectSlagyardId());
        carTransport.setProjectId(projectSlagyard.getProjectId());
        carTransport.setSlagyardId(projectSlagyard.getSlagyardId().toString());
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
