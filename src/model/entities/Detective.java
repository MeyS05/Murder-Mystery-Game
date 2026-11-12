package model.entities;

import model.interfaces.Collectable;

import java.util.*;

public class Detective extends Character {
    private int accusationsLeft;
    private int pressesLeft;

    private final List<String> notebook = new ArrayList<>();
    private final List<Collectable> inventory = new ArrayList<>();
    private final Set<String> talkedTo = new HashSet<>();

    public Detective(String name, String startingRoom) {
        super(name, startingRoom);
    }

    public void setAccusations(int count) {
        this.accusationsLeft = count;
    }

    public void useAccusation() {
        if (accusationsLeft > 0) accusationsLeft--;
    }

    public boolean hasAccusationsLeft() {
        return accusationsLeft > 0;
    }

    public int getAccusationsLeft() {
        return accusationsLeft;
    }

    public void setPresses(int count) {
        this.pressesLeft = count;
    }

    public void usePress() {
        if (pressesLeft > 0) pressesLeft--;
    }

    public boolean hasPressesLeft() {
        return pressesLeft > 0;
    }

    public int getPressesLeft() {
        return pressesLeft;
    }

    public void addNote(String note) {
        notebook.add(note);
    }

    public List<String> getNotes() {
        return Collections.unmodifiableList(notebook);
    }

    public void addToInventory(Collectable item) {
        inventory.add(item);
    }

    public boolean hasItem(String name) {
        for (Collectable i : inventory) {
            if (i.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public List<Collectable> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    public void addTalkedTo(String suspectName) {
        talkedTo.add(suspectName.toLowerCase());
    }

    public boolean hasTalkedTo(String suspectName) {
        return talkedTo.contains(suspectName.toLowerCase());
    }

    public Set<String> getTalkedTo() {
        return Collections.unmodifiableSet(talkedTo);
    }

    @Override
    public String describe() {
        return "Detective " + getName()
                + "  |  Accusations left: " + accusationsLeft
                + "  |  Presses left: " + pressesLeft
                + "  |  Items: " + inventory.size();
    }
}