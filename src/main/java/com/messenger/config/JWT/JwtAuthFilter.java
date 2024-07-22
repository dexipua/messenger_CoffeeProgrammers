package com.messenger.config.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        String jwtToken = parseJwt(request);
        if (jwtToken != null) {
            String username = jwtConfig.extractUsername(jwtToken);

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtConfig.validateToken(jwtToken, userDetails)) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken
                            (userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails
                            (new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    if (jwtConfig.extractExpiration(jwtToken).getTime() -
                            System.currentTimeMillis() < 1000 * 60 * 60) {
                        String refreshedToken = jwtConfig.refreshToken(jwtToken);
                        response.setHeader("Authorization", "Bearer " + refreshedToken);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
