package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.CommentDao;
import com.osetrova.project.entity.Comment;
import com.osetrova.project.entity.Comment_;
import com.osetrova.project.entity.Game;
import com.osetrova.project.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CommentDaoImpl extends BaseDaoImpl<Comment, Long> implements CommentDao {

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }

    @Override
    public List<Comment> getByGame(Game game) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Comment> criteria = cb.createQuery(Comment.class);
        Root<Comment> commentRoot = criteria.from(Comment.class);

        criteria.select(commentRoot)
                .where(
                    cb.equal(commentRoot.get(Comment_.game), game)
                );
        return getSessionFactory().getCurrentSession().createQuery(criteria).list();
    }

    @Override
    public List<Comment> getByUser(User user) {
        CriteriaBuilder cb = getSessionFactory().getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Comment> criteria = cb.createQuery(Comment.class);
        Root<Comment> commentRoot = criteria.from(Comment.class);

        criteria.select(commentRoot)
                .where(
                    cb.equal(commentRoot.get(Comment_.user), user)
                );
        return getSessionFactory().getCurrentSession().createQuery(criteria).list();
    }
}
