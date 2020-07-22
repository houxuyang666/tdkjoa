package com.tdkj.System.service.impl;

import com.tdkj.System.dao.CorpbasicinfoDao;
import com.tdkj.System.entity.Corpbasicinfo;
import com.tdkj.System.service.CorpbasicinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Corpbasicinfo)表服务实现类
 *
 * @author makejava
 * @since 2020-07-17 14:51:05
 */
@Service("corpbasicinfoService")
public class CorpbasicinfoServiceImpl implements CorpbasicinfoService {
    @Resource
    private CorpbasicinfoDao corpbasicinfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param corpid 主键
     * @return 实例对象
     */
    @Override
    public Corpbasicinfo queryById(Integer corpid) {
        return this.corpbasicinfoDao.queryById(corpid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Corpbasicinfo> queryAllByLimit(int offset, int limit) {
        return this.corpbasicinfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param corpbasicinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Corpbasicinfo insert(Corpbasicinfo corpbasicinfo) {
        this.corpbasicinfoDao.insert(corpbasicinfo);
        return corpbasicinfo;
    }

    /**
     * 修改数据
     *
     * @param corpbasicinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Corpbasicinfo update(Corpbasicinfo corpbasicinfo) {
        this.corpbasicinfoDao.update(corpbasicinfo);
        return this.queryById(corpbasicinfo.getCorpid());
    }

    /**
     * 通过主键删除数据
     *
     * @param corpid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer corpid) {
        return this.corpbasicinfoDao.deleteById(corpid) > 0;
    }

    @Override
    public Corpbasicinfo queryByCode(String corpcode) {
        return this.corpbasicinfoDao.queryByCode(corpcode);
    }

    @Override
    public Corpbasicinfo queryByemployeeId(Integer employeeid) {
        System.out.println("进入这里了吗");
        return this.corpbasicinfoDao.queryByemployeeId(employeeid);
    }
}