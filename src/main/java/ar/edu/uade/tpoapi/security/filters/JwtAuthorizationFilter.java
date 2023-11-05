package ar.edu.uade.tpoapi.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.edu.uade.tpoapi.security.jwt.JwtUtils;
import ar.edu.uade.tpoapi.services.UserDetailsImpl;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter{
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsImpl userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException
    {
        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);

            if(jwtUtils.validateAccesToken(token)){
                String mail = jwtUtils.getMailFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(mail);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
