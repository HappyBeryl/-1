package dao;

import entiy.User;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    //3.删除用户
    public int delete(int id) {
        
    }

}
