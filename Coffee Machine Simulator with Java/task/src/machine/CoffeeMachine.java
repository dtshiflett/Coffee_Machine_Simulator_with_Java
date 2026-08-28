package machine;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CoffeeMachine {

    protected static int currentWater = 400;
    protected static int currentMilk = 540;
    protected static int currentCoffeeBeans = 120;
    protected static double currentMoney = 550.00;
    protected static int currentNumDispCups = 9;
    protected static int currentUsesSinceCleaning = 0;

    protected static final int MAX_USES_BEFORE_CLEANING = 10;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean askAgain = true;
        while (askAgain) {
            System.out.println("\nWrite action (buy, fill, take, remaining, clean, exit): ");
            String action = sc.nextLine();
            if (action.equalsIgnoreCase("exit")) {
                askAgain = false;
                continue;
            }
            if (currentUsesSinceCleaning >= MAX_USES_BEFORE_CLEANING && !action.equalsIgnoreCase("clean")) {
                    System.out.println("I need cleaning!");
                    continue;
            }
            switch (action) {
                case "buy":
                    buyCoffee(sc);
                    break;
                case "fill":
                    fillCoffeeMachine(sc);
                    break;
                case "take":
                    takeMoney();
                    break;
                case "remaining":
                    printCurrentInventory();
                    break;
                case "clean":
                    currentUsesSinceCleaning = 0;
                    System.out.println("I have been cleaned!");
                    break;
            }
        }
        sc.close();
    }

    protected static void buyCoffee(Scanner sc) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        int coffeeType;
        try {
            coffeeType = sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine(); // flush input buffer
            return;
        }
        sc.nextLine();
        CoffeeTypes selectedCoffee;
        switch (coffeeType) {
            case 1:
                selectedCoffee = CoffeeTypes.ESPRESSO;
                break;
            case 2:
                selectedCoffee = CoffeeTypes.LATTE;
                break;
            case 3:
                selectedCoffee = CoffeeTypes.CAPPUCCINO;
                break;
            default:
                selectedCoffee = null;
        }

        if (selectedCoffee != null) {
            if (checkSupplies(selectedCoffee)) {
                System.out.println("I have enough resources, making you a coffee!");
                currentWater -= selectedCoffee.water;
                currentMilk -= selectedCoffee.milk;
                currentCoffeeBeans -= selectedCoffee.coffeeBeans;
                currentMoney += selectedCoffee.price;
                currentNumDispCups--;
                currentUsesSinceCleaning++;
            }
        }
    }

    private static boolean checkSupplies(CoffeeTypes selectedCoffee) {
        boolean isEnough = true;
        if (selectedCoffee.water > currentWater) {
            System.out.println("Sorry, not enough water!");
            isEnough = false;
        }
        if (selectedCoffee.milk > currentMilk) {
            System.out.println("Sorry, not enough milk!");
            isEnough = false;
        }
        if (selectedCoffee.coffeeBeans > currentCoffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
            isEnough = false;
        }
        if (currentNumDispCups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            isEnough = false;
        }
        return isEnough;
    }

    protected static void fillCoffeeMachine(Scanner sc) {
        System.out.println("Write how many ml of water you want to add:");
        currentWater += sc.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        currentMilk += sc.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        currentCoffeeBeans += sc.nextInt();
        System.out.println("Write how many disposable cups you want to add: ");
        currentNumDispCups += sc.nextInt();
        sc.nextLine(); // flush input buffer
    }

    protected static void takeMoney() {
        System.out.printf("I gave you $%.0f \n",currentMoney);
        currentMoney = 0;
        return;
    }
    protected static void printCurrentInventory() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(currentWater + " ml of water");
        System.out.println(currentMilk + " ml of milk");
        System.out.println(currentCoffeeBeans + " g of coffee beans");
        System.out.println(currentNumDispCups + " disposable cups");
        System.out.printf("$%.0f of money\n", currentMoney);
        return;
    }

}

enum CoffeeTypes {
    ESPRESSO(250,0, 16, 4.00),
    LATTE(350, 75, 20, 7.00),
    CAPPUCCINO(200, 100, 12, 6.00);

    public final int water;
    public final int milk;
    public final int coffeeBeans;
    public final double price;

    CoffeeTypes(int water, int milk, int coffeeBeans, double price) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.price = price;
    }
}


class Coffee {
    // per 1 cup coffee
    public final static int waterPerCup = 200; // ml
    public final static int milkPerCup = 50; // ml
    public final static int coffeeBeansPerCup = 15; // g
    public final static int dispCupsNeeded = 1;
}

