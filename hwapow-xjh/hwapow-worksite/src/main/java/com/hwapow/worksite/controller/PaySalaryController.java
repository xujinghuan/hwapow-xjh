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
import com.hwapow.worksite.domain.PaySalary;
import com.hwapow.worksite.service.IPaySalaryService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 工资发放Controller
 *
 * @author hwapow
 * @date 2021-08-11
 */
@RestController
@RequestMapping("/worksite/paySalary")
public class PaySalaryController extends BaseController
{
    @Autowired
    private IPaySalaryService paySalaryService;

    /**
     * 查询工资发放列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:paySalary:list')")
    @GetMapping("/list")
    public TableDataInfo list(PaySalary paySalary)
    {
        startPage();
        List<PaySalary> list = paySalaryService.selectPaySalaryList(paySalary);
        return getDataTable(list);
    }

    /**
     * 导出工资发放列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:paySalary:export')")
    @Log(title = "工资发放", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PaySalary paySalary)
    {
        List<PaySalary> list = paySalaryService.selectPaySalaryList(paySalary);
        ExcelUtil<PaySalary> util = new ExcelUtil<PaySalary>(PaySalary.class);
        return util.exportExcel(list, "paySalary");
    }

    /**
     * 获取工资发放详细信息
     */
    @PreAuthorize("@ss.hasPermi('worksite:paySalary:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(paySalaryService.selectPaySalaryById(id));
    }

    /**
     * 新增工资发放
     */
    @PreAuthorize("@ss.hasPermi('worksite:paySalary:add')")
    @Log(title = "工资发放", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PaySalary paySalary)
    {
        return toAjax(paySalaryService.insertPaySalary(paySalary));
    }

    /**
     * 修改工资发放
     */
    @PreAuthorize("@ss.hasPermi('worksite:paySalary:edit')")
    @Log(title = "工资发放", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PaySalary paySalary)
    {
        return toAjax(paySalaryService.updatePaySalary(paySalary));
    }

    /**
     * 删除工资发放
     */
    @PreAuthorize("@ss.hasPermi('worksite:paySalary:remove')")
    @Log(title = "工资发放", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(paySalaryService.deletePaySalaryByIds(ids));
    }
}
