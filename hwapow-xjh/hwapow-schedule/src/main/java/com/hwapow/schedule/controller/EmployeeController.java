package com.hwapow.schedule.controller;

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
import com.hwapow.schedule.domain.Employee;
import com.hwapow.schedule.service.IEmployeeService;
import com.hwapow.common.utils.poi.ExcelUtil;
import com.hwapow.common.core.page.TableDataInfo;

/**
 * 员工管理Controller
 *
 * @author xujinghuan
 * @date 2022-01-17
 */
@RestController
@RequestMapping("/schedule/employee")
public class EmployeeController extends BaseController
{
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 查询员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('schedule:employee:list')")
    @GetMapping("/list")
    public TableDataInfo list(Employee employee)
    {
        startPage();
        List<Employee> list = employeeService.selectEmployeeList(employee);
        return getDataTable(list);
    }

    /**
     * 导出员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('schedule:employee:export')")
    @Log(title = "员工管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Employee employee)
    {
        List<Employee> list = employeeService.selectEmployeeList(employee);
        ExcelUtil<Employee> util = new ExcelUtil<Employee>(Employee.class);
        return util.exportExcel(list, "employee");
    }

    /**
     * 获取员工管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('schedule:employee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(employeeService.selectEmployeeById(id));
    }

    /**
     * 新增员工管理
     */
    @PreAuthorize("@ss.hasPermi('schedule:employee:add')")
    @Log(title = "员工管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Employee employee)
    {
        if (UserConstants.NOT_UNIQUE.equals(employeeService.checkCodeOrIdCardUnique(employee)))
        {
            return AjaxResult.error("新增员工'" + employee.getName() + "'失败，编码或身份证号已存在！");
        }
        return toAjax(employeeService.insertEmployee(employee));
    }

    /**
     * 修改员工管理
     */
    @PreAuthorize("@ss.hasPermi('schedule:employee:edit')")
    @Log(title = "员工管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Employee employee)
    {
        if (UserConstants.NOT_UNIQUE.equals(employeeService.checkCodeOrIdCardUnique(employee)))
        {
            return AjaxResult.error("修改员工'" + employee.getName() + "'失败，编码或身份证号已存在！");
        }
        return toAjax(employeeService.updateEmployee(employee));
    }

    /**
     * 删除员工管理
     */
    @PreAuthorize("@ss.hasPermi('schedule:employee:remove')")
    @Log(title = "员工管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(employeeService.deleteEmployeeByIds(ids));
    }

    /**
     * 创建新员工入职事宜
     * @param employeeId
     * @return
     */
    @GetMapping(value = "/createEnteyThing/{employeeId}")
    public AjaxResult createEnteyThing(@PathVariable("employeeId") Long employeeId)
    {
        return AjaxResult.success(employeeService.createNewEmpEntryThing(employeeId));
    }
}
