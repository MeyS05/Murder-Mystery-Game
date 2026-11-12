package exception;

public class InvalidActionException extends GameException {

    private final String action;

    public InvalidActionException(String action, String reason) {
        super("Unknown command \"" + action + "\": " + reason);
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
