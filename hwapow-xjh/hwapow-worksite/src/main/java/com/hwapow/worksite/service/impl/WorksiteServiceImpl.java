package com.hwapow.worksite.service.impl;

import java.util.List;

import com.hwapow.common.annotation.DataScope;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.StringUtils;
import com.hwapow.worksite.domain.Worker;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.worksite.mapper.WorksiteMapper;
import com.hwapow.worksite.domain.Worksite;
import com.hwapow.worksite.service.IWorksiteService;

/**
 * 工地Service业务层处理
 *
 * @author hwapow
 * @date 2021-08-11
 */
@Service
public class WorksiteServiceImpl implements IWorksiteService {
    @Autowired
    private WorksiteMapper worksiteMapper;

    /**
     * 查询工地
     *
     * @param id 工地ID
     * @return 工地
     */
    @Override
    public Worksite selectWorksiteById(Long id) {
        return worksiteMapper.selectWorksiteById(id);
    }

    /**
     * 查询工地列表
     *
     * @param worksite 工地
     * @return 工地
     */
    @Override
    @DataScope
    public List<Worksite> selectWorksiteList(Worksite worksite) {
        return worksiteMapper.selectWorksiteList(worksite);
    }

    /**
     * 新增工地
     *
     * @param worksite 工地
     * @return 结果
     */
    @Override
    public int insertWorksite(Worksite worksite) {
        worksite.setCreateBy(SecurityUtils.getUsername());
        worksite.setCreateTime(DateUtils.getNowDate());
        worksite.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        return worksiteMapper.insertWorksite(worksite);
    }

    /**
     * 修改工地
     *
     * @param worksite 工地
     * @return 结果
     */
    @Override
    public int updateWorksite(Worksite worksite) {
        worksite.setUpdateBy(SecurityUtils.getUsername());
        worksite.setUpdateTime(DateUtils.getNowDate());
        worksite.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        return worksiteMapper.updateWorksite(worksite);
    }

    /**
     * 批量删除工地
     *
     * @param ids 需要删除的工地ID
     * @return 结果
     */
    @Override
    public int deleteWorksiteByIds(Long[] ids) {
        return worksiteMapper.deleteWorksiteByIds(ids);
    }

    /**
     * 删除工地信息
     *
     * @param id 工地ID
     * @return 结果
     */
    @Override
    public int deleteWorksiteById(Long id) {
        return worksiteMapper.deleteWorksiteById(id);
    }

    /**
     * 检查名称是否重复
     *
     * @param worksite
     * @return
     */
    @Override
    public String checkWorksiteUnique(Worksite worksite) {
        Worksite worksiteC = worksiteMapper.checkWorksiteUnique(worksite.getName(),SecurityUtils.getLoginUser().getUser().getUserId());
        if (StringUtils.isNotNull(worksiteC) && !worksiteC.getId().equals(worksite.getId())) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /***
     * 修改工地状态
     * @param status
     * @param id
     * @return
     */
    public int updateWorksiteStatus(@Param("status") String status, @Param("id") Long id) {
        return this.worksiteMapper.updateWorksiteStatus(status, id);
    }
}
