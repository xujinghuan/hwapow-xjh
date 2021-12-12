package com.hwapow.reservior.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hwapow.common.annotation.Excel;
import com.hwapow.common.core.domain.BaseEntity;

/**
 * 设备管理对象 res_senor
 *
 * @author hwapow
 * @date 2021-09-20
 */
public class ResSenor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 设备编码
     */
    @Excel(name = "设备编码")
    private String code;

    /*
     *对应设备id（每个坝体设备对应一个坝基设备）
     */
    private Long matchSenorId;

    /**
     * 返回数据默认单位（字典back_data_unit）
     */
    private String backDataUnit;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String name;

    /**
     * 所属断面
     */
    @Excel(name = "所属断面")
    private Long sectionId;

    /**
     * 所属类型（字典类型 senor_type）
     */
    private String type;

    /**
     * 用于断面名称
     */
    private String sectionName;

    /**
     * 用于显示水库名称
     */
    private String orgName;

    /**
     * 用于显示水库名称
     */
    private Long orgId;

    /**
     * 零点高程
     */
    @Excel(name = "零点高程")
    private BigDecimal zeroElevation;

    /**
     * 管口高程
     */
    @Excel(name = "管口高程")
    private BigDecimal nozzleElevation;

    /**
     * 取数指令
     */
    @Excel(name = "取数指令")
    private String getInstruction;

    /**
     * 以水库图左上角为原点，x轴距离原点的百分比
     */
    private BigDecimal x;

    /**
     * 以水库图左上角为原点，y轴距离原点的百分比
     */
    private BigDecimal y;

    /**
     * 顺序号
     */
    @Excel(name = "顺序号")
    private Integer sort;

    /**
     * 返回指令设备标识，比如返回指令01代表设备T1-1
     */
    private String backIdentification;

    /**
     * 返回指令中设备标识开始下标
     */
    private Integer backIdenIndexS;

    /**
     * 返回指令中数据开始下标
     */
    private Integer backDataIndexS;

    /**
     * 返回指令中数据结束下标
     */
    private Integer backDataIndexE;

    /**
     * 返回数据计算公式（sql）
     */
    private String backDataFormula;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 返回数据最大值（以原始数据为标准）
     */
    private Double backDataMax;

    /**
     * 报警水位最大值
     */
    private Double warnMax;

    /**
     * 报警水位最小值
     */
    private Double warnMin;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setZeroElevation(BigDecimal zeroElevation) {
        this.zeroElevation = zeroElevation;
    }

    public BigDecimal getZeroElevation() {
        return zeroElevation;
    }

    public void setNozzleElevation(BigDecimal nozzleElevation) {
        this.nozzleElevation = nozzleElevation;
    }

    public BigDecimal getNozzleElevation() {
        return nozzleElevation;
    }

    public void setGetInstruction(String getInstruction) {
        this.getInstruction = getInstruction;
    }

    public String getGetInstruction() {
        return getInstruction;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return sort;
    }

    public void setBackIdentification(String backIdentification) {
        this.backIdentification = backIdentification;
    }

    public String getBackIdentification() {
        return backIdentification;
    }

    public void setBackIdenIndexS(Integer backIdenIndexS) {
        this.backIdenIndexS = backIdenIndexS;
    }

    public Integer getBackIdenIndexS() {
        return backIdenIndexS;
    }

    public void setBackDataIndexS(Integer backDataIndexS) {
        this.backDataIndexS = backDataIndexS;
    }

    public Integer getBackDataIndexS() {
        return backDataIndexS;
    }

    public void setBackDataIndexE(Integer backDataIndexE) {
        this.backDataIndexE = backDataIndexE;
    }

    public Integer getBackDataIndexE() {
        return backDataIndexE;
    }

    public void setBackDataFormula(String backDataFormula) {
        this.backDataFormula = backDataFormula;
    }

    public String getBackDataFormula() {
        return backDataFormula;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public Long getMatchSenorId() {
        return matchSenorId;
    }

    public void setMatchSenorId(Long matchSenorId) {
        this.matchSenorId = matchSenorId;
    }

    public String getBackDataUnit() {
        return backDataUnit;
    }

    public void setBackDataUnit(String backDataUnit) {
        this.backDataUnit = backDataUnit;
    }

    public Double getBackDataMax() {
        return backDataMax;
    }

    public void setBackDataMax(Double backDataMax) {
        this.backDataMax = backDataMax;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("code", getCode())
                .append("name", getName())
                .append("sectionId", getSectionId())
                .append("zeroElevation", getZeroElevation())
                .append("nozzleElevation", getNozzleElevation())
                .append("getInstruction", getGetInstruction())
                .append("x", getX())
                .append("y", getY())
                .append("sort", getSort())
                .append("backIdentification", getBackIdentification())
                .append("backIdenIndexS", getBackIdenIndexS())
                .append("backDataIndexS", getBackDataIndexS())
                .append("backDataIndexE", getBackDataIndexE())
                .append("backDataFormula", getBackDataFormula())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
