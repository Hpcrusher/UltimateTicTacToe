package hpcrusher.web.controller;

import hpcrusher.exception.ValidationRuntimeException;
import hpcrusher.model.Error;
import hpcrusher.model.*;
import hpcrusher.repository.GameRepository;
import hpcrusher.services.GameService;
import hpcrusher.services.NameResolverService;
import hpcrusher.services.SecurityService;
import hpcrusher.util.TextKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author David Liebl
 */
@Controller
@Transactional
@RequestMapping(value = "game/")
public class GameController {

    private final GameRepository gameRepository;
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NameResolverService nameResolverService;

    @Autowired
    public GameController(GameRepository gameRepository, GameService gameService, SimpMessagingTemplate simpMessagingTemplate, NameResolverService nameResolverService) {
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.nameResolverService = nameResolverService;
    }

    @RequestMapping(value = "new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @MessageMapping("/move")
    public void response(Request request) throws Exception {
        Game game = gameRepository.findOne(request.getGameId());

        final Person loggedInPerson = SecurityService.getLoggedInPerson();

        final Person player1 = game.getPlayer1();
        final Person player2 = game.getPlayer2();
        if (loggedInPerson != null && (loggedInPerson.equals(player1) && game.isP1Turn() || loggedInPerson.equals(player2) && !game.isP1Turn())) {

            if (!gameService.isMoveValid(game, request.getBigField(), request.getSmallField())) {
                simpMessagingTemplate.convertAndSendToUser(nameResolverService.getUsername(loggedInPerson), "/queue/notValid", new Error("Move not Valid"));
                return;
            }

            final int[][] board = game.getBoard();
            final boolean p1Turn = game.isP1Turn();
            board[request.getBigField()][request.getSmallField()] = p1Turn ? 1 : -1;
            final int winner = gameService.checkForWin(game);
            switch (winner) {
                case 1:
                case -1:
                    game.setP1Winner(winner);
                    game = gameRepository.save(game);
                    Response payload = new Response(game);
                    player1.countGamesPlayedUp();
                    player2.countGamesPlayedUp();
                    if (winner == 1) {
                        player1.countWinsUp();
                        player2.countLossesUp();
                    } else {
                        player1.countLossesUp();
                        player2.countWinsUp();
                    }
                    simpMessagingTemplate.convertAndSendToUser(nameResolverService.getUsername(player1), "/queue/winner", payload);
                    simpMessagingTemplate.convertAndSendToUser(nameResolverService.getUsername(player2), "/queue/winner", payload);
                    return;
                case 0:
                    game.setNextValidQuadrant(gameService.getNextValidQuadrant(board[request.getSmallField()], request.getSmallField()));
                    game.setP1Turn(!p1Turn);
                    game = gameRepository.save(game);
                    final Person nextPlayer = getNextPlayer(game);
                    String username = nextPlayer != null ? nextPlayer.getUsername() : null;

                    if (username != null) {
                        simpMessagingTemplate.convertAndSendToUser(username, "/queue/game", new Response(game));
                    }
                    return;
                default:
            }
        } else {
            simpMessagingTemplate.convertAndSendToUser(nameResolverService.getUsername(loggedInPerson), "/queue/notValid", new Error("Not your turn!"));
        }
    }

    private Person getNextPlayer(Game game) {
        if (game.isP1Turn()) {
            return game.getPlayer1();
        } else {
            return game.getPlayer2();
        }
    }

    @RequestMapping(value = "lobby", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Game> getLobby() {
        final Person loggedInPerson = SecurityService.getLoggedInPerson();
        return gameRepository.findByPlayer1OrPlayer2OrPlayer2IsNull(loggedInPerson, loggedInPerson);
    }

    @RequestMapping(value = "{id}/join", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Game joinGame(@PathVariable UUID id) {
        Game game = gameRepository.findOne(id);
        if (game == null) {
            throw new ValidationRuntimeException(new TextKey("gameNotFound", "Spiel wurde nicht gefunden"), null);
        }
        Person loggedInPerson = SecurityService.getLoggedInPerson();
        if (loggedInPerson != null && (loggedInPerson.equals(game.getPlayer1()) || loggedInPerson.equals(game.getPlayer2()))) {
            return game;
        }
        if (game.getPlayer2() == null) {
            game.setPlayer2(loggedInPerson);
        }
        return gameRepository.save(game);
    }

}
