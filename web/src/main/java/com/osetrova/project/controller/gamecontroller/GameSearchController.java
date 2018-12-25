package com.osetrova.project.controller.gamecontroller;

import com.osetrova.project.entity.Genre;
import com.osetrova.project.entity.Subgenre;
import com.osetrova.project.entity.enumonly.GamePlatform;
import com.osetrova.project.service.GameService;
import com.osetrova.project.service.GenreService;
import com.osetrova.project.service.SubgenreService;
import com.osetrova.project.dto.gamedto.GameFullInfoDto;
import com.osetrova.project.dto.gamedto.GameSearchResultDto;
import com.osetrova.project.util.SearchResultUrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameSearchController {

    private static final String BY_NAME = "by_name";
    private static final String BY_PLATFORM = "by_platform";
    private static final String BY_GENRE = "by_genre";
    private static final String BY_SUBGENRE = "by_subgenre";
    private static final String BY_ISSUE_YEAR = "by_issue_year";

    private final GameService gameService;
    private final GenreService genreService;
    private final SubgenreService subgenreService;

    @GetMapping("/game-search")
    public String getSearchPage() {
        return "game-search";
    }

    @PostMapping("/game-search")
    public String getSearchResult(@RequestParam("search_parameter") String searchParameter) {
        String pageName;

        switch (searchParameter) {
            case BY_NAME:
                pageName = "search-by-name";
                break;
            case BY_PLATFORM:
                pageName = "search-by-platform";
                break;
            case BY_GENRE:
                pageName = "search-by-genre";
                break;
            case BY_SUBGENRE:
                    pageName = "search-by-subgenre";
                    break;
            case BY_ISSUE_YEAR:
                pageName = "search-by-issue-year";
                break;
            default:
                pageName = "game-search";
                break;
        }

        return "redirect:/" + pageName;
    }

    @GetMapping("/search-by-name")
    public String getSearchByNamePage() {
        return "search-by-name";
    }

    @PostMapping("/search-by-name")
    public String getSearchResultByName(@RequestParam("game_name") String gameName) {
        GameFullInfoDto game = gameService.findByName(gameName);

        return SearchResultUrlUtil.getRedirectGameInfoUrl(game.getId());
    }

    @GetMapping("/search-by-platform")
    public String getSearchByPlatformPage(Model model) {
        model.addAttribute("platforms", Arrays.asList(GamePlatform.values()));

        return "search-by-platform";
    }

    @PostMapping("/search-by-platform")
    public String getSearchResultByPlatform(@RequestParam("search_platform") String platform, Model model) {
        List<GameSearchResultDto> searchResultGames = gameService.findByPlatform(GamePlatform.getByName(platform));

        model.addAttribute("searchResultGames", searchResultGames);
        return "search-result";
    }

    @GetMapping("/search-by-genre")
    public String getSearchByGenrePage(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        return "search-by-genre";
    }

    @PostMapping("/search-by-genre")
    public String getSearchResultByGenre(@RequestParam("search_genre") String genre, Model model) {
        Genre foundGenre = genreService.findByName(genre);
        List<GameSearchResultDto> searchResultGames = gameService.findByGenre(foundGenre);

        model.addAttribute("searchResultGames", searchResultGames);
        return "search-result";
    }

    @GetMapping("/search-by-subgenre")
    public String getSearchBySubgenrePage(Model model) {
        List<Subgenre> subgenres = subgenreService.findAll();
        model.addAttribute("subgenres", subgenres);

        return "search-by-subgenre";
    }

    @PostMapping("/search-by-subgenre")
    public String getSearchResultBySubgenre(@RequestParam("search_subgenre") String subgenre, Model model) {
        Subgenre foundSubgenre = subgenreService.findByName(subgenre);
        List<GameSearchResultDto> searchResultGames = gameService.findBySubgenre(foundSubgenre);

        model.addAttribute("searchResultGames", searchResultGames);
        return "search-result";
    }

    @GetMapping("/search-by-issue-year")
    public String getSearchByIssueYearPage(Model model) {
        return "search-by-issue-year";
    }

    @PostMapping("/search-by-issue-year")
    public String getSearchResultByIssueYear(@RequestParam("search_issue_year") String issueYear, Model model) {
        List<GameSearchResultDto> searchResultGames = gameService.findByIssueYear(Integer.valueOf(issueYear));

        model.addAttribute("searchResultGames", searchResultGames);
        return "search-result";
    }
}
