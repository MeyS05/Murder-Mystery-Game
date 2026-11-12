package engine;

import exception.*;
import model.entities.*;
import model.interfaces.Collectable;
import model.world.*;

import java.util.List;
import java.util.Scanner;

public class GameEngine {

    private GameWorld world;
    private Detective detective;
    private DifficultyConfig difficulty;
    private boolean gameOver;


    public void start() {
        printBanner();

        Scanner sc = new Scanner(System.in);

        String detectiveName = askDetectiveName(sc);
        difficulty = chooseDifficulty(sc);

        if (gameOver) {
            sc.close();
            return;
        }

        world = new GameWorld(difficulty, detectiveName);
        detective = world.getDetective();
        gameOver = false;

        printDetectiveBanner();
        printHelp();

        while (!gameOver) {

            Room current = world.getRoom(detective.getCurrentLocation());

            System.out.println("\n  " + current.getSurfaceDescription());
            System.out.print("> ");

            String input = sc.nextLine().trim();

            if (input.isEmpty()) continue;

            try {
                String response = process(input);
                System.out.println(response);

            } catch (GameException e) {
                System.out.println(e.getMessage());
            }
        }

        sc.close();
    }

    private String askDetectiveName(Scanner sc) {
        System.out.print("Enter detective name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            return "Detective";
        }

        return name;
    }

    private void printDetectiveBanner() {
        System.out.println("\nWelcome, detective " + detective.getName() + "!");
        System.out.println(detective.describe() + "\n");
    }

    private String process(String input) throws GameException {
        String[] parts = input.split(" ", 2);
        String   cmd   = parts[0].toLowerCase();
        String   arg   = parts.length > 1 ? parts[1].trim() : "";

        switch (cmd) {
            case "go":        return move(arg);
            case "look":      return look();
            case "examine":   return examine(arg);
            case "talk":      return talk(arg);
            case "press":     return press(arg);
            case "accuse":    return accuse(arg);
            case "inventory":
            case "inv":       return getInventoryString();
            case "notes":     return executeNotes();
            case "suspects":  return listSuspects();
            case "map":       return getMapString();
            case "hint":      return hint();
            case "help":      printHelp(); return "Commands menu listed above.";
            case "quit":
            case "exit":      confirmQuit(); return "Exiting game...";
            default:
                throw new InvalidActionException(cmd, "type 'help' for a list of commands");
        }
    }

    private String move(String roomName) throws InvalidMoveException {
        String targetRoom = roomName.trim();

        if (targetRoom.isEmpty()) {
            return "Usage: go <room name>";
        }

        String actualRoomKey = null;

        for (String roomKey : world.getRooms().keySet()) {
            if (roomKey.trim().equalsIgnoreCase(targetRoom)) {
                actualRoomKey = roomKey;
                break;
            }
        }

        if (actualRoomKey == null) {
            for (String roomKey : world.getRooms().keySet()) {
                if (roomKey.toLowerCase().contains(targetRoom.toLowerCase())) {
                    actualRoomKey = roomKey;
                    break;
                }
            }
        }

        if (actualRoomKey == null) {
            throw new InvalidMoveException(targetRoom);
        }

        detective.moveTo(actualRoomKey);
        Room next = world.getRoom(actualRoomKey);
        return "Moved to " + actualRoomKey + ".\n\n" + next.getSurfaceDescription();
    }

    private String look() {
        Room room = world.getRoom(detective.getCurrentLocation());
        return room.getSurfaceDescription().toUpperCase() + "\n\n" + room.inspect();
    }

    private String examine(String clueName) throws ClueNotFoundException {
        if (clueName.isEmpty()) {
            return "Usage: examine <clue name>  (use 'look' first)";
        }

        Room room  = world.getRoom(detective.getCurrentLocation());
        Clue clue  = room.getClueByName(clueName);

        if (clue == null) {
            if (detective.hasItem(clueName)) {
                return "You already examined and collected the " + clueName + ". Check your Inventory!";
            }
            throw new ClueNotFoundException(clueName);
        }

        String result = clue.inspect();
        detective.addNote("[" + room.getName() + "] " + result);

        detective.addToInventory(clue);
        room.removeClue(clueName);

        return "[EXAMINED]: " + result + "\n\n[AUTO-COLLECTED]: Added to your Inventory!"
        +("\n\nCurrent detective status\n" + detective.describe());
    }

