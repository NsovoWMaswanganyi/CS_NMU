import java.util.List;

public class SequenceDice {

    /// Normal Fields

    Board board;
    Dice dice;
    List<Player> players;
    int currentPlayerIndex;
    boolean isExtraTurn; // set true if a 2 or 12 is rolled

    /// Observable properties

    Property<Player> currentPlayer;
    Property<Integer> diceTotal;
    Property<GamePhase> phase;
    Property<Player> winner;

    /// Methods

    //Initializes the board, sets phase to WAITING_FOR_ROLL
    public void startGame(List<Player> players) {
        board = new Board();
        phase.set(GamePhase.WAITING_FOR_ROLL);
    }

    //Rolls the dice, updates the diceTotal property, evaluates the rules to determine the new GamePhase, and notifies listeners
    public void rollDice(){
        dice.roll();
    }

}
