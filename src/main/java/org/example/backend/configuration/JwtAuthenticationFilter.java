package org.example.backend.configuration;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.persitence.repository.UserRepository;
import org.example.backend.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.example.backend.persitence.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService ;
    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse  response, FilterChain chain )
            throws IOException, ServletException, java.io.IOException {
        String skipAPI=request.getRequestURI();
        if(skipAPI.equals("/sfinvietnam/auth/login")|| skipAPI.equals("/sfinvietnam/auth/register-new-user")){
            chain.doFilter(request,response);
            return;
        }
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }
        try {
            String authHeader = request.getHeader("Authorization");
            String token = authHeader.substring(7);
            if(authHeader.startsWith("Bearer ")||authHeader!=null) {
                String username = jwtService.extractUsername(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authToken
                                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }

        }
        catch (Exception e) {
            System.err.println("Lỗi JWT Filter: " + e.getMessage());
        }
        chain.doFilter(request,response);
    }
    private String getCookieValue(HttpServletRequest request, String cookieName) {
        if(request.getCookies() == null){
            return null;
        } else {
            request.getCookies();
        }
        for(Cookie cookie:request.getCookies())
        {
            if(cookie.getName().equals(cookieName))
            {
                return cookie.getValue();
            }
        }
        return null;
    }
    private void handlRefreshToken(HttpServletRequest request,HttpServletResponse response
    ){
        try{
            String refeshToken=getCookieValue(request,"refreshToken");
            if(refeshToken==null) return;
            String username=jwtService.extractUsername(refeshToken);
            User user=userRepository.findByUsername(username).orElse(null);
            if(jwtService.isTokenValid(refeshToken,user))
            {
                String newAccessToken= jwtService.generateToken(user);
                ResponseCookie accessCookie=ResponseCookie.from("access_token",newAccessToken)
                        .secure(true)
                        .httpOnly(true)
                        .sameSite("none")
                        .path("/")
                        .maxAge(900)
                        .build();
                response.addHeader(HttpHeaders.SET_COOKIE,accessCookie.toString());
            }
        }
        catch(Exception ex)
        {
            System.err.println("Lỗi JWT Filter: " + ex.getMessage());
        }
    }

}
