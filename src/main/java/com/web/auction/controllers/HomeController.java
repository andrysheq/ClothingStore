package com.web.auction.controllers;

import com.web.auction.data.LotRepository;
import com.web.auction.data.VisitRepository;
import com.web.auction.models.Lot;
import com.web.auction.models.User;
import com.web.auction.models.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    private VisitRepository visitRepo;
    private LotRepository lotRepo;
    @GetMapping
    public String home(Model model) {
        Visit newVisit = new Visit();
        visitRepo.save(newVisit);

        Long visitCount = visitRepo.count();
        model.addAttribute("visitCount",String.valueOf(visitCount));

        List<Lot> lots = lotRepo.findAllByOrderByPlacedAtDesc();
        Map<Lot, String> lotPhotoMap = new HashMap<>();

        for (Lot lot : lots) {
            byte[] photoBytes = lot.getPhotoOfLot();
            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
            lotPhotoMap.put(lot, photoBase64);
        }

        model.addAttribute("lots", lots);
        model.addAttribute("lotPhotoMap", lotPhotoMap);

        return "home";
    }

    @Autowired
    public HomeController(VisitRepository visitRepo, LotRepository lotRepo) {
        this.visitRepo = visitRepo;
        this.lotRepo = lotRepo;

//        Visit newVisit = new Visit();
//        visitRepo.save(newVisit);
    }

//    @GetMapping
//    public String allLots(Model model) {
//
//
//        List<Lot> lots = lotRepo.findAllByOrderByPlacedAtDesc();
//        Map<Lot, String> lotPhotoMap = new HashMap<>();
//
//        for (Lot lot : lots) {
//            byte[] photoBytes = lot.getPhotoOfLot();
//            String photoBase64 = Base64.getEncoder().encodeToString(photoBytes);
//            lotPhotoMap.put(lot, photoBase64);
//        }
//
//        model.addAttribute("lots", lots);
//        model.addAttribute("lotPhotoMap", lotPhotoMap);
//
//        return "home";
//    }
}
