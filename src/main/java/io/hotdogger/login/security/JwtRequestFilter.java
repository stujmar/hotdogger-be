package io.hotdogger.login.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This filter class supports the JWT and is going to intersect every request once and examine the
 * header to get and check the JWT. It inherits from the OncePerRequestFilter which is a filter that
 * runs once per request.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * Connects to the custom UserDetailsService to get user details like email/username
     */
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    /**
     * Connects to the JwtUtil class that creates and validates JWT, so it is used to validate a JWT
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * This gets the authorization header from an incoming request and checks if it's a Bearer token,
     * then extracts and validates the JWT and authenticates the user. It then connects to the next
     * filter.
     *
     * @param httpServletRequest  the request body
     * @param httpServletResponse a response after request
     * @param filterChain         a filter chain, which this method has the option of passing on to
     *                            the next filter in the filter chain or ending request.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        //gets the authorization header from the request
        final String authHeader = httpServletRequest.getHeader("Authorization");

        String email = null;
        String jwt = null;

        //checks if it's a Bearer token, if so, extracts the JWT from it and get the email
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(email);

            //validates the JWT based on userDetails and see if its valid for given user
            if (jwtUtil.validateToken(jwt, userDetails)) {
                //this is simulating normal flow of operations of what Spring Security would do under
                // the condition that the JWT is valid
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        //once filter complete, enter the chain and handle control off to next filter
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}