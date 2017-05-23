package hpcrusher.web.controller;

import hpcrusher.model.Game;
import hpcrusher.model.Request;
import hpcrusher.model.Response;
import hpcrusher.repository.GameRepository;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public Game newGame() {
        final int[][] board = new int[9][9];
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

    @MessageMapping("/hello")
    @SendTo("/back")
    public Response greeting(Request message) throws Exception {
        return new Response();
    }

}
