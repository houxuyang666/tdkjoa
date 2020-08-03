package com.tdkj.System.dao;

import com.tdkj.System.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Menu)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-17 14:50:59
 */
public interface MenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param menuid 主键
     * @return 实例对象
     */
    Menu queryById(Integer menuid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Menu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param menu 实例对象
     * @return 对象列表
     */
    List<Menu> queryAll(Menu menu);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int insert(Menu menu);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int update(Menu menu);

    /**
     * 通过主键删除数据
     *
     * @param menuid 主键
     * @return 影响行数
     */
    int deleteById(Integer menuid);

    /**
     * @Author houxuyang
     * @Description //根据用户id查询用户权限
     * @Date 15:07 2020/7/17
     * @Param [userid]
     * @return java.util.List<com.tdkj.System.entity.Menu>
     **/
    List<Menu> findByIdGetPerms(Integer userid);

    List<Menu> findByUsernameGetMenu(String username);

    /*获取所有菜单*/
    List<Menu> findMenusAllMenu();

    /*添加时直接设置在权限管理表中插入关联*/
    int insertroleAndmenu(@Param("roleid")Integer roleid, @Param("menuid")Integer menuid);

    /*删除菜单及关联关系*/
    void deleteroleAndmenuById(Integer menuId);
}