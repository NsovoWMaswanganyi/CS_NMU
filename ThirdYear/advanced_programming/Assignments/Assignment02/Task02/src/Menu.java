import java.util.ArrayList;
import java.util.Scanner;

/// Remember to push to origin

public class Menu implements MenuChoice{
    /// Fields
    private String title;
    private ArrayList<MenuChoice> choices;
    Scanner scanner = new Scanner(System.in);

    /// Class Methods

    public Menu(String title) {
        this.title = title;
        choices = new ArrayList<>();
    }

    public void addChoice(MenuChoice choice){
        choices.add(choice);
    }

    protected void displayChoices(){

        System.out.println(title);

        for (int i = 0; i < choices.size(); i++) {
            MenuChoice choice = choices.get(i);
            String text = choice.getText();

            if(choice instanceof Menu){
                text += " (sub-menu)";
            }
            System.out.println((i+1) + ". " + text);
        }
        System.out.println((choices.size()+1)+ ". Exit");
    }

    protected int obtainChoice(){
        int choice = -1;

        while(choice < 1 || choice > choices.size()+1){
            System.out.println("Enter choice: ");

            try{
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }
        return choice;
    }

    protected void processChoice(int choice){
        if(choice == choices.size()+1) {
            //Exit Option
            System.out.println("Exiting " + title);
        } else {
            MenuChoice selected = choices.get(choice - 1);
            selected.run();
        }
    }


    /// Interface Methods
    @Override
    public String getText() {
        return title;
    }

    @Override
    public void run(){
        int choice;

        do{
            displayChoices();
            choice = obtainChoice();
            processChoice(choice);
        } while( choice != choices.size() +1 );
    }
}
