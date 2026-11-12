package model.entities;

import java.util.List;


public class KillerSuspect extends Suspect {

    private final String motive;
    private final String confession;

    public KillerSuspect(String name,
                         String location,
                         String description,
                         String initialStatement,
                         List<String> pressedStatements,
                         String motive,
                         String confession) {
        super(name, location, description, initialStatement, pressedStatements);
        this.motive     = motive;
        this.confession = confession;
    }

    @Override
    public String describeAccusationOutcome() {
        return "KILLER FOUND!\n"
                + "    Confession : " + confession + "\n"
                + "    Motive     : " + motive;
    }

    @Override
    public boolean isKiller() {
        return true;
    }
}
