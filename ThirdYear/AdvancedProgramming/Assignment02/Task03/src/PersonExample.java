public class PersonExample {
    static void main() {
        new PersonExample();


    }

    public PersonExample() {
        // create a new Person
        Person bob = new Person();
        Person sue = new Person();
// Set the value of bob’s name property. Note: if bob’s name had listeners
// already, they would be notified of the change in bob’s name, but since
// there are none, nothing will be notified at this time.
        bob.name.set("Bob");



// Add the code to be executed if bob’s name changes. In this case, display
// a message on the screen. Note: the valueChanged code will only be called
// when bob’s name value has changed using the set(…) method. This code will
// only be called if bob’s name changed. If sue’s name changed, nothing would
// happen.



        bob.name.addListeners(new PropertyListener<String>() {


            // The property passed in as the parameter is the property that
// had its value changed. In this set up, it’d be bob’s name property.



            public void valueChanged(Property<String> property, String oldValue, String newValue) {



// Display a message. Instead of property.get(), newValue could
// have been used as well.



                System.out.println("Bob’s name changed to"  + property.get());
            }
        });

        bob.name.addListener((name, oldValue,newValue) -> {
            
        });
// Trigger code -> this should cause the text
// “Bob’s name changed to Billy Bob” to be displayed on the console



        bob.name.set("Billy Bob");
    }
}
