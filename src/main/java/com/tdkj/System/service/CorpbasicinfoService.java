package com.tdkj.System.service;

import com.tdkj.System.entity.Corpbasicinfo;

import java.util.List;

/**
 * (Corpbasicinfo)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:51:05
 */
public interface CorpbasicinfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param corpid 主键
     * @return 实例对象
     */
    Corpbasicinfo queryById(Integer corpid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Corpbasicinfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param corpbasicinfo 实例对象
     * @return 实例对象
     */
    Corpbasicinfo insert(Corpbasicinfo corpbasicinfo);

    /**
     * 修改数据
     *
     * @param corpbasicinfo 实例对象
     * @return 实例对象
     */
    Corpbasicinfo update(Corpbasicinfo corpbasicinfo);

    /**
     * 通过主键删除数据
     *
     * @param corpid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer corpid);

    /**
     * @Author houxuyang
     * @Description //根据企业编号查询是否存在
     * @Date 9:28 2020/7/20
     * @Param [corpcode]
     * @return com.tdkj.System.entity.Corpbasicinfo
     **/
    Corpbasicinfo queryByCode(String corpcode);

    /**
     * @Author houxuyang
     * @Description //根据用户id查询用户所属公司信息
     * @Date 9:49 2020/7/21
     * @Param [employeeid]
     * @return com.tdkj.System.entity.Corpbasicinfo
     **/
    Corpbasicinfo queryByemployeeId(Integer employeeid);
}