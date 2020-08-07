package com.tdkj.System.service.impl;

import com.tdkj.System.dao.FileinfotemporaryDao;
import com.tdkj.System.entity.Fileinfotemporary;
import com.tdkj.System.service.FileinfotemporaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Fileinfotemporary)表服务实现类
 *
 * @author makejava
 * @since 2020-08-07 15:18:58
 */
@Service("fileinfotemporaryService")
public class FileinfotemporaryServiceImpl implements FileinfotemporaryService {
    @Resource
    private FileinfotemporaryDao fileinfotemporaryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param fileinfoid 主键
     * @return 实例对象
     */
    @Override
    public Fileinfotemporary queryById(Integer fileinfoid) {
        return this.fileinfotemporaryDao.queryById(fileinfoid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Fileinfotemporary> queryAllByLimit(int offset, int limit) {
        return this.fileinfotemporaryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param fileinfotemporary 实例对象
     * @return 实例对象
     */
    @Override
    public Fileinfotemporary insert(Fileinfotemporary fileinfotemporary) {
        this.fileinfotemporaryDao.insert(fileinfotemporary);
        return fileinfotemporary;
    }

    /**
     * 修改数据
     *
     * @param fileinfotemporary 实例对象
     * @return 实例对象
     */
    @Override
    public Fileinfotemporary update(Fileinfotemporary fileinfotemporary) {
        this.fileinfotemporaryDao.update(fileinfotemporary);
        return this.queryById(fileinfotemporary.getFileinfoid());
    }

    /**
     * 通过主键删除数据
     *
     * @param fileinfoid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer fileinfoid) {
        return this.fileinfotemporaryDao.deleteById(fileinfoid) > 0;
    }


    @Override
    public List<Fileinfotemporary> queryAlltemporaryUrl(Integer fileinfoid) {
        return this.fileinfotemporaryDao.queryAlltemporaryUrl(fileinfoid);
    }
}