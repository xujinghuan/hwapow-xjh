package com.hwapow.reservior.mapper;

import java.util.List;

import com.hwapow.reservior.domain.ResSection;
import com.hwapow.reservior.domain.ResSenor;
import org.apache.ibatis.annotations.Param;

/**
 * 设备管理Mapper接口
 * 
 * @author hwapow
 * @date 2021-09-20
 */
public interface ResSenorMapper 
{
    /**
     * 查询设备管理
     * 
     * @param id 设备管理ID
     * @return 设备管理
     */
    public ResSenor selectResSenorById(Long id);

    /**
     * 查询设备管理列表
     * 
     * @param resSenor 设备管理
     * @return 设备管理集合
     */
    public List<ResSenor> selectResSenorList(ResSenor resSenor);

    /**
     * 新增设备管理
     * 
     * @param resSenor 设备管理
     * @return 结果
     */
    public int insertResSenor(ResSenor resSenor);

    /**
     * 修改设备管理
     * 
     * @param resSenor 设备管理
     * @return 结果
     */
    public int updateResSenor(ResSenor resSenor);

    /**
     * 删除设备管理
     * 
     * @param id 设备管理ID
     * @return 结果
     */
    public int deleteResSenorById(Long id);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteResSenorByIds(Long[] ids);

    /**
     * 检查编码是否唯一
     * @param code 编码
     * @param sectionId 组织id
     * @return
     */
    public ResSenor checkSenorUnique(@Param("code") String code, @Param("sectionId")Long sectionId);
}
