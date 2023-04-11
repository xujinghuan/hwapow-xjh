package com.hwapow.schedule.service;

import java.util.List;
import com.hwapow.schedule.domain.Employee;

/**
 * 员工管理Service接口
 * 
 * @author xujinghuan
 * @date 2022-01-17
 */
public interface IEmployeeService 
{
    /**
     * 查询员工管理
     * 
     * @param id 员工管理ID
     * @return 员工管理
     */
    public Employee selectEmployeeById(Long id);

    /**
     * 查询员工管理列表
     * 
     * @param employee 员工管理
     * @return 员工管理集合
     */
    public List<Employee> selectEmployeeList(Employee employee);

    /**
     * 新增员工管理
     * 
     * @param employee 员工管理
     * @return 结果
     */
    public int insertEmployee(Employee employee);

    /**
     * 修改员工管理
     * 
     * @param employee 员工管理
     * @return 结果
     */
    public int updateEmployee(Employee employee);

    /**
     * 批量删除员工管理
     * 
     * @param ids 需要删除的员工管理ID
     * @return 结果
     */
    public int deleteEmployeeByIds(Long[] ids);

    /**
     * 删除员工管理信息
     * 
     * @param id 员工管理ID
     * @return 结果
     */
    public int deleteEmployeeById(Long id);

    public String checkCodeOrIdCardUnique(Employee employee);

    /**
     * 生成员工入职事宜
     * @param employeeId
     * @return
     */
    public int createNewEmpEntryThing(Long employeeId);
}
