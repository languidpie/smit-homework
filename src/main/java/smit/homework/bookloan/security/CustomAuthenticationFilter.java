package smit.homework.bookloan.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Custom authentication filter for handling login and logout requests.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 16.09.2024
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if ("POST".equalsIgnoreCase(request.getMethod()) && "/login".equals(request.getRequestURI())) {
            return handleLogin(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod()) && "/logout".equals(request.getRequestURI())) {
            handleLogout(request, response);
            return null;
        }
        return null;
    }

    // Handle login attempt
    private Authentication handleLogin(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestBody;
        try {
            requestBody = objectMapper.readValue(request.getInputStream(), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String username = requestBody.get("username");
        String password = requestBody.get("password");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        Optional<? extends GrantedAuthority> authorityOptional = authResult.getAuthorities().stream().findFirst();

        if (authorityOptional.isPresent()) {
            String authorityJson = objectMapper.writeValueAsString(authorityOptional.get());
            response.getWriter().write(authorityJson);
        } else {
            response.getWriter().write("{\"error\": \"No authority found\"}");
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
