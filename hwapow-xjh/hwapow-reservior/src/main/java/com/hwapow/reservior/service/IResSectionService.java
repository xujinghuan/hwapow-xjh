package com.hwapow.reservior.service;

import java.util.List;

import com.hwapow.reservior.domain.ResSection;

/**
 * 水库断面Service接口
 * 
 * @author hwapow
 * @date 2021-09-20
 */
public interface IResSectionService 
{
    /**
     * 查询水库断面
     * 
     * @param id 水库断面ID
     * @return 水库断面
     */
    public ResSection selectResSectionById(Long id);

    /**
     * 查询水库断面列表
     * 
     * @param resSection 水库断面
     * @return 水库断面集合
     */
    public List<ResSection> selectResSectionList(ResSection resSection);

    /**
     * 新增水库断面
     * 
     * @param resSection 水库断面
     * @return 结果
     */
    public int insertResSection(ResSection resSection);

    /**
     * 修改水库断面
     * 
     * @param resSection 水库断面
     * @return 结果
     */
    public int updateResSection(ResSection resSection);

    /**
     * 批量删除水库断面
     * 
     * @param ids 需要删除的水库断面ID
     * @return 结果
     */
    public int deleteResSectionByIds(Long[] ids);

    /**
     * 删除水库断面信息
     * 
     * @param id 水库断面ID
     * @return 结果
     */
    public int deleteResSectionById(Long id);

    /**
     * 检查断面编码是否唯一
     *
     * @param resSection 断面
     * @return
     */
    public String checkSectionUnique(ResSection resSection);
}
