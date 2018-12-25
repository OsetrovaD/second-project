package com.osetrova.project.jparepository;

import com.osetrova.project.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByGameId(Long gameId);

    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByUserLogin(String login);
}
