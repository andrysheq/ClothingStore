package com.web.auction.security;

import com.web.auction.data.RoleRepository;
import com.web.auction.data.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
@Component
@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final UserRepository userRepo;

    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationForm form;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder,RoleRepository roleRepo, RegistrationForm form) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.form = form;
        this.roleRepo = roleRepo;
    }


    @GetMapping
    public String registerForm(Model model, @RequestParam(name = "error", required = false) String error) {

        if ("duplicateUser".equals(error)) {
            model.addAttribute("duplicateUserErrorMessage", "Пользователь с таким логином уже существует");
        }

        model.addAttribute("registrationForm", form);
        return "registration";
    }
    @PostMapping
    public String processRegistration(@ModelAttribute("registrationForm") @Valid RegistrationForm form,
                                      BindingResult result,
                                      @RequestParam("photo") MultipartFile photo,
                                      Model model) {

        if (result.hasErrors()) {
            // Обработка ошибок валидации
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("registrationForm", form);
            return "registration"; // Вернуть страницу регистрации с сообщениями об ошибках
        }

        if(userRepo.findByUsername(form.getUsername())!=null){
            result.rejectValue("username", "duplicateUser", "Пользователь с таким логином уже существует");
            model.addAttribute("registrationForm", form);
            return "registration";
        }



        try {
            if(photo.getSize()<=0){
                result.rejectValue("photo", "photoNotAdded", "Вы не загрузили фото профиля");
                model.addAttribute("registrationForm", form);
                return "registration";
            }
            form.setPhoto(photo); // сохранить фото в объекте RegistrationForm
        }catch (Exception e){
            return "redirect:/register?error";
        }



        userRepo.save(form.toUser(passwordEncoder,roleRepo));

        return "redirect:/login";
    }
}
