package com.hwapow.worksite.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hwapow.common.constant.HttpStatus;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.utils.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hwapow.common.annotation.Log;
import com.hwapow.common.core.controller.BaseController;
import com.hwapow.common.core.domain.AjaxResult;
import com.hwapow.common.enums.BusinessType;
import com.hwapow.worksite.domain.Worktime;
import com.hwapow.worksite.service.IWorktimeService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 工时记录Controller
 *
 * @author hwapow
 * @date 2021-08-12
 */
@RestController
@RequestMapping("/worksite/worktime")
public class WorktimeController extends BaseController {
    @Autowired
    private IWorktimeService worktimeService;

    /**
     * 查询工时记录列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:list')")
    @GetMapping("/list")
    public TableDataInfo list(Worktime worktime) {
        startPage();
        List<Worktime> list = worktimeService.selectWorktimeList(worktime);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        if (list == null || list.size() == 0) {
            rspData.setMsg(null);
            Date date = this.worktimeService.getLastDay();
            if (date != null) {
                rspData.setMsg(DateUtils.parseDateToStr("yyyy年MM月dd日", date));
            }
        }
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 导出工时记录列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:export')")
    @Log(title = "工时记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Worktime worktime) {
        List<Worktime> list = worktimeService.selectWorktimeList(worktime);
        ExcelUtil<Worktime> util = new ExcelUtil<Worktime>(Worktime.class);
        return util.exportExcel(list, "worktime");
    }

    /**
     * 获取工时记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(worktimeService.selectWorktimeById(id));
    }

    /**
     * 新增工时记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:add')")
    @Log(title = "工时记录", businessType = BusinessType.INSERT)
    @PostMapping
    public synchronized AjaxResult add(@RequestBody Worktime worktime) {
        if (UserConstants.NOT_UNIQUE.equals(worktimeService.checkWorktimeUnique(worktime))) {
            return AjaxResult.error("此工人'" + DateUtils.parseDateToStr("yyyy年mm月dd日", worktime.getWorkDate()) + "'工时已记录，请查找并修改当天记录!");
        }
        return toAjax(worktimeService.insertWorktime(worktime));
    }

    /**
     * 导入工人数据
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:add')")
    @Log(title = "工时记录（导入工人）", businessType = BusinessType.INSERT)
    @PostMapping("/importWorker")
    public synchronized AjaxResult importWorker(@RequestParam(name = "workDate") Date workDate, @RequestBody List<Long> workers) {
        if (workDate == null) {
            return AjaxResult.error("要导入的日期不能为空！");
        }
        if (workers == null || workers.size() == 0) {
            return AjaxResult.error("要导入工人数据不能为空！");
        }
        int count = 0;
        for (Long worker : workers) {
            Worktime worktime = new Worktime();
            worktime.setWorker(worker);
            worktime.setWorkDate(workDate);
            worktime.setWorkDate(workDate);
            worktime.setTaskTime(new BigDecimal(0));
            if (UserConstants.NOT_UNIQUE.equals(worktimeService.checkWorktimeUnique(worktime))) {
                continue;
            }
            worktimeService.insertWorktime(worktime);
            count++;
        }
        return AjaxResult.success("导入成功！", count);
    }

    /**
     * 导入最次一次工时记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:add')")
    @Log(title = "工时记录(导入最近一次数据)", businessType = BusinessType.INSERT)
    @GetMapping("/importLastDay")
    public synchronized AjaxResult importLastDay(Date workDate) {
        if (workDate == null) {
            return AjaxResult.error("要导入的日期不能为空！");
        }
        Worktime sworktime = new Worktime();
        sworktime.setWorkDate(workDate);
        List<Worktime> worktimes = this.worktimeService.selectWorktimeList(sworktime);
        if (worktimes != null && worktimes.size() > 0) {
            return AjaxResult.error(DateUtils.parseDateToStr("yyyy年mm月dd日", sworktime.getWorkDate()) + "工时已记录，不需要导入！");
        }
        Date date = this.worktimeService.getLastDay();
        if (date == null) {
            return AjaxResult.error("当前无数据可以导入");
        }
        sworktime.setWorkDate(date);
        List<Worktime> lsatworktimes = this.worktimeService.selectWorktimeList(sworktime);
        if (lsatworktimes == null && lsatworktimes.size() == 0) {
            return AjaxResult.error("当前无数据可以导入");
        }
        int count = 0;
        for (Worktime worktime : lsatworktimes) {
            worktime.setId(null);
            worktime.setWorkDate(workDate);
            worktime.setTaskTime(new BigDecimal(0));
            worktime.setContent(null);
            worktime.setRemark(null);
            if (UserConstants.NOT_UNIQUE.equals(worktimeService.checkWorktimeUnique(worktime)) && worktime.getWorker() != null) {
                continue;
            }
            worktimeService.insertWorktime(worktime);
            count++;
        }
        return AjaxResult.success("导入成功！", count);
    }

    /**
     * 修改工时记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:edit')")
    @Log(title = "工时记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public synchronized AjaxResult edit(@RequestBody Worktime worktime) {
        if (UserConstants.NOT_UNIQUE.equals(worktimeService.checkWorktimeUnique(worktime))) {
            return AjaxResult.error("此工人'" + DateUtils.parseDateToStr("yyyy年mm月dd日", worktime.getWorkDate()) + "'工时已记录，请查找并修改当天记录!");
        }
        return toAjax(worktimeService.updateWorktime(worktime));
    }

    /**
     * 删除工时记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:remove')")
    @Log(title = "工时记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(worktimeService.deleteWorktimeByIds(ids));
    }

    /**
     * 全员按年统计
     *
     * @param year
     * @return
     */
    @GetMapping("/countAllYear")
    public AjaxResult countAllYear(String year) {
        return AjaxResult.success(this.worktimeService.countAllYear(year));
    }

    /**
     * 全员按月统计
     *
     * @param month
     * @return
     */
    @GetMapping("/countAllMonth")
    public AjaxResult countAllMonth(String month) {
        return AjaxResult.success(this.worktimeService.countAllMonth(month));
    }

    /**
     * 全员按天统计
     *
     * @param day
     * @return
     */
    @GetMapping("/countAllDay")
    public AjaxResult countAllDay(String day) {
        return AjaxResult.success(this.worktimeService.countAllDay(day));
    }

    /**
     * 单人按月统计
     *
     * @param month
     * @return
     */
    @GetMapping("/countOneMonth")
    public AjaxResult countOneMonth(Long worker, String month) {
        return AjaxResult.success(this.worktimeService.countOneMonth(worker, month));
    }

    /**
     * 单人按年统计
     *
     * @param year
     * @return
     */
    @GetMapping("/countOneYear")
    public AjaxResult countOneYear(Long worker, String year) {
        return AjaxResult.success(this.worktimeService.countOneYear(worker, year));
    }
}
