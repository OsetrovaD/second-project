package com.osetrova.project.controller;

import com.osetrova.project.util.InterestingFactsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

import static com.osetrova.project.util.InterestingFactsUtil.*;

@Controller
public class HomePageController {

    private static final Random RANDOM  = new Random();

    @GetMapping("/user")
    public String getHomePage(Model model) {
        int index = RANDOM.nextInt(FACTS.size());
        model.addAttribute("interestingFact", FACTS.get(index));

        return "user";
    }
}
