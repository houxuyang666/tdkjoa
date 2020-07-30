package com.tdkj.System.dao;

import com.tdkj.System.entity.Procurement;
import com.tdkj.System.entity.VO.ProcurementVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Procurement)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-17 14:50:56
 */
public interface ProcurementDao {

    /**
     * 通过ID查询单条数据
     *
     * @param proid 主键
     * @return 实例对象
     */
    Procurement queryById(String proid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Procurement> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param procurement 实例对象
     * @return 对象列表
     */
    List<Procurement> queryAll(Procurement procurement);

    /**
     * 新增数据
     *
     * @param procurement 实例对象
     * @return 影响行数
     */
    int insert(Procurement procurement);

    /**
     * 修改数据
     *
     * @param procurement 实例对象
     * @return 影响行数
     */
    int update(Procurement procurement);

    /**
     * 通过主键删除数据
     *
     * @param proid 主键
     * @return 影响行数
     */
    int deleteById(String proid);

    /**
     * @Author houxuyang
     * @Description //根据用户id查询所申请的采购单
     * @Date 15:06 2020/7/30
     * @Param [userid]
     * @return java.util.List<com.tdkj.System.entity.Procurement>
     **/
    List<ProcurementVO> queryByApplicantId(Integer userid);
    /**
     * @Author houxuyang
     * @Description //根据采购单ID查询信息，返回到页面
     * @Date 16:39 2020/7/30
     * @Param [proid]
     * @return com.tdkj.System.entity.VO.ProcurementVO
     **/
    ProcurementVO queryProVOByProId(String proid);
}