package traffic;

import java.io.IOException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args){
      Scanner keyboard = new Scanner(System.in);
      String userInput;

      System.out.println("Welcome to the traffic management system!");
      System.out.print("Input the number of roads: ");
      userInput = keyboard.nextLine();
      while (notAPositiveNumber(userInput)) {
          System.out.print("Error! Incorrect Input. Try again: ");
          userInput = keyboard.nextLine();
      }
      int numRoads = Integer.parseInt(userInput);
      System.out.print("Input the interval: ");
      userInput = keyboard.nextLine();
      while (notAPositiveNumber(userInput)) {
          System.out.print("Error! Incorrect Input. Try again: ");
          userInput = keyboard.nextLine();
      }
      int interval = Integer.parseInt(userInput);
      int userChoice = -1;
      clearScreen();

      SystemRunnable r = new SystemRunnable(numRoads, interval);
      Thread t = new Thread(r);
      t.setName("QueueThread");
      t.start();

      while (true) {
          printMenu();
          userInput = keyboard.nextLine();
          if (notAPositiveNumber(userInput) && !"0".equals(userInput)) {
              userChoice = -1;
          } else {
              userChoice = Integer.parseInt(userInput);
          }

          if (userChoice == 1) {
              System.out.println("Road added");
          } else if (userChoice == 2) {
              System.out.println("Road deleted");
          } else if (userChoice == 3) {
              r.enterSystemState();
              while (true) {
                if (keyboard.nextLine().isEmpty()) {
                    r.exitSystemState();
                    break;
                }
              }
          } else if (userChoice == 0) {
              System.out.println("Bye!");
              break;
          } else {
              System.out.println("Incorrect option");
          }

          keyboard.nextLine();
          clearScreen();
      }


  }

  public static void printMenu() {
      System.out.println("Menu:");
      System.out.println("1. Add road");
      System.out.println("2. Delete road");
      System.out.println("3. Open system");
      System.out.println("0. Quit");
  }

    public static boolean notAPositiveNumber(String str) {
        try {
            int num = Integer.parseInt(str);
            return num <= 0;
        } catch (Exception e) {
            return true;
        }
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
