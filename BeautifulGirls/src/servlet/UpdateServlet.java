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
 更新彩妆信息-3.对彩妆信息进行更新
 */

@WebServlet("/updateServlet")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sort  = req.getParameter("sort");
        String brand = req.getParameter("brand");
        String match = req.getParameter("match");
        String style = req.getParameter("style");
        String bydata = req.getParameter("bydata");
        String expdata = req.getParameter("expdata");
        String likes = req.getParameter("likes");
        double likedouble = Double.parseDouble(likes);


        Object good1 = req.getSession().getAttribute("updataGood");
        Good good = (Good) good1;

        Good updataGood = new Good();
        updataGood.setId(good.getId());
        updataGood.setSort(sort);
        updataGood.setBrand(brand);
        updataGood.setMatch(match);
        updataGood.setStyle(style);
        updataGood.setBydata(bydata);
        updataGood.setExpdata(expdata);
        updataGood.setLikes(likedouble);

        GoodService userService = new GoodService();
        int ret = userService.update(updataGood);

        Map<String,Object> returnMap = new HashMap<>();
        if(ret == 1) {
            System.out.println("ok");
            returnMap.put("msg",true);
        }else {
            System.out.println("no");
            returnMap.put("msg",false);
        }
        //把登录成功的map返回给前端。json      : 便于前端进行处理。
        ObjectMapper objectMapper = new ObjectMapper();
        //就是将returnMap，转换为json字符串
        objectMapper.writeValue(resp.getWriter(),returnMap);
    }
}

