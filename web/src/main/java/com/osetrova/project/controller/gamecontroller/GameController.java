package com.osetrova.project.controller.gamecontroller;

import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.service.GameService;
import com.osetrova.project.dto.gamedto.GameFullInfoDto;
import com.osetrova.project.webmoduledto.GamesFilterRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/all-games")
    public String getAllGames(Model model) {
        List<Game> games = gameService.findAll();
        AgeLimit[] ageLimits = AgeLimit.values();
        model.addAttribute("games", games);
        model.addAttribute("ageLimits", ageLimits);
        model.addAttribute("filters", new GamesFilterRequestDto());

        return "all-games";
    }

    @GetMapping("/game-info")
    public String getGameInfo(Model model, @RequestParam("id") String id) {
        GameFullInfoDto gameFullInfo = gameService.findById(Long.valueOf(id));
        Set<String> genresNames = new HashSet<>();
        gameFullInfo.getSubgenres().forEach(x -> genresNames.add(x.getGenreName()));

        model.addAttribute("game", gameFullInfo);
        model.addAttribute("genres", genresNames);

        return "game-info";
    }
}
