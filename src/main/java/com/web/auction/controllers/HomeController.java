package com.web.auction.controllers;

import com.web.auction.data.VisitRepository;
import com.web.auction.models.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private VisitRepository visitRepo;
    @GetMapping
    public String home(Model model) {
        Visit newVisit = new Visit();
        visitRepo.save(newVisit);

        Long visitCount = visitRepo.count();
        model.addAttribute("visitCount",String.valueOf(visitCount));

        return "home";
    }

    @Autowired
    public HomeController(VisitRepository visitRepo) {
        this.visitRepo = visitRepo;
//        Visit newVisit = new Visit();
//        visitRepo.save(newVisit);
    }
}
