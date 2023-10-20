package com.web.auction.security;

import com.web.auction.data.UserRepository;
import com.web.auction.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@EnableWebSecurity
public class SecurityConfig {
//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
//            throws Exception {
//        return http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests.anyRequest().permitAll()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()).build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
////                .antMatchers("/").hasRole("USER")
//                .requestMatchers(toH2Console()).permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin(form -> form.loginPage("/login")
//                        .usernameParameter("user")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/"))
//                .logout(logout -> logout.logoutSuccessUrl("/login"))
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers(toH2Console())
//                        .disable())
////                 .authorizeHttpRequests(auth -> auth
////                         .requestMatchers(toH2Console()).permitAll())
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .mvcMatchers("/design", "/orders").hasRole("USER")
                .anyRequest().permitAll()

                .and()
                .formLogin()
                .loginPage("/login")

                .and()
                .logout()
                .logoutSuccessUrl("/")

                // Make H2-Console non-secured; for debug purposes
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .build();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException(
                    "User '" + username + "' not found");
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
