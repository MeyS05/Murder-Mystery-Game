package exception;



public class SuspectNotFoundException extends GameException {

    public SuspectNotFoundException(String suspectName) {
        super("There is no suspect called \"" + suspectName + "\" here.");
    }

    
    public SuspectNotFoundException(String suspectName, String location) {
        super("\"" + suspectName + "\" is not here. They are in the " + location + ".");
    }
}