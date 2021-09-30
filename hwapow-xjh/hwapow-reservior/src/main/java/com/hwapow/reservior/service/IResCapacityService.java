package com.hwapow.reservior.service;

import java.util.List;

import com.hwapow.reservior.domain.ResCapacity;
import org.apache.ibatis.annotations.Param;

/**
 * 库容，主要管理水库水位线对应的库容值，方便取值Service接口
 *
 * @author hwapow
 * @date 2021-09-18
 */
public interface IResCapacityService {
    /**
     * 查询库容，主要管理水库水位线对应的库容值，方便取值
     *
     * @param id 库容，主要管理水库水位线对应的库容值，方便取值ID
     * @return 库容，主要管理水库水位线对应的库容值，方便取值
     */
    public ResCapacity selectResCapacityById(Long id);

    /**
     * 查询库容，主要管理水库水位线对应的库容值，方便取值列表
     *
     * @param resCapacity 库容，主要管理水库水位线对应的库容值，方便取值
     * @return 库容，主要管理水库水位线对应的库容值，方便取值集合
     */
    public List<ResCapacity> selectResCapacityList(ResCapacity resCapacity);

    /**
     * 新增库容，主要管理水库水位线对应的库容值，方便取值
     *
     * @param resCapacity 库容，主要管理水库水位线对应的库容值，方便取值
     * @return 结果
     */
    public int insertResCapacity(ResCapacity resCapacity);

    /**
     * 修改库容，主要管理水库水位线对应的库容值，方便取值
     *
     * @param resCapacity 库容，主要管理水库水位线对应的库容值，方便取值
     * @return 结果
     */
    public int updateResCapacity(ResCapacity resCapacity);

    /**
     * 批量删除库容，主要管理水库水位线对应的库容值，方便取值
     *
     * @param ids 需要删除的库容，主要管理水库水位线对应的库容值，方便取值ID
     * @return 结果
     */
    public int deleteResCapacityByIds(Long[] ids);

    /**
     * 删除库容，主要管理水库水位线对应的库容值，方便取值信息
     *
     * @param id 库容，主要管理水库水位线对应的库容值，方便取值ID
     * @return 结果
     */
    public int deleteResCapacityById(Long id);

    /**
     * 检查库容的水位是否唯一
     *
     * @param resCapacity 水容
     * @return
     */
    public String checkCapacityUnique(ResCapacity resCapacity);

    /**
     * 查找水位
     * @param waterLevel 水位
     * @param orgId 组织id
     * @return
     */
    public ResCapacity selectCapacity(Double waterLevel, Long orgId);
}
