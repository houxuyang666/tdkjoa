package com.tdkj.System.service;

import com.tdkj.System.entity.Log;

import java.util.List;

/**
 * 日志表(Log)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
public interface LogService {

    /**
     * 通过ID查询单条数据
     *
     * @param logid 主键
     * @return 实例对象
     */
    Log queryById(Integer logid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Log> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param log 实例对象
     * @return 实例对象
     */
    Log insert(Log log);

    /**
     * 修改数据
     *
     * @param log 实例对象
     * @return 实例对象
     */
    Log update(Log log);

    /**
     * 通过主键删除数据
     *
     * @param logid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer logid);

}