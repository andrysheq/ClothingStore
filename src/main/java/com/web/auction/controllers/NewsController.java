package com.web.auction.controllers;

import com.web.auction.data.LotRepository;
import com.web.auction.data.NewsRepository;
import com.web.auction.models.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

@Component
@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsRepository newsRepo;

    public NewsController(NewsRepository newsRepo) {
        this.newsRepo = newsRepo;
    }

    @GetMapping()
    public String newsPage(Model model) {
        List<News> newsList = newsRepo.findAllByOrderByPlacedAtDesc();

        model.addAttribute("newsList", newsList);
        return "allNews";
    }

    @GetMapping("/{id}")
    public String updateLot(@PathVariable Long id,
                            Model model) {
        News news = newsRepo.findNewsById(id);
        model.addAttribute("news",news);
        return "newsPage";
    }
}
