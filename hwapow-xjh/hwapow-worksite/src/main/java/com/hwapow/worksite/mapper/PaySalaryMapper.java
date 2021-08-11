package com.hwapow.worksite.mapper;

import java.util.List;
import com.hwapow.worksite.domain.PaySalary;

/**
 * 工资发放Mapper接口
 * 
 * @author hwapow
 * @date 2021-08-11
 */
public interface PaySalaryMapper 
{
    /**
     * 查询工资发放
     * 
     * @param id 工资发放ID
     * @return 工资发放
     */
    public PaySalary selectPaySalaryById(Long id);

    /**
     * 查询工资发放列表
     * 
     * @param paySalary 工资发放
     * @return 工资发放集合
     */
    public List<PaySalary> selectPaySalaryList(PaySalary paySalary);

    /**
     * 新增工资发放
     * 
     * @param paySalary 工资发放
     * @return 结果
     */
    public int insertPaySalary(PaySalary paySalary);

    /**
     * 修改工资发放
     * 
     * @param paySalary 工资发放
     * @return 结果
     */
    public int updatePaySalary(PaySalary paySalary);

    /**
     * 删除工资发放
     * 
     * @param id 工资发放ID
     * @return 结果
     */
    public int deletePaySalaryById(Long id);

    /**
     * 批量删除工资发放
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePaySalaryByIds(Long[] ids);
}
