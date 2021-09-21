package com.hwapow.reservior.service;

import java.util.List;

import com.hwapow.reservior.domain.ResSenor;

/**
 * 设备管理Service接口
 * 
 * @author hwapow
 * @date 2021-09-20
 */
public interface IResSenorService 
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
     * 批量删除设备管理
     * 
     * @param ids 需要删除的设备管理ID
     * @return 结果
     */
    public int deleteResSenorByIds(Long[] ids);

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理ID
     * @return 结果
     */
    public int deleteResSenorById(Long id);

    /**
     * 检查断面编码是否唯一
     *
     * @param resSenor
     * @return
     */
    public String checkSenorUnique(ResSenor resSenor);
}
