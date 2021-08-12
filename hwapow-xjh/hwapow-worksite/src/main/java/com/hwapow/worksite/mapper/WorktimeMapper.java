package com.hwapow.worksite.mapper;

import java.util.Date;
import java.util.List;

import com.hwapow.worksite.domain.Worktime;
import org.apache.ibatis.annotations.Param;

/**
 * 工时记录Mapper接口
 *
 * @author hwapow
 * @date 2021-08-12
 */
public interface WorktimeMapper
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
     * 删除工时记录
     *
     * @param id 工时记录ID
     * @return 结果
     */
    public int deleteWorktimeById(Long id);

    /**
     * 批量删除工时记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorktimeByIds(Long[] ids);

    /**
     * 检查记录是否重复
     * @param worker
     * @param workDate
     * @return
     */
    public Worktime checkWorktimeUnique(@Param("worker")Long worker, @Param("workDate") Date workDate);
}
