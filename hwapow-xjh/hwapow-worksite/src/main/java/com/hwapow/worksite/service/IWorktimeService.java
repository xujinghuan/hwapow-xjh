package com.hwapow.worksite.service;

import java.util.List;
import com.hwapow.worksite.domain.Worktime;

/**
 * 工时记录Service接口
 *
 * @author hwapow
 * @date 2021-08-12
 */
public interface IWorktimeService
{
    /**
     * 查询工时记录
     *
     * @param id 工时记录ID
     * @return 工时记录
     */
    public Worktime selectWorktimeById(Long id);

    /**
     * 查询工时记录列表
     *
     * @param worktime 工时记录
     * @return 工时记录集合
     */
    public List<Worktime> selectWorktimeList(Worktime worktime);

    /**
     * 新增工时记录
     *
     * @param worktime 工时记录
     * @return 结果
     */
    public int insertWorktime(Worktime worktime);

    /**
     * 修改工时记录
     *
     * @param worktime 工时记录
     * @return 结果
     */
    public int updateWorktime(Worktime worktime);

    /**
     * 批量删除工时记录
     *
     * @param ids 需要删除的工时记录ID
     * @return 结果
     */
    public int deleteWorktimeByIds(Long[] ids);

    /**
     * 删除工时记录信息
     *
     * @param id 工时记录ID
     * @return 结果
     */
    public int deleteWorktimeById(Long id);

    /**
     * 检查否重复
     * @param worktime
     * @return
     */
    public String checkWorktimeUnique(Worktime worktime);
}
