package org.example.backend.configuration;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.persitence.repository.UserRepository;
import org.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserService userService ;
    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse  response, FilterChain chain )
            throws IOException, ServletException, java.io.IOException {
        String skipAPI=request.getRequestURI();
        if(skipAPI.equals("/sfinvietnam/auth/login")|| skipAPI.equals("/sfinvietnam/auth/register-new-user")){
            chain.doFilter(request,response);
            return;
        }

        String authHeader=request.getHeader("Authorization");
        if(!authHeader.startsWith("Bearer ") || authHeader == null)
        {
            chain.doFilter(request,response);
            return;
        }
        String token=authHeader.substring(7);
        String username=jwtService.extractUsername(token);
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request,response);
    }

}
