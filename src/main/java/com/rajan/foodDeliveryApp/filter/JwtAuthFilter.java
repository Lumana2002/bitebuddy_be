package com.rajan.foodDeliveryApp.filter;

import com.rajan.foodDeliveryApp.services.authentication.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // DEBUG: Log all incoming requests
        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request Path: " + request.getServletPath());

        if (request.getServletPath().contains("/api/auth")) {
            System.out.println("⚠️ Skipping JWT validation for auth endpoint");
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorizationHeader);

        final String jwtToken;
        final String email;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("❌ No valid Authorization header found - proceeding without authentication");
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authorizationHeader.substring(7);
        System.out.println("Extracted JWT Token: " + jwtToken.substring(0, Math.min(50, jwtToken.length())) + "...");

        try {
            email = jwtService.extractEmail(jwtToken);
            System.out.println("Extracted Email: " + email);
        } catch (Exception e) {
            System.out.println("❌ Error extracting email from token: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Collection<? extends GrantedAuthority> authorities =
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + jwtService.extractRole(jwtToken)));
                System.out.println("Extracted Role: " + jwtService.extractRole(jwtToken));

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                System.out.println("User Details Found: " + userDetails.getUsername());

                if (jwtService.validateToken(jwtToken, userDetails)) {
                    System.out.println("✅ Token is valid - setting authentication");
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    System.out.println("❌ Token validation failed");
                }
            } catch (Exception e) {
                System.out.println("❌ Error during authentication process: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            if (email == null) {
                System.out.println("❌ Email is null");
            }
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                System.out.println("ℹ️ Authentication already exists in context");
            }
        }

        System.out.println("=== END JWT FILTER DEBUG ===\n");
        filterChain.doFilter(request, response);
    }
}