package com.tdkj.System.service;

import com.tdkj.System.entity.Attendance;
import com.tdkj.System.entity.VO.AttendanceVO;

import java.util.List;

/**
 * (Attendance)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:51:09
 */
public interface AttendanceService {

    /**
     * 通过ID查询单条数据
     *
     * @param attendanceid 主键
     * @return 实例对象
     */
    Attendance queryById(Integer attendanceid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Attendance> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param attendance 实例对象
     * @return 实例对象
     */
    Attendance insert(Attendance attendance);

    /**
     * 修改数据
     *
     * @param attendance 实例对象
     * @return 实例对象
     */
    Attendance update(Attendance attendance);

    /**
     * 通过主键删除数据
     *
     * @param attendanceid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer attendanceid);

    /**
     * @Author houxuyang
     * @Description //查询今天是否签到
     * @Date 16:39 2020/7/20
     * @Param [userid, today]
     * @return com.tdkj.System.entity.Attendance
     **/
    Attendance queryByIdAndData(Integer userid, String today);

    /**
     * @Author houxuyang
     * @Description //查询公司员工签到情况
     * @Date 10:33 2020/8/12
     * @Param [year, month, corpid]
     * @return java.util.List<com.tdkj.System.entity.VO.AttendanceVO>
     **/
    List<AttendanceVO> queryAllData(String year, String month, Integer corpid, String name);
}