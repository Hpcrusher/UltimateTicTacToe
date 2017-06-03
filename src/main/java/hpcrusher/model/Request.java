package hpcrusher.model;

import java.util.UUID;

/**
 * @author liebl
 */
public class Request {

    private UUID gameId;
    private int bigField;
    private int smallField;

    public Request() {
    }

    public Request(UUID gameId, int bigField, int smallField) {
        this.gameId = gameId;
        this.bigField = bigField;
        this.smallField = smallField;
    }

    public int getBigField() {
        return bigField;
    }

    public void setBigField(int bigField) {
        this.bigField = bigField;
    }

    public int getSmallField() {
        return smallField;
    }

    public void setSmallField(int smallField) {
        this.smallField = smallField;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}