package com.hwapow.taxProcess.mapper;

import java.util.List;
import com.hwapow.taxProcess.domain.TaxBase;

/**
 * 基础信息表Mapper接口
 * 
 * @author hwapow
 * @date 2023-04-17
 */
public interface TaxBaseMapper 
{
    /**
     * 查询基础信息表
     * 
     * @param id 基础信息表ID
     * @return 基础信息表
     */
    public TaxBase selectTaxBaseById(Long id);

    /**
     * 查询基础信息表列表
     * 
     * @param taxBase 基础信息表
     * @return 基础信息表集合
     */
    public List<TaxBase> selectTaxBaseList(TaxBase taxBase);

    /**
     * 新增基础信息表
     * 
     * @param taxBase 基础信息表
     * @return 结果
     */
    public int insertTaxBase(TaxBase taxBase);

    /**
     * 修改基础信息表
     * 
     * @param taxBase 基础信息表
     * @return 结果
     */
    public int updateTaxBase(TaxBase taxBase);

    /**
     * 删除基础信息表
     * 
     * @param id 基础信息表ID
     * @return 结果
     */
    public int deleteTaxBaseById(Long id);

    /**
     * 批量删除基础信息表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTaxBaseByIds(Long[] ids);
}
