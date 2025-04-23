package com.mts.work.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {
  private final String secretKey = "secret";

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response, FilterChain flterChain)
          throws ServletException, IOException {
    String token = request.getHeader("Authorization");
    if (token != null) {
      System.out.println("Jwt token: " + token);
    }

    flterChain.doFilter(request, response);
  }
}