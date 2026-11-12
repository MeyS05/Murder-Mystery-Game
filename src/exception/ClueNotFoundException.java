package exception;

public class ClueNotFoundException extends GameException {

    private final String clueName;

    public ClueNotFoundException(String clueName) {
        super("There is no clue called \"" + clueName + "\" in this room.");
        this.clueName = clueName;
    }

    public String getClueName() {
        return clueName;
    }
}
