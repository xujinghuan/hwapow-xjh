package com.hwapow.worksite.service;

import java.util.List;

import com.hwapow.worksite.domain.Worksite;
import org.apache.ibatis.annotations.Param;

/**
 * 工地Service接口
 *
 * @author hwapow
 * @date 2021-08-11
 */
public interface IWorksiteService
{
    /**
     * 查询工地
     *
     * @param id 工地ID
     * @return 工地
     */
    public Worksite selectWorksiteById(Long id);

    /**
     * 查询工地列表
     *
     * @param worksite 工地
     * @return 工地集合
     */
    public List<Worksite> selectWorksiteList(Worksite worksite);

    /**
     * 新增工地
     *
     * @param worksite 工地
     * @return 结果
     */
    public int insertWorksite(Worksite worksite);

    /**
     * 修改工地
     *
     * @param worksite 工地
     * @return 结果
     */
    public int updateWorksite(Worksite worksite);

    /**
     * 批量删除工地
     *
     * @param ids 需要删除的工地ID
     * @return 结果
     */
    public int deleteWorksiteByIds(Long[] ids);

    /**
     * 删除工地信息
     *
     * @param id 工地ID
     * @return 结果
     */
    public int deleteWorksiteById(Long id);

    /**
     * 检查名称是否重复
     * @param workesite
     * @return
     */
    public String checkWorksiteUnique(Worksite workesite);

    /***
     * 修改工地状态
     * @param status
     * @param id
     * @return
     */
    public int updateWorksiteStatus(@Param("status")String status, @Param("id")Long id);
}
