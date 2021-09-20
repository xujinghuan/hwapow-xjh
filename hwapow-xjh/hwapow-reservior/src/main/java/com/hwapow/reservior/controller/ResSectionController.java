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
import com.hwapow.reservior.domain.ResSection;
import com.hwapow.reservior.service.IResSectionService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 水库断面Controller
 *
 * @author hwapow
 * @date 2021-09-20
 */
@RestController
@RequestMapping("/reservior/section")
public class ResSectionController extends BaseController
{
    @Autowired
    private IResSectionService resSectionService;

    /**
     * 查询水库断面列表
     */
    @PreAuthorize("@ss.hasPermi('reservior:section:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResSection resSection)
    {
        startPage();
        List<ResSection> list = resSectionService.selectResSectionList(resSection);
        return getDataTable(list);
    }

    /**
     * 导出水库断面列表
     */
    @PreAuthorize("@ss.hasPermi('reservior:section:export')")
    @Log(title = "水库断面", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ResSection resSection)
    {
        List<ResSection> list = resSectionService.selectResSectionList(resSection);
        ExcelUtil<ResSection> util = new ExcelUtil<ResSection>(ResSection.class);
        return util.exportExcel(list, "section");
    }

    /**
     * 获取水库断面详细信息
     */
    @PreAuthorize("@ss.hasPermi('reservior:section:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(resSectionService.selectResSectionById(id));
    }

    /**
     * 新增水库断面
     */
    @PreAuthorize("@ss.hasPermi('reservior:section:add')")
    @Log(title = "水库断面", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResSection resSection)
    {
        if (UserConstants.NOT_UNIQUE.equals(resSectionService.checkSectionUnique(resSection))){
            return AjaxResult.error("该断面编码已存在，不可以重复添加！");
        }
        return toAjax(resSectionService.insertResSection(resSection));
    }

    /**
     * 修改水库断面
     */
    @PreAuthorize("@ss.hasPermi('reservior:section:edit')")
    @Log(title = "水库断面", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResSection resSection)
    {
        if (UserConstants.NOT_UNIQUE.equals(resSectionService.checkSectionUnique(resSection))){
            return AjaxResult.error("该断面编码已存在，不可以重复添加！");
        }
        return toAjax(resSectionService.updateResSection(resSection));
    }

    /**
     * 删除水库断面
     */
    @PreAuthorize("@ss.hasPermi('reservior:section:remove')")
    @Log(title = "水库断面", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(resSectionService.deleteResSectionByIds(ids));
    }
}
