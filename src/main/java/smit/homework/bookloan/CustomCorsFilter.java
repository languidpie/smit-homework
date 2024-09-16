package smit.homework.bookloan;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Mari-Liis Pihlapuu
 * Date: 16.09.2024
 */
@Component
public class CustomCorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        httpServletRequest.setAttribute("Access-Control-Allow-Origin", "*");
        httpServletRequest.setAttribute("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletRequest.setAttribute("Access-Control-Allow-Headers", "Authorization, Content-Type");
        httpServletRequest.setAttribute("Access-Control-Allow-Credentials", "true");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}