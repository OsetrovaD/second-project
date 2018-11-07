package service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import project.dao.entitydao.GameDaoImpl;
import project.dto.GameFilterDto;
import project.dto.LimitOffsetDto;
import project.entity.Game;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameService {

    private static final GameService INSTANCE = new GameService();

    public List<Game> findAll(Session session) {
            return GameDaoImpl.getInstance().findAll(session);
    }

    public List<Game> filterGames(Session session, GameFilterDto filters, LimitOffsetDto limitOffset) {
        return GameDaoImpl.getInstance().filterAllGames(session, filters, limitOffset);
    }

    public static GameService getInstance() {
        return INSTANCE;
    }
}
