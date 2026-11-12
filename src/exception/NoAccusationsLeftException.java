package exception;


public class NoAccusationsLeftException extends GameException {

    public NoAccusationsLeftException() {
        super("You have no accusations left. The killer has escaped!");
    }
}
