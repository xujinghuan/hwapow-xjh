package com.hwapow.reservior.service.impl;

import java.util.List;

import com.hwapow.common.annotation.DataScope;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.reservior.mapper.ResMonitorDataMapper;
import com.hwapow.reservior.domain.ResMonitorData;
import com.hwapow.reservior.service.IResMonitorDataService;

/**
 * 传感器监测数据Service业务层处理
 *
 * @author hwapow
 * @date 2021-09-21
 */
@Service
public class ResMonitorDataServiceImpl implements IResMonitorDataService {
    @Autowired
    private ResMonitorDataMapper resMonitorDataMapper;

    /**
     * 查询传感器监测数据
     *
     * @param id 传感器监测数据ID
     * @return 传感器监测数据
     */
    @Override
    public ResMonitorData selectResMonitorDataById(Long id) {
        return resMonitorDataMapper.selectResMonitorDataById(id);
    }

    /**
     * 查询传感器监测数据列表
     *
     * @param resMonitorData 传感器监测数据
     * @return 传感器监测数据
     */
    @Override
    @DataScope(orgAlias = "a")
    public List<ResMonitorData> selectResMonitorDataList(ResMonitorData resMonitorData) {
        return resMonitorDataMapper.selectResMonitorDataList(resMonitorData);
    }


    /**
     * 查询传感器监测数据列表
     *
     * @param resMonitorData 传感器监测数据
     * @return 传感器监测数据集合
     */
    @Override
    public List<ResMonitorData> selectResMonitorDataListByDay(ResMonitorData resMonitorData){
        return resMonitorDataMapper.selectResMonitorDataListByDay(resMonitorData);
    }

    /**
     * 新增传感器监测数据
     *
     * @param resMonitorData 传感器监测数据
     * @return 结果
     */
    @Override
    public int insertResMonitorData(ResMonitorData resMonitorData) {
        resMonitorData.setCreateBy("自动监测");
        resMonitorData.setCreateTime(DateUtils.getNowDate());
        return resMonitorDataMapper.insertResMonitorData(resMonitorData);
    }

    /**
     * 修改传感器监测数据
     *
     * @param resMonitorData 传感器监测数据
     * @return 结果
     */
    @Override
    public int updateResMonitorData(ResMonitorData resMonitorData) {
        resMonitorData.setUpdateBy(SecurityUtils.getUsername());
        resMonitorData.setUpdateTime(DateUtils.getNowDate());
        return resMonitorDataMapper.updateResMonitorData(resMonitorData);
    }

    /**
     * 批量删除传感器监测数据
     *
     * @param ids 需要删除的传感器监测数据ID
     * @return 结果
     */
    @Override
    public int deleteResMonitorDataByIds(Long[] ids) {
        return resMonitorDataMapper.deleteResMonitorDataByIds(ids);
    }

    /**
     * 删除传感器监测数据信息
     *
     * @param id 传感器监测数据ID
     * @return 结果
     */
    @Override
    public int deleteResMonitorDataById(Long id) {
        return resMonitorDataMapper.deleteResMonitorDataById(id);
    }

    /**
     * 获取某天之前的设备数据
     *
     * @param resMonitorData 日期
     * @return 结果
     */
    @Override
    public List<ResMonitorData> selectLastDataByDay(ResMonitorData resMonitorData){
        return resMonitorDataMapper.selectLastDataByDay(resMonitorData);
    }
}
