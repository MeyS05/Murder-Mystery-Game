package engine;

import model.entities.*;
import model.world.*;

import java.util.*;

public class GameWorld {

    private final Map<String, Room> rooms    = new HashMap<>();
    private final List<Suspect>     suspects = new ArrayList<>();
    private final Detective         detective;
    private final Victim            victim;
    private static final String KILLER_NAME = "Victor";
    private static final String VICTIM_NAME = "Edward Harlow";
    private static final String START_ROOM  = "Entrance";


    public GameWorld(DifficultyConfig difficulty, String detectiveName) {

        

        Room entrance = new Room("Entrance", "A cold marble foyer.");
        Room study    = new Room("Study",    "Walls lined with leather-bound books. A desk sits centre stage.");
        Room kitchen  = new Room("Kitchen",  "Copper pots hang overhead. The smell of iron fills the air.");
        Room garden   = new Room("Garden",   "A moonlit garden.");
        Room library  = new Room("Library",  "Rows of shelves. A reading lamp flickers near the fireplace.");

        

        study.addClue(new Clue(
                "letter",
                "a crumpled letter on the desk",
                "The letter reads: 'Pay what you owe or face the consequences. V"
        ));

        study.addClue(new Clue(
                "inkstain",
                "fresh ink stain on the left side of the desk",
                "The stain is still wet. someone wrote here very recently."
        ));

        kitchen.addClue(new Clue(
                "knife",
                "a bloodstained carving knife near the sink",
                "The blade carries a partial fingerprint. a right-handed grip."
        ));

        kitchen.addClue(new Clue(
                "glass",
                "a shattered wine glass on the floor",
                "Shattered from above, not dropped. it was thrown."
        ));

        garden.addClue(new Clue(
                "footprints",
                "muddy footprints leading from the garden door",
                "Size 10 shoes, deeply imprinted. the person was running."
        ));

        library.addClue(new Clue(
                "diary",
                "an open diary on the reading table",
                "Entry dated tonight: 'Victor came to collect. I refused. He was furious.'"
        ));

        

        rooms.put("Entrance", entrance);
        rooms.put("Study",    study);
        rooms.put("Kitchen",  kitchen);
        rooms.put("Garden",   garden);
        rooms.put("Library",  library);

        

        KillerSuspect victor = new KillerSuspect(
                KILLER_NAME,        
                "Study",
                "Cold-eyed businessman, immaculate suit",
                "I had a drink with the victim earlier. Perfectly civil.",
                List.of(
                        "Alright.. we argued about money. But I left him alive.",
                        "He owed me everything. I just... lost my temper.",
                        "Fine. I did it. He refused to pay. I snapped."
                ),
                "Unpaid debt of £50,000",
                "He laughed in my face. No one does that. Not to me."
        );

        InnocentSuspect anna = new InnocentSuspect(
                "Anna",
                "Kitchen",
                "Victim's younger sister, red-eyed from crying",
                "I was in the kitchen all evening preparing dinner.",
                List.of(
                        "I heard shouting from the study but I was too frightened to go in.",
                        "The shouting was Victor's voice, I'm certain of it."
                ),
                "I was in the kitchen the whole night. the cook saw me."
        );

        InnocentSuspect margaret = new InnocentSuspect(
                "Margaret",
                "Library",
                "The household housekeeper, composed but shaken",
                "I was reading in the library until I heard the commotion.",
                List.of(
                        "Victor passed the library twice. He looked like he was pacing.",
                        "He went toward the study the second time. I heard the door slam."
                ),
                "I never left the library. the diary I was reading proves the timestamp."
        );

        InnocentSuspect daniel = new InnocentSuspect(
                "Daniel",
                "Kitchen",
                "The family cook, nervous and fidgety",
                "I was in the kitchen all night. Never left once.",
                List.of(
                        "Anna was with me the whole evening, I swear it. She never went near the study.",
                        "I heard Victor's voice from the hallway. he sounded furious."
                ),
                "I was cooking all night. Anna was right there with me. We never left the kitchen."
        );

        suspects.add(victor);
        suspects.add(anna);
        suspects.add(margaret);
        suspects.add(daniel);

        

        study.addSuspect(victor);
        kitchen.addSuspect(anna);
        kitchen.addSuspect(daniel);
        library.addSuspect(margaret);

        

        victim = new Victim(
                VICTIM_NAME,        
                "Study",
                "Blunt force trauma to the back of the head"
        );

        

        detective = new Detective(detectiveName, START_ROOM);
        detective.setAccusations(difficulty.getAccusations());
        detective.setPresses(difficulty.getMaxPresses());
    }

    

    public Room getRoom(String name) {
        return rooms.get(name);
    }

    public Room getRoomIgnoreCase(String name) {
        for (Map.Entry<String, Room> entry : rooms.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Detective         getDetective()    { return detective;                              }
    public Victim            getVictim()       { return victim;                                 }
    public List<Suspect>     getAllSuspects()   { return Collections.unmodifiableList(suspects); }
    public Map<String, Room> getRooms()        { return Collections.unmodifiableMap(rooms);     }

    public Suspect getSuspectByName(String name) {
        for (Suspect s : suspects) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
}