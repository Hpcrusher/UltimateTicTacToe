package timetakers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import timetakers.model.Game;

import java.util.UUID;

/**
 * @author David Liebl
 */
public interface GameRepository extends JpaRepository<Game, UUID>, JpaSpecificationExecutor<Game> {
}
