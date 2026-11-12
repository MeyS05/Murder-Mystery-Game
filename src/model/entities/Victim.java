package model.entities;

public class Victim extends Character {

    private final String causeOfDeath;

    public Victim(String name, String location, String causeOfDeath) {
        super(name, location);
        this.causeOfDeath = causeOfDeath;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    @Override
    public String describe() {
        return "Victim: " + getName()
                + "  |  Cause of death: " + causeOfDeath;
    }
}
