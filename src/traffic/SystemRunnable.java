package traffic;

public class SystemRunnable implements Runnable {
    private int seconds;
    private boolean inSystemState;
    private final TrafficManager manager;

    public SystemRunnable(TrafficManager manager) {
        this.seconds = 0;
        this.inSystemState = false;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
                seconds++;
                manager.tick();
                if (inSystemState) {
                    TrafficManager.clearScreen();
                    printSystemInfo();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

    }

    public void printSystemInfo() {
        System.out.printf("! %ds. have passed since system startup !\n", seconds);
        System.out.printf("! Number of roads: %d !\n", manager.getMaxNumRoads());
        System.out.printf("! Interval: %d !\n", manager.getInterval());
        if (!manager.queueIsEmpty()) {
            manager.printRoads();
        }
        System.out.println("! Press \"Enter\" to open menu !");
    }


    public void enterSystemState() {
        inSystemState = true;
    }

    public void exitSystemState() {
        inSystemState = false;
    }


}
