package com.hwapow.schedule.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 员工管理对象 employee
 *
 * @author xujinghuan
 * @date 2022-01-17
 */
public class Employee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 员工编码 */
    @Excel(name = "员工编码")
    private String code;

    /** 员工名称 */
    @Excel(name = "员工名称")
    private String name;

    /** 所属部门 */
    @Excel(name = "所属部门")
    private Long orgId;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCard;

    /** 入职日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryDate;

    /** 员工生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "员工生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 身份证有效截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "身份证有效截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cardExpiryDate;

    /** 状态（1：正常，2：停用） */
    @Excel(name = "状态", readConverterExp = "1=：正常，2：停用")
    private String status;

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
    public void setOrgId(Long orgId)
    {
        this.orgId = orgId;
    }

    public Long getOrgId()
    {
        return orgId;
    }
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getIdCard()
    {
        return idCard;
    }
    public void setEntryDate(Date entryDate)
    {
        this.entryDate = entryDate;
    }

    public Date getEntryDate()
    {
        return entryDate;
    }
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Date getBirthday()
    {
        return birthday;
    }
    public void setCardExpiryDate(Date cardExpiryDate)
    {
        this.cardExpiryDate = cardExpiryDate;
    }

    public Date getCardExpiryDate()
    {
        return cardExpiryDate;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("orgId", getOrgId())
            .append("idCard", getIdCard())
            .append("entryDate", getEntryDate())
            .append("birthday", getBirthday())
            .append("cardExpiryDate", getCardExpiryDate())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
