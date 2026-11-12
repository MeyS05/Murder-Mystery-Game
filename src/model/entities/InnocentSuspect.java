package model.entities;

import java.util.List;

public class InnocentSuspect extends Suspect {

    private final String alibi;

    public InnocentSuspect(String name,
                           String location,
                           String description,
                           String initialStatement,
                           List<String> pressedStatements,
                           String alibi) {
        super(name, location, description, initialStatement, pressedStatements);
        this.alibi = alibi;
    }

    @Override
    public String describeAccusationOutcome() {
        return "Wrong accusation.\n"
                + "    Alibi: " + alibi;
    }

    @Override
    public boolean isKiller() {
        return false;
    }
}
