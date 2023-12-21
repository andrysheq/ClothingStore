package com.web.auction.security;

import com.web.auction.data.UserRepository;
import com.web.auction.security.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

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
    public String processRegistration(@ModelAttribute("form") @Valid RegistrationForm form,
                                      BindingResult result,
                                      @RequestParam("photo") MultipartFile photo,
                                      Model model) {

        // Проверяем, существует ли пользователь с таким логином
        if (userRepo.findByUsername(form.getUsername()) != null) {
            return "redirect:/register?error";
        }

        try {
            form.setPhoto(photo); // сохранить фото в объекте RegistrationForm
        }catch (Exception e){
            return "redirect:/register?error";
        }


        userRepo.save(form.toUser(passwordEncoder));

        return "redirect:/login";
    }
}
