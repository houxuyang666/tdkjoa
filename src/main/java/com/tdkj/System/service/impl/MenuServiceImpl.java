package com.tdkj.System.service.impl;

import com.tdkj.System.dao.MenuDao;
import com.tdkj.System.entity.Menu;
import com.tdkj.System.entity.MenuTree;
import com.tdkj.System.service.MenuService;
import com.tdkj.System.utils.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2020-07-17 14:51:01
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param menuid 主键
     * @return 实例对象
     */
    @Override
    public Menu queryById(Integer menuid) {
        return this.menuDao.queryById(menuid);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Menu> queryAllByLimit(int offset, int limit) {
        return this.menuDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    @Override
    public Menu insert(Menu menu) {
        this.menuDao.insert(menu);
        return menu;
    }

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    @Override
    public Menu update(Menu menu) {
        this.menuDao.update(menu);
        return this.queryById(menu.getMenuid());
    }

    /**
     * 通过主键删除数据
     *
     * @param menuid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer menuid) {
        return this.menuDao.deleteById(menuid) > 0;
    }

    @Override
    public List<Menu> findByIdGetPerms(Integer userid) {
        return this.menuDao.findByIdGetPerms(userid);
    }

    @Override
    public MenuTree<Menu> findByUsernameGetMenu(String username) {
        List<Menu> menus = this.menuDao.findByUsernameGetMenu(username);
        List<MenuTree<Menu>> trees = this.convertMenus(menus);
        return TreeUtil.buildMenuTree(trees);
    }


    /**
     *
     * @author Chang
     * @description
     * @date 2020/7/1 8:29
     * @return
     */
    private List<MenuTree<Menu>> convertMenus(List<Menu> menus) {
        List<MenuTree<Menu>> trees = new ArrayList<>();
        menus.forEach(menu -> {
            MenuTree<Menu> tree = new MenuTree<>();
            tree.setId(String.valueOf(menu.getMenuid()));
            tree.setParentId(String.valueOf(menu.getParentid()));
            tree.setTitle(menu.getTitle());
            tree.setIcon(menu.getIcon());
            tree.setHref(menu.getHref());
            tree.setData(menu);
            trees.add(tree);
        });


        return trees;
    }
}