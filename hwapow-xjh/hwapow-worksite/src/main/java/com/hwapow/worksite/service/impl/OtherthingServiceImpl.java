package com.hwapow.worksite.service.impl;

import java.util.List;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.worksite.mapper.OtherthingMapper;
import com.hwapow.worksite.domain.Otherthing;
import com.hwapow.worksite.service.IOtherthingService;

/**
 * 杂事记录Service业务层处理
 *
 * @author hwapow
 * @date 2021-08-11
 */
@Service
public class OtherthingServiceImpl implements IOtherthingService
{
    @Autowired
    private OtherthingMapper otherthingMapper;

    /**
     * 查询杂事记录
     *
     * @param id 杂事记录ID
     * @return 杂事记录
     */
    @Override
    public Otherthing selectOtherthingById(Long id)
    {
        return otherthingMapper.selectOtherthingById(id);
    }

    /**
     * 查询杂事记录列表
     *
     * @param otherthing 杂事记录
     * @return 杂事记录
     */
    @Override
    public List<Otherthing> selectOtherthingList(Otherthing otherthing)
    {
        return otherthingMapper.selectOtherthingList(otherthing);
    }

    /**
     * 新增杂事记录
     *
     * @param otherthing 杂事记录
     * @return 结果
     */
    @Override
    public int insertOtherthing(Otherthing otherthing)
    {
                            otherthing.setCreateBy(SecurityUtils.getUsername());
            otherthing.setCreateTime(DateUtils.getNowDate());
                        return otherthingMapper.insertOtherthing(otherthing);
    }

    /**
     * 修改杂事记录
     *
     * @param otherthing 杂事记录
     * @return 结果
     */
    @Override
    public int updateOtherthing(Otherthing otherthing)
    {
    otherthing.setUpdateBy(SecurityUtils.getUsername());
        otherthing.setUpdateTime(DateUtils.getNowDate());
        return otherthingMapper.updateOtherthing(otherthing);
    }

    /**
     * 批量删除杂事记录
     *
     * @param ids 需要删除的杂事记录ID
     * @return 结果
     */
    @Override
    public int deleteOtherthingByIds(Long[] ids)
    {
        return otherthingMapper.deleteOtherthingByIds(ids);
    }

    /**
     * 删除杂事记录信息
     *
     * @param id 杂事记录ID
     * @return 结果
     */
    @Override
    public int deleteOtherthingById(Long id)
    {
        return otherthingMapper.deleteOtherthingById(id);
    }
}
