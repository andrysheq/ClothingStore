package com.web.auction.controllers;

import com.web.auction.models.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/account")
public class PersonalAccountController {
    @GetMapping
    public String myAccount (@AuthenticationPrincipal User user,Model model){

        if (user == null) {
            return "redirect:/login";
        }

        if (user.getPhoto() != null) {
            byte[] photoBytes = user.getPhoto();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            model.addAttribute("photoBase64", photoBase64);
        }

        model.addAttribute("user", user);

        return "personalAccount";
    }


}
