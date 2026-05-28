public class Program {
    public Program() {
        task2Jar();
        task03Dependency();
    }

    void task2Jar() {

        //Setting p degree is a bit stupid. REFACTOR!!
        Polynomial f = new Polynomial();
        Polynomial g = new Polynomial();

        f.setCoefficient(0, 5);
        f.setCoefficient(1, 2);

        g.setCoefficient(0, -2);
        g.setCoefficient(1, 1);
        g.setCoefficient(2, 3);

        // Testing Add method
        Polynomial h = f.add(2,g);

        //Derivative
        Polynomial gPrime =  g.derivative();



        System.out.println("Evaluate f(2): "+ f.evaluate(2));

        //Testing toString
        System.out.println("f(x) = " + f);
        System.out.println("g(x) = " + g);

        System.out.println("h(x) = f(x) + 2.g(x) = " + h);

        System.out.println("g'(x) = " + gPrime);
    }

    void task03Dependency(){
         
    }

    static void main() {
        new Program();
    }
}
