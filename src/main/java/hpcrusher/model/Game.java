package hpcrusher.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author liebl
 */

@Entity
@Table(name = "game")
@Proxy(lazy = false)
public class Game extends AbstractIdEntity {

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "player1_id")
    private Person player1;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "player2_id")
    private Person player2;

    @Column(name = "board")
    private int[][] board;

    @Column(name = "won_quadrants")
    private int[] wonQuadrants;

    @Column(name = "p1_turn")
    private boolean p1Turn;

    @Column(name = "next_valid_quadrant")
    private int nextValidQuadrant = -1;

    @Column(name = "p1_winner")
    private int winner;

    public Person getPlayer1() {
        return player1;
    }

    public void setPlayer1(Person player1) {
        this.player1 = player1;
    }

    public Person getPlayer2() {
        return player2;
    }

    public void setPlayer2(Person player2) {
        this.player2 = player2;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getWonQuadrants() {
        return wonQuadrants;
    }

    public void setWonQuadrants(int[] wonQuadrants) {
        if (wonQuadrants == null) {
            this.wonQuadrants = new int[9];
        } else {
            this.wonQuadrants = wonQuadrants;
        }
    }

    public boolean isP1Turn() {
        return p1Turn;
    }

    public void setP1Turn(boolean p1Turn) {
        this.p1Turn = p1Turn;
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public int getNextValidQuadrant() {
        return nextValidQuadrant;
    }

    public void setNextValidQuadrant(int nextValidQuadrant) {
        this.nextValidQuadrant = nextValidQuadrant;
    }

    public void setP1Winner(int winner) {
        this.winner = winner;
    }

    public int getWinner() {
        return winner;
    }

    public static final class GameBuilder {
        protected UUID id;
        private Person player1;
        private Person player2;
        private int[][] board;
        private int[] wonQuadrants;
        private boolean p1Turn;
        private int winner;

        private GameBuilder() {
        }

        public GameBuilder withPlayer1(Person player1) {
            this.player1 = player1;
            return this;
        }

        public GameBuilder withPlayer2(Person player2) {
            this.player2 = player2;
            return this;
        }

        public GameBuilder withBoard(int[][] board) {
            this.board = board;
            return this;
        }

        public GameBuilder withP1Turn(boolean p1Turn) {
            this.p1Turn = p1Turn;
            return this;
        }

        public GameBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public GameBuilder withWinner(int winner) {
            this.winner = winner;
            return this;
        }

        public GameBuilder withWonQuadrants(int[] wonQuadrants) {
            this.wonQuadrants = wonQuadrants;
            return this;
        }

        public Game build() {
            Game game = new Game();
            game.setId(id);
            game.setBoard(board);
            game.setWonQuadrants(wonQuadrants);
            game.setPlayer1(player1);
            game.setPlayer2(player2);
            game.setP1Turn(p1Turn);
            game.setP1Winner(winner);
            return game;
        }
    }
}
