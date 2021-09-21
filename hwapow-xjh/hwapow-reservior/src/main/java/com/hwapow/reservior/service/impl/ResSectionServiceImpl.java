package com.hwapow.reservior.service.impl;

import java.util.List;

import com.hwapow.common.annotation.DataScope;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.StringUtils;
import com.hwapow.reservior.domain.ResSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.reservior.mapper.ResSectionMapper;
import com.hwapow.reservior.domain.ResSection;
import com.hwapow.reservior.service.IResSectionService;

/**
 * 水库断面Service业务层处理
 *
 * @author hwapow
 * @date 2021-09-20
 */
@Service
public class ResSectionServiceImpl implements IResSectionService
{
    @Autowired
    private ResSectionMapper resSectionMapper;

    /**
     * 查询水库断面
     *
     * @param id 水库断面ID
     * @return 水库断面
     */
    @Override
    public ResSection selectResSectionById(Long id)
    {
        return resSectionMapper.selectResSectionById(id);
    }

    /**
     * 查询水库断面列表
     *
     * @param resSection 水库断面
     * @return 水库断面
     */
    @Override
    @DataScope(orgAlias = "a")
    public List<ResSection> selectResSectionList(ResSection resSection)
    {
        return resSectionMapper.selectResSectionList(resSection);
    }

    /**
     * 新增水库断面
     *
     * @param resSection 水库断面
     * @return 结果
     */
    @Override
    public int insertResSection(ResSection resSection)
    {
                                    resSection.setCreateBy(SecurityUtils.getUsername());
            resSection.setCreateTime(DateUtils.getNowDate());
                        return resSectionMapper.insertResSection(resSection);
    }

    /**
     * 修改水库断面
     *
     * @param resSection 水库断面
     * @return 结果
     */
    @Override
    public int updateResSection(ResSection resSection)
    {
    resSection.setUpdateBy(SecurityUtils.getUsername());
        resSection.setUpdateTime(DateUtils.getNowDate());
        return resSectionMapper.updateResSection(resSection);
    }

    /**
     * 批量删除水库断面
     *
     * @param ids 需要删除的水库断面ID
     * @return 结果
     */
    @Override
    public int deleteResSectionByIds(Long[] ids)
    {
        return resSectionMapper.deleteResSectionByIds(ids);
    }

    /**
     * 删除水库断面信息
     *
     * @param id 水库断面ID
     * @return 结果
     */
    @Override
    public int deleteResSectionById(Long id)
    {
        return resSectionMapper.deleteResSectionById(id);
    }

    /**
     * 检查断面编码是否唯一
     *
     * @param resSection 断面
     * @return
     */
    @Override
    public String checkSectionUnique(ResSection resSection){
        ResSection resSectionC = resSectionMapper.checkSectionUnique(resSection.getCode(),resSection.getOrgId());
        if (StringUtils.isNotNull(resSectionC) && !resSectionC.getId().equals(resSection.getId()))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
