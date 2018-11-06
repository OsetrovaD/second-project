package project.dao.entitydao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import project.dao.BaseDaoImpl;
import project.dao.entitydaointerface.CommentDao;
import project.entity.Comment;
import project.entity.Comment_;
import project.entity.Game;
import project.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDaoImpl extends BaseDaoImpl<Comment, Long> implements CommentDao {

    private static final CommentDaoImpl INSTANCE = new CommentDaoImpl();

    public static CommentDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }

    @Override
    public List<Comment> getByGame(Session session, Game game) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Comment> criteria = cb.createQuery(Comment.class);
        Root<Comment> commentRoot = criteria.from(Comment.class);

        criteria.select(commentRoot)
                .where(
                    cb.equal(commentRoot.get(Comment_.game), game)
                );
        return session.createQuery(criteria).list();
    }

    @Override
    public List<Comment> getByUser(Session session, User user) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Comment> criteria = cb.createQuery(Comment.class);
        Root<Comment> commentRoot = criteria.from(Comment.class);

        criteria.select(commentRoot)
                .where(
                    cb.equal(commentRoot.get(Comment_.user), user)
                );
        return session.createQuery(criteria).list();
    }
}
