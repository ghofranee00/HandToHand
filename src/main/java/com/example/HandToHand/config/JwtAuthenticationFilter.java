package com.example.HandToHand.config;

import com.example.HandToHand.Service.DonneurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private DonneurService donneurService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);

        if (token != null) {
            try {
                // Check if the token is blacklisted
                if (donneurService.isTokenBlacklisted(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalidated");
                    return;
                }

                // Retrieve the user profile from the token
                Map<String, Object> profile = donneurService.getProfileFromToken(token);

                // Create an authentication token based on the profile
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                profile.get("email"), // Principal (user email)
                                null,                  // Credentials (password is not needed here)
                                Collections.emptyList()); // Authorities (empty if none)

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Handle invalid token or other errors
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        // Extract token from the Authorization header
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Return the token without the "Bearer " prefix
        }
        return null; // Return null if no token is found
    }}