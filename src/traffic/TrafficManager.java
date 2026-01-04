package traffic;

import java.io.IOException;

public class TrafficManager {
    private int numRoads;
    private int interval;

    public TrafficManager(int numRoads, int interval) {
        this.numRoads = numRoads;
        this.interval = interval;
    }

    public static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add road");
        System.out.println("2. Delete road");
        System.out.println("3. Open system");
        System.out.println("0. Quit");
    }

    public int getNumRoads() {
        return numRoads;
    }

    public int getInterval() {
        return interval;
    }

    public static void clearScreen() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        }
        catch (IOException | InterruptedException e) {
            System.out.println("Exception in clearScreen() method");
        }
    }
}
