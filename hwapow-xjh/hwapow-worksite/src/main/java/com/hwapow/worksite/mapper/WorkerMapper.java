package com.hwapow.worksite.mapper;

import java.util.List;
import com.hwapow.worksite.domain.Worker;
import org.apache.ibatis.annotations.Param;

/**
 * 工人Mapper接口
 *
 * @author hwapow
 * @date 2021-08-11
 */
public interface WorkerMapper
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
     * 删除工人
     *
     * @param id 工人ID
     * @return 结果
     */
    public int deleteWorkerById(Long id);

    /**
     * 批量删除工人
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkerByIds(Long[] ids);

    /**
     * 检查名称和身份证号码是否重复
     * @param name
     * @param idcard
     * @return
     */
    public Worker checkWorkerUnique(@Param("name")String name, @Param("idcard")String idcard);

    /***
     * 修改工人状态
     * @param status
     * @param id
     * @return
     */
    public int updateWorkerStatus(@Param("status")String status, @Param("id")Long id);
}
