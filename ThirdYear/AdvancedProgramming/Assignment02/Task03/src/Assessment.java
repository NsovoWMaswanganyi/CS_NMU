public class Assessment {
    Property<Double> mark;

    public Assessment(Object owner, double initialValue) {
        mark = new Property<>(owner,0.0);
    }
}
