package com.web.auction.controllers;

import com.web.auction.data.NewsRepository;
import com.web.auction.data.RoleRepository;
import com.web.auction.data.UserRepository;
import com.web.auction.models.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.web.auction.models.LotForm.getLotFormWithLot;
import static com.web.auction.models.NewsForm.getNewsFormWithNews;

@Component
@Controller
@PreAuthorize("hasRole('ROLE_MODERATOR')")
@RequestMapping("/moderator-panel")
public class ModeratorPanelController {
    private UserRepository userRepo;
    private NewsRepository newsRepo;
    private NewsForm form;

    public ModeratorPanelController(UserRepository userRepo, NewsRepository newsRepo, NewsForm form) {
        this.userRepo = userRepo;
        this.newsRepo = newsRepo;
        this.form = form;
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/current")
    public String orderForm(Model model,@AuthenticationPrincipal User user,
                            @ModelAttribute Lot lot) {

//        if (lot.getStreet() == null) {
//            lot.setStreet(user.getStreet());
//        }
//        if (lot.getCity() == null) {
//            lot.setCity(user.getCity());
//        }

        model.addAttribute("newsForm", form);
        return "createNews";
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping
    public String processNews(@ModelAttribute("newsForm") @Valid NewsForm form,
                             BindingResult result,
                             Model model, Errors errors,
                             SessionStatus sessionStatus,
                             @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("newsForm", form);
            return "createNews";
        }

        News newsToSave = form.toNews();

        newsToSave.setUser(user);

        newsRepo.save(newsToSave);

        sessionStatus.setComplete();

        return "redirect:/moderator-panel";
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping
    public String lotsForModerator(
            @AuthenticationPrincipal User user, Model model) {


        List<News> newsList = newsRepo.findAllByOrderByPlacedAtDesc();

        model.addAttribute("newsList", newsList);

        return "moderatorPanel";
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/{id}")
    public String updateNews(@PathVariable Long id,
                            @ModelAttribute("newsForm") @Valid NewsForm form,
                            BindingResult result,
                            Model model, Errors errors,
                            SessionStatus sessionStatus,
                            @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("newsForm", form);
            News news = newsRepo.findNewsById(id);
            model.addAttribute("news", news);
            return "editNews";
        }
        News newsOld = newsRepo.findNewsById(id);
        News newsToUpdate = form.toNews();

        newsToUpdate.setId(id);

        newsToUpdate.setUser(user);

        newsToUpdate.setPlacedAt(newsOld.getPlacedAt());

        newsRepo.save(newsToUpdate);

        sessionStatus.setComplete();

        return "redirect:/moderator-panel";
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/{id}/edit")
    public String editNews(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        News news = newsRepo.findNewsById(id);
        model.addAttribute("news", news);
        model.addAttribute("newsForm", getNewsFormWithNews(news));
        return "editNews";
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PostMapping("/delete-news")
    public String deleteUser(@RequestParam("newsId") Long newsId) {

        News news = newsRepo.findById(newsId).orElse(null);
        if (news != null) {
            // Удаление пользователя из репозитория
            newsRepo.delete(news);

            // Вернуть пользователя на страницу администратора после удаления
            return "redirect:/moderator-panel";
        }

        // Если пользователь не найден, вернуть на страницу с сообщением об ошибке или другую страницу
        return "redirect:/moderator-panel?error=UserNotFound";
    }


}
