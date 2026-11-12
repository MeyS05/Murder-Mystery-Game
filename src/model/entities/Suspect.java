package model.entities;

import model.interfaces.Interrogatable;

import java.util.List;

public abstract class Suspect extends Character implements Interrogatable {

    private final String       description;
    private final String       initialStatement;
    private final List<String> pressedStatements;

    private int pressCount = 0;

    public abstract boolean isKiller();

    public Suspect(String name,
                      String location,
                      String description,
                      String initialStatement,
                      List<String> pressedStatements) {
        super(name, location);
        this.description       = description;
        this.initialStatement  = initialStatement;
        this.pressedStatements = pressedStatements;
    }

    

    @Override
    public String getInitialStatement() {
        return initialStatement;
    }


    @Override
    public String press() {
        String result;
        if (pressedStatements.isEmpty()) {
            result = initialStatement;
        } else {
            int index = Math.min(pressCount, pressedStatements.size() - 1);
            result = pressedStatements.get(index);
        }
        if (pressCount < pressedStatements.size() - 1) {
            pressCount++;
        }
        return result;
    }

    public boolean hasMoreToReveal() {
        return pressCount < pressedStatements.size() - 1;
    }


    public abstract String describeAccusationOutcome();

    

    public String getDescription() {
        return description;
    }

    @Override
    public String describe() {
        return getName() + " — " + description;
    }

}