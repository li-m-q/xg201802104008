package cn.edu.sdjzu.xg.bysj.controller.login;

import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.service.UserService;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        JSONObject message = new JSONObject();
        try{
            User loggerUser = UserService.getInstance().login(username,password);
            if (loggerUser != null) {
                message.put("message","登陆成功");
                HttpSession session = req.getSession();
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("currentUser",loggerUser);
                resp.getWriter().println(message);
                return;
            } else{
                message.put("message","用户名或密码错误");
            }
        }catch (SQLException e){
            message.put("message","数据库操作异常");
        }
        resp.getWriter().println(message);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session  = request.getSession();
        session.invalidate();
        //返回登陆页面，提醒用户信息
        request.setAttribute("msg","您已经安全退出");
        request.getRequestDispatcher("/WEB-INF/pages/login/login.jsp").forward(request,response);
   }
}
