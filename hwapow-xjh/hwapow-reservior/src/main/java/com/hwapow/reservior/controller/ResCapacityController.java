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
import com.hwapow.reservior.domain.ResCapacity;
import com.hwapow.reservior.service.IResCapacityService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 库容管理Controller
 *
 * @author hwapow
 * @date 2021-09-18
 */
@RestController
@RequestMapping("/reservior/capacity")
public class ResCapacityController extends BaseController
{
    @Autowired
    private IResCapacityService resCapacityService;

    /**
     * 查询库容管理列表
     */
    @PreAuthorize("@ss.hasPermi('reservior:capacity:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResCapacity resCapacity)
    {
        startPage();
        List<ResCapacity> list = resCapacityService.selectResCapacityList(resCapacity);
        return getDataTable(list);
    }

    /**
     * 导出库容管理列表
     */
    @PreAuthorize("@ss.hasPermi('reservior:capacity:export')")
    @Log(title = "库容管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ResCapacity resCapacity)
    {
        List<ResCapacity> list = resCapacityService.selectResCapacityList(resCapacity);
        ExcelUtil<ResCapacity> util = new ExcelUtil<ResCapacity>(ResCapacity.class);
        return util.exportExcel(list, "capacity");
    }

    /**
     * 获取库容管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('reservior:capacity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resCapacityService.selectResCapacityById(id));
    }

    /**
     * 新增库容管理
     */
    @PreAuthorize("@ss.hasPermi('reservior:capacity:add')")
    @Log(title = "库容管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResCapacity resCapacity)
    {
        if (UserConstants.NOT_UNIQUE.equals(resCapacityService.checkCapacityUnique(resCapacity))){
            return AjaxResult.error("该水库水位"+resCapacity.getWaterLevel()+"对应的库容已存在，不可以重复添加！");
        }
        return toAjax(resCapacityService.insertResCapacity(resCapacity));
    }

    /**
     * 修改库容管理
     */
    @PreAuthorize("@ss.hasPermi('reservior:capacity:edit')")
    @Log(title = "库容管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResCapacity resCapacity)
    {
        if (UserConstants.NOT_UNIQUE.equals(resCapacityService.checkCapacityUnique(resCapacity))){
            return AjaxResult.error("该水库水位"+resCapacity.getWaterLevel()+"对应的库容已存在，不可以重复添加！");
        }
        return toAjax(resCapacityService.updateResCapacity(resCapacity));
    }

    /**
     * 删除库容管理
     */
    @PreAuthorize("@ss.hasPermi('reservior:capacity:remove')")
    @Log(title = "库容管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(resCapacityService.deleteResCapacityByIds(ids));
    }
}
