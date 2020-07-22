package com.tdkj.System.service.impl;

import com.tdkj.System.dao.AttendancesynthesisDao;
import com.tdkj.System.entity.Attendancesynthesis;
import com.tdkj.System.service.AttendancesynthesisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Attendancesynthesis)表服务实现类
 *
 * @author makejava
 * @since 2020-07-17 14:51:06
 */
@Service("attendancesynthesisService")
public class AttendancesynthesisServiceImpl implements AttendancesynthesisService {
    @Resource
    private AttendancesynthesisDao attendancesynthesisDao;

    /**
     * 通过ID查询单条数据
     *
     * @param attensynid 主键
     * @return 实例对象
     */
    @Override
    public Attendancesynthesis queryById(Integer attensynid) {
        return this.attendancesynthesisDao.queryById(attensynid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Attendancesynthesis> queryAllByLimit(int offset, int limit) {
        return this.attendancesynthesisDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param attendancesynthesis 实例对象
     * @return 实例对象
     */
    @Override
    public Attendancesynthesis insert(Attendancesynthesis attendancesynthesis) {
        this.attendancesynthesisDao.insert(attendancesynthesis);
        return attendancesynthesis;
    }

    /**
     * 修改数据
     *
     * @param attendancesynthesis 实例对象
     * @return 实例对象
     */
    @Override
    public Attendancesynthesis update(Attendancesynthesis attendancesynthesis) {
        this.attendancesynthesisDao.update(attendancesynthesis);
        return this.queryById(attendancesynthesis.getAttensynid());
    }

    /**
     * 通过主键删除数据
     *
     * @param attensynid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer attensynid) {
        return this.attendancesynthesisDao.deleteById(attensynid) > 0;
    }
}