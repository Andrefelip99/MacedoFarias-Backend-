package com.example.confeitariaMacedoFarias.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.confeitariaMacedoFarias.services.JwtService;
import com.example.confeitariaMacedoFarias.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtFilter(
            JwtService jwtService,
            UserDetailsServiceImpl userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

  @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws IOException, ServletException {

    System.out.println("========== JWT FILTER ==========");
    System.out.println("URI: " + request.getRequestURI());

    String authHeader = request.getHeader("Authorization");

   System.out.println("AUTHORIZATION CHEGOU: [" + authHeader + "]");


    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }


    String token = authHeader.substring(7);

    String username = jwtService.extractUsername(token);

    System.out.println("USERNAME DO TOKEN: " + username);


    if (username != null &&
        SecurityContextHolder.getContext().getAuthentication() == null) {


        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);


        System.out.println("AUTHORITIES: " + userDetails.getAuthorities());


        if (jwtService.isTokenValid(token, userDetails.getUsername())) {

            System.out.println("TOKEN VALIDO");


            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());


            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
        }
    }


    filterChain.doFilter(request, response);
}}