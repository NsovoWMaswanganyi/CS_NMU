import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    public Program() {
        /// I used the Menu Structure from Task 2

        Scanner scanner = new Scanner(System.in);

        ArrayList<Student> students = new ArrayList<>();

        Menu mainMenu = new Menu("Academic Record System");

        // Add Student
        mainMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() { return "Add Student"; }

                    @Override
                    public void run() {
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        Student student = new Student(name);

                        // Listener for overall average
                        student.overallAverage.addListener((property, oldValue, newValue) ->
                                System.out.println("Student " + student.getName() +
                                        " overall average updated to " + newValue)
                        );

                        students.add(student);
                        System.out.println("Student " + name + " added.");
                    }
                }
        );

        // Add Module
        mainMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() { return "Add Module to Student"; }

                    @Override
                    public void run() {
                        //A module can belong to any student, so we first have to
                        //look for the student that needs their modules added.
                        System.out.print("Enter student name: ");
                        String studentName = scanner.nextLine();
                        Student student = findStudent(students, studentName);

                        if (student == null) {
                            System.out.println("Student not found.");
                            return;
                        }

                        System.out.print("Enter module name: ");
                        String moduleName = scanner.nextLine();
                        Module module = new Module(moduleName);
                        student.addModule(module);

                        System.out.println("Module " + moduleName + " added to " + studentName);
                    }
                }
        );

        // Add Assessment
        mainMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() { return "Add Assessment to Module"; }

                    @Override
                    public void run() {
                        System.out.print("Enter student name: ");
                        String studentName = scanner.nextLine();
                        Student student = findStudent(students, studentName);

                        if (student == null) {
                            System.out.println("Student not found.");
                            return;
                        }

                        System.out.print("Enter module name: ");
                        String moduleName = scanner.nextLine();
                        Module module = student.getModule(moduleName);

                        if (module == null) {
                            System.out.println("Module not found.");
                            return;
                        }

                        System.out.print("Enter initial grade: ");
                        double grade = Double.parseDouble(scanner.nextLine());
                        Assessment assessment = new Assessment(module, grade);
                        module.addAssessment(assessment);

                        System.out.println("Assessment added to " + moduleName);
                    }
                }
        );

        // Update Assessment
        mainMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() { return "Update Assessment Grade"; }

                    @Override
                    public void run() {
                        System.out.print("Enter student name: ");
                        String studentName = scanner.nextLine();
                        Student student = findStudent(students, studentName);

                        if (student == null) {
                            System.out.println("Student not found.");
                            return;
                        }

                        System.out.print("Enter module name: ");
                        String moduleName = scanner.nextLine();
                        Module module = student.getModule(moduleName);

                        if (module == null) {
                            System.out.println("Module not found.");
                            return;
                        }

                        System.out.print("Enter assessment index (starting at 0): ");
                        int index = Integer.parseInt(scanner.nextLine());

                        if (index < 0 || index >= module.getAssessments().size()) {
                            System.out.println("Invalid assessment index.");
                            return;
                        }

                        System.out.print("Enter new grade: ");
                        double newGrade = Double.parseDouble(scanner.nextLine());
                        module.getAssessments().get(index).mark.set(newGrade);

                        System.out.println("Assessment updated.");
                    }
                }
        );

        // Display Averages
        mainMenu.addChoice(
                new MenuChoice() {
                    @Override
                    public String getText() { return "Display Student Averages"; }

                    @Override
                    public void run() {
                        for (Student student : students) {
                            System.out.println(student.getName() +
                                    " overall average: " + student.overallAverage.get());
                            for (Module module : student.getModules()) {
                                System.out.println("  " + module.getName() +
                                        " average: " + module.average.get());
                            }
                        }
                    }
                }
        );

        // Run the menu
        mainMenu.run();
    }

    // Helper function to find student by name
    private static Student findStudent(ArrayList<Student> students, String name) {
        for (Student s : students) {
            if (s.getName().equals(name)) return s;
        }
        return null;

    }

    static void main(String[] args) {
        new Program();
    }
}