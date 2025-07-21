package in.rw.userService.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static in.rw.userService.LoggingMarkers.SECURITY;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
@Slf4j
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String queryParam = request.getQueryString();
        String uri = request.getRequestURI();
        String originalUrl = queryParam == null || queryParam.trim().isEmpty() ? uri : uri + "?" + queryParam;
        log.atError()
                .setCause(authException)
                .addMarker(SECURITY)
                .log("{}. Authentication failed on \"{}\" from IP \"{}\"!",
                        authException.getMessage(),
                        originalUrl,
                        request.getRemoteAddr());
        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"Error\": \"Bad credentials - Invalid user name or password!\"}");
    }
}
