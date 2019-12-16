package com.sams.demo.web.filter;

import com.sams.demo.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private UserDetailsService userDetailsService;
    private JwtTokenProvider jwtTokenProvider;
    private SkipUriFilter skipUriFilter;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        RequestWrapper requestWrapper =
                new RequestWrapper(httpServletRequest, httpServletResponse);

        if (skipUriFilter.skipUriCheck(requestWrapper)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {

            String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);

            if (isNotBlank(authorizationHeader)
                    && authorizationHeader.startsWith(BEARER_PREFIX)) {

                String email = jwtTokenProvider
                        .getUserEmail(authorizationHeader.replace(BEARER_PREFIX, ""));

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}