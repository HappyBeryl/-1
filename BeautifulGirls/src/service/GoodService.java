package service;

import dao.GoodDao;
import entiy.Good;
import entiy.PageBean;
import entiy.User;

import java.util.List;
import java.util.Map;

public class GoodService {
    //1.登陆
    public User login(User loginUser) {
        GoodDao userDao = new GoodDao();
        User user = userDao.login(loginUser);
        return user;
    }

    // 2.添加化妆品
    public int add(Good good) {
        GoodDao goodDao = new GoodDao();
        int ret = goodDao.add(good);
        return ret;
    }

    //3.删除化妆品
    public int delete(int id) {
        GoodDao goodDao = new GoodDao();
        int ret = goodDao.delete(id);
        return ret;
    }

    //4.查找化妆品
    public Good find(int id) {
        GoodDao goodDao = new GoodDao();
        Good good = goodDao.find(id);
        return good;
    }

    // 5.更新化妆品信息
    public int update(Good updateGood) {
        GoodDao userDao = new GoodDao();
        int i = userDao.update(updateGood);
        return i;
    }

    //6.分页查询所有用户信息+7.查询共有多少条记录
    //根据map的条件 进行查询
    /*
    需要确定：
    private int totalCount; //总记录数 12
    private int totalPage; //总页码 3
    private List<T> list; //每页中的数据 --》findByPage
    private int currentPage; //当前页码 --》currentPage 已知的
    private int rows; //每一页的记录数 5
     */

    public PageBean<Good> findAllByPage(int currentPage, int rows, Map<String, String[]> map) {
        PageBean<Good> pageBean = new PageBean<>();

        GoodDao goodDao = new GoodDao();
        int totalPage = 0; //页数
        int totalCount = goodDao.findAllRecord(map);//查询共有多少条记录
        // 总共的页数 totalPage
        if (totalCount % rows == 0) {
            totalPage = totalCount / rows;
        } else {
            totalPage = totalCount / rows + 1;
        }
        pageBean.setTotalPage(totalPage);

        pageBean.setTotalCount(totalCount);

        /*
        当前假设是第一页 （1-1）*5=0
        当前假设是第二页 （2-1）*5=5
         */
        int start = (currentPage-1)*rows;
        List<Good> userList = goodDao.findByPage(start, rows, map);
        pageBean.setList(userList);

        pageBean.setCurrentPage(currentPage);

        pageBean.setRows(rows);

        return pageBean;
    }


}

