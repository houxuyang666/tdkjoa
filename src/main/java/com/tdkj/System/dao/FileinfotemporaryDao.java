package com.tdkj.System.dao;

import com.tdkj.System.entity.Fileinfotemporary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Fileinfotemporary)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-07 15:18:57
 */
public interface FileinfotemporaryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param fileinfoid 主键
     * @return 实例对象
     */
    Fileinfotemporary queryById(Integer fileinfoid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Fileinfotemporary> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param fileinfotemporary 实例对象
     * @return 对象列表
     */
    List<Fileinfotemporary> queryAll(Fileinfotemporary fileinfotemporary);

    /**
     * 新增数据
     *
     * @param fileinfotemporary 实例对象
     * @return 影响行数
     */
    int insert(Fileinfotemporary fileinfotemporary);

    /**
     * 修改数据
     *
     * @param fileinfotemporary 实例对象
     * @return 影响行数
     */
    int update(Fileinfotemporary fileinfotemporary);

    /**
     * 通过主键删除数据
     *
     * @param fileinfoid 主键
     * @return 影响行数
     */
    int deleteById(Integer fileinfoid);

    /**
     * @Author houxuyang
     * @Description //获取除了本次上传的文件外的其他文件url
     * @Date 15:34 2020/8/7
     * @Param [fileinfoid]
     * @return java.util.List<com.tdkj.System.entity.Fileinfotemporary>
     **/
    List<Fileinfotemporary> queryAlltemporaryUrl(Integer fileinfoid);
}