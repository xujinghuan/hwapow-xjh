package com.hwapow.taxProcess.controller;

import java.util.List;

import com.hwapow.taxProcess.utils.ExportDocUtil;
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
import com.hwapow.taxProcess.domain.TaxBase;
import com.hwapow.taxProcess.service.ITaxBaseService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 基础信息表Controller
 *
 * @author hwapow
 * @date 2023-04-17
 */
@RestController
@RequestMapping("/taxProcess/base")
public class TaxBaseController extends BaseController {
    @Autowired
    private ITaxBaseService taxBaseService;

    /**
     * 查询基础信息表列表
     */
    @PreAuthorize("@ss.hasPermi('taxProcess:base:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaxBase taxBase) {
        startPage();
        List<TaxBase> list = taxBaseService.selectTaxBaseList(taxBase);
        return getDataTable(list);
    }

    /**
     * 导出基础信息表列表
     */
    @PreAuthorize("@ss.hasPermi('taxProcess:base:export')")
    @Log(title = "基础信息表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TaxBase taxBase) {
        List<TaxBase> list = taxBaseService.selectTaxBaseList(taxBase);
        ExcelUtil<TaxBase> util = new ExcelUtil<TaxBase>(TaxBase.class);
        return util.exportExcel(list, "base");
    }

    /**
     * 获取基础信息表详细信息
     */
    @PreAuthorize("@ss.hasPermi('taxProcess:base:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(taxBaseService.selectTaxBaseById(id));
    }

    /**
     * 新增基础信息表
     */
    @PreAuthorize("@ss.hasPermi('taxProcess:base:add')")
    @Log(title = "基础信息表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaxBase taxBase) {
        return toAjax(taxBaseService.insertTaxBase(taxBase));
    }

    /**
     * 修改基础信息表
     */
    @PreAuthorize("@ss.hasPermi('taxProcess:base:edit')")
    @Log(title = "基础信息表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaxBase taxBase) {
        return toAjax(taxBaseService.updateTaxBase(taxBase));
    }

    /**
     * 删除基础信息表
     */
    @PreAuthorize("@ss.hasPermi('taxProcess:base:remove')")
    @Log(title = "基础信息表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(taxBaseService.deleteTaxBaseByIds(ids));
    }


    /**
     * 下载分析报告
     */
    @GetMapping(value = "/downloadFxbg/{id}")
    public AjaxResult downloadFxbg(@PathVariable("id") Long id) throws Exception {
        if (id == null) {
            throw new Exception("上传参数有误");
        }
        TaxBase taxBase = this.taxBaseService.selectTaxBaseById(id);
        System.out.println("正在下载："+taxBase.getColB()+"-"+taxBase.getColA());
        ExportDocUtil util = new ExportDocUtil();
        AjaxResult a=util.exportFxbg(taxBase);
        this.taxBaseService.updateTaxBase(taxBase);
        return a;
    }
}
