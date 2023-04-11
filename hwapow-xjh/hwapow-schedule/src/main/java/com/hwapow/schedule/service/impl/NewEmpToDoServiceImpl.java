package com.hwapow.schedule.service.impl;

import java.util.List;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.schedule.mapper.NewEmpToDoMapper;
import com.hwapow.schedule.domain.NewEmpToDo;
import com.hwapow.schedule.service.INewEmpToDoService;

/**
 * 新员工入职事宜Service业务层处理
 *
 * @author xujinghuan
 * @date 2022-01-18
 */
@Service
public class NewEmpToDoServiceImpl implements INewEmpToDoService
{
    @Autowired
    private NewEmpToDoMapper newEmpToDoMapper;

    /**
     * 查询新员工入职事宜
     *
     * @param id 新员工入职事宜ID
     * @return 新员工入职事宜
     */
    @Override
    public NewEmpToDo selectNewEmpToDoById(Long id)
    {
        return newEmpToDoMapper.selectNewEmpToDoById(id);
    }

    /**
     * 查询新员工入职事宜列表
     *
     * @param newEmpToDo 新员工入职事宜
     * @return 新员工入职事宜
     */
    @Override
    public List<NewEmpToDo> selectNewEmpToDoList(NewEmpToDo newEmpToDo)
    {
        return newEmpToDoMapper.selectNewEmpToDoList(newEmpToDo);
    }

    /**
     * 新增新员工入职事宜
     *
     * @param newEmpToDo 新员工入职事宜
     * @return 结果
     */
    @Override
    public int insertNewEmpToDo(NewEmpToDo newEmpToDo)
    {
                        newEmpToDo.setCreateBy(SecurityUtils.getUsername());
            newEmpToDo.setCreateTime(DateUtils.getNowDate());
                    return newEmpToDoMapper.insertNewEmpToDo(newEmpToDo);
    }

    /**
     * 修改新员工入职事宜
     *
     * @param newEmpToDo 新员工入职事宜
     * @return 结果
     */
    @Override
    public int updateNewEmpToDo(NewEmpToDo newEmpToDo)
    {
    newEmpToDo.setUpdateBy(SecurityUtils.getUsername());
        newEmpToDo.setUpdateTime(DateUtils.getNowDate());
        return newEmpToDoMapper.updateNewEmpToDo(newEmpToDo);
    }

    /**
     * 批量删除新员工入职事宜
     *
     * @param ids 需要删除的新员工入职事宜ID
     * @return 结果
     */
    @Override
    public int deleteNewEmpToDoByIds(Long[] ids)
    {
        return newEmpToDoMapper.deleteNewEmpToDoByIds(ids);
    }

    /**
     * 删除新员工入职事宜信息
     *
     * @param id 新员工入职事宜ID
     * @return 结果
     */
    @Override
    public int deleteNewEmpToDoById(Long id)
    {
        return newEmpToDoMapper.deleteNewEmpToDoById(id);
    }
}
