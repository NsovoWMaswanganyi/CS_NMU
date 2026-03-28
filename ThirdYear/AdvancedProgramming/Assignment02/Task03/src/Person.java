public class Person {
    public Property<String> name;

    public Person() {
    // This person owns this name property.
        name = new Property<>(this, "" );
    }

    // rest of class initialisation code
}
