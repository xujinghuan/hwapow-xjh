package com.hwapow.reservior.mapper;

import java.util.List;

import com.hwapow.reservior.domain.ResSection;
import org.apache.ibatis.annotations.Param;

/**
 * 水库断面Mapper接口
 * 
 * @author hwapow
 * @date 2021-09-20
 */
public interface ResSectionMapper 
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
     * 删除水库断面
     * 
     * @param id 水库断面ID
     * @return 结果
     */
    public int deleteResSectionById(Long id);

    /**
     * 批量删除水库断面
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteResSectionByIds(Long[] ids);

    /**
     * 检查编码是否唯一
     * @param code 编码
     * @param orgId 组织id
     * @return
     */
    public ResSection checkSectionUnique(@Param("code") String code, @Param("orgId")Long orgId);
}
