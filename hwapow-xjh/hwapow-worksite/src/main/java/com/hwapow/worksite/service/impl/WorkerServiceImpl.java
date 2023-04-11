package com.hwapow.worksite.service.impl;

import java.util.List;

import com.hwapow.common.annotation.DataScope;
import com.hwapow.common.constant.UserConstants;
import com.hwapow.common.core.domain.model.LoginUser;
import com.hwapow.common.utils.DateUtils;
import com.hwapow.common.utils.SecurityUtils;
import com.hwapow.common.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hwapow.worksite.mapper.WorkerMapper;
import com.hwapow.worksite.domain.Worker;
import com.hwapow.worksite.service.IWorkerService;

/**
 * 工人Service业务层处理
 *
 * @author hwapow
 * @date 2021-08-11
 */
@Service
public class WorkerServiceImpl implements IWorkerService {
    @Autowired
    private WorkerMapper workerMapper;

    /**
     * 查询工人
     *
     * @param id 工人ID
     * @return 工人
     */
    @Override
    public Worker selectWorkerById(Long id) {
        return workerMapper.selectWorkerById(id);
    }

    /**
     * 查询工人列表
     *
     * @param worker 工人
     * @return 工人
     */
    @Override
    @DataScope
    public List<Worker> selectWorkerList(Worker worker) {
        return workerMapper.selectWorkerList(worker);
    }

    /**
     * 新增工人
     *
     * @param worker 工人
     * @return 结果
     */
    @Override
    public int insertWorker(Worker worker) {
        worker.setCreateBy(SecurityUtils.getUsername());
        worker.setCreateTime(DateUtils.getNowDate());
        worker.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        return workerMapper.insertWorker(worker);
    }

    /**
     * 修改工人
     *
     * @param worker 工人
     * @return 结果
     */
    @Override
    public int updateWorker(Worker worker) {
        worker.setUpdateBy(SecurityUtils.getUsername());
        worker.setUpdateTime(DateUtils.getNowDate());
        worker.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        return workerMapper.updateWorker(worker);
    }

    /**
     * 批量删除工人
     *
     * @param ids 需要删除的工人ID
     * @return 结果
     */
    @Override
    public int deleteWorkerByIds(Long[] ids) {
        return workerMapper.deleteWorkerByIds(ids);
    }

    /**
     * 删除工人信息
     *
     * @param id 工人ID
     * @return 结果
     */
    @Override
    public int deleteWorkerById(Long id) {
        return workerMapper.deleteWorkerById(id);
    }

    /**
     * 检查名称和身份证号是否重复
     *
     * @param worker
     * @return
     */
    @Override
    public String checkWorkerUnique(Worker worker) {
        Worker workerC = workerMapper.checkWorkerUnique(worker.getName(), worker.getIdcard(),SecurityUtils.getLoginUser().getUser().getUserId());
        if (StringUtils.isNotNull(workerC) && !workerC.getId().equals(worker.getId())) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /***
     * 修改工人状态
     * @param status
     * @param id
     * @return
     */
    public int updateWorkerStatus(@Param("status") String status, @Param("id") Long id) {
        return this.workerMapper.updateWorkerStatus(status, id);
    }
}
