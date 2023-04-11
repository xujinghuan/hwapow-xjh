package com.hwapow.schedule.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 新员工入职事宜对象 new_emp_to_do
 *
 * @author xujinghuan
 * @date 2022-01-18
 */
public class NewEmpToDo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 入职后几天办理 */
    @Excel(name = "入职后几天办理")
    private Long entryDayNum;

    /** 办理的事情 */
    @Excel(name = "办理的事情")
    private String toDoThing;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setEntryDayNum(Long entryDayNum)
    {
        this.entryDayNum = entryDayNum;
    }

    public Long getEntryDayNum()
    {
        return entryDayNum;
    }
    public void setToDoThing(String toDoThing)
    {
        this.toDoThing = toDoThing;
    }

    public String getToDoThing()
    {
        return toDoThing;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("entryDayNum", getEntryDayNum())
            .append("toDoThing", getToDoThing())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
