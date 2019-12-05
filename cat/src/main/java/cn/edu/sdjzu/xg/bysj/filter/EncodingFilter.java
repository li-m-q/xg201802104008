//201802104008 李美青
package cn.edu.sdjzu.xg.bysj.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

@WebFilter(filterName = "filter 00",urlPatterns = {"/*"})
public class EncodingFilter  implements Filter {
    Collection toExclude = new HashSet();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        toExclude.add("/login");
        toExclude.add("/myapp");
    }

    @Override
    public void destroy() { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String path = request.getRequestURI();
        System.out.println("请求的资源为:" + path);
        System.out.println("Filter 00 - encoding begins");
        if (!toExclude.contains(path)){
            String method = request.getMethod();
            if ("POST-PUT".contains(method)) {
                request.setCharacterEncoding("UTF-8");
                //response.setContentType("text/html;charset = UTF-8");
                System.out.println("设置成功");
            }
            response.setContentType("text/html;charset = UTF-8");
            System.out.println("设置字符为utf8");
        }
        // 执行其他过滤器，如过滤器已经执行完毕，则执行原请求
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Filter 00 - encoding ends");
    }
}
