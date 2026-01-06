package traffic;

public class Road {
    private final String name;
    private int timeRemaining;
    private boolean open;

    public Road(String name, int timeRemaining) {
        this.name = name;
        this.timeRemaining = timeRemaining;
        this.open = false;
    }

    public String getName() {
        return this.name;
    }

    public int getTimeRemaining() {
        return this.timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void openRoad() {
        this.open = true;
    }

    public void closeRoad() {
        this.open = false;
    }

    public void tick() {
        if (this.timeRemaining > 0) {
            this.timeRemaining--;
        }
    }

    private String isOpen() {
        if (open) {
            return "open";
        }
        return "closed";
    }

    @Override
    public String toString() {
        return String.format("Road \"%s\" will be %s for %ds.", name, isOpen(), timeRemaining);
    }
}
