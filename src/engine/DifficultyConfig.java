package engine;

public enum DifficultyConfig {

    EASY(2, 4, true),
    MEDIUM(1, 2, false),
    HARD(1, 1, false);

    private final int     accusations;
    private final int     maxPresses;
    private final boolean hintsEnabled;

    DifficultyConfig(int accusations, int maxPresses, boolean hintsEnabled) {
        this.accusations  = accusations;
        this.maxPresses   = maxPresses;
        this.hintsEnabled = hintsEnabled;
    }

    public int     getAccusations() { return accusations;  }
    public int     getMaxPresses()  { return maxPresses;   }
    public boolean isHintsEnabled() { return hintsEnabled; }

    public static DifficultyConfig fromString(String input) {
        for (DifficultyConfig d : values()) {
            if (d.name().equalsIgnoreCase(input.trim())) {
                return d;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name()
                + "  (accusations: " + accusations
                + ", max presses: " + maxPresses
                + ", hints: " + (hintsEnabled ? "yes" : "no") + ")";
    }
}