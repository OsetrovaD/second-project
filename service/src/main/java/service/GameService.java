package service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import project.connection.ApplicationSessionFactory;
import project.dao.entitydao.GameDaoImpl;
import project.dto.GameFilterDto;
import project.dto.LimitOffsetDto;
import project.entity.Game;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameService {

    private static final GameService INSTANCE = new GameService();
    private static final SessionFactory FACTORY = ApplicationSessionFactory.getSessionFactory();

    public List<Game> findAll() {
        try (Session session = FACTORY.openSession()) {
            return GameDaoImpl.getInstance().findAll(session);
        }
    }

    public List<Game> filterGames(GameFilterDto filters, LimitOffsetDto limitOffset) {
        try (Session session = FACTORY.openSession()) {
            return GameDaoImpl.getInstance().filterAllGames(session, filters, limitOffset);
        }
    }

    public static GameService getInstance() {
        return INSTANCE;
    }
}
