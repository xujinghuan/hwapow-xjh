package com.hwapow.worksite.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 工资发放对象 pay_salary
 *
 * @author hwapow
 * @date 2021-08-11
 */
public class PaySalary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 工人 */
    @Excel(name = "工人")
    private Long worker;

    /*为了显示*/
    private String workerName;

    /*为了显示*/
    private String payWayName;

    /** 理由 */
    @Excel(name = "理由")
    private String reason;

    /** 工资 */
    @Excel(name = "工资")
    private BigDecimal salary;

    /** 发放日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发放日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payDate;

    /**工资发放路径*/
    private String payWay;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setWorker(Long worker)
    {
        this.worker = worker;
    }
    public String getWorkerName() {
        return workerName;
    }

    public Long getWorker()
    {
        return worker;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return reason;
    }
    public void setSalary(BigDecimal salary)
    {
        this.salary = salary;
    }

    public BigDecimal getSalary()
    {
        return salary;
    }
    public void setPayDate(Date payDate)
    {
        this.payDate = payDate;
    }

    public Date getPayDate()
    {
        return payDate;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public String getPayWay() {
        return payWay;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("worker", getWorker())
            .append("reason", getReason())
            .append("salary", getSalary())
            .append("payDate", getPayDate())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
