package servlet;

import entiy.Good;
import service.GoodService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 修改彩妆信息-1.在数据库中查找彩妆并返回
 */
@WebServlet("/findGoodServlet")
public class FindGoodServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String idString = req.getParameter("id");
        int id = Integer.parseInt(idString);
        GoodService goodService = new GoodService();
        Good good = goodService.find(id);
        if(good == null) {
            System.out.println("没有要修改的对象！");
        }else {
            req.getSession().setAttribute("updataGood",good);
            resp.sendRedirect("/goodmanager/update.html");
        }
    }
}
