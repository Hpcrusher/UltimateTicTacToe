package timetakers.web.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import timetakers.model.Game;
import timetakers.repository.GameRepository;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author David Liebl
 */
@Controller
@RequestMapping(value = "game/")
public class GameController {

    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping(value = "new", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Game newGame() {
        final Integer[][] board = new Integer[9][9];
        for (int i = 0;i< board.length;i++) {
            Arrays.fill(board[i], 0);

        }
        return Game.builder()
                .withBoard(board)
                .build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Game getGameAsJson(UUID id) {
        return gameRepository.findOne(id);
    }
}
