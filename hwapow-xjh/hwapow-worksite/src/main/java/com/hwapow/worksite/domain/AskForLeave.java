package com.hwapow.worksite.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 请假对象 ask_for_leave
 *
 * @author hwapow
 * @date 2021-08-11
 */
public class AskForLeave extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 工人id */
    @Excel(name = "工人id")
    private Long worker;

    /*为了显示*/
    private String workerName;

    /** 请假理由 */
    @Excel(name = "请假理由")
    private String reason;

    /** 请假工时 */
    @Excel(name = "请假工时")
    private BigDecimal leaveTime;

    /** 请假日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "请假日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaveDate;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

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
    public void setLeaveTime(BigDecimal leaveTime)
    {
        this.leaveTime = leaveTime;
    }

    public BigDecimal getLeaveTime()
    {
        return leaveTime;
    }
    public void setLeaveDate(Date leaveDate)
    {
        this.leaveDate = leaveDate;
    }

    public Date getLeaveDate()
    {
        return leaveDate;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("worker", getWorker())
            .append("reason", getReason())
            .append("leaveTime", getLeaveTime())
            .append("leaveDate", getLeaveDate())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
