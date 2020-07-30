package com.tdkj.System.service.impl;

import com.tdkj.System.dao.ProcurementDao;
import com.tdkj.System.entity.Procurement;
import com.tdkj.System.entity.VO.ProcurementVO;
import com.tdkj.System.service.ProcurementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Procurement)表服务实现类
 *
 * @author makejava
 * @since 2020-07-17 14:50:57
 */
@Service("procurementService")
public class ProcurementServiceImpl implements ProcurementService {
    @Resource
    private ProcurementDao procurementDao;

    /**
     * 通过ID查询单条数据
     *
     * @param proid 主键
     * @return 实例对象
     */
    @Override
    public Procurement queryById(String proid) {
        return this.procurementDao.queryById(proid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Procurement> queryAllByLimit(int offset, int limit) {
        return this.procurementDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param procurement 实例对象
     * @return 实例对象
     */
    @Override
    public Procurement insert(Procurement procurement) {
        this.procurementDao.insert(procurement);
        return procurement;
    }

    /**
     * 修改数据
     *
     * @param procurement 实例对象
     * @return 实例对象
     */
    @Override
    public Procurement update(Procurement procurement) {
        this.procurementDao.update(procurement);
        return this.queryById(procurement.getProid());
    }

    /**
     * 通过主键删除数据
     *
     * @param proid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String proid) {
        return this.procurementDao.deleteById(proid) > 0;
    }

    /**
     * @Author houxuyang
     * @Description //根据用户id查询所申请的采购单
     * @Date 15:06 2020/7/30
     * @Param [userid]
     * @return java.util.List<com.tdkj.System.entity.Procurement>
     **/
    @Override
    public List<ProcurementVO> queryByApplicantId(Integer userid) {
        return this.procurementDao.queryByApplicantId(userid);
    }

    /**
     * @Author houxuyang
     * @Description //根据采购单ID查询信息，返回到页面
     * @Date 16:38 2020/7/30
     * @Param [proid]
     * @return com.tdkj.System.entity.VO.ProcurementVO
     **/
    @Override
    public ProcurementVO queryProVOByProId(String proid) {
        return this.procurementDao.queryProVOByProId(proid);
    }
}