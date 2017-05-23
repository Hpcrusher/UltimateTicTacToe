package hpcrusher.model;

/**
 * @author liebl
 */
public class Response {

    private Game content;

    public Response() {
    }

    public Response(Game content) {
        this.content = content;
    }

    public Game getContent() {
        return content;
    }

}
