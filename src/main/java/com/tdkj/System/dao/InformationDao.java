package com.tdkj.System.dao;

import com.tdkj.System.entity.Information;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Information)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-11 09:53:40
 */
public interface InformationDao {

    /**
     * 通过ID查询单条数据
     *
     * @param infoid 主键
     * @return 实例对象
     */
    Information queryById(Integer infoid);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<Information> queryAllByCropId(Integer Corpid);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param information 实例对象
     * @return 对象列表
     */
    List<Information> queryAll(Information information);

    /**
     * 新增数据
     *
     * @param information 实例对象
     * @return 影响行数
     */
    int insert(Information information);

    /**
     * 修改数据
     *
     * @param information 实例对象
     * @return 影响行数
     */
    int update(Information information);

    /**
     * 通过主键删除数据
     *
     * @param infoid 主键
     * @return 影响行数
     */
    int deleteById(Integer infoid);



}