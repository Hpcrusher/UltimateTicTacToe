package hpcrusher.model;

/**
 * @author liebl
 */
public class Request {

    private int bigField;
    private int smallField;

    public Request() {
    }

    public Request(int bigField, int smallField) {
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
}