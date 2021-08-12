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
import com.hwapow.worksite.domain.Otherthing;
import com.hwapow.worksite.service.IOtherthingService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 杂事记录Controller
 *
 * @author hwapow
 * @date 2021-08-11
 */
@RestController
@RequestMapping("/worksite/otherthing")
public class OtherthingController extends BaseController
{
    @Autowired
    private IOtherthingService otherthingService;

    /**
     * 查询杂事记录列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:otherthing:list')")
    @GetMapping("/list")
    public TableDataInfo list(Otherthing otherthing)
    {
        startPage();
        List<Otherthing> list = otherthingService.selectOtherthingList(otherthing);
        return getDataTable(list);
    }

    /**
     * 导出杂事记录列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:otherthing:export')")
    @Log(title = "杂事记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Otherthing otherthing)
    {
        List<Otherthing> list = otherthingService.selectOtherthingList(otherthing);
        ExcelUtil<Otherthing> util = new ExcelUtil<Otherthing>(Otherthing.class);
        return util.exportExcel(list, "otherthing");
    }

    /**
     * 获取杂事记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('worksite:otherthing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(otherthingService.selectOtherthingById(id));
    }

    /**
     * 新增杂事记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:otherthing:add')")
    @Log(title = "杂事记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Otherthing otherthing)
    {
        return toAjax(otherthingService.insertOtherthing(otherthing));
    }

    /**
     * 修改杂事记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:otherthing:edit')")
    @Log(title = "杂事记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Otherthing otherthing)
    {
        return toAjax(otherthingService.updateOtherthing(otherthing));
    }

    /**
     * 删除杂事记录
     */
    @PreAuthorize("@ss.hasPermi('worksite:otherthing:remove')")
    @Log(title = "杂事记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(otherthingService.deleteOtherthingByIds(ids));
    }
}
