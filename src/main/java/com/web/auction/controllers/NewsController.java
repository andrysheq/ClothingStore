package com.web.auction.controllers;

import com.web.auction.data.NewsRepository;
import com.web.auction.models.News;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String openNews(@PathVariable Long id,
                            Model model) {
        News news = newsRepo.findNewsById(id);
        model.addAttribute("news",news);
        return "newsPage";
    }
}
