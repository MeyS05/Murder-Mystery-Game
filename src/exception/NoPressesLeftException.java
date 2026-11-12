package exception;

public class NoPressesLeftException extends GameException {

    public NoPressesLeftException() {
        super("You have already used all presses available! \nyou can try and talk to the suspect instead!");
    }
}