    private String talk(String suspectName) throws SuspectNotFoundException {
        if (suspectName.isEmpty()) {
            return "Usage: talk <suspect name>";
        }

        Suspect suspect = getLocalSuspect(suspectName);
        String statement = suspect.getInitialStatement();

        detective.addNote("[" + suspect.getName() + "] " + statement);
        detective.addTalkedTo(suspect.getName());
        return suspect.getName() + ": \"" + statement + "\"";
    }

    private String press(String suspectName) throws SuspectNotFoundException, NoPressesLeftException {
        if (suspectName.isEmpty()) {
            return "Usage: press <suspect name>";
        }

        if (!detective.hasPressesLeft()) {
            throw new NoPressesLeftException();
        }

        Suspect suspect = getLocalSuspect(suspectName);
        String statement = suspect.press();
        detective.usePress();

        detective.addNote("[" + suspect.getName() + " pressed] " + statement);

        String result = suspect.getName() + " (under pressure): \"" + statement + "\"";
        result += "\n\n(Presses remaining: " + detective.getPressesLeft() + ")";

        if (!suspect.hasMoreToReveal()) {
            result += "\n(This suspect has nothing more to say under pressure.)";
        }
        return result + "\n\nCurrent detective status\n" + detective.describe();
    }

    private String accuse(String suspectName) throws NoAccusationsLeftException {
        if (suspectName.isEmpty()) {
            return "Usage: accuse <suspect name>";
        }

        if (!hasTalkedToAllSuspects()) {
            StringBuilder sb = new StringBuilder("ACCUSATION LOCKED!\n\nYou must talk to all suspects first. Remaining:\n");
            for (Suspect s : world.getAllSuspects()) {
                if (!detective.hasTalkedTo(s.getName())) {
                    sb.append("• ")
                            .append(s.getName())
                            .append(" (Location: ")
                            .append(s.getCurrentLocation())
                            .append(")\n");
                }
            }
            return sb.toString().trim();
        }

        if (!detective.hasAccusationsLeft()) {
            throw new NoAccusationsLeftException();
        }

        Suspect suspect = world.getSuspectByName(suspectName);
        if (suspect == null) {
            return "No suspect named \"" + suspectName + "\" exists.";
        }

        String outcome = suspect.describeAccusationOutcome();

        if (suspect.isKiller()) {
            gameOver = true;
            return "CASE SOLVED!\n\n" + outcome;
        } else {
            detective.useAccusation();
            int left = detective.getAccusationsLeft();
            if (left <= 0) {
                gameOver = true;
                return "GAME OVER\n\n" + outcome + "\n\nYou ran out of accusations. The case goes cold.";
            }
            return outcome + "\n\nAccusations remaining: " + left +  "\n\nCurrent detective status\n"
                    + detective.describe();
        }
    }

    public boolean isGameOver() { return this.gameOver; }
    public GameWorld getWorld() { return this.world; }
    public Detective getDetective() { return this.detective; }
    public String getCurrentRoomName() { return (detective == null) ? "Unknown" : detective.getCurrentLocation(); }
    public Room getCurrentRoom() { return (world == null || detective == null) ? null : world.getRoom(detective.getCurrentLocation()); }

    public String executeInventory() { return getInventoryString(); }
    public String executeNotes() { return getNotesString(); }

    private String getInventoryString() {
        List<Collectable> items = detective.getInventory();
        if (items.isEmpty()) return "Your inventory is empty.";
        StringBuilder sb = new StringBuilder();

        sb.append("INVENTORY (")
                .append(items.size())
                .append("):\n");

        for (Collectable item : items) {
            sb.append("• ")
                    .append(item.getInventoryDescription())
                    .append("\n");
        }

        return sb.toString().trim();
    }

