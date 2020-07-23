package servlet;

import com.sun.net.httpserver.HttpsServer;
import entiy.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginServlet")

public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username+password);

        User loginUser =new User(); //创建一个数据库实体类对象
        loginUser.setUsername(username);
        loginUser.setPassword(password);

        UserService userService=new UserService(); //创建service层的对象

        User user = userService.login(loginUser);


    }
}
