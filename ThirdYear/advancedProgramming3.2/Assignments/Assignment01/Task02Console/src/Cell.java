public class Cell {
    Position position;
    int number; // The dice value that this cell represents
    Player occupant = null; //Who owns this cell currently

    public Position getPosition() {
        return position;
    }

    public int getNumber() {
        return number;
    }

    public Player getOccupant() {
        return occupant;
    }

    public void setOccupant(Player occupant) {
        this.occupant = occupant;
    }

    public boolean isEmpty() {
        return this.occupant == null;
    }

    public boolean isGreyCell() {
        return this.number == 2 || this.number == 12;
    }
}
