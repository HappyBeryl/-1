package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 更新彩妆信息-2.把查找到的彩妆信息再转化为json返回给前端
 */
@WebServlet("/returnServlet")
public class ReturnServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json;charset=utf-8");

        Object user = req.getSession().getAttribute("updataGood");

        //把登录成功的map返回给前端。json      : 便于前端进行处理。
        ObjectMapper objectMapper = new ObjectMapper();
        //就是将returnMap，转换为json字符串
        objectMapper.writeValue(resp.getWriter(),user);

    }
}
