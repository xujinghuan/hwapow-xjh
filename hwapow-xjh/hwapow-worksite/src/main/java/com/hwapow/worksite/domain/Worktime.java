package com.hwapow.worksite.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 工时记录对象 worktime
 *
 * @author hwapow
 * @date 2021-08-12
 */
public class Worktime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 工人id */
    @Excel(name = "工人id")
    private Long worker;

    /*为了显示*/
    private String workerName;

    /** 工地id */
    @Excel(name = "工地id")
    private Long worksite;

    /*为了显示*/
    private String worksiteName;

    /** 基本工资（日工资） */
    @Excel(name = "基本工资", readConverterExp = "日=工资")
    private BigDecimal basicWage;

    /** 工时 */
    @Excel(name = "工时")
    private BigDecimal taskTime;

    /** 工作内容 */
    @Excel(name = "工作内容")
    private String content;

    /** 工作日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "工作日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workDate;

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

    public Long getWorker()
    {
        return worker;
    }
    public void setWorksite(Long worksite)
    {
        this.worksite = worksite;
    }

    public Long getWorksite()
    {
        return worksite;
    }
    public void setBasicWage(BigDecimal basicWage)
    {
        this.basicWage = basicWage;
    }

    public BigDecimal getBasicWage()
    {
        return basicWage;
    }
    public void setTaskTime(BigDecimal taskTime)
    {
        this.taskTime = taskTime;
    }

    public BigDecimal getTaskTime()
    {
        return taskTime;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setWorkDate(Date workDate)
    {
        this.workDate = workDate;
    }

    public Date getWorkDate()
    {
        return workDate;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorksiteName() {
        return worksiteName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("worker", getWorker())
            .append("worksite", getWorksite())
            .append("basicWage", getBasicWage())
            .append("taskTime", getTaskTime())
            .append("content", getContent())
            .append("workDate", getWorkDate())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
