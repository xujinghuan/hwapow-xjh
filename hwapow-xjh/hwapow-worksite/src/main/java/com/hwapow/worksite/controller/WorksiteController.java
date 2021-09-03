package com.hwapow.worksite.controller;

import java.util.List;

import com.hwapow.common.constant.UserConstants;
import com.hwapow.worksite.domain.Worker;
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
import com.hwapow.worksite.domain.Worksite;
import com.hwapow.worksite.service.IWorksiteService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 工地Controller
 *
 * @author hwapow
 * @date 2021-08-11
 */
@RestController
@RequestMapping("/worksite/worksite")
public class WorksiteController extends BaseController
{
    @Autowired
    private IWorksiteService worksiteService;

    /**
     * 查询工地列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:worksite:list')")
    @GetMapping("/list")
    public TableDataInfo list(Worksite worksite)
    {
        startPage();
        List<Worksite> list = worksiteService.selectWorksiteList(worksite);
        return getDataTable(list);
    }

    /**
     * 导出工地列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:worksite:export')")
    @Log(title = "工地", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Worksite worksite)
    {
        List<Worksite> list = worksiteService.selectWorksiteList(worksite);
        ExcelUtil<Worksite> util = new ExcelUtil<Worksite>(Worksite.class);
        return util.exportExcel(list, "worksite");
    }

    /**
     * 获取工地详细信息
     */
    @PreAuthorize("@ss.hasPermi('worksite:worksite:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(worksiteService.selectWorksiteById(id));
    }

    /**
     * 新增工地
     */
    @PreAuthorize("@ss.hasPermi('worksite:worksite:add')")
    @Log(title = "工地", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Worksite worksite)
    {
        if (UserConstants.NOT_UNIQUE.equals(worksiteService.checkWorksiteUnique(worksite)))
        {
            return AjaxResult.error("新增工地'" + worksite.getName() + "'失败，工地名称已存在，请核实后保存");
        }
        return toAjax(worksiteService.insertWorksite(worksite));
    }

    /**
     * 修改工地
     */
    @PreAuthorize("@ss.hasPermi('worksite:worksite:edit')")
    @Log(title = "工地", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Worksite worksite)
    {
        if (UserConstants.NOT_UNIQUE.equals(worksiteService.checkWorksiteUnique(worksite)))
        {
            return AjaxResult.error("修改工地'" + worksite.getName() + "'失败，工地名称已存在，请核实后保存");
        }
        return toAjax(worksiteService.updateWorksite(worksite));
    }

    /**
     * 修改工地状态
     */
    @PreAuthorize("@ss.hasPermi('worksite:worksite:edit')")
    @Log(title = "工地", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody Worksite worksite)
    {
        return toAjax(worksiteService.updateWorksiteStatus(worksite.getStatus(),worksite.getId()));
    }

    /**
     * 获取工地下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(Worksite worksite)
    {
        List<Worksite> worksites = worksiteService.selectWorksiteList(worksite);
        return AjaxResult.success(worksites);
    }

    /**
     * 删除工地
     */
    @PreAuthorize("@ss.hasPermi('worksite:worksite:remove')")
    @Log(title = "工地", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(worksiteService.deleteWorksiteByIds(ids));
    }
}
