package com.hwapow.worksite.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 工人对象 worker
 *
 * @author hwapow
 * @date 2021-08-11
 */
public class Worker extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 工人名称 */
    @Excel(name = "工人名称")
    private String name;

    /**用于显示树名称**/
    private String label;

    /** 工人昵称 */
    @Excel(name = "工人昵称")
    private String nickname;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idcard;

    /** 电话号码 */
    @Excel(name = "电话号码")
    private String phone;

    /** 性别 */
    @Excel(name = "性别")
    private Long sex;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 住址 */
    private String address;

    /** 籍贯 */
    private String nativePlace;

    /** 工种 */
    @Excel(name = "工种")
    private Long workType;

    /** 银行账户名称 */
    private String accountName;

    /** 银行账户 */
    private String account;

    /** 开户行 */
    private String bankName;

    /** 状态（0：启用，1：停用） */
    @Excel(name = "状态", readConverterExp = "0：启用，1：停用")
    private String status;

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
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }
    public void setIdcard(String idcard)
    {
        this.idcard = idcard;
    }

    public String getIdcard()
    {
        return idcard;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setSex(Long sex)
    {
        this.sex = sex;
    }

    public Long getSex()
    {
        return sex;
    }
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Date getBirthday()
    {
        return birthday;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setNativePlace(String nativePlace)
    {
        this.nativePlace = nativePlace;
    }

    public String getNativePlace()
    {
        return nativePlace;
    }
    public void setWorkType(Long workType)
    {
        this.workType = workType;
    }

    public Long getWorkType()
    {
        return workType;
    }
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getAccountName()
    {
        return accountName;
    }
    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getAccount()
    {
        return account;
    }
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBankName()
    {
        return bankName;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
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
            .append("nickname", getNickname())
            .append("idcard", getIdcard())
            .append("phone", getPhone())
            .append("sex", getSex())
            .append("birthday", getBirthday())
            .append("address", getAddress())
            .append("nativePlace", getNativePlace())
            .append("workType", getWorkType())
            .append("accountName", getAccountName())
            .append("account", getAccount())
            .append("bankName", getBankName())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
