package com.hwapow.schedule.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 应办事情对象 to_do_thing
 *
 * @author xujinghuan
 * @date 2022-01-18
 */
public class ToDoThing extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 办理的事情 */
    @Excel(name = "办理的事情")
    private String toDoThing;

    /** 办理的时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "办理的时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date toDoTime;

    /** 关联的员工（入职员工使用），只有入职相关事宜才能使用 */
    @Excel(name = "关联的员工", readConverterExp = "入=职员工使用")
    private Long employeeId;

    /** 办理人 */
    @Excel(name = "办理人")
    private Long transactor;

    /** 完成情况 */
    private String doneThing;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date doneTime;

    /** 是否关闭（0：否，1：是） */
    @Excel(name = "是否关闭", readConverterExp = "0=：否，1：是")
    private String isClose;

    /** 是否完成（0：否，1：是） */
    @Excel(name = "是否完成", readConverterExp = "0=：否，1：是")
    private String isDone;

    /** 记录人（每个人只可以看到自己的内容） */
    private Long userId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setToDoThing(String toDoThing)
    {
        this.toDoThing = toDoThing;
    }

    public String getToDoThing()
    {
        return toDoThing;
    }
    public void setToDoTime(Date toDoTime)
    {
        this.toDoTime = toDoTime;
    }

    public Date getToDoTime()
    {
        return toDoTime;
    }
    public void setEmployeeId(Long employeeId)
    {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId()
    {
        return employeeId;
    }
    public void setTransactor(Long transactor)
    {
        this.transactor = transactor;
    }

    public Long getTransactor()
    {
        return transactor;
    }
    public void setDoneThing(String doneThing)
    {
        this.doneThing = doneThing;
    }

    public String getDoneThing()
    {
        return doneThing;
    }
    public void setDoneTime(Date doneTime)
    {
        this.doneTime = doneTime;
    }

    public Date getDoneTime()
    {
        return doneTime;
    }
    public void setIsClose(String isClose)
    {
        this.isClose = isClose;
    }

    public String getIsClose()
    {
        return isClose;
    }
    public void setIsDone(String isDone)
    {
        this.isDone = isDone;
    }

    public String getIsDone()
    {
        return isDone;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("toDoThing", getToDoThing())
            .append("toDoTime", getToDoTime())
            .append("employeeId", getEmployeeId())
            .append("transactor", getTransactor())
            .append("doneThing", getDoneThing())
            .append("doneTime", getDoneTime())
            .append("isClose", getIsClose())
            .append("isDone", getIsDone())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
