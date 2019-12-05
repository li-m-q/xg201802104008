package cn.edu.sdjzu.xg.bysj.controller.basic.user;

import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

    /**
     * @description 登录请求处理类
     * @author WANGZIC
     */
    @WebServlet("/LoginServlet")
    public class UserController extends HttpServlet {

        private static final long serialVersionUID = 1L;

        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //接收表单信息
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            //设置回显
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            try {
                User user = null;
                //根据用户名查询用户
                user = UserService.getInstance().findByUsername(username);
                if (user != null) {
                    if (user.getPassword().equals(password)) {
                        request.getSession().setAttribute("user", user);
                        response.sendRedirect("index.jsp");
                    } else {
                        request.setAttribute("loginError", "* 密码错误");
                        request.getRequestDispatcher("/login.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("loginError", "* 用户不存在");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request, response);
        }
}

