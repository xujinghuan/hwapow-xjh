package com.hwapow.worksite.mapper;

import java.util.List;
import com.hwapow.worksite.domain.AskForLeave;

/**
 * 请假Mapper接口
 * 
 * @author hwapow
 * @date 2021-08-11
 */
public interface AskForLeaveMapper 
{
    /**
     * 查询请假
     * 
     * @param id 请假ID
     * @return 请假
     */
    public AskForLeave selectAskForLeaveById(Long id);

    /**
     * 查询请假列表
     * 
     * @param askForLeave 请假
     * @return 请假集合
     */
    public List<AskForLeave> selectAskForLeaveList(AskForLeave askForLeave);

    /**
     * 新增请假
     * 
     * @param askForLeave 请假
     * @return 结果
     */
    public int insertAskForLeave(AskForLeave askForLeave);

    /**
     * 修改请假
     * 
     * @param askForLeave 请假
     * @return 结果
     */
    public int updateAskForLeave(AskForLeave askForLeave);

    /**
     * 删除请假
     * 
     * @param id 请假ID
     * @return 结果
     */
    public int deleteAskForLeaveById(Long id);

    /**
     * 批量删除请假
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAskForLeaveByIds(Long[] ids);
}
