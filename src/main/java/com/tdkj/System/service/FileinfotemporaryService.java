package com.tdkj.System.service;

import com.tdkj.System.entity.Fileinfotemporary;

import java.util.List;

/**
 * (Fileinfotemporary)表服务接口
 *
 * @author makejava
 * @since 2020-08-07 15:18:58
 */
public interface FileinfotemporaryService {

    /**
     * 通过ID查询单条数据
     *
     * @param fileinfoid 主键
     * @return 实例对象
     */
    Fileinfotemporary queryById(Integer fileinfoid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Fileinfotemporary> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param fileinfotemporary 实例对象
     * @return 实例对象
     */
    Fileinfotemporary insert(Fileinfotemporary fileinfotemporary);

    /**
     * 修改数据
     *
     * @param fileinfotemporary 实例对象
     * @return 实例对象
     */
    Fileinfotemporary update(Fileinfotemporary fileinfotemporary);

    /**
     * 通过主键删除数据
     *
     * @param fileinfoid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer fileinfoid);
    /**
     * @Author houxuyang
     * @Description //获取除了本次上传的文件外的其他文件url
     * @Date 15:28 2020/8/7
     * @Param [fileinfoid]
     * @return java.util.List<java.lang.String>
     **/
    List<Fileinfotemporary> queryAlltemporaryUrl(Integer fileinfoid);
}