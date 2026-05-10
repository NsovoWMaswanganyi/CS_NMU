import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    static void main() {
        new Program();
    }

    public Program() {

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(5);

        Menu mainMenu = new Menu("Main Menu");

        //First choice: Add Numbers
        mainMenu.addChoice (
                new MenuChoice() {
                    @Override
                    public String getText() {
                        return "Add number: ";
                    }

                    @Override
                    public void run() {
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Enter a number: ");

                        try{
                            int num = Integer.parseInt(scanner.nextLine());
                            numbers.add(num);
                        } catch (NumberFormatException e){
                            System.out.println("Invalid number");
                        }
                    }
                }
        );

        //Display
        mainMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() {
                        return "Display";
                    }

                    @Override
                    public void run() {
                        System.out.println("Numbers: " + numbers);
                    }
                }
        );

        //Clear Numbers
        mainMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() {
                        return "Clear numbers";
                    }

                    @Override
                    public void run() {
                        numbers.clear();
                        System.out.println("List is now empty");
                    }
                }
        );

        //This is a sub-menu that handles the calc of mean, mode, and prime
        Menu operationsMenu = new Menu("Operations");

        //Get average
        operationsMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() {
                        return "Get the mean(average) of the numbers";
                    }

                    @Override
                    public void run() {

                        double count = 0, mean;
                        double sum= 0;

                        for (int i = 0; i < numbers.size(); i++) {
                            count++;
                            sum += numbers.get(i);
                        }

                        mean = sum/count;
                        System.out.println("Mean: " + mean);

                    }
                }
        );

        //Get mode
        operationsMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() {
                        return "Get Mode: ";
                    }

                    @Override
                    public void run() {
                        if (numbers.isEmpty()) {
                            System.out.println("List is empty.");
                            return;
                        }

                        int mode = numbers.get(0);
                        int maxCount = 0;

                        // Compare each number with the rest
                        for (int i = 0; i < numbers.size(); i++) {
                            int count = 0;
                            for (int j = 0; j < numbers.size(); j++) {
                                if (numbers.get(i).equals(numbers.get(j))) {
                                    count++;
                                }
                            }
                            if (count > maxCount) {
                                maxCount = count;
                                mode = numbers.get(i);
                            }
                        }

                        System.out.println("Mode: " + mode);
                    }
                }
        );

        //Get primes

        operationsMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() {
                        return "Get primes: ";
                    }

                    @Override
                    public void run() {

                        System.out.println("Primes: ");
                        for (Integer number : numbers) {
                            if (isPrime(number)) {
                                System.out.print(number + " ");
                            }
                        }
                        System.out.println();
                    }

                    private boolean isPrime(int n) {
                        if(n<=1) return false;

                        for (int i = 2; i <= Math.sqrt(n); i++) {
                            if(n % 2 == 0) return false;
                        }
                        return true;
                    }
                }
        );

        mainMenu.addChoice(operationsMenu);

        mainMenu.run();
    }
}
