package com.osetrova.project.service;

import com.osetrova.project.converter.commentconverter.CommentToGameCommentDtoConverter;
import com.osetrova.project.converter.commentconverter.CommentToUserCommentDtoConverter;
import com.osetrova.project.dto.commentdto.GameCommentDto;
import com.osetrova.project.dto.commentdto.UserCommentDto;
import com.osetrova.project.entity.Comment;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.User;
import com.osetrova.project.exception.DaoException;
import com.osetrova.project.jparepository.CommentRepository;
import com.osetrova.project.jparepository.UserRepository;
import com.osetrova.project.jparepository.gamerepository.GameRepository;
import com.osetrova.project.servicedto.NewCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final CommentToGameCommentDtoConverter gameCommentConverter;
    private final CommentToUserCommentDtoConverter userCommentConverter;

    public List<GameCommentDto> findGameComments(Long gameId) {
        return commentRepository.findAllByGameId(gameId).stream()
                .map(gameCommentConverter::convert)
                .collect(toList());
    }

    public List<UserCommentDto> findUserComments(Long userId) {
        return commentRepository.findAllByUserId(userId).stream()
                .map(userCommentConverter::convert)
                .collect(toList());
    }

    public List<UserCommentDto> findUserComments(String login) {
        return commentRepository.findAllByUserLogin(login).stream()
                .map(userCommentConverter::convert)
                .collect(toList());
    }

    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public Comment save(NewCommentDto newComment) {
        Game game = gameRepository.findById(newComment.getGameId()).orElseThrow(DaoException::new);
        User user = userRepository.findByLogin(newComment.getUsername()).orElseThrow(DaoException::new);

        return commentRepository.save(Comment.builder()
                .date(LocalDate.now())
                .text(newComment.getText())
                .game(game)
                .user(user)
                .build());
    }
}
