package com.example.tfglibraryofohara.Security;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    /*

1.- Comprueba la existencia del token (existeJWTToken(...)).
2.- Si existe, lo desencripta y valida (validateToken(...)).
3.- Si está todo OK, añade la configuración necesaria al contexto de Spring
         para autorizar la petición (setUpSpringAuthentication(...)).

Para este último punto, se hace uso del objeto GrantedAuthority que se incluyó
en el token durante el proceso de autenticación.
     */


    /*
    Estos token están compuestos por tres partes:

Header: contiene el hash que se usa para encriptar el token.
Payload: contiene una serie de atributos (clave, valor) que se encriptan en el token.
Firma: contiene header y payload concatenados y encriptados (Header + “.” + Payload + Secret key).
     */
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mySecretKey";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("mySecretKey".getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            List<GrantedAuthority> authorities;

            Object rawAuthorities = claims.get("authorities");

            if (rawAuthorities instanceof List<?>) {
                List<?> roles = (List<?>) rawAuthorities;
                if (!roles.isEmpty() && roles.get(0) instanceof String) {
                    authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority((String) role))
                            .collect(Collectors.toList());
                } else {
                    authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority(((Map<?, ?>) role).get("authority").toString()))
                            .collect(Collectors.toList());
                }
            } else {
                authorities = List.of(); // vacío, por si acaso
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    /**
     * Authentication method in Spring flow
     *
     * @param claims
     */

    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    //existeJWTToken
    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
            return false;
        return true;
    }

}
