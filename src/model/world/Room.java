package model.world;

import model.entities.Suspect;
import model.interfaces.Inspectable;

import java.util.*;

public class Room implements Inspectable {

    private final String name;
    private final String description;

    private final List<Clue> clues = new ArrayList<>();
    private final List<Suspect> suspects = new ArrayList<>();
    private final List<String> connections = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public void addClue(Clue clue) {
        clues.add(clue);
    }

    public void addSuspect(Suspect s) {
        suspects.add(s);
    }

    public boolean removeClue(String name) {
        return clues.removeIf(c -> c.getName().equalsIgnoreCase(name));
    }


    public String getName() {
        return name;
    }


    public List<Suspect> getSuspects() {
        return Collections.unmodifiableList(suspects);
    }

    public Clue getClueByName(String name) {
        for (Clue c : clues) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }


    @Override
    public String getSurfaceDescription() {
        return name + " — " + description;
    }

    @Override
    public String inspect() {
        StringBuilder sb = new StringBuilder();

        if (clues.isEmpty()) {
            sb.append("  Clues    : none\n");
        } else {
            sb.append("  Clues    :\n");
            clues.forEach(c -> sb.append("    • ").append(c.getSurfaceDescription()).append("\n"));
        }

        if (suspects.isEmpty()) {
            sb.append("  Suspects : none\n");
        } else {
            sb.append("  Suspects :\n");
            suspects.forEach(s -> sb.append("    • ")
                    .append(s.getName())
                    .append(" — ")
                    .append(s.getDescription())
                    .append("\n"));
        }

        return sb.toString();
    }

}