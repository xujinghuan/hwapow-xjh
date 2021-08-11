package com.hwapow.worksite.service.impl;

import java.util.List;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.worksite.mapper.AskForLeaveMapper;
import com.hwapow.worksite.domain.AskForLeave;
import com.hwapow.worksite.service.IAskForLeaveService;

/**
 * 请假Service业务层处理
 *
 * @author hwapow
 * @date 2021-08-11
 */
@Service
public class AskForLeaveServiceImpl implements IAskForLeaveService
{
    @Autowired
    private AskForLeaveMapper askForLeaveMapper;

    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    @Override
    public AskForLeave selectAskForLeaveById(Long id)
    {
        return askForLeaveMapper.selectAskForLeaveById(id);
    }

    /**
     * 查询请假列表
     *
     * @param askForLeave 请假
     * @return 请假
     */
    @Override
    public List<AskForLeave> selectAskForLeaveList(AskForLeave askForLeave)
    {
        return askForLeaveMapper.selectAskForLeaveList(askForLeave);
    }

    /**
     * 新增请假
     *
     * @param askForLeave 请假
     * @return 结果
     */
    @Override
    public int insertAskForLeave(AskForLeave askForLeave)
    {
                                    askForLeave.setCreateBy(SecurityUtils.getUsername());
            askForLeave.setCreateTime(DateUtils.getNowDate());
                        return askForLeaveMapper.insertAskForLeave(askForLeave);
    }

    /**
     * 修改请假
     *
     * @param askForLeave 请假
     * @return 结果
     */
    @Override
    public int updateAskForLeave(AskForLeave askForLeave)
    {
    askForLeave.setUpdateBy(SecurityUtils.getUsername());
        askForLeave.setUpdateTime(DateUtils.getNowDate());
        return askForLeaveMapper.updateAskForLeave(askForLeave);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    @Override
    public int deleteAskForLeaveByIds(Long[] ids)
    {
        return askForLeaveMapper.deleteAskForLeaveByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    @Override
    public int deleteAskForLeaveById(Long id)
    {
        return askForLeaveMapper.deleteAskForLeaveById(id);
    }
}
