package com.tdkj.System.service;

import com.tdkj.System.entity.Fileinfo;

import java.util.List;

/**
 * (Fileinfo)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
public interface FileinfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param fileinfoid 主键
     * @return 实例对象
     */
    Fileinfo queryById(Integer fileinfoid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Fileinfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param fileinfo 实例对象
     * @return 实例对象
     */
    Fileinfo insert(Fileinfo fileinfo);

    /**
     * 修改数据
     *
     * @param fileinfo 实例对象
     * @return 实例对象
     */
    Fileinfo update(Fileinfo fileinfo);

    /**
     * 通过主键删除数据
     *
     * @param fileinfoid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer fileinfoid);

    /**
     * @Author houxuyang
     * @Description 根据公司id查询公司所有合同
     * @Date 16:05 2020/8/5
     * @Param [fileinfo]
     * @return java.util.List<com.tdkj.System.entity.Fileinfo>
     **/
    List<Fileinfo> queryAll(Fileinfo fileinfo);
}