package com.hwapow.worksite.controller;

import java.util.List;
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
import com.hwapow.worksite.domain.AskForLeave;
import com.hwapow.worksite.service.IAskForLeaveService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 请假Controller
 *
 * @author hwapow
 * @date 2021-08-11
 */
@RestController
@RequestMapping("/worksite/askForLeave")
public class AskForLeaveController extends BaseController
{
    @Autowired
    private IAskForLeaveService askForLeaveService;

    /**
     * 查询请假列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:askForLeave:list')")
    @GetMapping("/list")
    public TableDataInfo list(AskForLeave askForLeave)
    {
        startPage();
        List<AskForLeave> list = askForLeaveService.selectAskForLeaveList(askForLeave);
        return getDataTable(list);
    }

    /**
     * 导出请假列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:askForLeave:export')")
    @Log(title = "请假", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AskForLeave askForLeave)
    {
        List<AskForLeave> list = askForLeaveService.selectAskForLeaveList(askForLeave);
        ExcelUtil<AskForLeave> util = new ExcelUtil<AskForLeave>(AskForLeave.class);
        return util.exportExcel(list, "askForLeave");
    }

    /**
     * 获取请假详细信息
     */
    @PreAuthorize("@ss.hasPermi('worksite:askForLeave:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(askForLeaveService.selectAskForLeaveById(id));
    }

    /**
     * 新增请假
     */
    @PreAuthorize("@ss.hasPermi('worksite:askForLeave:add')")
    @Log(title = "请假", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AskForLeave askForLeave)
    {
        return toAjax(askForLeaveService.insertAskForLeave(askForLeave));
    }

    /**
     * 修改请假
     */
    @PreAuthorize("@ss.hasPermi('worksite:askForLeave:edit')")
    @Log(title = "请假", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AskForLeave askForLeave)
    {
        return toAjax(askForLeaveService.updateAskForLeave(askForLeave));
    }

    /**
     * 删除请假
     */
    @PreAuthorize("@ss.hasPermi('worksite:askForLeave:remove')")
    @Log(title = "请假", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(askForLeaveService.deleteAskForLeaveByIds(ids));
    }
}