    private String getNotesString() {
        List<String> notes = detective.getNotes();
        if (notes.isEmpty()) return "Your notebook is empty. Examine clues and talk to suspects!";
        return "NOTEBOOK (" + notes.size() + "):\n" + String.join("\n", notes);
    }

    private String listSuspects() {
        StringBuilder sb = new StringBuilder("ALL SUSPECTS:\n");
        world.getAllSuspects().forEach(s ->
                sb.append("• ").append(s.describe()).append(" (Location: ").append(s.getCurrentLocation()).append(")\n")
        );
        return sb.toString().trim();
    }

    private String getMapString() {
        StringBuilder sb = new StringBuilder("GAME MAP:\n");
        world.getRooms().forEach((name, room) -> {
            boolean here = name.equals(detective.getCurrentLocation());
            sb.append(here ? "► " : "  ").append(name)
                    .append(here ? " ← YOU ARE HERE" : "").append("\n");
        });
        return sb.toString().trim();
    }

    private String hint() {
        if (!difficulty.isHintsEnabled()) {
            return "Hints are disabled on " + difficulty.name() + " difficulty.";
        }
        return "Hint: The killer is someone with a financial motive. Try examining documents and pressing suspects.";
    }

    private DifficultyConfig chooseDifficulty(Scanner sc) {
        System.out.println("\nChoose difficulty:");
        for (DifficultyConfig d : DifficultyConfig.values()) {
            System.out.println("  " + d);
        }
        System.out.print("\nEnter difficulty (EASY / MEDIUM / HARD): ");
        while (true) {
            String input = sc.nextLine().trim();
            DifficultyConfig chosen = DifficultyConfig.fromString(input);
            if (chosen != null) {
                System.out.println("\n  Difficulty set to " + chosen.name() + ".\n");
                return chosen;
            }
            System.out.print("  Invalid choice. Try again: ");
        }
    }

    private boolean hasTalkedToAllSuspects() {

        for (Suspect s : world.getAllSuspects()) {
            if (!detective.hasTalkedTo(s.getName())) {
                return false;
            }
        }

        return true;
    }

    private Suspect getLocalSuspect(String name) throws SuspectNotFoundException {
        Suspect suspect = world.getSuspectByName(name);
        if (suspect == null) {
            throw new SuspectNotFoundException("\"" + name + "\" no such suspect exists. Type 'suspects' to see everyone.");
        }
        if (!suspect.getCurrentLocation().equalsIgnoreCase(detective.getCurrentLocation())) {
            throw new SuspectNotFoundException(name, suspect.getCurrentLocation());
        }
        return suspect;
    }

    private void confirmQuit() {
        System.out.println("\n\nDetective "+ detective.getName()
                + " closes the notebook and leaves the mansion, the case goes cold."
                + "\n\nThanks for playing Murder Mystery. Goodbye!");
        gameOver = true;
    }

    private void printBanner() {
        System.out.println(
                        "\n\n          MURDER MYSTERY GAME             \n\n" +
                        "    Can you find the killer in time?      \n"
        );
        System.out.println("The victim: Edward Harlow, found dead in the Study.");
        System.out.println("Four suspects. One killer. The truth is yours to find.\n");
    }

    private void printHelp() {
        System.out.println(
                        "\n\n               COMMANDS                   \n\n" +
                        "  go <room>        Move to a room         \n" +
                        "  look             Examine current room   \n" +
                        "  examine <clue>   Inspect & take a clue  \n" +
                        "  talk <suspect>   Speak to a suspect     \n" +
                        "  press <suspect>  Press for more info    \n" +
                        "  accuse <suspect> Make an accusation     \n" +
                        "  inventory / inv  Show collected items   \n" +
                        "  notes            Show your notebook     \n" +
                        "  suspects         List all suspects      \n" +
                        "  map              Show the game map      \n" +
                        "  hint             Get a hint (EASY only) \n" +
                        "  help             Show this menu         \n" +
                        "  quit             Exit the game          \n"
        );
    }
}