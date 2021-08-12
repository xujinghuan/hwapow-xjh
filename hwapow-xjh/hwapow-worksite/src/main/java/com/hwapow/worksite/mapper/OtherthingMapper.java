package com.hwapow.worksite.mapper;

import java.util.List;
import com.hwapow.worksite.domain.Otherthing;

/**
 * 杂事记录Mapper接口
 * 
 * @author hwapow
 * @date 2021-08-11
 */
public interface OtherthingMapper 
{
    /**
     * 查询杂事记录
     * 
     * @param id 杂事记录ID
     * @return 杂事记录
     */
    public Otherthing selectOtherthingById(Long id);

    /**
     * 查询杂事记录列表
     * 
     * @param otherthing 杂事记录
     * @return 杂事记录集合
     */
    public List<Otherthing> selectOtherthingList(Otherthing otherthing);

    /**
     * 新增杂事记录
     * 
     * @param otherthing 杂事记录
     * @return 结果
     */
    public int insertOtherthing(Otherthing otherthing);

    /**
     * 修改杂事记录
     * 
     * @param otherthing 杂事记录
     * @return 结果
     */
    public int updateOtherthing(Otherthing otherthing);

    /**
     * 删除杂事记录
     * 
     * @param id 杂事记录ID
     * @return 结果
     */
    public int deleteOtherthingById(Long id);

    /**
     * 批量删除杂事记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOtherthingByIds(Long[] ids);
}
