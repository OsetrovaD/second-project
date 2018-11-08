package project.dao.entitydaointerface;

import org.hibernate.Session;
import project.dao.BaseDao;
import project.entity.Comment;
import project.entity.Game;
import project.entity.User;

import java.util.List;

public interface CommentDao extends BaseDao<Comment, Long> {

    List<Comment> getByGame(Session session, Game game);

    List<Comment> getByUser(Session session, User user);
}
