package dao;

import entiy.Good;
import entiy.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoodDao {
    //1.用户登陆
    public  User login(User loginUser) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        String sql = "select * from users where username=? and password=?";

        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, loginUser.getUsername());
            ps.setString(2,loginUser.getPassword());
            rs = ps.executeQuery();

            if (rs.next()) {
                //用户的组装
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            } else {
                System.out.println("登陆失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, rs);
        }
        return user;
    }

    /*
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("baiqiong");
        user.setPassword("678");
        if (login(user)==null) {
            System.out.println("登陆失败");
        } else {
            System.out.println("登陆成功");
        }
    }*/

    //2.添加美妆产品
    public  int add(Good addGood) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "insert into goodmessage(`sort`, `brand`, `style`,`match`,`bydata`,`expdata`,`likes`) values (?,?,?,?,?,?,?)";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setString(1, addGood.getSort());
            ps.setString(2, addGood.getBrand());
            ps.setString(3, addGood.getStyle());
            ps.setString(4, addGood.getMatch());
            ps.setString(5, addGood.getBydata());
            ps.setString(6, addGood.getExpdata());
            ps.setDouble(7, addGood.getLikes());

            int ret = ps.executeUpdate();
            return ret;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, rs);
        }
        //添加失败
        return 0;
    }

    /*
    public static void main(String[] args) {
        Good good = new Good();
        good.setSort("口红");
        good.setBrand("mac");
        good.setStyle("女神");
        good.setMatch("深色系服饰");
        good.setBydata("2019-12");
        good.setExpdata("2021-12");
        good.setLikes(0.2);

        int ret = add(good);
        if (ret == 0) {
            System.out.println("插入失败");
        }else {
            System.out.println("添加成功");
        }
    }*/

    //3.删除化妆品信息
    public  int delete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "delete from goodmessage where id=?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);

            int ret = ps.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, rs);
        }
        //删除失败
        return 0;
    }

    /*
    public static void main(String[] args) {
        int ret = delete(10);
        if (ret == 0) {
            System.out.println("删除失败");
        }else {
            System.out.println("删除成功");
        }
    }*/


    //4.查找商品
    public  Good find(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Good good = null;

        String sql = "select * from goodmessage where id=?";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                good = new Good();
                good.setId(rs.getInt("id"));
                good.setSort(rs.getString("sort"));
                good.setBrand(rs.getString("brand"));
                good.setStyle(rs.getString("style"));
                good.setMatch(rs.getString("match"));
                good.setBydata(rs.getString("bydata"));
                good.setExpdata(rs.getString("expdata"));
                good.setLikes(rs.getDouble("likes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, rs);
        }
        return good;
    }

    /*
    public static void main(String[] args) {
        Good good = find(9);
        System.out.println(good);
    }*/


    //5.更新美妆产品信息,给一个美妆产品对象
    public static int update(Good updataGood) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            String sql = "update goodmessage set `sort`=?,`brand`=?,`style`=?,`match`=?,`bydata`=?,`expdata`=?,likes=? where id=?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setString(1, updataGood.getSort());
            ps.setString(2, updataGood.getBrand());
            ps.setString(3, updataGood.getStyle());
            ps.setString(4, updataGood.getMatch());
            ps.setString(5, updataGood.getBydata());
            ps.setString(6, updataGood.getExpdata());
            ps.setDouble(7, updataGood.getLikes());
            ps.setInt(8,updataGood.getId());

            int ret = ps.executeUpdate();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps,null);
        }
        //更新失败
        return 0;
    }

    /*
    public static void main(String[] args) {
        Good good = new Good();
        good.setId(1);
        good.setSort("口红");
        good.setBrand("迪奥");
        good.setStyle("御姐");
        good.setMatch("深色系服饰");
        good.setBydata("2019-12");
        good.setExpdata("2021-12");
        good.setLikes(0.8);

        int ret = update(good);
        if (ret == 0) {
            System.out.println("更新失败");
        } else {
            System.out.println("更新成功");
        }
    }*/


    //6.分页查询所有货物信息（查询当前条件下所有货物的信息）
    //条件随机组合，也可以不给
    /*
     start：开始查询的起始位置
     rows：共查询的记录
     map：包含：currentPage、rows、sort、brand、style
     */
    public List<Good> findByPage(int start, int rows, Map<String, String[]> map) {

        List<Good> goodList = new ArrayList<>();

        String sql = "select * from goodmessage where  1=1";

        // String sql = "select * from usermessage where 1=1
        // and name like ? and email like ? limit ?,?"

        StringBuffer sb = new StringBuffer(sql);

        List<Object> list = new ArrayList<>();

        Set<String> keySet = map.keySet();
        for (String key: keySet) {
            String value = map.get(key)[0];
            if (value != null && !"".equals(value)) {
                sb.append(" and ").append(key).append(" like ? ");
                list.add("%"+value+"%");
            }
        }
        sb.append(" limit ?,? ");
        list.add(start);
        list.add(rows);

        System.out.println("sql" + sb);
        System.out.println("list" + list);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sb.toString());
            //给sql语句赋值
            setValues(ps,list.toArray());
            rs = ps.executeQuery();
            //结果集可能有多条数据
            while (rs.next()) {
                //商品的组装
                Good good = new Good();
                good.setId(rs.getInt("id"));
                good.setSort(rs.getString("sort"));
                good.setBrand(rs.getString("brand"));
                good.setStyle(rs.getString("style"));
                good.setMatch(rs.getString("match"));
                good.setBydata(rs.getString("bydata"));
                good.setExpdata(rs.getString("expdata"));
                good.setLikes(rs.getDouble("likes"));
                goodList.add(good);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return  goodList;
    }

    private static void setValues(PreparedStatement ps, Object... array) throws SQLException {
        for (int i = 0; i < array.length; i++) {
            //ps下标从1开始
            ps.setObject(i+1, array[i]);
        }
    }

    //7.查询共有多少条记录
    // map 包含 sort brand style
    public  int findAllRecord(Map<String, String[]> map){
        int count = 0;
        String sql = "select count(*) from goodmessage where  1=1";
        StringBuffer sb = new StringBuffer(sql);
        List<Object> list = new ArrayList<>();
        Set<String> keySet = map.keySet();
        for (String key: keySet) {
            String value = map.get(key)[0];
            if (value != null && !"".equals(value)) {
                sb.append(" and ").append(key).append(" like ? ");
                list.add("%"+value+"%");
            }
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sb.toString());
            //给sql语句赋值
            setValues(ps,list.toArray());
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return count;
    }

}
