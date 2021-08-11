package com.hwapow.worksite.service.impl;

import java.util.List;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.worksite.mapper.PaySalaryMapper;
import com.hwapow.worksite.domain.PaySalary;
import com.hwapow.worksite.service.IPaySalaryService;

/**
 * 工资发放Service业务层处理
 *
 * @author hwapow
 * @date 2021-08-11
 */
@Service
public class PaySalaryServiceImpl implements IPaySalaryService
{
    @Autowired
    private PaySalaryMapper paySalaryMapper;

    /**
     * 查询工资发放
     *
     * @param id 工资发放ID
     * @return 工资发放
     */
    @Override
    public PaySalary selectPaySalaryById(Long id)
    {
        return paySalaryMapper.selectPaySalaryById(id);
    }

    /**
     * 查询工资发放列表
     *
     * @param paySalary 工资发放
     * @return 工资发放
     */
    @Override
    public List<PaySalary> selectPaySalaryList(PaySalary paySalary)
    {
        return paySalaryMapper.selectPaySalaryList(paySalary);
    }

    /**
     * 新增工资发放
     *
     * @param paySalary 工资发放
     * @return 结果
     */
    @Override
    public int insertPaySalary(PaySalary paySalary)
    {
                                    paySalary.setCreateBy(SecurityUtils.getUsername());
            paySalary.setCreateTime(DateUtils.getNowDate());
                        return paySalaryMapper.insertPaySalary(paySalary);
    }

    /**
     * 修改工资发放
     *
     * @param paySalary 工资发放
     * @return 结果
     */
    @Override
    public int updatePaySalary(PaySalary paySalary)
    {
    paySalary.setUpdateBy(SecurityUtils.getUsername());
        paySalary.setUpdateTime(DateUtils.getNowDate());
        return paySalaryMapper.updatePaySalary(paySalary);
    }

    /**
     * 批量删除工资发放
     *
     * @param ids 需要删除的工资发放ID
     * @return 结果
     */
    @Override
    public int deletePaySalaryByIds(Long[] ids)
    {
        return paySalaryMapper.deletePaySalaryByIds(ids);
    }

    /**
     * 删除工资发放信息
     *
     * @param id 工资发放ID
     * @return 结果
     */
    @Override
    public int deletePaySalaryById(Long id)
    {
        return paySalaryMapper.deletePaySalaryById(id);
    }
}
