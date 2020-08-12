package com.tdkj.System.service;

import com.tdkj.System.entity.Information;
import java.util.List;

/**
 * (Information)表服务接口
 *
 * @author makejava
 * @since 2020-08-11 09:53:40
 */
public interface InformationService {

    /**
     * 通过ID查询单条数据
     *
     * @param infoid 主键
     * @return 实例对象
     */
    Information queryById(Integer infoid);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<Information> queryAllByCropId(Integer Corpid);

    /**
     * 新增数据
     *
     * @param information 实例对象
     * @return 实例对象
     */
    Information insert(Information information);

    /**
     * 修改数据
     *
     * @param information 实例对象
     * @return 实例对象
     */
    Information update(Information information);

    /**
     * 通过主键删除数据
     *
     * @param infoid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer infoid);


    List<Information> queryAll(Information information);



}