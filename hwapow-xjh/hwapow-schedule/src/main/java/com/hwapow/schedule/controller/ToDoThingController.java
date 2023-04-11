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
import com.hwapow.schedule.domain.ToDoThing;
import com.hwapow.schedule.service.IToDoThingService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 应办事情Controller
 *
 * @author xujinghuan
 * @date 2022-01-18
 */
@RestController
@RequestMapping("/schedule/toDoThing")
public class ToDoThingController extends BaseController
{
    @Autowired
    private IToDoThingService toDoThingService;

    /**
     * 查询应办事情列表
     */
    @PreAuthorize("@ss.hasPermi('schedule:toDoThing:list')")
    @GetMapping("/list")
    public TableDataInfo list(ToDoThing toDoThing)
    {
        startPage();
        List<ToDoThing> list = toDoThingService.selectToDoThingList(toDoThing);
        return getDataTable(list);
    }

    /**
     * 导出应办事情列表
     */
    @PreAuthorize("@ss.hasPermi('schedule:toDoThing:export')")
    @Log(title = "应办事情", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ToDoThing toDoThing)
    {
        List<ToDoThing> list = toDoThingService.selectToDoThingList(toDoThing);
        ExcelUtil<ToDoThing> util = new ExcelUtil<ToDoThing>(ToDoThing.class);
        return util.exportExcel(list, "toDoThing");
    }

    /**
     * 获取应办事情详细信息
     */
    @PreAuthorize("@ss.hasPermi('schedule:toDoThing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(toDoThingService.selectToDoThingById(id));
    }

    /**
     * 新增应办事情
     */
    @PreAuthorize("@ss.hasPermi('schedule:toDoThing:add')")
    @Log(title = "应办事情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ToDoThing toDoThing)
    {
        return toAjax(toDoThingService.insertToDoThing(toDoThing));
    }

    /**
     * 修改应办事情
     */
    @PreAuthorize("@ss.hasPermi('schedule:toDoThing:edit')")
    @Log(title = "应办事情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ToDoThing toDoThing)
    {
        return toAjax(toDoThingService.updateToDoThing(toDoThing));
    }

    /**
     * 删除应办事情
     */
    @PreAuthorize("@ss.hasPermi('schedule:toDoThing:remove')")
    @Log(title = "应办事情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(toDoThingService.deleteToDoThingByIds(ids));
    }

}
