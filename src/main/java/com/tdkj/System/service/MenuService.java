package com.tdkj.System.service;

import com.tdkj.System.entity.Menu;
import com.tdkj.System.entity.MenuTree;
import com.tdkj.System.entity.RoleMenu;

import java.util.List;

/**
 * (Menu)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 14:51:00
 */
public interface MenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param menuid 主键
     * @return 实例对象
     */
    Menu queryById(Integer menuid);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Menu> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    Menu insert(Menu menu);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    Menu update(Menu menu);

    /**
     * 通过主键删除数据
     *
     * @param menuid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer menuid);

    /**
     * @Author houxuyang
     * @Description //通过id查询用户权限
     * @Date 15:06 2020/7/17
     * @Param [userid]
     * @return java.util.List<com.tdkj.System.entity.Menu>
     **/
    List<Menu> findByIdGetPerms(Integer userid);

    MenuTree<Menu> findByUsernameGetMenu(String username);

    /*获取所有菜单*/
    List<Menu> findMenusAllMenu();
    /*添加时直接设置在权限管理表中插入关联*/
    int insertroleAndmenu(Integer roleid, Integer menuid);
    /*查询该目录所对应的所有角色*/
    List<RoleMenu> queryroleAndmenuById(Integer menuId);
}