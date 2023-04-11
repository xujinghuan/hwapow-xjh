package com.hwapow.worksite.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public Worktime checkWorktimeUnique(@Param("worker")Long worker, @Param("workDate") Date workDate, @Param("userId") Long userId);

    /***
     * 获取最近一次工时
     * @return
     */
    public Date getLastDay(@Param("userId")Long userId);

    /**
     * 全员按年统计
     * @param year
     * @return
     */
    public List<Map<String,Object>> countAllYear(@Param("year")String year,@Param("userId")Long userId);

    /**
     * 全员按月统计
     * @param month
     * @return
     */
    public List<Map<String,Object>> countAllMonth(@Param("month")String month,@Param("userId")Long userId);

    /**
     * 全员按天统计
     * @param day
     * @return
     */
    public List<Map<String,Object>> countAllDay(@Param("day")String day,@Param("userId")Long userId);

    /**
     * 单人按月统计
     * @param month
     * @param worker
     * @return
     */
    public List<Map<String,Object>> countOneMonth(@Param("worker")Long worker,@Param("month")String month,@Param("userId")Long userId);

    /**
     * 单人按年统计
     * @param year
     * @param worker
     * @return
     */
    public List<Map<String,Object>> countOneYear(@Param("worker")Long worker,@Param("year")String year,@Param("userId")Long userId);
}
