package project.dao.entitydaointerface;

import org.hibernate.Session;
import project.dao.BaseDao;
import project.dto.GameFilterDto;
import project.dto.LimitOffsetDto;
import project.entity.Game;
import project.entity.Genre;
import project.entity.Subgenre;
import project.entity.enumonly.GamePlatform;

import java.util.List;

public interface GameDao extends BaseDao<Game, Long> {

    List<Game> filterAllGames(Session session, GameFilterDto filters, LimitOffsetDto limitOffset);

    List<Game> findByGenre(Session session, Genre genre);

    List<Game> findBySubgenre(Session session, Subgenre subgenre);

    List<Game> findByIssueYear(Session session, Integer issueYear);

    List<Game> findByName(Session session, String name);

    List<Game> findByGamePlatfrom(Session session, GamePlatform platform);
}
