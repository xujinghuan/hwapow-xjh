package com.hwapow.reservior.service;

import java.util.List;
import com.hwapow.reservior.domain.ResMonitorData;

/**
 * 传感器监测数据Service接口
 * 
 * @author hwapow
 * @date 2021-09-21
 */
public interface IResMonitorDataService 
{
    /**
     * 查询传感器监测数据
     * 
     * @param id 传感器监测数据ID
     * @return 传感器监测数据
     */
    public ResMonitorData selectResMonitorDataById(Long id);

    /**
     * 查询传感器监测数据列表
     * 
     * @param resMonitorData 传感器监测数据
     * @return 传感器监测数据集合
     */
    public List<ResMonitorData> selectResMonitorDataList(ResMonitorData resMonitorData);

    /**
     * 查询传感器监测数据列表
     *
     * @param resMonitorData 传感器监测数据
     * @return 传感器监测数据集合
     */
    public List<ResMonitorData> selectResMonitorDataListByDay(ResMonitorData resMonitorData);

    /**
     * 新增传感器监测数据
     * 
     * @param resMonitorData 传感器监测数据
     * @return 结果
     */
    public int insertResMonitorData(ResMonitorData resMonitorData);

    /**
     * 修改传感器监测数据
     * 
     * @param resMonitorData 传感器监测数据
     * @return 结果
     */
    public int updateResMonitorData(ResMonitorData resMonitorData);

    /**
     * 批量删除传感器监测数据
     * 
     * @param ids 需要删除的传感器监测数据ID
     * @return 结果
     */
    public int deleteResMonitorDataByIds(Long[] ids);

    /**
     * 删除传感器监测数据信息
     * 
     * @param id 传感器监测数据ID
     * @return 结果
     */
    public int deleteResMonitorDataById(Long id);

    /**
     * 获取某天之前的设备数据
     *
     * @param resMonitorData
     * @return 结果
     */
    public List<ResMonitorData> selectLastDataByDay(ResMonitorData resMonitorData);
}
