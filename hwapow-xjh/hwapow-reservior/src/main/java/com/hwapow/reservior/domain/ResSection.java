package com.hwapow.reservior.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 水库断面对象 res_section
 *
 * @author hwapow
 * @date 2021-09-20
 */
public class ResSection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 断面编码 */
    @Excel(name = "断面编码")
    private String code;

    /** 断面名称 */
    @Excel(name = "断面名称")
    private String name;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 组织id（主要是水库的id，水库存在组织中，不同水库用户只可以看到自己水库的断面数据） */
    @Excel(name = "组织id")
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
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getSort()
    {
        return sort;
    }
    public void setOrgId(Long orgId)
    {
        this.orgId = orgId;
    }

    public Long getOrgId()
    {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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
            .append("code", getCode())
            .append("name", getName())
            .append("sort", getSort())
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
