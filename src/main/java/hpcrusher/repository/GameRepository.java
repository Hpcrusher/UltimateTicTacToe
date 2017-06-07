package hpcrusher.repository;

import hpcrusher.model.Game;
import hpcrusher.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

/**
 * @author David Liebl
 */
public interface GameRepository extends JpaRepository<Game, UUID>, JpaSpecificationExecutor<Game> {

    List<Game> findByPlayer1OrPlayer2OrPlayer2IsNull(Person player1, Person player2);

}
