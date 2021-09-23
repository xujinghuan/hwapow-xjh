package com.hwapow.reservior.controller;

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
import com.hwapow.reservior.domain.ResMonitorData;
import com.hwapow.reservior.service.IResMonitorDataService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 传感器监测数据Controller
 *
 * @author hwapow
 * @date 2021-09-21
 */
@RestController
@RequestMapping("/reservior/data")
public class ResMonitorDataController extends BaseController
{
    @Autowired
    private IResMonitorDataService resMonitorDataService;

    /**
     * 查询传感器监测数据列表
     */
    @PreAuthorize("@ss.hasPermi('reservior:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResMonitorData resMonitorData)
    {
        startPage();
        List<ResMonitorData> list = resMonitorDataService.selectResMonitorDataList(resMonitorData);
        return getDataTable(list);
    }

    /**
     * 导出传感器监测数据列表
     */
    @PreAuthorize("@ss.hasPermi('reservior:data:export')")
    @Log(title = "传感器监测数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ResMonitorData resMonitorData)
    {
        List<ResMonitorData> list = resMonitorDataService.selectResMonitorDataList(resMonitorData);
        ExcelUtil<ResMonitorData> util = new ExcelUtil<ResMonitorData>(ResMonitorData.class);
        return util.exportExcel(list, "data");
    }

    /**
     * 获取传感器监测数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('reservior:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resMonitorDataService.selectResMonitorDataById(id));
    }

    /**
     * 新增传感器监测数据
     */
    @PreAuthorize("@ss.hasPermi('reservior:data:add')")
    @Log(title = "传感器监测数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResMonitorData resMonitorData)
    {
        return toAjax(resMonitorDataService.insertResMonitorData(resMonitorData));
    }

    /**
     * 修改传感器监测数据
     */
    @PreAuthorize("@ss.hasPermi('reservior:data:edit')")
    @Log(title = "传感器监测数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResMonitorData resMonitorData)
    {
        return toAjax(resMonitorDataService.updateResMonitorData(resMonitorData));
    }

    /**
     * 删除传感器监测数据
     */
    @PreAuthorize("@ss.hasPermi('reservior:data:remove')")
    @Log(title = "传感器监测数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(resMonitorDataService.deleteResMonitorDataByIds(ids));
    }
}
