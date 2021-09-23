package com.hwapow.reservior.mapper;

import java.util.List;
import com.hwapow.reservior.domain.ResMonitorData;

/**
 * 传感器监测数据Mapper接口
 * 
 * @author hwapow
 * @date 2021-09-21
 */
public interface ResMonitorDataMapper 
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
     * 删除传感器监测数据
     * 
     * @param id 传感器监测数据ID
     * @return 结果
     */
    public int deleteResMonitorDataById(Long id);

    /**
     * 批量删除传感器监测数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteResMonitorDataByIds(Long[] ids);
}
