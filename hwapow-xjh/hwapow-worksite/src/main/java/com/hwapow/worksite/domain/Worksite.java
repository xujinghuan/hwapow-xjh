package com.hwapow.worksite.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 工地对象 worksite
 *
 * @author hwapow
 * @date 2021-08-11
 */
public class Worksite extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 工地名称 */
    @Excel(name = "工地名称")
    private String name;

    /**用于显示树名称**/
    private String label;

    /** 工地位置 */
    @Excel(name = "工地位置")
    private String address;

    /** 工地内容 */
    private String content;

    /** 工地单位（甲方） */
    @Excel(name = "工地单位", readConverterExp = "甲=方")
    private String serviceOrg;

    /** 开工时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开工时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 预计结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expectEndTime;

    /** 实际结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualEndTime;

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
    public void setName(String name)
    {
        this.name = name;
    }

    public String getLabel()
    {
        return name;
    }
    public String getName()
    {
        return name;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setServiceOrg(String serviceOrg)
    {
        this.serviceOrg = serviceOrg;
    }

    public String getServiceOrg()
    {
        return serviceOrg;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }
    public void setExpectEndTime(Date expectEndTime)
    {
        this.expectEndTime = expectEndTime;
    }

    public Date getExpectEndTime()
    {
        return expectEndTime;
    }
    public void setActualEndTime(Date actualEndTime)
    {
        this.actualEndTime = actualEndTime;
    }

    public Date getActualEndTime()
    {
        return actualEndTime;
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
            .append("name", getName())
            .append("address", getAddress())
            .append("content", getContent())
            .append("serviceOrg", getServiceOrg())
            .append("startTime", getStartTime())
            .append("expectEndTime", getExpectEndTime())
            .append("actualEndTime", getActualEndTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
