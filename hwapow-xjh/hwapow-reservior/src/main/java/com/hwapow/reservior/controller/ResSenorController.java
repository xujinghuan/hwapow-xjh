package com.hwapow.reservior.controller;

import java.util.List;

import com.hwapow.common.constant.UserConstants;
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
import com.hwapow.reservior.domain.ResSenor;
import com.hwapow.reservior.service.IResSenorService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 设备管理Controller
 *
 * @author hwapow
 * @date 2021-09-20
 */
@RestController
@RequestMapping("/reservior/senor")
public class ResSenorController extends BaseController
{
    @Autowired
    private IResSenorService resSenorService;

    /**
     * 查询设备管理列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ResSenor resSenor)
    {
        startPage();
        List<ResSenor> list = resSenorService.selectResSenorList(resSenor);
        return getDataTable(list);
    }

    /**
     * 导出设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('reservior:senor:export')")
    @Log(title = "设备管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ResSenor resSenor)
    {
        List<ResSenor> list = resSenorService.selectResSenorList(resSenor);
        ExcelUtil<ResSenor> util = new ExcelUtil<ResSenor>(ResSenor.class);
        return util.exportExcel(list, "senor");
    }

    /**
     * 获取设备管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('reservior:senor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resSenorService.selectResSenorById(id));
    }

    /**
     * 新增设备管理
     */
    @PreAuthorize("@ss.hasPermi('reservior:senor:add')")
    @Log(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResSenor resSenor)
    {
        if (UserConstants.NOT_UNIQUE.equals(resSenorService.checkSenorUnique(resSenor))){
            return AjaxResult.error("该设备编码已存在，不可以重复添加！");
        }
        return toAjax(resSenorService.insertResSenor(resSenor));
    }

    /**
     * 修改设备管理
     */
    @PreAuthorize("@ss.hasPermi('reservior:senor:edit')")
    @Log(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResSenor resSenor)
    {
        if (UserConstants.NOT_UNIQUE.equals(resSenorService.checkSenorUnique(resSenor))){
            return AjaxResult.error("该设备编码已存在，不可以重复添加！");
        }
        return toAjax(resSenorService.updateResSenor(resSenor));
    }

    /**
     * 删除设备管理
     */
    @PreAuthorize("@ss.hasPermi('reservior:senor:remove')")
    @Log(title = "设备管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(resSenorService.deleteResSenorByIds(ids));
    }
}
