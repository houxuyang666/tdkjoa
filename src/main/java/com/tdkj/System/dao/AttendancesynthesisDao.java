package com.tdkj.System.dao;

import com.tdkj.System.entity.Attendancesynthesis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Attendancesynthesis)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-17 14:51:06
 */
public interface AttendancesynthesisDao {

    /**
     * 通过ID查询单条数据
     *
     * @param attensynid 主键
     * @return 实例对象
     */
    Attendancesynthesis queryById(Integer attensynid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Attendancesynthesis> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param attendancesynthesis 实例对象
     * @return 对象列表
     */
    List<Attendancesynthesis> queryAll(Attendancesynthesis attendancesynthesis);

    /**
     * 新增数据
     *
     * @param attendancesynthesis 实例对象
     * @return 影响行数
     */
    int insert(Attendancesynthesis attendancesynthesis);

    /**
     * 修改数据
     *
     * @param attendancesynthesis 实例对象
     * @return 影响行数
     */
    int update(Attendancesynthesis attendancesynthesis);

    /**
     * 通过主键删除数据
     *
     * @param attensynid 主键
     * @return 影响行数
     */
    int deleteById(Integer attensynid);

}