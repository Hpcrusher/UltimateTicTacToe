package hpcrusher.services;

import hpcrusher.model.Game;
import org.springframework.stereotype.Service;

/**
 * @author liebl
 */
@Service
public class GameService {

    public boolean isMoveValid(Game game, int bigField, int smallField) {
        return !(game.getNextValidQuadrant() != bigField && game.getNextValidQuadrant() != -1) && game.getBoard()[bigField][smallField] == 0;
    }

    public int checkForWin(Game game) {
        int[] wins = new int[9];
        for (int i = 0; i < 9; i++) {
            wins[i] = checkWin(game.getBoard()[i]);
        }
        return checkWin(wins);
    }

    private int checkWin(int[] board) {
        if (board.length != 9) {
            throw new RuntimeException();
        }
        for (int i = 0; i < 3; i++) {
            if (board[i] != 0) {
                if (board[i] == board[i + 1] && board[i + 1] == board[i + 2]) {
                    return board[i];
                }
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

}
