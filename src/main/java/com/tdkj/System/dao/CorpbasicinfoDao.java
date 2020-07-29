package com.tdkj.System.dao;

import com.tdkj.System.entity.Corpbasicinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Corpbasicinfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-17 14:51:05
 */
public interface CorpbasicinfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param corpid 主键
     * @return 实例对象
     */
    Corpbasicinfo queryById(Integer corpid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Corpbasicinfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param corpbasicinfo 实例对象
     * @return 对象列表
     */
    List<Corpbasicinfo> queryAll(Corpbasicinfo corpbasicinfo);

    /**
     * 新增数据
     *
     * @param corpbasicinfo 实例对象
     * @return 影响行数
     */
    int insert(Corpbasicinfo corpbasicinfo);

    /**
     * 修改数据
     *
     * @param corpbasicinfo 实例对象
     * @return 影响行数
     */
    int update(Corpbasicinfo corpbasicinfo);

    /**
     * 通过主键删除数据
     *
     * @param corpid 主键
     * @return 影响行数
     */
    int deleteById(Integer corpid);

    /**
     * @return com.tdkj.System.entity.Corpbasicinfo
     * @Author houxuyang
     * @Description //企业编号查询企业是否存在
     * @Date 9:29 2020/7/20
     * @Param [corpcode]
     **/
    Corpbasicinfo queryByCode(String corpcode);

    /**
     * @return com.tdkj.System.entity.Corpbasicinfo
     * @Author houxuyang
     * @Description //根据用户id查询用户所属公司信息
     * @Date 9:49 2020/7/21
     * @Param [employeeid]
     **/
    Corpbasicinfo queryByemployeeId(Integer employeeid);

    /**
     * @return java.util.List<com.tdkj.System.entity.Corpbasicinfo>
     * @author Chang
     * @description
     * @date 2020/7/28 15:43
     */
    List<Corpbasicinfo> queryAlls();
}