package com.hwapow.schedule.service.impl;

import java.util.List;

import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.StringUtils;
import com.hwapow.schedule.domain.NewEmpToDo;
import com.hwapow.schedule.domain.ToDoThing;
import com.hwapow.schedule.service.INewEmpToDoService;
import com.hwapow.schedule.service.IToDoThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.schedule.mapper.EmployeeMapper;
import com.hwapow.schedule.domain.Employee;
import com.hwapow.schedule.service.IEmployeeService;

/**
 * 员工管理Service业务层处理
 *
 * @author xujinghuan
 * @date 2022-01-17
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService
{
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private IToDoThingService toDoThingService;

    @Autowired
    private INewEmpToDoService newEmpToDoService;

    /**
     * 查询员工管理
     *
     * @param id 员工管理ID
     * @return 员工管理
     */
    @Override
    public Employee selectEmployeeById(Long id)
    {
        return employeeMapper.selectEmployeeById(id);
    }

    /**
     * 查询员工管理列表
     *
     * @param employee 员工管理
     * @return 员工管理
     */
    @Override
    public List<Employee> selectEmployeeList(Employee employee)
    {
        return employeeMapper.selectEmployeeList(employee);
    }

    /**
     * 新增员工管理
     *
     * @param employee 员工管理
     * @return 结果
     */
    @Override
    public int insertEmployee(Employee employee)
    {
        employee.setCreateBy(SecurityUtils.getUsername());
        employee.setCreateTime(DateUtils.getNowDate());
        return employeeMapper.insertEmployee(employee);
    }

    /**
     * 修改员工管理
     *
     * @param employee 员工管理
     * @return 结果
     */
    @Override
    public int updateEmployee(Employee employee)
    {
        employee.setUpdateBy(SecurityUtils.getUsername());
        employee.setUpdateTime(DateUtils.getNowDate());
        return employeeMapper.updateEmployee(employee);
    }

    /**
     * 批量删除员工管理
     *
     * @param ids 需要删除的员工管理ID
     * @return 结果
     */
    @Override
    public int deleteEmployeeByIds(Long[] ids)
    {
        return employeeMapper.deleteEmployeeByIds(ids);
    }

    /**
     * 删除员工管理信息
     *
     * @param id 员工管理ID
     * @return 结果
     */
    @Override
    public int deleteEmployeeById(Long id)
    {
        return employeeMapper.deleteEmployeeById(id);
    }

    /**
     * 校验是否唯一
     *
     * @param employee 用户信息
     * @return
     */
    @Override
    public String checkCodeOrIdCardUnique(Employee employee)
    {
        Long employeeId = StringUtils.isNull(employee.getId()) ? -1L : employee.getId();
        Employee info = employeeMapper.checkCodeOrIdCardUnique(employee.getCode(),employee.getIdCard());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != employeeId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 生成员工入职事宜
     * @param employeeId
     * @return
     */
    @Override
    public int createNewEmpEntryThing(Long employeeId){
        Employee employee=this.selectEmployeeById(employeeId);
        if(employee==null){
            return 0;
        }
        //删除原先的事情
        this.toDoThingService.deleteToDoThingByEmployeeId(employeeId);
        //查询需要用到的入职事宜
        List<NewEmpToDo> listNewTodo=this.newEmpToDoService.selectNewEmpToDoList(null);
        for(NewEmpToDo item:listNewTodo){
            ToDoThing toDoThing=new ToDoThing();
            toDoThing.setEmployeeId(employeeId);
            toDoThing.setToDoTime(DateUtils.addDays(employee.getEntryDate(),item.getEntryDayNum().intValue()));
            toDoThing.setToDoThing(employee.getName()+"-"+item.getToDoThing());
            this.toDoThingService.insertToDoThing(toDoThing);
        }
        return listNewTodo.size();
    }
}
