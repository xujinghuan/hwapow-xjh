package com.hwapow.reservior.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 库容，主要管理水库水位线对应的库容值，方便取值对象 res_capacity
 *
 * @author hwapow
 * @date 2021-09-18
 */
public class ResCapacity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 水位对应的库容（立方米） */
    @Excel(name = "库容（立方米）")
    private Double capacity;

    /** 水位(米) */
    @Excel(name = "水位（米）")
    private Double waterLevel;

    /** 对应水库id */
    @Excel(name = "对应水库id")
    private Long orgId;

    /**
     * 用于显示水库名称
     */
    private String orgName;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setCapacity(Double capacity)
    {
        this.capacity = capacity;
    }

    public Double getCapacity()
    {
        return capacity;
    }
    public void setWaterLevel(Double waterLevel)
    {
        this.waterLevel = waterLevel;
    }

    public Double getWaterLevel()
    {
        return waterLevel;
    }
    public void setOrgId(Long orgId)
    {
        this.orgId = orgId;
    }

    public Long getOrgId()
    {
        return orgId;
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
            .append("capacity", getCapacity())
            .append("waterLevel", getWaterLevel())
            .append("orgId", getOrgId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
