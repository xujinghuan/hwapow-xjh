package com.hwapow.web.controller.system;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hwapow.common.annotation.Log;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.controller.BaseController;
import com.hwapow.common.core.domain.AjaxResult;
import com.hwapow.common.core.domain.entity.SysOrg;
import com.hwapow.common.enums.BusinessType;
import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.StringUtils;
import com.hwapow.system.service.ISysOrgService;

/**
 * 组织信息
 *
 * @author hwapow
 */
@RestController
@RequestMapping("/system/org")
public class SysOrgController extends BaseController
{
    @Autowired
    private ISysOrgService orgService;

    /**
     * 获取组织列表
     */
    @PreAuthorize("@ss.hasPermi('system:org:list')")
    @GetMapping("/list")
    public AjaxResult list(SysOrg org)
    {
        List<SysOrg> orgs = orgService.selectOrgList(org);
        return AjaxResult.success(orgs);
    }

    /**
     * 获取组织列表无权限控制
     */
    @GetMapping("/listNoDataScode")
    public AjaxResult listNoDataScode(SysOrg org)
    {
        List<SysOrg> orgs = orgService.selectOrgListNoDataScode(org);
        return AjaxResult.success(orgs);
    }

    /**
     * 查询组织列表（排除节点）
     */
    @PreAuthorize("@ss.hasPermi('system:org:list')")
    @GetMapping("/list/exclude/{orgId}")
    public AjaxResult excludeChild(@PathVariable(value = "orgId", required = false) Long orgId)
    {
        List<SysOrg> orgs = orgService.selectOrgList(new SysOrg());
        Iterator<SysOrg> it = orgs.iterator();
        while (it.hasNext())
        {
            SysOrg d = (SysOrg) it.next();
            if (d.getOrgId().intValue() == orgId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), orgId + ""))
            {
                it.remove();
            }
        }
        return AjaxResult.success(orgs);
    }

    /**
     * 根据组织编号获取详细信息
     */
    @GetMapping(value = "/{orgId}")
    public AjaxResult getInfo(@PathVariable Long orgId)
    {
        return AjaxResult.success(orgService.selectOrgById(orgId));
    }

    /**
     * 获取组织下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysOrg org)
    {
        List<SysOrg> orgs = orgService.selectOrgList(org);
        return AjaxResult.success(orgService.buildOrgTreeSelect(orgs));
    }

    /**
     * 加载对应角色组织列表树
     */
    @GetMapping(value = "/roleOrgTreeselect/{roleId}")
    public AjaxResult roleOrgTreeselect(@PathVariable("roleId") Long roleId)
    {
        List<SysOrg> orgs = orgService.selectOrgList(new SysOrg());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", orgService.selectOrgListByRoleId(roleId));
        ajax.put("orgs", orgService.buildOrgTreeSelect(orgs));
        return ajax;
    }

    /**
     * 新增组织
     */
    @PreAuthorize("@ss.hasPermi('system:org:add')")
    @Log(title = "组织管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysOrg org)
    {
        if (UserConstants.NOT_UNIQUE.equals(orgService.checkOrgNameUnique(org)))
        {
            return AjaxResult.error("新增组织'" + org.getOrgName() + "'失败，组织名称已存在");
        }
        org.setCreateBy(SecurityUtils.getUsername());
        return toAjax(orgService.insertOrg(org));
    }

    /**
     * 修改组织
     */
    @PreAuthorize("@ss.hasPermi('system:org:edit')")
    @Log(title = "组织管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysOrg org)
    {
        if (UserConstants.NOT_UNIQUE.equals(orgService.checkOrgNameUnique(org)))
        {
            return AjaxResult.error("修改组织'" + org.getOrgName() + "'失败，组织名称已存在");
        }
        else if (org.getParentId().equals(org.getOrgId()))
        {
            return AjaxResult.error("修改组织'" + org.getOrgName() + "'失败，上级组织不能是自己");
        }
        else if (StringUtils.equals(UserConstants.ORG_DISABLE, org.getStatus())
                && orgService.selectNormalChildrenOrgById(org.getOrgId()) > 0)
        {
            return AjaxResult.error("该组织包含未停用的子组织！");
        }
        org.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(orgService.updateOrg(org));
    }

    /**
     * 删除组织
     */
    @PreAuthorize("@ss.hasPermi('system:org:remove')")
    @Log(title = "组织管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orgId}")
    public AjaxResult remove(@PathVariable Long orgId)
    {
        if (orgService.hasChildByOrgId(orgId))
        {
            return AjaxResult.error("存在下级组织,不允许删除");
        }
        if (orgService.checkOrgExistUser(orgId))
        {
            return AjaxResult.error("组织存在用户,不允许删除");
        }
        return toAjax(orgService.deleteOrgById(orgId));
    }
}
