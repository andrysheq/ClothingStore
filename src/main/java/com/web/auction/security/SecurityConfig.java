package com.web.auction.security;

import com.web.auction.data.UserRepository;
import com.web.auction.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .and()
                .logout()
                .logoutSuccessUrl("/")
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

//    @Bean
//    public CommandLineRunner dataLoader(RoleRepository roleRepo,
//                                        UserRepository userRepo, PasswordEncoder encoder) { // user repo for ease of testing with a built-in user
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//

//                User admin = new User("admin", encoder.encode("root"),
//                        "Andrey Admin", "123 North Street", "Cross Roads", "TX",
//                        "76227", "123-123-1234");
//                admin.addRole(roleRepo.findById("ADM"));
//
//                User moderator = new User("moderator", encoder.encode("root"),
//                        "Andrey Moderator", "123 North Street", "Cross Roads", "TX",
//                        "76227", "123-123-1234");
//                moderator.addRole(roleRepo.findById("MDR"));
//
//                userRepo.save(admin);
//                userRepo.save(moderator);
//
//            }
//        };
//    }

}
