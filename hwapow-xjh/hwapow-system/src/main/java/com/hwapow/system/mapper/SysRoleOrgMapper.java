package com.hwapow.system.mapper;

import java.util.List;
import com.hwapow.system.domain.SysRoleOrg;

/**
 * 角色与组织关联表 数据层
 *
 * @author hwapow
 */
public interface SysRoleOrgMapper
{
    /**
     * 通过角色ID删除角色和组织关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleOrgByRoleId(Long roleId);

    /**
     * 批量删除角色组织关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleOrg(Long[] ids);

    /**
     * 查询组织使用数量
     *
     * @param orgId 组织ID
     * @return 结果
     */
    public int selectCountRoleOrgByOrgId(Long orgId);

    /**
     * 批量新增角色组织信息
     *
     * @param roleOrgList 角色组织列表
     * @return 结果
     */
    public int batchRoleOrg(List<SysRoleOrg> roleOrgList);
}
