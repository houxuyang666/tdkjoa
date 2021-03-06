package com.tdkj.System.service;

import com.tdkj.System.entity.Attendancesynthesis;

import java.util.List;

/**
 * (Attendancesynthesis)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:51:06
 */
public interface AttendancesynthesisService {

    /**
     * 通过ID查询单条数据
     *
     * @param attensynid 主键
     * @return 实例对象
     */
    Attendancesynthesis queryById(Integer attensynid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Attendancesynthesis> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param attendancesynthesis 实例对象
     * @return 实例对象
     */
    Attendancesynthesis insert(Attendancesynthesis attendancesynthesis);

    /**
     * 修改数据
     *
     * @param attendancesynthesis 实例对象
     * @return 实例对象
     */
    Attendancesynthesis update(Attendancesynthesis attendancesynthesis);

    /**
     * 通过主键删除数据
     *
     * @param attensynid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer attensynid);

}