package com.hwapow.worksite.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hwapow.common.annotation.DataScope;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.StringUtils;
import com.hwapow.worksite.domain.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.worksite.mapper.WorktimeMapper;
import com.hwapow.worksite.domain.Worktime;
import com.hwapow.worksite.service.IWorktimeService;

/**
 * 工时记录Service业务层处理
 *
 * @author hwapow
 * @date 2021-08-12
 */
@Service
public class WorktimeServiceImpl implements IWorktimeService {
    @Autowired
    private WorktimeMapper worktimeMapper;

    /**
     * 查询工时记录
     *
     * @param id 工时记录ID
     * @return 工时记录
     */
    @Override
    public Worktime selectWorktimeById(Long id) {
        return worktimeMapper.selectWorktimeById(id);
    }

    /**
     * 查询工时记录列表
     *
     * @param worktime 工时记录
     * @return 工时记录
     */
    @Override
    @DataScope(userAlias = "a")
    public List<Worktime> selectWorktimeList(Worktime worktime) {
        return worktimeMapper.selectWorktimeList(worktime);
    }

    /**
     * 新增工时记录
     *
     * @param worktime 工时记录
     * @return 结果
     */
    @Override
    public int insertWorktime(Worktime worktime) {
        worktime.setCreateBy(SecurityUtils.getUsername());
        worktime.setCreateTime(DateUtils.getNowDate());
        worktime.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        return worktimeMapper.insertWorktime(worktime);
    }

    /**
     * 修改工时记录
     *
     * @param worktime 工时记录
     * @return 结果
     */
    @Override
    public int updateWorktime(Worktime worktime) {
        worktime.setUpdateBy(SecurityUtils.getUsername());
        worktime.setUpdateTime(DateUtils.getNowDate());
        worktime.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        return worktimeMapper.updateWorktime(worktime);
    }

    /**
     * 批量删除工时记录
     *
     * @param ids 需要删除的工时记录ID
     * @return 结果
     */
    @Override
    public int deleteWorktimeByIds(Long[] ids) {
        return worktimeMapper.deleteWorktimeByIds(ids);
    }

    /**
     * 删除工时记录信息
     *
     * @param id 工时记录ID
     * @return 结果
     */
    @Override
    public int deleteWorktimeById(Long id) {
        return worktimeMapper.deleteWorktimeById(id);
    }

    /**
     * 检查否重复
     *
     * @param worktime
     * @return
     */
    @Override
    public String checkWorktimeUnique(Worktime worktime) {
        Worktime worktimeC = worktimeMapper.checkWorktimeUnique(worktime.getWorker(), worktime.getWorkDate(),SecurityUtils.getLoginUser().getUser().getUserId());
        if (StringUtils.isNotNull(worktimeC) && !worktimeC.getId().equals(worktime.getId())) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /***
     * 获取最近一次工时
     * @return
     */
    @Override
    public Date getLastDay() {
        return this.worktimeMapper.getLastDay(SecurityUtils.getLoginUser().getUser().getUserId());
    }

    /**
     * 全员按年统计
     *
     * @param year
     * @return
     */
    @Override
    public List<Map<String, Object>> countAllYear(String year) {
        return this.worktimeMapper.countAllYear(year,SecurityUtils.getLoginUser().getUser().getUserId());
    }


    /**
     * 全员按月统计
     *
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Object>> countAllMonth(String month) {
        return this.worktimeMapper.countAllMonth(month,SecurityUtils.getLoginUser().getUser().getUserId());
    }

    /**
     * 全员按天统计
     *
     * @param day
     * @return
     */
    @Override
    public List<Map<String, Object>> countAllDay(String day) {
        return this.worktimeMapper.countAllDay(day,SecurityUtils.getLoginUser().getUser().getUserId());
    }

    /**
     * 单人按月统计
     *
     * @param month
     * @param worker
     * @return
     */
    @Override
    public List<Map<String, Object>> countOneMonth(Long worker, String month) {
        return this.worktimeMapper.countOneMonth(worker, month,SecurityUtils.getLoginUser().getUser().getUserId());
    }

    /**
     * 单人按年统计
     *
     * @param year
     * @param worker
     * @return
     */
    @Override
    public List<Map<String, Object>> countOneYear(Long worker, String year) {
        return this.worktimeMapper.countOneYear(worker, year,SecurityUtils.getLoginUser().getUser().getUserId());
    }
}
