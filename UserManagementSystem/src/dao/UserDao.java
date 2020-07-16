package dao;

import entiy.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDao {

    //1.用户登陆
    public  User login(User loginUser) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        String sql = "select * from usermessage where username=? and password=?";

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
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
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

    //2.添加用户
    public int add(User addUser) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            String sql = "insert into usermessage(name, username, password, gender, age, address, qq, email) values (?,?,?,?,?,?,?,?)";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);

            ps.setString(1, addUser.getName());
            ps.setString(2, addUser.getUsername());
            ps.setString(3, addUser.getPassword());
            ps.setString(4, addUser.getGender());
            ps.setInt(5, addUser.getAge());
            ps.setString(6, addUser.getAddress());
            ps.setString(7, addUser.getQq());
            ps.setString(8, addUser.getEmail());

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

    //3.删除用户信息
    public int delete(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "delete from usermessage where id=?";
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

    //4.查找用户
    public User find(int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        String sql = "select * from usermessage where id=?";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));

                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, ps, rs);
        }
        return user;
    }

    //5.更新用户信息,给一个用户对象
    public int update(User updUser) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            String sql = "update usermessage set gender=?,age=?,address=?,qq=?,email=? where id=?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1,updUser.getGender());
            ps.setInt(2,updUser.getAge());
            ps.setString(3,updUser.getAddress());
            ps.setString(4,updUser.getQq());
            ps.setString(5,updUser.getEmail());
            ps.setInt(6,updUser.getId());

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

    //6.分页查询所有用户信息（查询当前条件下所有用户的信息）
    //条件随机组合，也可以不给
    /*
     start：开始查询的起始位置
     rows：共查询的记录
     map：包含：currentPage、rows、name、address、email
     {
        name:"bq"
        address:"陕西"
        email:"123..."
     }
     */
    public  List<User> findByPage(int start, int rows, Map<String, String[]> map) {

        List<User> userList = new ArrayList<>();

        String sql = "select * from usermessage where  1=1";

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
                //用户的组装
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,ps,rs);
        }
        return  userList;
    }

    private static void setValues(PreparedStatement ps, Object... array) throws SQLException {
        for (int i = 0; i < array.length; i++) {
            //ps下标从1开始
            ps.setObject(i+1, array[i]);
        }
    }

    //7.查询共有多少条记录
    // map 包含 name address email
    public  int findAllRecord(Map<String, String[]> map){
        int count = 0;
        String sql = "select count(*) from usermessage where  1=1";
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

   /* public static void main(String[] args) {
        Map<String, String[]> map = new HashMap<>();
        String[] names = {""};
        map.put("name",names);
        String[] addresses = {""};
        map.put("address",addresses);
        String[] emails = {""};
        map.put("emails",emails);

//        System.out.println(findAllRecord(map));
//        List<User> userList = findByPage(0,5,map);
//        for (User user:userList) {
//            System.out.println(user);
//        }
    }*/



}
