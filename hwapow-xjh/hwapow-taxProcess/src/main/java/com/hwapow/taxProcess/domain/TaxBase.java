package com.hwapow.taxProcess.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 基础信息表对象 tax_base
 *
 * @author hwapow
 * @date 2023-04-17
 */
public class TaxBase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 纳税人名称 */
    @Excel(name = "纳税人名称")
    private String colB;

    /** 状态 */
    @Excel(name = "状态")
    private String colC;

    /** 纳税人识别号 */
    @Excel(name = "纳税人识别号")
    private String colA;

    /** 登记注册类型 */
    @Excel(name = "登记注册类型")
    private String colD;

    /** 开业设立日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开业设立日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date colE;

    /** 行业 */
    @Excel(name = "行业")
    private String colF;

    /** 登记日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登记日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date colG;

    /** 主管税务所（科、分局） */
    @Excel(name = "主管税务所", readConverterExp = "科=、分局")
    private String colH;

    /** 街道乡镇 */
    @Excel(name = "街道乡镇")
    private String colI;

    /** 注册地址 */
    @Excel(name = "注册地址")
    private String colJ;

    /** 注册地联系电话 */
    @Excel(name = "注册地联系电话")
    private String colK;

    /** 经营地址 */
    @Excel(name = "经营地址")
    private String colL;

    /** 法定代表人（负责人、业主）姓名 */
    @Excel(name = "法定代表人", readConverterExp = "负=责人、业主")
    private String colM;

    /** 法定代表人（负责人、业主）移动电话 */
    @Excel(name = "法定代表人", readConverterExp = "负=责人、业主")
    private String colN;

    /** 财务负责人姓名 */
    @Excel(name = "财务负责人姓名")
    private String colO;

    /** 财务负责人移动电话 */
    @Excel(name = "财务负责人移动电话")
    private String colP;

    /** 办税人姓名 */
    @Excel(name = "办税人姓名")
    private String colQ;

    /** 办税人移动电话 */
    @Excel(name = "办税人移动电话")
    private String colR;

    private String down;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setColB(String colB)
    {
        this.colB = colB;
    }

    public String getColB()
    {
        return colB;
    }
    public void setColC(String colC)
    {
        this.colC = colC;
    }

    public String getColC()
    {
        return colC;
    }
    public void setColA(String colA)
    {
        this.colA = colA;
    }

    public String getColA()
    {
        return colA;
    }
    public void setColD(String colD)
    {
        this.colD = colD;
    }

    public String getColD()
    {
        return colD;
    }
    public void setColE(Date colE)
    {
        this.colE = colE;
    }

    public Date getColE()
    {
        return colE;
    }
    public void setColF(String colF)
    {
        this.colF = colF;
    }

    public String getColF()
    {
        return colF;
    }
    public void setColG(Date colG)
    {
        this.colG = colG;
    }

    public Date getColG()
    {
        return colG;
    }
    public void setColH(String colH)
    {
        this.colH = colH;
    }

    public String getColH()
    {
        return colH;
    }
    public void setColI(String colI)
    {
        this.colI = colI;
    }

    public String getColI()
    {
        return colI;
    }
    public void setColJ(String colJ)
    {
        this.colJ = colJ;
    }

    public String getColJ()
    {
        return colJ;
    }
    public void setColK(String colK)
    {
        this.colK = colK;
    }

    public String getColK()
    {
        return colK;
    }
    public void setColL(String colL)
    {
        this.colL = colL;
    }

    public String getColL()
    {
        return colL;
    }
    public void setColM(String colM)
    {
        this.colM = colM;
    }

    public String getColM()
    {
        return colM;
    }
    public void setColN(String colN)
    {
        this.colN = colN;
    }

    public String getColN()
    {
        return colN;
    }
    public void setColO(String colO)
    {
        this.colO = colO;
    }

    public String getColO()
    {
        return colO;
    }
    public void setColP(String colP)
    {
        this.colP = colP;
    }

    public String getColP()
    {
        return colP;
    }
    public void setColQ(String colQ)
    {
        this.colQ = colQ;
    }

    public String getColQ()
    {
        return colQ;
    }
    public void setColR(String colR)
    {
        this.colR = colR;
    }

    public String getColR()
    {
        return colR;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("colB", getColB())
            .append("colC", getColC())
            .append("colA", getColA())
            .append("colD", getColD())
            .append("colE", getColE())
            .append("colF", getColF())
            .append("colG", getColG())
            .append("colH", getColH())
            .append("colI", getColI())
            .append("colJ", getColJ())
            .append("colK", getColK())
            .append("colL", getColL())
            .append("colM", getColM())
            .append("colN", getColN())
            .append("colO", getColO())
            .append("colP", getColP())
            .append("colQ", getColQ())
            .append("colR", getColR())
            .toString();
    }
}
