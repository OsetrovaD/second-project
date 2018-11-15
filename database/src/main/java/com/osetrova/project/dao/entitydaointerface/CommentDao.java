package com.osetrova.project.dao.entitydaointerface;

import com.osetrova.project.dao.BaseDao;
import com.osetrova.project.entity.Comment;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.User;

import java.util.List;

public interface CommentDao extends BaseDao<Comment, Long> {

    List<Comment> getByGame(Game game);

    List<Comment> getByUser(User user);
}
