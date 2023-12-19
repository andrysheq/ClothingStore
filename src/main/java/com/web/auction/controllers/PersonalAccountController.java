package com.web.auction.controllers;

import com.web.auction.models.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class PersonalAccountController {
    @GetMapping
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {

        model.addAttribute("user", user);

        return "personalAccount";
    }

//    @GetMapping
//    public String ordersForUser() {
//        return "personalAccount";
//    }
}
