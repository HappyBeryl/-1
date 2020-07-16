package service;

import dao.UserDao;
import entiy.PageBean;
import entiy.User;

import java.util.List;
import java.util.Map;

public class UserService {

    //1.登陆
    public User login(User loginUser) {
        UserDao userDao = new UserDao();
        User user = userDao.login(loginUser);
        return user;
    }

    // 2.添加用户
    public int add(User user) {
        UserDao userDao = new UserDao();
        int ret = userDao.add(user);
        return ret;
    }

    //3.删除用户
    public int delete(int id) {
        UserDao userDao = new UserDao();
        int ret = userDao.delete(id);
        return ret;
    }

    //4.查找用户
    public User find(int id) {
        UserDao userDao = new UserDao();
        User user = userDao.find(id);
        return user;
    }

    // 5.更新用户
    public int update(User updateUser) {
        UserDao userDao = new UserDao();
        int i = userDao.update(updateUser);
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
    public PageBean<User> findAllByPage(int currentPage, int rows, Map<String, String[]> map) {
        PageBean<User> pageBean = new PageBean<>();

        UserDao userDao = new UserDao();
        int totalPage = 0; //页数
        int totalCount = userDao.findAllRecord(map);//查询共有多少条记录
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
        List<User> userList = userDao.findByPage(start, rows, map);
        pageBean.setList(userList);

        pageBean.setCurrentPage(currentPage);

        pageBean.setRows(rows);

        return pageBean;
    }

}
