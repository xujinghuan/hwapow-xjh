package com.hwapow.reservior.service.impl;

import java.util.List;

import com.hwapow.common.annotation.DataScope;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.StringUtils;
import com.hwapow.reservior.domain.ResSenor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.reservior.mapper.ResSenorMapper;
import com.hwapow.reservior.service.IResSenorService;

/**
 * 设备管理Service业务层处理
 *
 * @author hwapow
 * @date 2021-09-20
 */
@Service
public class ResSenorServiceImpl implements IResSenorService {
    @Autowired
    private ResSenorMapper resSenorMapper;

    /**
     * 查询设备管理
     *
     * @param id 设备管理ID
     * @return 设备管理
     */
    @Override
    public ResSenor selectResSenorById(Long id) {
        return resSenorMapper.selectResSenorById(id);
    }

    /**
     * 查询设备管理列表
     *
     * @param resSenor 设备管理
     * @return 设备管理
     */
    @Override
    @DataScope(orgAlias = "b")
    public List<ResSenor> selectResSenorList(ResSenor resSenor) {
        return resSenorMapper.selectResSenorList(resSenor);
    }

    /**
     * 新增设备管理
     *
     * @param resSenor 设备管理
     * @return 结果
     */
    @Override
    public int insertResSenor(ResSenor resSenor) {
        resSenor.setCreateBy(SecurityUtils.getUsername());
        resSenor.setCreateTime(DateUtils.getNowDate());
        return resSenorMapper.insertResSenor(resSenor);
    }

    /**
     * 修改设备管理
     *
     * @param resSenor 设备管理
     * @return 结果
     */
    @Override
    public int updateResSenor(ResSenor resSenor) {
        resSenor.setUpdateBy(SecurityUtils.getUsername());
        resSenor.setUpdateTime(DateUtils.getNowDate());
        return resSenorMapper.updateResSenor(resSenor);
    }

    /**
     * 批量删除设备管理
     *
     * @param ids 需要删除的设备管理ID
     * @return 结果
     */
    @Override
    public int deleteResSenorByIds(Long[] ids) {
        return resSenorMapper.deleteResSenorByIds(ids);
    }

    /**
     * 删除设备管理信息
     *
     * @param id 设备管理ID
     * @return 结果
     */
    @Override
    public int deleteResSenorById(Long id) {
        return resSenorMapper.deleteResSenorById(id);
    }

    /**
     * 检查断面编码是否唯一
     *
     * @param resSenor
     * @return
     */
    @Override
    public String checkSenorUnique(ResSenor resSenor) {
        ResSenor resSenorC = resSenorMapper.checkSenorUnique(resSenor.getCode(), resSenor.getSectionId());
        if (StringUtils.isNotNull(resSenorC) && !resSenorC.getId().equals(resSenor.getId())) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 根据返回指令查询设备管理
     *
     * @param backData 返回指令
     * @return 设备管理
     */
    @Override
    public ResSenor selectResSenorByBackData(String backData){
        return this.resSenorMapper.selectResSenorByBackData(backData);
    }

    /**
     * 执行计算sql
     *
     * @param sql
     * @return 结果
     */
    @Override
    public double executeFormulaSql(String sql){
        return this.resSenorMapper.executeFormulaSql(sql);
    }
}
