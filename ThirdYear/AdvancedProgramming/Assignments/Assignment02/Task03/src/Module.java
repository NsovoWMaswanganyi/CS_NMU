import java.util.ArrayList;

public class Module {

    String name;
    ArrayList<Assessment> assessments = new ArrayList<>();
    public Property<Double> average;

    public Module(String name) {
        this.name = name;
        average = new Property<>(this,0.0);
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);

        //When an assessment grade changes, we need an update

        assessment.mark.addListener(((property, oldValue, newValue) -> {
            System.out.println("Assessment grade Changed in " + name + " from" + oldValue + " to" + newValue);
            recalcAverage();
        }));
        recalcAverage();
    }

    void recalcAverage() {
        double sum = 0;

        for (Assessment a : assessments) {
            sum += a.mark.get();
        }

        //Add 0 to the average if list is empty, else get the average from the sum that you calculated
        //This is to avoid division by 0 (especially if the list is empty), and i didn't want to use exception handling
        average.set(assessments.isEmpty()? 0 : sum / assessments.size());
    }

    public String getName() { return name; }
    public ArrayList<Assessment> getAssessments() { return assessments; }

}
