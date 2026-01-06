package traffic;

import java.util.Scanner;

public class Main {
  public static void main(String[] args){
      Scanner keyboard = new Scanner(System.in);
      String userInput;
      int numRoads, interval, userChoice;

      System.out.println("Welcome to the traffic management system!");
      System.out.print("Input the number of roads: ");
      userInput = keyboard.nextLine();
      while (notAPositiveNumber(userInput)) {
          System.out.print("Error! Incorrect Input. Try again: ");
          userInput = keyboard.nextLine();
      }
      numRoads = Integer.parseInt(userInput);
      System.out.print("Input the interval: ");
      userInput = keyboard.nextLine();
      while (notAPositiveNumber(userInput)) {
          System.out.print("Error! Incorrect Input. Try again: ");
          userInput = keyboard.nextLine();
      }
      interval = Integer.parseInt(userInput);
      TrafficManager.clearScreen();

      TrafficManager manager = new TrafficManager(numRoads, interval);
      SystemRunnable r = new SystemRunnable(manager);
      Thread t = new Thread(r);
      t.setName("QueueThread");
      //t.start();

      while (true) {
          TrafficManager.printMenu();
          userInput = keyboard.nextLine();
          if (notAPositiveNumber(userInput) && !"0".equals(userInput)) {
              userChoice = -1;
          } else {
              userChoice = Integer.parseInt(userInput);
          }

          if (userChoice == 1) {
              System.out.print("Input road name: ");
              userInput = keyboard.nextLine();
              if (manager.queueIsFull()) {
                  System.out.println("queue is full");
              } else {
                  if (manager.queueIsEmpty()) {
                      Road road = new Road(userInput, interval);
                      road.openRoad();
                      manager.addRoad(road);
                  } else {
                      int timeRemaining = manager.getCurrentRemainingTime() + interval * (manager.getCurrentNumRoads() - 1);
                      manager.addRoad(new Road(userInput, timeRemaining));
                  }

                  System.out.println(userInput + " Added!");
              }
          } else if (userChoice == 2) {
              if (manager.queueIsEmpty()) {
                  System.out.println("queue is empty");
              } else {
                  String deleted = manager.deleteRoad().getName();
                  System.out.println(deleted + " Deleted!");
              }
          } else if (userChoice == 3) {
              if (!t.isAlive()) {
                  t.start();
              }
              r.enterSystemState();
              while (true) {
                if (keyboard.nextLine().isEmpty()) {
                    r.exitSystemState();
                    break;
                }
              }
              continue;
          } else if (userChoice == 0) {
              t.interrupt();
              System.out.println("Bye!");
              break;
          } else {
              System.out.println("Incorrect option");
          }

          keyboard.nextLine();
          TrafficManager.clearScreen();
      }

  }


    public static boolean notAPositiveNumber(String str) {
        try {
            int num = Integer.parseInt(str);
            return num <= 0;
        } catch (Exception e) {
            return true;
        }
    }

}
