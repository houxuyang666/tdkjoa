package com.tdkj.System.service.impl;

import com.tdkj.System.entity.Information;
import com.tdkj.System.dao.InformationDao;
import com.tdkj.System.service.InformationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Information)表服务实现类
 *
 * @author makejava
 * @since 2020-08-11 09:53:40
 */
@Service("informationService")
public class InformationServiceImpl implements InformationService {
    @Resource
    private InformationDao informationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param infoid 主键
     * @return 实例对象
     */
    @Override
    public Information queryById(Integer infoid) {
        return this.informationDao.queryById(infoid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Information> queryAllByLimit(int offset, int limit) {
        return this.informationDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param information 实例对象
     * @return 实例对象
     */
    @Override
    public Information insert(Information information) {
        this.informationDao.insert(information);
        return information;
    }

    /**
     * 修改数据
     *
     * @param information 实例对象
     * @return 实例对象
     */
    @Override
    public Information update(Information information) {
        this.informationDao.update(information);
        return this.queryById(information.getInfoid());
    }

    /**
     * 通过主键删除数据
     *
     * @param infoid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer infoid) {
        return this.informationDao.deleteById(infoid) > 0;
    }
}