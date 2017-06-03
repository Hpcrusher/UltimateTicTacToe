package hpcrusher.web.controller;

import hpcrusher.exception.InvalidMoveException;
import hpcrusher.model.*;
import hpcrusher.repository.GameRepository;
import hpcrusher.services.GameService;
import hpcrusher.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author David Liebl
 */
@Controller
@RequestMapping(value = "game/")
public class GameController {

    private final GameRepository gameRepository;
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GameController(GameRepository gameRepository, GameService gameService, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RequestMapping(value = "new", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Game newGame() {
        final int[][] board = new int[9][9];
        for (int[] aBoard : board) {
            Arrays.fill(aBoard, 0);
        }

        Game game = Game.builder()
                .withPlayer1(SecurityService.getLoggedInPerson())
                .withBoard(board)
                .withP1Turn(true)
                .build();

        return gameRepository.save(game);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Game getGameAsJson(UUID id) {
        return gameRepository.findOne(id);
    }

    @MessageMapping("/hello")
    public void response(Request request) throws Exception {
        final Game game = gameRepository.findOne(request.getGameId());

        final Person loggedInPerson = SecurityService.getLoggedInPerson();

//        if (loggedInPerson.equals(game.getPlayer1()) && game.isP1Turn()) {
//            simpMessagingTemplate.convertAndSendToUser();
//        }

        if (!gameService.isMoveValid(game, request.getBigField(), request.getSmallField())) {
            throw new InvalidMoveException();
        }
        final int winner = gameService.checkForWin(game);
        switch (winner) {
            case 1:
            case -1:
                return;
            case 0:
                game.setNextValidQuadrant(request.getBigField());
                final int[][] board = game.getBoard();
                final boolean p1Turn = game.isP1Turn();
                board[request.getBigField()][request.getSmallField()] = p1Turn ? 1 : -1;
                game.setP1Turn(!p1Turn);
                gameRepository.save(game);
                simpMessagingTemplate.convertAndSendToUser();
            default:
                return;
        }
    }

    private String getUserNameOfPerson(Person person) {
        SecurityService.
    }

    @RequestMapping(value = "/lobby", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UUID> getLobby() {
        return gameRepository.findAll().stream().map(AbstractIdEntity::getId).collect(Collectors.toList());
    }

}
