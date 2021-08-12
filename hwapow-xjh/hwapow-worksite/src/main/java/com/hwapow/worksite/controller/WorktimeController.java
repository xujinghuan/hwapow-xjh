package com.hwapow.worksite.controller;

import java.util.List;

import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.utils.DateUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
public class WorktimeController extends BaseController
{
    @Autowired
    private IWorktimeService worktimeService;

    /**
     * 查询工时记录列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:list')")
    @GetMapping("/list")
    public TableDataInfo list(Worktime worktime)
    {
        startPage();
        List<Worktime> list = worktimeService.selectWorktimeList(worktime);
        return getDataTable(list);
    }

    /**
     * 导出工时记录列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:export')")
    @Log(title = "工时记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Worktime worktime)
    {
        List<Worktime> list = worktimeService.selectWorktimeList(worktime);
        ExcelUtil<Worktime> util = new ExcelUtil<Worktime>(Worktime.class);
        return util.exportExcel(list, "worktime");
    }

    /**
     * 获取工时记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(worktimeService.selectWorktimeById(id));
    }

    /**
     * 新增工时记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:add')")
    @Log(title = "工时记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Worktime worktime)
    {
        if (UserConstants.NOT_UNIQUE.equals(worktimeService.checkWorktimeUnique(worktime)))
        {
            return AjaxResult.error("此工人'" + DateUtils.parseDateToStr("yyyy年mm月dd日",worktime.getWorkDate()) + "'工时已记录，请查找并修改当天记录!");
        }
        return toAjax(worktimeService.insertWorktime(worktime));
    }

    /**
     * 修改工时记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:edit')")
    @Log(title = "工时记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Worktime worktime)
    {
        if (UserConstants.NOT_UNIQUE.equals(worktimeService.checkWorktimeUnique(worktime)))
        {
            return AjaxResult.error("此工人'" + DateUtils.parseDateToStr("yyyy年mm月dd日",worktime.getWorkDate()) + "'工时已记录，请查找并修改当天记录!");
        }
        return toAjax(worktimeService.updateWorktime(worktime));
    }

    /**
     * 删除工时记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:worktime:remove')")
    @Log(title = "工时记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(worktimeService.deleteWorktimeByIds(ids));
    }
}
