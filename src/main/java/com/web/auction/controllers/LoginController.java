package com.web.auction.controllers;


import com.web.auction.data.UserRepository;
import lombok.extern.flogger.Flogger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String login() {
        return "login";
    }
}
