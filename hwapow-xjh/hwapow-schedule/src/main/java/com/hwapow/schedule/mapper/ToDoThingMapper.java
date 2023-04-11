package com.hwapow.schedule.mapper;

import java.util.List;
import com.hwapow.schedule.domain.ToDoThing;

/**
 * 应办事情Mapper接口
 * 
 * @author xujinghuan
 * @date 2022-01-18
 */
public interface ToDoThingMapper 
{
    /**
     * 查询应办事情
     * 
     * @param id 应办事情ID
     * @return 应办事情
     */
    public ToDoThing selectToDoThingById(Long id);

    /**
     * 查询应办事情列表
     * 
     * @param toDoThing 应办事情
     * @return 应办事情集合
     */
    public List<ToDoThing> selectToDoThingList(ToDoThing toDoThing);

    /**
     * 新增应办事情
     * 
     * @param toDoThing 应办事情
     * @return 结果
     */
    public int insertToDoThing(ToDoThing toDoThing);

    /**
     * 修改应办事情
     * 
     * @param toDoThing 应办事情
     * @return 结果
     */
    public int updateToDoThing(ToDoThing toDoThing);

    /**
     * 删除应办事情
     * 
     * @param id 应办事情ID
     * @return 结果
     */
    public int deleteToDoThingById(Long id);

    /**
     * 批量删除应办事情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteToDoThingByIds(Long[] ids);

    public int deleteToDoThingByEmployeeId(Long employeeId);
}
