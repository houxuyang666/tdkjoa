package com.tdkj.System.service.impl;

import com.tdkj.System.dao.AttendanceDao;
import com.tdkj.System.entity.Attendance;
import com.tdkj.System.entity.VO.AttendanceVO;
import com.tdkj.System.service.AttendanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Attendance)表服务实现类
 *
 * @author makejava
 * @since 2020-07-17 14:51:09
 */
@Service("attendanceService")
public class AttendanceServiceImpl implements AttendanceService {
    @Resource
    private AttendanceDao attendanceDao;

    /**
     * 通过ID查询单条数据
     *
     * @param attendanceid 主键
     * @return 实例对象
     */
    @Override
    public Attendance queryById(Integer attendanceid) {
        return this.attendanceDao.queryById(attendanceid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Attendance> queryAllByLimit(int offset, int limit) {
        return this.attendanceDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param attendance 实例对象
     * @return 实例对象
     */
    @Override
    public Attendance insert(Attendance attendance) {
        this.attendanceDao.insert(attendance);
        return attendance;
    }

    /**
     * 修改数据
     *
     * @param attendance 实例对象
     * @return 实例对象
     */
    @Override
    public Attendance update(Attendance attendance) {
        this.attendanceDao.update(attendance);
        return this.queryById(attendance.getAttendanceid());
    }

    /**
     * 通过主键删除数据
     *
     * @param attendanceid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer attendanceid) {
        return this.attendanceDao.deleteById(attendanceid) > 0;
    }

    @Override
    public Attendance queryByIdAndData(Integer userid, String today) {
        return this.attendanceDao.queryByIdAndData(userid,today);
    }

    @Override
    public List<AttendanceVO> queryAllData(String year, String month, Integer corpid, String name) {
        return this.attendanceDao.queryAllData(year,month,corpid,name);
    }
}