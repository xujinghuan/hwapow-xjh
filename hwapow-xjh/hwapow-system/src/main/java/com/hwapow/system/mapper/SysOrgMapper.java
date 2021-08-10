package com.hwapow.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hwapow.common.core.domain.entity.SysOrg;

/**
 * 组织管理 数据层
 *
 * @author hwapow
 */
public interface SysOrgMapper
{
    /**
     * 查询组织管理数据
     *
     * @param org 组织信息
     * @return 组织信息集合
     */
    public List<SysOrg> selectOrgList(SysOrg org);

    /**
     * 根据角色ID查询组织树信息
     *
     * @param roleId 角色ID
     * @param orgCheckStrictly 组织树选择项是否关联显示
     * @return 选中组织列表
     */
    public List<Integer> selectOrgListByRoleId(@Param("roleId") Long roleId, @Param("orgCheckStrictly") boolean orgCheckStrictly);

    /**
     * 根据组织ID查询信息
     *
     * @param orgId 组织ID
     * @return 组织信息
     */
    public SysOrg selectOrgById(Long orgId);

    /**
     * 根据ID查询所有子组织
     *
     * @param orgId 组织ID
     * @return 组织列表
     */
    public List<SysOrg> selectChildrenOrgById(Long orgId);

    /**
     * 根据ID查询所有子组织且orgType为sum的汇总组织
     *
     * @param org 组织信息
     * @return  组织列表
     */
    public List<SysOrg> selectChildrenOrgByIdAndType(SysOrg org);

    /**
     * 根据ID查询所有子组织（正常状态）
     *
     * @param orgId 组织ID
     * @return 子组织数
     */
    public int selectNormalChildrenOrgById(Long orgId);

    /**
     * 是否存在子节点
     *
     * @param orgId 组织ID
     * @return 结果
     */
    public int hasChildByOrgId(Long orgId);

    /**
     * 查询组织是否存在用户
     *
     * @param orgId 组织ID
     * @return 结果
     */
    public int checkOrgExistUser(Long orgId);

    /**
     * 校验组织名称是否唯一
     *
     * @param orgName 组织名称
     * @param parentId 父组织ID
     * @return 结果
     */
    public SysOrg checkOrgNameUnique(@Param("orgName") String orgName, @Param("parentId") Long parentId);

    /**
     * 新增组织信息
     *
     * @param org 组织信息
     * @return 结果
     */
    public int insertOrg(SysOrg org);

    /**
     * 修改组织信息
     *
     * @param org 组织信息
     * @return 结果
     */
    public int updateOrg(SysOrg org);

    /**
     * 修改所在组织的父级组织状态
     *
     * @param org 组织
     */
    public void updateOrgStatus(SysOrg org);

    /**
     * 修改子元素关系
     *
     * @param orgs 子元素
     * @return 结果
     */
    public int updateOrgChildren(@Param("orgs") List<SysOrg> orgs);

    /**
     * 删除组织管理信息
     *
     * @param orgId 组织ID
     * @return 结果
     */
    public int deleteOrgById(Long orgId);
}
