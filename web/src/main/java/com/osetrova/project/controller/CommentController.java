package com.osetrova.project.controller;

import com.osetrova.project.service.CommentService;
import com.osetrova.project.dto.commentdto.GameCommentDto;
import com.osetrova.project.dto.commentdto.UserCommentDto;
import com.osetrova.project.servicedto.NewCommentDto;
import com.osetrova.project.util.GetCurrentUserDetailsUtil;
import com.osetrova.project.util.SearchResultUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/game-comments")
    public String getGameCommentsPage(Model model, @RequestParam("id") Long id) {
        List<GameCommentDto> gameComments = commentService.findGameComments(id);
        String username = GetCurrentUserDetailsUtil.getCurrentUser().getUsername();
        model.addAttribute("gameComments", gameComments);
        model.addAttribute("username", username);

        return "game-comments";
    }

    @GetMapping("/user-comments")
    public String getUserCommentsPage(Model model, @RequestParam(value = "id", required = false) Long id) {
        List<UserCommentDto> userComments;

        if (id != null) {
            userComments = commentService.findUserComments(id);
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails user = (UserDetails) authentication.getPrincipal();

            userComments = commentService.findUserComments(user.getUsername());
        }

        model.addAttribute("userComments", userComments);

        return "user-comments";
    }

    @GetMapping("/add-comment")
    public String getNewCommentPage(@RequestParam("id") Long gameId, Model model) {
        model.addAttribute("newComment", NewCommentDto.builder()
                                                .gameId(gameId)
                                                .build());

        return "new-comment";
    }

    @PostMapping("/add-comment")
    public String addNewComment(NewCommentDto commentData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String username = user.getUsername();

        commentData.setUsername(username);
        commentService.save(commentData);

        return SearchResultUrlUtil.getRedirectGameCommentsUrl(commentData.getGameId());
    }

    @GetMapping("/delete-comment")
    public String deleteComment(@RequestParam("id") Long id, @RequestParam(value = "gameId", required = false) Long gameId) {
        String resultPage;
        if (gameId != null) {
            resultPage = "redirect:/game-comments?id=" + gameId;
        } else {
            resultPage = "redirect:/user-comments";
        }
        commentService.deleteById(id);

        return resultPage;
    }
}
