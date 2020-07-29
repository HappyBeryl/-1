package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import entiy.Good;
import service.GoodService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 添加货物
 */
@WebServlet("/addServlet")
public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");

        String sort  = req.getParameter("sort");
        String brand = req.getParameter("brand");
        String match = req.getParameter("match");
        String style = req.getParameter("style");
        String bydata = req.getParameter("bydata");
        String expdata = req.getParameter("expdata");
        String likes = req.getParameter("likes");
        double likedouble = Double.parseDouble(likes);

        Good addGood = new Good();
        addGood.setSort(sort);
        addGood.setBrand(brand);
        addGood.setMatch(match);
        addGood.setStyle(style);
        addGood.setBydata(bydata);
        addGood.setExpdata(expdata);
        addGood.setLikes(likedouble);


        GoodService goodService = new GoodService();
        int ret = goodService.add(addGood);

        Map<String,Object> returnMap = new HashMap<>();
        if(ret == 1) {
            returnMap.put("msg",true);
        }else {
            returnMap.put("msg",false);
        }
        //把登录成功的map返回给前端。json      : 便于前端进行处理。
        ObjectMapper objectMapper = new ObjectMapper();
        //就是将returnMap，转换为json字符串
        objectMapper.writeValue(resp.getWriter(),returnMap);
    }
}
