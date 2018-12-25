package com.osetrova.project.controller.gamecontroller;

import com.osetrova.project.dto.gamedto.NewGameDto;
import com.osetrova.project.dto.gamepricedto.NewGamePriceDto;
import com.osetrova.project.entity.DeveloperCompany;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.enumonly.AgeLimit;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.service.DeveloperCompanyService;
import com.osetrova.project.service.NewGameService;
import com.osetrova.project.service.SubgenreService;
import com.osetrova.project.util.SearchResultUrlUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NewGameController {

    private NewGameService gameService;
    private SubgenreService subgenreService;
    private DeveloperCompanyService companyService;

    @GetMapping("/add-new-game")
    public String getAddNewGamePage(Model model) {
        List<DeveloperCompany> companies = companyService.findAll();
        List<Subgenre> subgenres = subgenreService.findAll();

        model.addAttribute("ageLimits", Arrays.asList(AgeLimit.values()));
        model.addAttribute("platforms", Arrays.asList(GamePlatform.values()));
        model.addAttribute("subgenres", subgenres);
        model.addAttribute("companies", companies);
        model.addAttribute("newGame", new NewGameDto());

        return "add-new-game";
    }

    @PostMapping("/add-new-game")
    public String addNewGame(NewGameDto newGame, HttpServletRequest request) {
        String[] platforms = request.getParameterValues("game_platforms");
        Set<NewGamePriceDto> prices = newGame.getPrices();

        for (String platform : platforms) {
            prices.add(NewGamePriceDto.builder()
                    .gamePlatform(GamePlatform.getByName(platform))
                    .price(Integer.valueOf(request.getParameter(platform)))
                    .numberInStorage(Short.valueOf(request.getParameter(platform + "_1")))
                    .build());
        }

        Game game = gameService.save(newGame);

        return SearchResultUrlUtil.getRedirectGameInfoUrl(game.getId());
    }
}
