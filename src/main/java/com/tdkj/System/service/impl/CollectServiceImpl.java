package com.tdkj.System.service.impl;

import com.tdkj.System.dao.CollectDao;
import com.tdkj.System.entity.Collect;
import com.tdkj.System.entity.VO.CollectVO;
import com.tdkj.System.service.CollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Collect)表服务实现类
 *
 * @author makejava
 * @since 2020-08-14 11:47:57
 */
@Service("collectService")
public class CollectServiceImpl implements CollectService {
    @Resource
    private CollectDao collectDao;

    /**
     * 通过ID查询单条数据
     *
     * @param collectid 主键
     * @return 实例对象
     */
    @Override
    public Collect queryById(String collectid) {
        return this.collectDao.queryById(collectid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Collect> queryAllByLimit(int offset, int limit) {
        return this.collectDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param collect 实例对象
     * @return 实例对象
     */
    @Override
    public Collect insert(Collect collect) {
        this.collectDao.insert(collect);
        return collect;
    }

    /**
     * 修改数据
     *
     * @param collect 实例对象
     * @return 实例对象
     */
    @Override
    public Collect update(Collect collect) {
        this.collectDao.update(collect);
        return this.queryById(collect.getCollectid());
    }

    /**
     * 通过主键删除数据
     *
     * @param collectid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String collectid) {
        return this.collectDao.deleteById(collectid) > 0;
    }

    /**
     * @Author houxuyang
     * @Description //根据条件查询本公司的领用记录
     * @Date 13:51 2020/8/14
     * @Param [collectVO]
     * @return java.util.List<com.tdkj.System.entity.VO.CollectVO>
     **/
    @Override
    public List<CollectVO> queryBycollectVO(CollectVO collectVO) {
        return this.collectDao.queryBycollectVO(collectVO);
    }
}