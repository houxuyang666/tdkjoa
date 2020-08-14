package com.tdkj.System.service;

import com.tdkj.System.entity.Collect;
import com.tdkj.System.entity.VO.CollectVO;

import java.util.List;

/**
 * (Collect)表服务接口
 *
 * @author makejava
 * @since 2020-08-14 11:47:56
 */
public interface CollectService {

    /**
     * 通过ID查询单条数据
     *
     * @param collectid 主键
     * @return 实例对象
     */
    Collect queryById(String collectid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Collect> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param collect 实例对象
     * @return 实例对象
     */
    Collect insert(Collect collect);

    /**
     * 修改数据
     *
     * @param collect 实例对象
     * @return 实例对象
     */
    Collect update(Collect collect);

    /**
     * 通过主键删除数据
     *
     * @param collectid 主键
     * @return 是否成功
     */
    boolean deleteById(String collectid);

    /**
     * @Author houxuyang
     * @Description //根据条件查询本公司的领用记录
     * @Date 13:51 2020/8/14
     * @Param [collectVO]
     * @return java.util.List<com.tdkj.System.entity.VO.CollectVO>
     **/
    List<CollectVO> queryBycollectVO(CollectVO collectVO);
}