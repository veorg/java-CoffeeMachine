package machine;
import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Machine mixer = new Machine(400, 540, 120, 9, 550);
        do {
            System.out.println(mixer.display());
            System.out.println(mixer.routine(scanner.next()));
        } while (!mixer.state.equals(Machine.State.EXIT));
    }
}
class Machine {

    int water, milk, beans, cups, money;
    State state;

    public enum State {
        IDLE, BUY, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS, EXIT
    }

    public enum Coffee {
        ESPRESSO(1,"Espresso", 250, 0, 16, 1, 4),
        LATTE(2,"Latte", 350, 75, 20, 1, 7),
        CAPPUCCINO(3,"Cappuccino", 200, 100, 12, 1, 6);

        String name;
        int index, water, milk, beans, cups, money;

        Coffee(int index, String name, int water, int milk, int beans, int cups, int money) {
            this.index = index;
            this.name = name;
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cups = cups;
            this.money = money;
        }

        public String getName() {
            return name;
        }
        public int getWater() {
            return water;
        }
        public int getMilk() {
            return milk;
        }
        public int getBeans() {
            return beans;
        }
        public int getCups() {
            return cups;
        }
        public int getMoney() {
            return money;
        }

        public static Coffee findByName(String name) {
            for (Coffee value : values()){
                if (name.equals(value.name)) {
                    return value;
                }
            }
            return null;
        }
    }

    Machine (int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
        state = State.IDLE;
    }

    public String display() {
        switch (state) {
            case IDLE:
                return "Write action (buy, fill, take, remaining, exit):";
            case BUY:
                return "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";
            case FILL_WATER:
                return "Write how many ml of water do you want to add: ";
            case FILL_MILK:
                return "Write how many ml of milk do you want to add:";
            case FILL_BEANS:
                return "Write how many grams of coffee beans do you want to add: ";
            case FILL_CUPS:
                return "Write how many disposable cups of coffee do you want to add:";
            default:
                return "unknown state";
        }
    }

    public String makeCoffee (Coffee request){
        state = State.IDLE;
        if (water < request.water) {
            return "Sorry, not enough water!\n";
        } else if (milk < request.milk) {
            return "Sorry, not enough milk!\n";
        } else if (beans < request.beans) {
            return "Sorry, not enough coffee beans!\n";
        } else if (cups < request.cups) {
            return "Sorry, not enough cups!\n";
        }
        water -= request.water;
        milk -= request.milk;
        beans -= request.beans;
        cups -= request.cups;
        money += request.money;
        return "I have enough resources, making you a coffee!\n";
    }

    public String routine (String command) {
        switch (state) {
            case IDLE:
                switch (command) {
                    case "buy":
                        state = State.BUY;
                        return "";
                    case "fill":
                        state = State.FILL_WATER;
                        return "";
                    case "take":
                        int dMoney = money;
                        money = 0;
                        return "I gave you $" + dMoney;
                    case "remaining":
                        return "\nThe coffee machine has:\n" +
                                water + " of water\n" +
                                milk + " of milk\n" +
                                beans + " of beans\n" +
                                cups + " of cups\n" +
                                "$" + money + " of money\n";

                    case "exit":
                        state = State.EXIT;
                        return "";
                    default:
                        return "";
                }
            case FILL_WATER:
                water += Integer.parseInt(command);
                state = State.FILL_MILK;
                return "";
            case FILL_MILK:
                milk += Integer.parseInt(command);
                state = State.FILL_BEANS;
                return "";
            case FILL_BEANS:
                beans += Integer.parseInt(command);
                state = State.FILL_CUPS;
                return "";
            case FILL_CUPS:
                cups += Integer.parseInt(command);
                state = State.IDLE;
                return "";
            case BUY:
                switch (command) {
                    case "1":
                        return makeCoffee(Coffee.ESPRESSO);
                    case "2":
                        return makeCoffee(Coffee.LATTE);
                    case "3":
                        return makeCoffee(Coffee.CAPPUCCINO);
                    case "back":
                        state = State.IDLE;
                        return "";
                    default:
                        return "";
                }
        }
        return "";
    }


}

/*
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            String command = scanner.next();
            switch (command) {
                case "buy":
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                    String drink = scanner.next();
                    int waterAsk = 0;
                    int milkAsk = 0;
                    int beansAsk = 0;
                    int cupsAsk = 0;
                    int moneyAsk = 0;
                    boolean ready = true;

                    switch (drink) {
                        case "1":
                            waterAsk = 250;
                            beansAsk = 16;
                            moneyAsk = 4;
                            cupsAsk = 1;
                            break;
                        case "2":
                            waterAsk = 350;
                            milkAsk = 75;
                            beansAsk = 20;
                            moneyAsk = 7;
                            cupsAsk = 1;
                            break;
                        case "3":
                            waterAsk = 200;
                            milkAsk = 100;
                            beansAsk = 12;
                            moneyAsk = 6;
                            cupsAsk = 1;
                            break;
                        default:
                            ready = false;
                            break;
                    }

                    if (water - waterAsk < 0) {
                        System.out.println("Sorry, not enough water!");
                        //break;
                    } else if (milk - milkAsk < 0) {
                        System.out.println("Sorry, not enough milk!");
                        //break;
                    } else if (beans - beansAsk < 0) {
                        System.out.println("Sorry, not enough coffee beans!");
                        //break;
                    } else if (cups - cupsAsk < 0) {
                        System.out.println("Sorry, not enough cups!");
                       // break;
                    } else if (ready) {
                        System.out.println("I have enough resources, making you a coffee!");
                        water -= waterAsk;
                        milk -= milkAsk;
                        beans -= beansAsk;
                        cups -= cupsAsk;
                        money += moneyAsk;
                    }
                    break;
                case "fill":
                    System.out.println("Write how many ml of water do you want to add:");
                    water += scanner.nextInt();
                    System.out.println("Write how many ml of milk do you want to add:");
                    milk += scanner.nextInt();
                    System.out.println("Write how many grams of coffee beans do you want to add:");
                    beans += scanner.nextInt();
                    System.out.println("Write how many disposable cups of coffee do you want to add:");
                    cups += scanner.nextInt();
                    break;
                case "take":
                    System.out.println("I gave you $" + money);
                    money = 0;
                    break;
                case "remaining":
                    System.out.println("The coffee machine has:");
                    System.out.println(water + " of water");
                    System.out.println(milk + " of milk");
                    System.out.println(beans + " of coffee beans");
                    System.out.println(cups + " of disposable cups");
                    System.out.println(money + " money");
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    break;
            }
        }*/