package com.example.tfglibraryofohara;

import com.example.tfglibraryofohara.Security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    private static final String[] AUTH_WHITELIST = { //SWAGGER
// -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
// -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/doc/**"
// other public endpoints of your API may be appended to this array
    };

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/user").permitAll()//antMatchers OBSOLETO

                    //dejo acceso a ver que datos hay en bd
                    .antMatchers(HttpMethod.GET,"/api/autor/").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/autor/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/autor/registrar").permitAll()

                    .antMatchers(HttpMethod.GET,"/api/genero/").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/genero/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/genero/registrar").permitAll()

                    .antMatchers(HttpMethod.GET,"/api/libro/").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/libro/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/libro/registrar").permitAll()

                    /*.antMatchers("/api/habitacion/").permitAll()
                    .antMatchers("/api/hotel/{id}").permitAll()
                    .antMatchers("/api/habitacion/{id}").permitAll()
                    .antMatchers("/api/hotel/filtrar/**").permitAll()
                    .antMatchers("/api/habitacion/filtrar/**").permitAll()*/

                    .antMatchers(AUTH_WHITELIST).permitAll() //SWAGGER
                    .anyRequest().authenticated();
            //cualquier solicitud debe ser autenticada, de lo contrario, mi aplicación Spring devolverá una respuesta 401.
        }
    }
}
