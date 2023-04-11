package com.hwapow.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.common.annotation.DataScope;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.domain.TreeSelect;
import com.hwapow.common.core.domain.entity.SysOrg;
import com.hwapow.common.core.domain.entity.SysRole;
import com.hwapow.common.exception.CustomException;
import com.hwapow.common.utils.StringUtils;
import com.hwapow.system.mapper.SysOrgMapper;
import com.hwapow.system.mapper.SysRoleMapper;
import com.hwapow.system.service.ISysOrgService;

/**
 * 组织管理 服务实现
 *
 * @author hwapow
 */
@Service
public class SysOrgServiceImpl implements ISysOrgService
{
    @Autowired
    private SysOrgMapper orgMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询组织管理数据
     *
     * @param org 组织信息
     * @return 组织信息集合
     */
    @Override
    @DataScope(orgAlias = "d")
    public List<SysOrg> selectOrgList(SysOrg org)
    {
        return orgMapper.selectOrgList(org);
    }

    /**
     * 查询组织管理数据
     *
     * @param org 组织信息
     * @return 组织信息集合
     */
    @Override
    public List<SysOrg> selectOrgListNoDataScode(SysOrg org)
    {
        return orgMapper.selectOrgList(org);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param orgs 组织列表
     * @return 树结构列表
     */
    @Override
    public List<SysOrg> buildOrgTree(List<SysOrg> orgs)
    {
        List<SysOrg> returnList = new ArrayList<SysOrg>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysOrg org : orgs)
        {
            tempList.add(org.getOrgId());
        }
        for (Iterator<SysOrg> iterator = orgs.iterator(); iterator.hasNext();)
        {
            SysOrg org = (SysOrg) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(org.getParentId()))
            {
                recursionFn(orgs, org);
                returnList.add(org);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = orgs;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param orgs 组织列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildOrgTreeSelect(List<SysOrg> orgs)
    {
        List<SysOrg> orgTrees = buildOrgTree(orgs);
        return orgTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询组织树信息
     *
     * @param roleId 角色ID
     * @return 选中组织列表
     */
    @Override
    public List<Integer> selectOrgListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return orgMapper.selectOrgListByRoleId(roleId, role.isOrgCheckStrictly());
    }

    /**
     * 根据组织ID查询信息
     *
     * @param orgId 组织ID
     * @return 组织信息
     */
    @Override
    public SysOrg selectOrgById(Long orgId)
    {
        return orgMapper.selectOrgById(orgId);
    }

    /**
     * 根据ID查询所有子组织（正常状态）
     *
     * @param orgId 组织ID
     * @return 子组织数
     */
    @Override
    public int selectNormalChildrenOrgById(Long orgId)
    {
        return orgMapper.selectNormalChildrenOrgById(orgId);
    }

    /**
     * 是否存在子节点
     *
     * @param orgId 组织ID
     * @return 结果
     */
    @Override
    public boolean hasChildByOrgId(Long orgId)
    {
        int result = orgMapper.hasChildByOrgId(orgId);
        return result > 0 ? true : false;
    }

    /**
     * 查询组织是否存在用户
     *
     * @param orgId 组织ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkOrgExistUser(Long orgId)
    {
        int result = orgMapper.checkOrgExistUser(orgId);
        return result > 0 ? true : false;
    }

    /**
     * 校验组织名称是否唯一
     *
     * @param org 组织信息
     * @return 结果
     */
    @Override
    public String checkOrgNameUnique(SysOrg org)
    {
        Long orgId = StringUtils.isNull(org.getOrgId()) ? -1L : org.getOrgId();
        SysOrg info = orgMapper.checkOrgNameUnique(org.getOrgName(), org.getParentId());
        if (StringUtils.isNotNull(info) && info.getOrgId().longValue() != orgId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存组织信息
     *
     * @param org 组织信息
     * @return 结果
     */
    @Override
    public int insertOrg(SysOrg org)
    {
        SysOrg info = orgMapper.selectOrgById(org.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.ORG_NORMAL.equals(info.getStatus()))
        {
            throw new CustomException("组织停用，不允许新增");
        }
        org.setAncestors(info.getAncestors() + "," + org.getParentId());
        return orgMapper.insertOrg(org);
    }

    /**
     * 修改保存组织信息
     *
     * @param org 组织信息
     * @return 结果
     */
    @Override
    public int updateOrg(SysOrg org)
    {
        SysOrg newParentOrg = orgMapper.selectOrgById(org.getParentId());
        SysOrg oldOrg = orgMapper.selectOrgById(org.getOrgId());
        if (StringUtils.isNotNull(newParentOrg) && StringUtils.isNotNull(oldOrg))
        {
            String newAncestors = newParentOrg.getAncestors() + "," + newParentOrg.getOrgId();
            String oldAncestors = oldOrg.getAncestors();
            org.setAncestors(newAncestors);
            updateOrgChildren(org.getOrgId(), newAncestors, oldAncestors);
        }
        int result = orgMapper.updateOrg(org);
        if (UserConstants.ORG_NORMAL.equals(org.getStatus()))
        {
            // 如果该组织是启用状态，则启用该组织的所有上级组织
            updateParentOrgStatus(org);
        }
        return result;
    }

    /**
     * 修改该组织的父级组织状态
     *
     * @param org 当前组织
     */
    private void updateParentOrgStatus(SysOrg org)
    {
        String updateBy = org.getUpdateBy();
        org = orgMapper.selectOrgById(org.getOrgId());
        org.setUpdateBy(updateBy);
        orgMapper.updateOrgStatus(org);
    }

    /**
     * 修改子元素关系
     *
     * @param orgId 被修改的组织ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateOrgChildren(Long orgId, String newAncestors, String oldAncestors)
    {
        List<SysOrg> children = orgMapper.selectChildrenOrgById(orgId);
        for (SysOrg child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            orgMapper.updateOrgChildren(children);
        }
    }

    /**
     * 删除组织管理信息
     *
     * @param orgId 组织ID
     * @return 结果
     */
    @Override
    public int deleteOrgById(Long orgId)
    {
        return orgMapper.deleteOrgById(orgId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysOrg> list, SysOrg t)
    {
        // 得到子节点列表
        List<SysOrg> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysOrg tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysOrg> getChildList(List<SysOrg> list, SysOrg t)
    {
        List<SysOrg> tlist = new ArrayList<SysOrg>();
        Iterator<SysOrg> it = list.iterator();
        while (it.hasNext())
        {
            SysOrg n = (SysOrg) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getOrgId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysOrg> list, SysOrg t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
