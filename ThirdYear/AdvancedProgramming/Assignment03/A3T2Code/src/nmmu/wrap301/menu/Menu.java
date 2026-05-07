package nmmu.wrap301.menu;

import java.util.Scanner;

public class Menu implements Runnable {
    private String title;
    private Pairs<String, Runnable> choices;

    public String getTitle() {
        return title;
    }

    public Menu(String title) {
        this.title = title;
        choices = new Pairs<>();
    }

    public void add(String text, Runnable action) {
        choices.set(text, action);
    }

    @Override
    public void run() {
        int choice = -1;
        do {
            displayChoices();
            choice = getChoice();
            processChoice(choice);
        } while (choice <= choices.size());
    }

    private void displayChoices() {
        System.out.println(title);

        for (int i = 0; i < choices.size(); i++)
            System.out.printf("%d) %s\n", i + 1, choices.get(i).getKey());

        System.out.printf("%d) Exit\n", choices.size() + 1);
    }

    private int getChoice() {
        int choice = 0;
        Scanner in = new Scanner(System.in);

        do {
            System.out.print(">");
            choice = in.nextInt();
        } while ((choice <= 0) || (choice > (choices.size() + 1)));

        return choice;
    }

    private void processChoice(int choice) {
        if (choice <= choices.size())
            choices.get(choice - 1).getValue().run();
    }
}
