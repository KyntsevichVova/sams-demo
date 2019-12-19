package com.sams.demo.web.filter;

import com.sams.demo.model.error.ErrorMessage;
import com.sams.demo.model.response.ResponseBuilder;
import com.sams.demo.security.JwtTokenProvider;
import com.sams.demo.security.SecurityPrincipal;
import com.sams.demo.service.IAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.sams.demo.common.ApplicationConstant.BEARER_PREFIX;
import static com.sams.demo.model.error.ErrorCode.SESSION_EXPIRED;
import static com.sams.demo.model.error.ErrorCode.TOKEN_MISSING;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonList;
import static java.util.Locale.US;
import static java.util.Locale.forLanguageTag;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_TOKEN_ERROR_PATTERN = "" +
            "Bearer error=\"invalid_token\", error_description=\"%s\"";
    private static final String BEARER_TOKEN_EXPIRED = "The token is expired.";
    private static final String BEARER_TOKEN_MISSING = "The token is missing.";

    private IAuthenticationService authenticationService;
    private JwtTokenProvider jwtTokenProvider;
    private SkipUriFilter skipUriFilter;
    private MessageSource messageSource;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        RequestWrapper requestWrapper =
                new RequestWrapper(httpServletRequest, httpServletResponse);

        if (skipUriFilter.skipUriCheck(requestWrapper)) {

            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);

        if (isBlank(authorizationHeader)
                || !authorizationHeader.startsWith(BEARER_PREFIX)) {

            handleUnauthorized(
                    httpServletRequest,
                    httpServletResponse,
                    TOKEN_MISSING,
                    format(BEARER_TOKEN_ERROR_PATTERN, BEARER_TOKEN_MISSING));

            return;
        }

        String email;
        try {
            email = jwtTokenProvider.getUserEmail(authorizationHeader.replace(BEARER_PREFIX, ""));
        } catch (ExpiredJwtException ex) {

            handleUnauthorized(
                    httpServletRequest,
                    httpServletResponse,
                    SESSION_EXPIRED,
                    format(BEARER_TOKEN_ERROR_PATTERN, BEARER_TOKEN_EXPIRED));

            return;
        }

        SecurityPrincipal principal =
                (SecurityPrincipal)authenticationService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void handleUnauthorized(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    String payloadMessage,
                                    String wwwAuthenticateHeaderMessage) throws IOException {

        String localeHeader = httpServletRequest.getHeader(ACCEPT_LANGUAGE);
        Locale locale = isNotBlank(localeHeader)
                ? forLanguageTag(localeHeader)
                : US;

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(messageSource.getMessage(
                payloadMessage,
                null,
                locale));

        String response =
                ResponseBuilder
                        .failure()
                        .withErrorMessage(singletonList(errorMessage))
                        .withHttpStatus(UNAUTHORIZED)
                        .toJson();

        httpServletResponse.setLocale(locale);
        httpServletResponse.setCharacterEncoding(UTF_8.name());
        httpServletResponse.addHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        httpServletResponse.addHeader(WWW_AUTHENTICATE, wwwAuthenticateHeaderMessage);
        httpServletResponse.setStatus(UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(response);
    }
}