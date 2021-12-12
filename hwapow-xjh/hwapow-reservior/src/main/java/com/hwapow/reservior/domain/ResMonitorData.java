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
    private Long senorId;

    /**
     * 设备断面名称，不保存，只显示
     */
    @Excel(name = "断面")
    private String sectionName;

    /**
     * 设备名称，不保存，只显示
     */
    @Excel(name = "设备")
    private String senorName;

    /**
     * 报警水位最大值,不保存，只显示
     */
    private Double warnMax;

    /**
     * 报警水位最小值,不保存，只显示
     */
    private Double warnMin;

    private String senorType;

    /**
     * 设备组织名称，不保存，只显示
     */
    private String orgName;

    /**
     * 返回数据单位，不保存，只显示
     */
    private String backDataUnit;

    /** 数据获取时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "数据获取时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date getTime;

    /** 返回指令 */
    private String backInstruction;

    /** 原始数据 */
    private String rawData;

    /** 数据 */
    @Excel(name = "水位（米）")
    private String data;

    /**
     * 库容
     */
    private Double capacity;

    /** 断面 */
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

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public String getSenorName() {
        return senorName;
    }

    public void setSenorName(String senorName) {
        this.senorName = senorName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getBackDataUnit() {
        return backDataUnit;
    }

    public void setBackDataUnit(String backDataUnit) {
        this.backDataUnit = backDataUnit;
    }

    public String getSenorType() {
        return senorType;
    }

    public void setSenorType(String senorType) {
        this.senorType = senorType;
    }

    public Double getWarnMax() {
        return warnMax;
    }

    public void setWarnMax(Double warnMax) {
        this.warnMax = warnMax;
    }

    public Double getWarnMin() {
        return warnMin;
    }

    public void setWarnMin(Double warnMin) {
        this.warnMin = warnMin;
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
