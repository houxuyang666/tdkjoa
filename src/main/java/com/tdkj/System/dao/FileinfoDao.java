package com.tdkj.System.dao;

import com.tdkj.System.entity.Fileinfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (Fileinfo)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-17 14:51:03
 */
public interface FileinfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param fileinfoid 主键
     * @return 实例对象
     */
    Fileinfo queryById(Integer fileinfoid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Fileinfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param fileinfo 实例对象
     * @return 对象列表
     */
    List<Fileinfo> queryAll(Fileinfo fileinfo);

    /**
     * 新增数据
     *
     * @param fileinfo 实例对象
     * @return 影响行数
     */
    int insert(Fileinfo fileinfo);

    /**
     * 修改数据
     *
     * @param fileinfo 实例对象
     * @return 影响行数
     */
    int update(Fileinfo fileinfo);

    /**
     * 通过主键删除数据
     *
     * @param fileinfoid 主键
     * @return 影响行数
     */
    int deleteById(Integer fileinfoid);

    /**
     * @Author houxuyang
     * @Description //向数据表中插入该文件的原始名称
     * @Date 11:43 2020/8/7
     * @Param [fileinfoid, url]
     * @return void
     **/
    void insertTemporary(Integer fileinfoid, String url, Date date);
    /**
     * @Author houxuyang
     * @Description //查询临时数据表中的url
     * @Date 13:08 2020/8/7
     * @Param [fileinfoid]
     * @return java.lang.String
     **/
    String querytemporaryById(Integer fileinfoid);

    /**
     * @Author houxuyang
     * @Description //同时删除临时表中数据
     * @Date 13:08 2020/8/7
     * @Param [fileinfoid]
     * @return void
     **/
    void deletetemporaryById(Integer fileinfoid);
}