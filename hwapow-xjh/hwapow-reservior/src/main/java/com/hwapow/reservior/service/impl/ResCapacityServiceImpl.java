package com.hwapow.reservior.service.impl;

import java.util.List;

import com.hwapow.common.annotation.DataScope;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.reservior.mapper.ResCapacityMapper;
import com.hwapow.reservior.domain.ResCapacity;
import com.hwapow.reservior.service.IResCapacityService;

/**
 * 库容，主要管理水库水位线对应的库容值，方便取值Service业务层处理
 *
 * @author hwapow
 * @date 2021-09-18
 */
@Service
public class ResCapacityServiceImpl implements IResCapacityService {
    @Autowired
    private ResCapacityMapper resCapacityMapper;

    /**
     * 查询库容，主要管理水库水位线对应的库容值，方便取值
     *
     * @param id 库容，主要管理水库水位线对应的库容值，方便取值ID
     * @return 库容，主要管理水库水位线对应的库容值，方便取值
     */
    @Override
    public ResCapacity selectResCapacityById(Long id) {
        return resCapacityMapper.selectResCapacityById(id);
    }

    /**
     * 查询库容，主要管理水库水位线对应的库容值，方便取值列表
     *
     * @param resCapacity 库容，主要管理水库水位线对应的库容值，方便取值
     * @return 库容，主要管理水库水位线对应的库容值，方便取值
     */
    @Override
    @DataScope(orgAlias = "a")
    public List<ResCapacity> selectResCapacityList(ResCapacity resCapacity) {
        return resCapacityMapper.selectResCapacityList(resCapacity);
    }

    /**
     * 新增库容，主要管理水库水位线对应的库容值，方便取值
     *
     * @param resCapacity 库容，主要管理水库水位线对应的库容值，方便取值
     * @return 结果
     */
    @Override
    public int insertResCapacity(ResCapacity resCapacity) {
        resCapacity.setCreateBy(SecurityUtils.getUsername());
        resCapacity.setCreateTime(DateUtils.getNowDate());
        return resCapacityMapper.insertResCapacity(resCapacity);
    }

    /**
     * 修改库容，主要管理水库水位线对应的库容值，方便取值
     *
     * @param resCapacity 库容，主要管理水库水位线对应的库容值，方便取值
     * @return 结果
     */
    @Override
    public int updateResCapacity(ResCapacity resCapacity) {
        resCapacity.setUpdateBy(SecurityUtils.getUsername());
        resCapacity.setUpdateTime(DateUtils.getNowDate());
        return resCapacityMapper.updateResCapacity(resCapacity);
    }

    /**
     * 批量删除库容，主要管理水库水位线对应的库容值，方便取值
     *
     * @param ids 需要删除的库容，主要管理水库水位线对应的库容值，方便取值ID
     * @return 结果
     */
    @Override
    public int deleteResCapacityByIds(Long[] ids) {
        return resCapacityMapper.deleteResCapacityByIds(ids);
    }

    /**
     * 删除库容，主要管理水库水位线对应的库容值，方便取值信息
     *
     * @param id 库容，主要管理水库水位线对应的库容值，方便取值ID
     * @return 结果
     */
    @Override
    public int deleteResCapacityById(Long id) {
        return resCapacityMapper.deleteResCapacityById(id);
    }

    /**
     * 检查库容的水位是否唯一
     *
     * @param resCapacity 水容
     * @return
     */
    @Override
    public String checkCapacityUnique(ResCapacity resCapacity){
        ResCapacity resCapacityC = resCapacityMapper.checkCapacityUnique(resCapacity.getWaterLevel(),resCapacity.getOrgId());
        if (StringUtils.isNotNull(resCapacityC) && !resCapacityC.getId().equals(resCapacity.getId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
