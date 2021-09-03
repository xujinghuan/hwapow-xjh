package com.hwapow.worksite.controller;

import java.util.List;

import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.domain.entity.SysOrg;
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
import com.hwapow.worksite.domain.Worker;
import com.hwapow.worksite.service.IWorkerService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 工人Controller
 *
 * @author hwapow
 * @date 2021-08-11
 */
@RestController
@RequestMapping("/worksite/worker")
public class WorkerController extends BaseController
{
    @Autowired
    private IWorkerService workerService;

    /**
     * 查询工人列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:worker:list')")
    @GetMapping("/list")
    public TableDataInfo list(Worker worker)
    {
        startPage();
        List<Worker> list = workerService.selectWorkerList(worker);
        return getDataTable(list);
    }

    /**
     * 导出工人列表
     */
    @PreAuthorize("@ss.hasPermi('worksite:worker:export')")
    @Log(title = "工人", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Worker worker)
    {
        List<Worker> list = workerService.selectWorkerList(worker);
        ExcelUtil<Worker> util = new ExcelUtil<Worker>(Worker.class);
        return util.exportExcel(list, "worker");
    }

    /**
     * 获取工人详细信息
     */
    @PreAuthorize("@ss.hasPermi('worksite:worker:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(workerService.selectWorkerById(id));
    }

    /**
     * 新增工人
     */
    @PreAuthorize("@ss.hasPermi('worksite:worker:add')")
    @Log(title = "工人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Worker worker)
    {
        if (UserConstants.NOT_UNIQUE.equals(workerService.checkWorkerUnique(worker)))
        {
            return AjaxResult.error("新增工人'" + worker.getName() + "'失败，工人名称或身份证号已存在，请核实后保存");
        }
        return toAjax(workerService.insertWorker(worker));
    }

    /**
     * 修改工人
     */
    @PreAuthorize("@ss.hasPermi('worksite:worker:edit')")
    @Log(title = "工人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Worker worker)
    {
        if (UserConstants.NOT_UNIQUE.equals(workerService.checkWorkerUnique(worker)))
        {
            return AjaxResult.error("修改工人'" + worker.getName() + "'失败，工人名称或身份证号已存在，请核实后保存");
        }
        return toAjax(workerService.updateWorker(worker));
    }

    /**
     * 修改工人状态
     */
    @PreAuthorize("@ss.hasPermi('worksite:worker:edit')")
    @Log(title = "工人", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody Worker worker)
    {
        return toAjax(workerService.updateWorkerStatus(worker.getStatus(),worker.getId()));
    }

    /**
     * 获取工人下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(Worker worker)
    {
        List<Worker> workers = workerService.selectWorkerList(worker);
        return AjaxResult.success(workers);
    }

    /**
     * 删除工人
     */
    @PreAuthorize("@ss.hasPermi('worksite:worker:remove')")
    @Log(title = "工人", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(workerService.deleteWorkerByIds(ids));
    }
}
