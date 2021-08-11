package com.hwapow.worksite.service;

import java.util.List;
import com.hwapow.worksite.domain.Worker;

/**
 * 工人Service接口
 *
 * @author hwapow
 * @date 2021-08-11
 */
public interface IWorkerService
{
    /**
     * 查询工人
     *
     * @param id 工人ID
     * @return 工人
     */
    public Worker selectWorkerById(Long id);

    /**
     * 查询工人列表
     *
     * @param worker 工人
     * @return 工人集合
     */
    public List<Worker> selectWorkerList(Worker worker);

    /**
     * 新增工人
     *
     * @param worker 工人
     * @return 结果
     */
    public int insertWorker(Worker worker);

    /**
     * 修改工人
     *
     * @param worker 工人
     * @return 结果
     */
    public int updateWorker(Worker worker);

    /**
     * 批量删除工人
     *
     * @param ids 需要删除的工人ID
     * @return 结果
     */
    public int deleteWorkerByIds(Long[] ids);

    /**
     * 删除工人信息
     *
     * @param id 工人ID
     * @return 结果
     */
    public int deleteWorkerById(Long id);

    /**
     * 检查名称和身份证号是否重复
     * @param worker
     * @return
     */
    public String checkWorkerUnique(Worker worker);
}
