package src.filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

//@WebFilter(filterName = "Filter 1",urlPatterns = "/*")
public class Filter1 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        //HttpServletResponse response=(HttpServletResponse)servletResponse;
        Date time =new Date();
        String path=request.getRequestURI();
        System.out.println(path+"@"+time);
        System.out.println("Filter 1 begins");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Filter 1 ends");
    }
}
