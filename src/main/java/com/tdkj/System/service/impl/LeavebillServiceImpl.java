package com.tdkj.System.service.impl;

import com.tdkj.System.dao.LeavebillDao;
import com.tdkj.System.entity.Leavebill;
import com.tdkj.System.service.LeavebillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hxy
 * @version 1.0
 * @date 2020/7/27 10:09
 */
@Service("leavebillService")
public class LeavebillServiceImpl implements LeavebillService {


        @Resource
        private LeavebillDao LeaveBillDao;

        /**
         * 通过ID查询单条数据
         *
         * @param id 主键
         * @return 实例对象
         */
        @Override
        public Leavebill queryById(Integer id) {
            return this.LeaveBillDao.queryById(id);
        }

        /**
         * 查询多条数据
         *
         * @param offset 查询起始位置
         * @param limit  查询条数
         * @return 对象列表
         */
        @Override
        public List<Leavebill> queryAllByLimit(int offset, int limit) {
            return this.LeaveBillDao.queryAllByLimit(offset, limit);
        }

        /**
         * 新增数据
         *
         * @param LeaveBill 实例对象
         * @return 实例对象
         */
        @Override
        public Leavebill insert(Leavebill LeaveBill) {
            this.LeaveBillDao.insert(LeaveBill);
            return LeaveBill;
        }

        /**
         * 修改数据
         *
         * @param LeaveBill 实例对象
         * @return 实例对象
         */
        @Override
        public Leavebill update(Leavebill LeaveBill) {
            this.LeaveBillDao.update(LeaveBill);
            return this.queryById(LeaveBill.getId());
        }

        /**
         * 通过主键删除数据
         *
         * @param id 主键
         * @return 是否成功
         */
        @Override
        public boolean deleteById(Integer id) {
            return this.LeaveBillDao.deleteById(id) > 0;
        }

    @Override
    public List<Leavebill> queryAllLeavebill(Leavebill leavebill) {
        return this.LeaveBillDao.queryAllLeaveBill(leavebill);
    }

}
