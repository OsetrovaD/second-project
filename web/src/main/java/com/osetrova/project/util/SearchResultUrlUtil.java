package com.osetrova.project.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SearchResultUrlUtil {


    private static final String SEARCH_RESULT_PREFIX = "redirect:/search-result?parameter=";
    private static final String SEARCH_RESULT_SUFFIX = "&name=";
    private static final String GAME_COMMENTS_PREFIX = "redirect:/game-comments?id=";
    private static final String GAME_INFO_PREFIX = "redirect:/game-info?id=";

    public static String getRedirectSearchResultUrl(String searchParameter, String parameterName) {
        return SEARCH_RESULT_PREFIX + searchParameter + SEARCH_RESULT_SUFFIX + parameterName;
    }

    public static String getRedirectGameCommentsUrl(Long gameId) {
        return GAME_COMMENTS_PREFIX + gameId;
    }

    public static String getRedirectGameInfoUrl(Long gameId) {
        return GAME_INFO_PREFIX + gameId;
    }
}
