package com.tdkj.System.service.impl;

import com.tdkj.System.dao.FileinfoDao;
import com.tdkj.System.entity.Fileinfo;
import com.tdkj.System.service.FileinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Fileinfo)表服务实现类
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
@Service("fileinfoService")
public class FileinfoServiceImpl implements FileinfoService {
    @Resource
    private FileinfoDao fileinfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param fileinfoid 主键
     * @return 实例对象
     */
    @Override
    public Fileinfo queryById(Integer fileinfoid) {
        return this.fileinfoDao.queryById(fileinfoid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Fileinfo> queryAllByLimit(int offset, int limit) {
        return this.fileinfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param fileinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Fileinfo insert(Fileinfo fileinfo) {
        this.fileinfoDao.insert(fileinfo);
        return fileinfo;
    }

    /**
     * 修改数据
     *
     * @param fileinfo 实例对象
     * @return 实例对象
     */
    @Override
    public Fileinfo update(Fileinfo fileinfo) {
        this.fileinfoDao.update(fileinfo);
        return this.queryById(fileinfo.getFileinfoid());
    }

    /**
     * 通过主键删除数据
     *
     * @param fileinfoid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer fileinfoid) {
        return this.fileinfoDao.deleteById(fileinfoid) > 0;
    }

    @Override
    public List<Fileinfo> queryAll(Fileinfo fileinfo) {
        return this.fileinfoDao.queryAll(fileinfo);
    }
}