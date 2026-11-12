package exception;

public class InvalidMoveException extends GameException {

    private final String attemptedRoom;

    public InvalidMoveException(String roomName) {
        super("There is no room called \"" + roomName + "\" in this house. Type 'map' to see all rooms.");
        this.attemptedRoom = roomName;
    }

    public String getAttemptedRoom() {
        return attemptedRoom;
    }
}
