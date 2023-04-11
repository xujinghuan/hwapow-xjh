package com.hwapow.schedule.controller;

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
import com.hwapow.schedule.domain.NewEmpToDo;
import com.hwapow.schedule.service.INewEmpToDoService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 新员工入职事宜Controller
 *
 * @author xujinghuan
 * @date 2022-01-18
 */
@RestController
@RequestMapping("/schedule/newEmpToDo")
public class NewEmpToDoController extends BaseController
{
    @Autowired
    private INewEmpToDoService newEmpToDoService;

    /**
     * 查询新员工入职事宜列表
     */
    @PreAuthorize("@ss.hasPermi('schedule:newEmpToDo:list')")
    @GetMapping("/list")
    public TableDataInfo list(NewEmpToDo newEmpToDo)
    {
        startPage();
        List<NewEmpToDo> list = newEmpToDoService.selectNewEmpToDoList(newEmpToDo);
        return getDataTable(list);
    }

    /**
     * 导出新员工入职事宜列表
     */
    @PreAuthorize("@ss.hasPermi('schedule:newEmpToDo:export')")
    @Log(title = "新员工入职事宜", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(NewEmpToDo newEmpToDo)
    {
        List<NewEmpToDo> list = newEmpToDoService.selectNewEmpToDoList(newEmpToDo);
        ExcelUtil<NewEmpToDo> util = new ExcelUtil<NewEmpToDo>(NewEmpToDo.class);
        return util.exportExcel(list, "newEmpToDo");
    }

    /**
     * 获取新员工入职事宜详细信息
     */
    @PreAuthorize("@ss.hasPermi('schedule:newEmpToDo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(newEmpToDoService.selectNewEmpToDoById(id));
    }

    /**
     * 新增新员工入职事宜
     */
    @PreAuthorize("@ss.hasPermi('schedule:newEmpToDo:add')")
    @Log(title = "新员工入职事宜", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewEmpToDo newEmpToDo)
    {
        return toAjax(newEmpToDoService.insertNewEmpToDo(newEmpToDo));
    }

    /**
     * 修改新员工入职事宜
     */
    @PreAuthorize("@ss.hasPermi('schedule:newEmpToDo:edit')")
    @Log(title = "新员工入职事宜", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewEmpToDo newEmpToDo)
    {
        return toAjax(newEmpToDoService.updateNewEmpToDo(newEmpToDo));
    }

    /**
     * 删除新员工入职事宜
     */
    @PreAuthorize("@ss.hasPermi('schedule:newEmpToDo:remove')")
    @Log(title = "新员工入职事宜", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(newEmpToDoService.deleteNewEmpToDoByIds(ids));
    }
}
