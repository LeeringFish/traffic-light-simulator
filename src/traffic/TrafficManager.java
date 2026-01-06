package traffic;

import java.io.IOException;

public class TrafficManager {
    private final int maxNumRoads;
    private final int interval;
    private int currentNumRoads;
    private final Road[] roadQueue;
    private int front, rear;
    private int openOffset;

    public TrafficManager(int numRoads, int interval) {
        this.maxNumRoads = numRoads;
        this.interval = interval;
        this.roadQueue = new Road[numRoads];
        this.front = 0;
        this.rear = 0;
        this.currentNumRoads = 0;
        this.openOffset = 0;
    }

    public void tick() {
        // tick() all roads
        // if current open road's remainingTime == 0 and currentNumRoads > 1, rotate roads

        if (currentNumRoads == 0) {
            return;
        }

        int current = front;
        for (int i = 0; i < currentNumRoads; i++) {
            roadQueue[current].tick();
            current = (current + 1) % maxNumRoads;
        }

        int currentOpenIndex = openIndex();
        if (roadQueue[currentOpenIndex].getTimeRemaining() == 0 && currentNumRoads > 1) {
            rotate();
        }
    }

    public void rotate() {
        // close current open road
        // set new road remainingTime to interval, open
        // iterate over rest of the roads (start at i = 1)
        // set new remainingTime to (i * interval)

        if (currentNumRoads <= 1) {
            return;
        }

        int oldOpenIndex = openIndex();
        roadQueue[oldOpenIndex].closeRoad();

        openOffset = (openOffset + 1) % currentNumRoads;

        int newOpenIndex = openIndex();
        roadQueue[newOpenIndex].openRoad();
        roadQueue[newOpenIndex].setTimeRemaining(interval);

        for (int i = 1; i < currentNumRoads; i++) {
            int currentIndex = (front + openOffset + i) % maxNumRoads;
            roadQueue[currentIndex].setTimeRemaining(interval * (i + 1));
            roadQueue[currentIndex].closeRoad();
        }

    }

    public int getCurrentRemainingTime() {
        if (currentNumRoads == 0) {
            return 0;
        }
        return roadQueue[openIndex()].getTimeRemaining();
    }

    public static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add road");
        System.out.println("2. Delete road");
        System.out.println("3. Open system");
        System.out.println("0. Quit");
    }

    public void printRoads() {
        if (!queueIsEmpty()) {
            System.out.println();
            int current = front;
            for (int i = 0; i < currentNumRoads; i++) {
                System.out.println(roadQueue[current]);
                current = (current + 1) % maxNumRoads;
            }
            System.out.println();
        }
    }

    public void addRoad(Road road) {
        roadQueue[rear] = road;
        rear = (rear + 1) % maxNumRoads;
        currentNumRoads++;
    }

    public Road deleteRoad() {
        Road removed = roadQueue[front];
        front = (front + 1) % maxNumRoads;
        currentNumRoads--;
        return removed;
    }

    public boolean queueIsFull() {
        return currentNumRoads == maxNumRoads;
    }

    public boolean queueIsEmpty() {
        return currentNumRoads == 0;
    }

    public int getCurrentNumRoads() {
        return currentNumRoads;
    }

    public int getMaxNumRoads() {
        return maxNumRoads;
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

    private int openIndex() {
        return (front + openOffset) % maxNumRoads;
    }
}
