import java.util.Random;

public class Dice {

    int die1, die2, total;

    //Generates two random numbers between 1-6 and calculates the total
    public void roll() {

        Random rand = new Random();

        die1  = rand.nextInt(6)+1;
        die2 = rand.nextInt(6)+1;
        total = die1 + die2;
    }

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }

    public int getTotal() {
        return total;
    }
}
