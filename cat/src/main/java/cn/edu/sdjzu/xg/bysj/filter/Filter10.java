package cn.edu.sdjzu.xg.bysj.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@WebFilter(filterName = "filter 10",urlPatterns = {"/*"})
public class Filter10 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String path = request.getRequestURI();
        Date date =new Date();
        System.out.println(path + "@" + date);
        // 执行其他过滤器，如过滤器已经执行完毕，则执行原请求
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
