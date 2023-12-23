package com.web.auction.controllers;


import com.web.auction.data.UserRepository;
import com.web.auction.security.RegistrationForm;
import lombok.extern.flogger.Flogger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Возникла ошибка входа. Пожалуйста, проверьте введенные данные.");
        }
        return "login";
    }
}
