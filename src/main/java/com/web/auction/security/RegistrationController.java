package com.web.auction.security;

import com.web.auction.data.UserRepository;
import com.web.auction.security.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    public RegistrationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping
    public String registerForm() {
        return "registration";
    }
    @PostMapping
    public String processRegistration(RegistrationForm form) {

        // Проверяем, существует ли пользователь с таким логином
        if (userRepo.findByUsername(form.getUsername()) != null) {
            return "redirect:/register?error";
        }


        userRepo.save(form.toUser(passwordEncoder));

        return "redirect:/login";
    }
}
