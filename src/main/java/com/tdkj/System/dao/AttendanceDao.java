package com.tdkj.System.dao;

import com.tdkj.System.entity.Attendance;
import com.tdkj.System.entity.VO.AttendanceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Attendance)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-17 14:51:08
 */
public interface AttendanceDao {

    /**
     * 通过ID查询单条数据
     *
     * @param attendanceid 主键
     * @return 实例对象
     */
    Attendance queryById(Integer attendanceid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Attendance> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param attendance 实例对象
     * @return 对象列表
     */
    List<Attendance> queryAll(Attendance attendance);

    /**
     * 新增数据
     *
     * @param attendance 实例对象
     * @return 影响行数
     */
    int insert(Attendance attendance);

    /**
     * 修改数据
     *
     * @param attendance 实例对象
     * @return 影响行数
     */
    int update(Attendance attendance);

    /**
     * 通过主键删除数据
     *
     * @param attendanceid 主键
     * @return 影响行数
     */
    int deleteById(Integer attendanceid);

    /**
     * @Author houxuyang
     * @Description //查询今天是否签到
     * @Date 16:39 2020/7/20
     * @Param [userid, today]
     * @return com.tdkj.System.entity.Attendance
     **/
    Attendance queryByIdAndData(@Param("userid")Integer userid,@Param("today") String today);

    /**
     * @Author houxuyang
     * @Description //查询员工签到情况
     * @Date 10:33 2020/8/12
     * @Param [year, month, corpid]
     * @return java.util.List<com.tdkj.System.entity.VO.AttendanceVO>
     **/
    List<AttendanceVO> queryAllData(String year, String month, Integer corpid, String name);
}