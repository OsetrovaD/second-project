package com.osetrova.project.controller;

import com.osetrova.project.dto.InterestingFactDto;
import com.osetrova.project.service.InterestingFactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Random;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HomePageController {

    private InterestingFactService service;
    private static final Random RANDOM  = new Random();

    @GetMapping("/user")
    public String getHomePage(Model model) {
        List<InterestingFactDto> facts = service.findAll();
        int index = RANDOM.nextInt(facts.size());

        model.addAttribute("interestingFact", facts.get(index).getName());

        return "user";
    }
}
