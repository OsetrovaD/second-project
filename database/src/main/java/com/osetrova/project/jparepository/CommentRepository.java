package com.osetrova.project.jparepository;

import com.osetrova.project.entity.Comment;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByGame(Game game);

    List<Comment> findAllByUser(User user);
}
