package hpcrusher.repository;

import hpcrusher.model.Game;
import hpcrusher.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author David Liebl
 */
public interface GameRepository extends JpaRepository<Game, UUID>, JpaSpecificationExecutor<Game> {

    @Query(value = "SELECT g FROM Game g WHERE ((player1 = :p1 OR player2 = :p2) AND p1Winner IS NULL) OR player2 IS NULL")
    List<Game> findByPlayer1OrPlayer2OrPlayer2IsNull(@Param("p1") Person player1, @Param("p2") Person player2);

}
