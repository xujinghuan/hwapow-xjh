package com.hwapow.reservior.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 传感器监测数据对象 res_monitor_data
 *
 * @author hwapow
 * @date 2021-09-21
 */
public class ResMonitorData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 传感器设备id */
    @Excel(name = "传感器设备id")
    private Long senorId;

    /** 数据获取时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据获取时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date getTime;

    /** 返回指令 */
    @Excel(name = "返回指令")
    private String backInstruction;

    /** 原始数据 */
    @Excel(name = "原始数据")
    private String rawData;

    /** 数据 */
    @Excel(name = "数据")
    private String data;

    /** 断面 */
    @Excel(name = "断面")
    private Long sectionId;

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
    public void setSenorId(Long senorId)
    {
        this.senorId = senorId;
    }

    public Long getSenorId()
    {
        return senorId;
    }
    public void setGetTime(Date getTime)
    {
        this.getTime = getTime;
    }

    public Date getGetTime()
    {
        return getTime;
    }
    public void setBackInstruction(String backInstruction)
    {
        this.backInstruction = backInstruction;
    }

    public String getBackInstruction()
    {
        return backInstruction;
    }
    public void setRawData(String rawData)
    {
        this.rawData = rawData;
    }

    public String getRawData()
    {
        return rawData;
    }
    public void setData(String data)
    {
        this.data = data;
    }

    public String getData()
    {
        return data;
    }
    public void setSectionId(Long sectionId)
    {
        this.sectionId = sectionId;
    }

    public Long getSectionId()
    {
        return sectionId;
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
            .append("senorId", getSenorId())
            .append("getTime", getGetTime())
            .append("backInstruction", getBackInstruction())
            .append("rawData", getRawData())
            .append("data", getData())
            .append("sectionId", getSectionId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
