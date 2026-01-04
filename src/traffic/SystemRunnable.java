package traffic;

import java.io.IOException;

public class SystemRunnable implements Runnable {
    private int seconds;
    private boolean inSystemState;
    private boolean isInterrupted;
    private final TrafficManager manager;

    public SystemRunnable(TrafficManager manager) {
        this.seconds = 0;
        this.inSystemState = false;
        this.isInterrupted = false;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (!isInterrupted) {
            try {
                Thread.sleep(1000);
                seconds++;
                if (inSystemState) {
                    clearScreen();
                    printSystemInfo();
                }
            } catch (InterruptedException ignored) {};
        }

    }

    public void printSystemInfo() {
        System.out.printf("! %ds. have passed since system startup !\n", seconds);
        System.out.printf("! Number of roads: %d !\n", manager.getNumRoads());
        System.out.printf("! Interval: %d !\n", manager.getInterval());
        System.out.println("! Press \"Enter\" to open menu !");
    }

    public void enterSystemState() {
        inSystemState = true;
    }

    public void exitSystemState() {
        inSystemState = false;
    }

    public void sendInterrupt() {
        isInterrupted = true;
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
