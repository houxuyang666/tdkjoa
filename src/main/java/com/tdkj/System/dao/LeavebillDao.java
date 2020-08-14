package com.tdkj.System.dao;

import com.tdkj.System.entity.Leavebill;
import com.tdkj.System.entity.VO.LeavebillVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Leavebill)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-23 21:08:48
 */
public interface LeavebillDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Leavebill queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Leavebill> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param leavebill 实例对象
     * @return 对象列表
     */
    List<Leavebill> queryAll(Leavebill leavebill);

    /**
     * 新增数据
     *
     * @param leavebill 实例对象
     * @return 影响行数
     */
    int insert(Leavebill leavebill);

    /**
     * 修改数据
     *
     * @param leavebill 实例对象
     * @return 影响行数
     */
    int update(Leavebill leavebill);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    List<Leavebill> queryAllLeaveBill(Leavebill leavebill);

    /**
     * @Author houxuyang
     * @Description //根据条件查询该公司的所有考勤申请
     * @Date 16:09 2020/8/14
     * @Param [leavebillVO]
     * @return java.util.List<com.tdkj.System.entity.VO.LeavebillVO>
     **/
    List<LeavebillVO> queryAllByLeavebillVO(LeavebillVO leavebillVO);
}