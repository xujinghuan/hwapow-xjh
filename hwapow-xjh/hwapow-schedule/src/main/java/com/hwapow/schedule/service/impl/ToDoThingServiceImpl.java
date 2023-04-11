package com.hwapow.schedule.service.impl;

import java.util.List;

import com.hwapow.common.annotation.DataScope;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.schedule.mapper.ToDoThingMapper;
import com.hwapow.schedule.domain.ToDoThing;
import com.hwapow.schedule.service.IToDoThingService;

/**
 * 应办事情Service业务层处理
 *
 * @author xujinghuan
 * @date 2022-01-18
 */
@Service
public class ToDoThingServiceImpl implements IToDoThingService
{
    @Autowired
    private ToDoThingMapper toDoThingMapper;

    /**
     * 查询应办事情
     *
     * @param id 应办事情ID
     * @return 应办事情
     */
    @Override
    public ToDoThing selectToDoThingById(Long id)
    {
        return toDoThingMapper.selectToDoThingById(id);
    }

    /**
     * 查询应办事情列表
     *
     * @param toDoThing 应办事情
     * @return 应办事情
     */
    @Override
    @DataScope
    public List<ToDoThing> selectToDoThingList(ToDoThing toDoThing)
    {
        return toDoThingMapper.selectToDoThingList(toDoThing);
    }

    /**
     * 新增应办事情
     *
     * @param toDoThing 应办事情
     * @return 结果
     */
    @Override
    public int insertToDoThing(ToDoThing toDoThing)
    {
        toDoThing.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        toDoThing.setCreateBy(SecurityUtils.getUsername());
        toDoThing.setCreateTime(DateUtils.getNowDate());
        return toDoThingMapper.insertToDoThing(toDoThing);
    }

    /**
     * 修改应办事情
     *
     * @param toDoThing 应办事情
     * @return 结果
     */
    @Override
    public int updateToDoThing(ToDoThing toDoThing)
    {
        if ("1".equals(toDoThing.getIsDone())){//如果是已完成，则把办理人写成自己
            toDoThing.setTransactor(SecurityUtils.getLoginUser().getUser().getUserId());
        }
        toDoThing.setUpdateBy(SecurityUtils.getUsername());
        toDoThing.setUpdateTime(DateUtils.getNowDate());
        return toDoThingMapper.updateToDoThing(toDoThing);
    }

    /**
     * 批量删除应办事情
     *
     * @param ids 需要删除的应办事情ID
     * @return 结果
     */
    @Override
    public int deleteToDoThingByIds(Long[] ids)
    {
        return toDoThingMapper.deleteToDoThingByIds(ids);
    }

    /**
     * 删除应办事情信息
     *
     * @param id 应办事情ID
     * @return 结果
     */
    @Override
    public int deleteToDoThingById(Long id)
    {
        return toDoThingMapper.deleteToDoThingById(id);
    }

    @Override
    public int deleteToDoThingByEmployeeId(Long employeeId){
        return toDoThingMapper.deleteToDoThingByEmployeeId(employeeId);
    }
}
