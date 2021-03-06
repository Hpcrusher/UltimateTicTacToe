package hpcrusher.services;

import hpcrusher.model.Game;
import org.springframework.stereotype.Service;

/**
 * @author liebl
 */
@Service
public class GameService {

    public boolean isMoveValid(Game game, int bigField, int smallField) {
        if (checkWin(game.getBoard()[bigField]) != 0) {
            return false;
        }
        if (!(game.getNextValidQuadrant() != bigField && game.getNextValidQuadrant() != -1)) {
            if (game.getBoard()[bigField][smallField] == 0) {
                return true;
            }
        }
        return false;
    }

    public int checkForWin(Game game) {
        int[] wonQuadrants = game.getWonQuadrants();
        for (int i = 0; i < 9; i++) {
            if (wonQuadrants[i] == 0) {
                wonQuadrants[i] = checkWin(game.getBoard()[i]);
            }
        }
        return checkWin(wonQuadrants);
    }

    private int checkWin(final int[] board) {
        if (board.length != 9) {
            throw new RuntimeException();
        }
        for (int i = 0; i != 9; i+=3) {
            if (board[i] != 0) {
                if (board[i] == board[i + 1] && board[i + 1] == board[i + 2]) {
                    return board[i];
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board[i] != 0) {
                if (board[i] == board[i + 3] && board[i + 3] == board[i + 6]) {
                    return board[i];
                }
            }
        }
        if (board[4] != 0) {
            if (board[0] == board[4] && board[4] == board[8]) {
                return board[4];
            }
            if (board[2] == board[4] && board[4] == board[6]) {
                return board[4];
            }
        }
        return 0;
    }

    public int getNextValidQuadrant(final int[] board, int smallField) {
        final int win = checkWin(board);
        return win == 0 ? smallField : -1;
    }
}
