package src.filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "Filter 0",urlPatterns = {"/*"})
public class Filter0 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter 0 begins");
        HttpServletRequest request =(HttpServletRequest)servletRequest;
        HttpServletResponse response =(HttpServletResponse)servletResponse;
            if (request.getServletPath().contains("/login")) {
            }
            else if ((request.getMethod().equals("dopost")) || request.getMethod().equals("doput")) {
                request.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
            } else {
                response.setContentType("text/html;charset=UTF-8");
            }
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Filter 0 ends");
    }
}
