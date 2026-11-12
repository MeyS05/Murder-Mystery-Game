package model.world;

import model.interfaces.Collectable;
import model.interfaces.Inspectable;


public class Clue implements Inspectable, Collectable {

    private final String name;
    private final String surfaceDescription;
    private final String analysis;

    public Clue(String name, String surfaceDescription, String analysis) {
        this.name               = name;
        this.surfaceDescription = surfaceDescription;
        this.analysis           = analysis;
    }

    

    @Override
    public String getSurfaceDescription() {
        return name + ": " + surfaceDescription;
    }

    @Override
    public String inspect() {
        return "  [" + name.toUpperCase() + "]  " + analysis;
    }



    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getInventoryDescription() {
        return name + " — " + surfaceDescription;
    }
}
