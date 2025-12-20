package traffic;

import java.util.Scanner;

public class Main {
  public static void main(String[] args){
      Scanner keyboard = new Scanner(System.in);

      System.out.println("Welcome to the traffic management system!");
      System.out.print("Input the number of roads: ");
      int numRoads = Integer.parseInt(keyboard.nextLine());
      System.out.print("Input the interval: ");
      int interval = Integer.parseInt(keyboard.nextLine());
      int userChoice = -1;

      while (userChoice != 0) {
          printMenu();
          userChoice = Integer.parseInt(keyboard.nextLine());
          if (userChoice == 1) {
              System.out.println("Road added");
          } else if (userChoice == 2) {
              System.out.println("Road deleted");
          } else if (userChoice == 3) {
              System.out.println("System opened");
          }
      }

      System.out.println("Bye!");
  }

  public static void printMenu() {
      System.out.println("Menu:");
      System.out.println("1. Add road");
      System.out.println("2. Delete road");
      System.out.println("3. Open system");
      System.out.println("0. Quit");
  }
}
