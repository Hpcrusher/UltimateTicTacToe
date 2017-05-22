package hpcrusher.model;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author liebl
 */

@Entity
@Table(name = "game")
public class Game extends AbstractIdEntity {

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "player1_id")
    private Person player1;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "player2_id")
    private Person player2;

    @Column(name = "board")
    private Integer[][] board;

    @Column(name = "p1_turn")
    private boolean p1Turn;

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

    public Integer[][] getBoard() {
        return board;
    }

    public void setBoard(Integer[][] board) {
        this.board = board;
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

    public static final class GameBuilder {
        protected UUID id;
        private Person player1;
        private Person player2;
        private Integer[][] board;
        private boolean p1Turn;

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

        public GameBuilder withBoard(Integer[][] board) {
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

        public Game build() {
            Game game = new Game();
            game.setId(id);
            game.setBoard(board);
            game.setPlayer1(player1);
            game.setPlayer2(player2);
            game.setP1Turn(p1Turn);
            return game;
        }
    }
}