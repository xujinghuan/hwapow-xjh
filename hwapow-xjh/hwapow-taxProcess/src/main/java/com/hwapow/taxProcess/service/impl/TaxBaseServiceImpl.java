package com.hwapow.taxProcess.service.impl;

import java.util.List;
import com.hwapow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.taxProcess.mapper.TaxBaseMapper;
import com.hwapow.taxProcess.domain.TaxBase;
import com.hwapow.taxProcess.service.ITaxBaseService;

/**
 * 基础信息表Service业务层处理
 *
 * @author hwapow
 * @date 2023-04-17
 */
@Service
public class TaxBaseServiceImpl implements ITaxBaseService
{
    @Autowired
    private TaxBaseMapper taxBaseMapper;

    /**
     * 查询基础信息表
     *
     * @param id 基础信息表ID
     * @return 基础信息表
     */
    @Override
    public TaxBase selectTaxBaseById(Long id)
    {
        return taxBaseMapper.selectTaxBaseById(id);
    }

    /**
     * 查询基础信息表列表
     *
     * @param taxBase 基础信息表
     * @return 基础信息表
     */
    @Override
    public List<TaxBase> selectTaxBaseList(TaxBase taxBase)
    {
        return taxBaseMapper.selectTaxBaseList(taxBase);
    }

    /**
     * 新增基础信息表
     *
     * @param taxBase 基础信息表
     * @return 结果
     */
    @Override
    public int insertTaxBase(TaxBase taxBase)
    {
                                                                                    return taxBaseMapper.insertTaxBase(taxBase);
    }

    /**
     * 修改基础信息表
     *
     * @param taxBase 基础信息表
     * @return 结果
     */
    @Override
    public int updateTaxBase(TaxBase taxBase)
    {
        return taxBaseMapper.updateTaxBase(taxBase);
    }

    /**
     * 批量删除基础信息表
     *
     * @param ids 需要删除的基础信息表ID
     * @return 结果
     */
    @Override
    public int deleteTaxBaseByIds(Long[] ids)
    {
        return taxBaseMapper.deleteTaxBaseByIds(ids);
    }

    /**
     * 删除基础信息表信息
     *
     * @param id 基础信息表ID
     * @return 结果
     */
    @Override
    public int deleteTaxBaseById(Long id)
    {
        return taxBaseMapper.deleteTaxBaseById(id);
    }
}
