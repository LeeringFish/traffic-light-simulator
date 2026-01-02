package traffic;

import java.io.IOException;

public class SystemRunnable implements Runnable {
    private int seconds;
    private boolean inSystemState;
    private int numRoads;
    private int interval;

    public SystemRunnable(int numRoads, int interval) {
        this.numRoads = numRoads;
        this.interval = interval;
        this.seconds = 0;
        this.inSystemState = false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                seconds++;
                if (inSystemState) {
                    clearScreen();
                    printSystemInfo();
                }
            } catch (InterruptedException e) {};
        }

    }

    public void printSystemInfo() {
        System.out.printf("! %ds. have passed since system startup !\n", seconds);
        System.out.printf("! Number of roads: %d !\n", numRoads);
        System.out.printf("! Interval: %d !\n", interval);
        System.out.println("! Press \"Enter\" to open menu !");
    }

    public void enterSystemState() {
        inSystemState = true;
    }

    public void exitSystemState() {
        inSystemState = false;
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
