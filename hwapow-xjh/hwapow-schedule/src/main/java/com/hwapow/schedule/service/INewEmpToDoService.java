package com.hwapow.schedule.service;

import java.util.List;
import com.hwapow.schedule.domain.NewEmpToDo;

/**
 * 新员工入职事宜Service接口
 * 
 * @author xujinghuan
 * @date 2022-01-18
 */
public interface INewEmpToDoService 
{
    /**
     * 查询新员工入职事宜
     * 
     * @param id 新员工入职事宜ID
     * @return 新员工入职事宜
     */
    public NewEmpToDo selectNewEmpToDoById(Long id);

    /**
     * 查询新员工入职事宜列表
     * 
     * @param newEmpToDo 新员工入职事宜
     * @return 新员工入职事宜集合
     */
    public List<NewEmpToDo> selectNewEmpToDoList(NewEmpToDo newEmpToDo);

    /**
     * 新增新员工入职事宜
     * 
     * @param newEmpToDo 新员工入职事宜
     * @return 结果
     */
    public int insertNewEmpToDo(NewEmpToDo newEmpToDo);

    /**
     * 修改新员工入职事宜
     * 
     * @param newEmpToDo 新员工入职事宜
     * @return 结果
     */
    public int updateNewEmpToDo(NewEmpToDo newEmpToDo);

    /**
     * 批量删除新员工入职事宜
     * 
     * @param ids 需要删除的新员工入职事宜ID
     * @return 结果
     */
    public int deleteNewEmpToDoByIds(Long[] ids);

    /**
     * 删除新员工入职事宜信息
     * 
     * @param id 新员工入职事宜ID
     * @return 结果
     */
    public int deleteNewEmpToDoById(Long id);
}
