package com.hwapow.schedule.mapper;

import java.util.List;
import com.hwapow.schedule.domain.Employee;
import org.apache.ibatis.annotations.Param;

/**
 * 员工管理Mapper接口
 * 
 * @author xujinghuan
 * @date 2022-01-17
 */
public interface EmployeeMapper 
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
     * 删除员工管理
     * 
     * @param id 员工管理ID
     * @return 结果
     */
    public int deleteEmployeeById(Long id);

    /**
     * 批量删除员工管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEmployeeByIds(Long[] ids);

    /**
     * 检查员工编码和身份证号是否唯一
     * @param code
     * @param idCard
     * @return
     */
    public Employee checkCodeOrIdCardUnique(@Param("code") String code,@Param("idCard")  String idCard);
}
