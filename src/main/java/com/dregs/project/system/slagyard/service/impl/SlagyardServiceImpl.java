package com.dregs.project.system.slagyard.service.impl;

import com.dregs.common.utils.DateUtils;
import com.dregs.common.utils.StringUtils;
import com.dregs.common.utils.text.Convert;
import com.dregs.project.system.pay.domain.TProjectPay;
import com.dregs.project.system.pay.mapper.TProjectPayMapper;
import com.dregs.project.system.project.domain.SlagYardStatistics;
import com.dregs.project.system.project.domain.SlagYardStatisticsProject;
import com.dregs.project.system.project.domain.StaProject;
import com.dregs.project.system.project.domain.TProject;
import com.dregs.project.system.project.mapper.TProjectMapper;
import com.dregs.project.system.slagyard.domain.Slagyard;
import com.dregs.project.system.slagyard.domain.TProjectSlagyard;
import com.dregs.project.system.slagyard.mapper.SlagyardMapper;
import com.dregs.project.system.slagyard.mapper.TProjectSlagyardMapper;
import com.dregs.project.system.slagyard.service.ISlagyardService;
import com.dregs.project.system.transport.domain.CarTransport;
import com.dregs.project.system.transport.mapper.CarTransportMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private TProjectSlagyardMapper tProjectSlagyardMapper;

    @Autowired
    private CarTransportMapper carTransportMapper;

    @Autowired
    private TProjectPayMapper projectPayMapper;


    @Autowired
    private TProjectMapper tProjectMapper;

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

    @Override
    public List<Slagyard> selectSlagyardByPayList() {
        return slagyardMapper.selectSlagyardByPayList();
    }

    @Override
    public List<SlagYardStatistics> slagYardStatistics(SlagYardStatistics slagYardStatistics) {
        //查询所有渣场
        Slagyard _slagyard = new Slagyard();
        BeanUtils.copyProperties(slagYardStatistics,_slagyard);
        if (StringUtils.isNotEmpty(slagYardStatistics.getSlagyardId())){
            _slagyard.setId(Long.parseLong(slagYardStatistics.getSlagyardId()));
        }

        List<SlagYardStatistics> _list = new ArrayList<>();
        List<Slagyard> list = slagyardMapper.selectSlagyardList(_slagyard);
        list.forEach(slagYard -> {
            //根据渣场查询渣场项目关联关系
            TProjectSlagyard projectSlagyard = new TProjectSlagyard();

            projectSlagyard.setSlagyardId(slagYard.getId());
            List<TProjectSlagyard> projectSlagYardList = tProjectSlagyardMapper.selectTProjectSlagyardList(projectSlagyard);

            Integer staMoney = 0;
            Integer staTotalNum = 0;

            for (TProjectSlagyard tProjectSlagyard:projectSlagYardList) {
                CarTransport carTransport = new CarTransport();
                BeanUtils.copyProperties(slagYardStatistics,carTransport);
                carTransport.setTransportType("2");
                carTransport.setProjectSlagyardId(tProjectSlagyard.getId());
                Integer staNum = carTransportMapper.getTransportNumByProjectSlagyardId(carTransport);
                Integer pushSlagyardMoney = Integer.parseInt(tProjectSlagyard.getPushSlagyardMaoney());

                staMoney += (pushSlagyardMoney * staNum);
                staTotalNum = staTotalNum + staNum;
            }

            //查询渣场已付金额
            TProjectPay tProjectPay = new TProjectPay();
            tProjectPay.setRelationId(slagYard.getId());
            tProjectPay.setPayType(2L);
            tProjectPay.setType("2");
            String pullEndMoney = projectPayMapper.getMoneyByObjectId(tProjectPay);

            SlagYardStatistics _staProject = new SlagYardStatistics();
            _staProject.setSlagyardId(slagYard.getId().toString());
            _staProject.setPushSlaTotalMoney(staMoney+"");
            _staProject.setName(slagYard.getTitle());
            _staProject.setSlaTotal(staTotalNum.toString());
            _staProject.setPullEndMoney(pullEndMoney);
            _list.add(_staProject);
        });
        return _list;
    }

    @Override
    public List<SlagYardStatisticsProject> slagYardStatisticsProject(SlagYardStatisticsProject slagYardStatisticsProject) {
        List<SlagYardStatisticsProject> _list = new ArrayList<>();
        if (StringUtils.isNotEmpty(slagYardStatisticsProject.getSlagyardId())){
            Long slagyardId = Long.parseLong(slagYardStatisticsProject.getSlagyardId());
            Slagyard slagYard = slagyardMapper.selectSlagyardById(slagyardId);

            TProjectSlagyard projectSlagyard = new TProjectSlagyard();

            projectSlagyard.setSlagyardId(slagYard.getId());
            List<TProjectSlagyard> projectSlagYardList = tProjectSlagyardMapper.selectTProjectSlagyardList(projectSlagyard);

            List<TProject> projects = tProjectMapper.selectTProjectList(new TProject());

            for (TProjectSlagyard tProjectSlagyard:projectSlagYardList) {
                CarTransport carTransport = new CarTransport();
                BeanUtils.copyProperties(slagYardStatisticsProject,carTransport);
                carTransport.setTransportType("2");
                carTransport.setProjectSlagyardId(tProjectSlagyard.getId());
                Integer staNum = carTransportMapper.getTransportNumByProjectSlagyardId(carTransport);
                Integer pushSlagyardMoney = Integer.parseInt(tProjectSlagyard.getPushSlagyardMaoney());

                Integer staMoney = (pushSlagyardMoney * staNum);

                //查询项目对渣场已付金额
                TProjectPay tProjectPay = new TProjectPay();
                tProjectPay.setRelationId(slagYard.getId());
                tProjectPay.setPayObjId(Long.parseLong(tProjectSlagyard.getProjectId()));
                tProjectPay.setPayType(2L);
                tProjectPay.setType("2");
                String pullEndMoney = projectPayMapper.getMoneyByObjectId(tProjectPay);
                Optional<TProject> aaa =  projects.stream().filter(tProject -> tProject.getId().toString().equals(tProjectSlagyard.getProjectId())).findFirst();

                SlagYardStatisticsProject _slagYardStatisticsProject = new SlagYardStatisticsProject();
                if (aaa.isPresent()){
                    _slagYardStatisticsProject.setPname(aaa.get().getName());
                }
                _slagYardStatisticsProject.setName(slagYard.getTitle());
                _slagYardStatisticsProject.setSlaTotal(staNum+"");
                _slagYardStatisticsProject.setPushSlaTotalMoney(staMoney.toString());
                _slagYardStatisticsProject.setPullEndMoney(pullEndMoney);
                _list.add(_slagYardStatisticsProject);
            }
        }
        return _list;
    }
}
