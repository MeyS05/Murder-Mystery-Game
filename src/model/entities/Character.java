package model.entities;

public abstract class Character {

    private final String name;
    private String currentLocation;

    public Character(String name, String startingLocation) {
        this.name            = name;
        this.currentLocation = startingLocation;
    }

    

    public String getName() {
        return name;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    

    public void moveTo(String roomName) {
        this.currentLocation = roomName;
    }

    public abstract String describe();
}