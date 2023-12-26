package com.web.auction.security;

import com.web.auction.data.RoleRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.models.Role;
import com.web.auction.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/account/admin").hasRole("ADMIN")
                .antMatchers("/account/moderator").hasRole("MODERATOR")
                .antMatchers("/account", "/lots", "/lots/current").authenticated()
                .anyRequest().permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
//                .successHandler((request, response, authentication) -> {
//                    if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//                        response.sendRedirect("/account/admin");
//                    } else {
//                        response.sendRedirect("/account/user");
//                    }
//                })
                .and()
                .logout()
                .logoutSuccessUrl("/")
                // Make H2-Console non-secured; for debug purposes
//                .and()
//                .csrf()
//                .ignoringAntMatchers("/h2-console/**")
//
//                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()

                .and()
                .build();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password("{noop}admin").roles("ADMIN")
//                .and()
//                .withUser("moderator").password("{noop}moderator").roles("MODERATOR");
//    }
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

//    @Bean
//    public CommandLineRunner dataLoader(RoleRepository roleRepo,
//                                        UserRepository userRepo, PasswordEncoder encoder) { // user repo for ease of testing with a built-in user
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//
////                Role adminRole = new Role("ADM", "ROLE_ADMIN");
////                Role moderatorRole = new Role("MDR", "ROLE_MODERATOR");
////                Role userRole = new Role("USR", "ROLE_USER");
////
////                roleRepo.save(adminRole);
////                roleRepo.save(moderatorRole);
////                roleRepo.save(userRole);
////
////                User admin = new User("admin1", encoder.encode("Root!@1"),
////                        "Andrey Admin", "123 North Street", "Cross Roads", "TX",
////                        "76227", "123-123-1234");
////                admin.addRole(roleRepo.findById("ADM"));
////
////                User moderator = new User("moderator1", encoder.encode("Root!@1"),
////                        "Andrey Moderator", "123 North Street", "Cross Roads", "TX",
////                        "76227", "123-123-1234");
////                moderator.addRole(roleRepo.findById("MDR"));
////
////                userRepo.save(admin);
////                userRepo.save(moderator);
//
//            }
//        };
//    }

}
