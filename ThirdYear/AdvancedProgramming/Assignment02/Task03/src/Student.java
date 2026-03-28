import java.util.ArrayList;
import java.util.List;

class Student {
    private String name;
    private ArrayList<Module> modules = new ArrayList<>();
    public Property<Double> overallAverage;

    public Student(String name) {
        this.name = name;
        overallAverage = new Property<>(this, 0.0);
    }

    public void addModule(Module module) {
        modules.add(module);

        // Listen for module average changes
        module.average.addListener((property, oldValue, newValue) -> {
            System.out.println("Module " + module.getName() +
                    " average changed from " + oldValue + " to " + newValue);
            recalcOverallAverage();
        });

        recalcOverallAverage();
    }

    private void recalcOverallAverage() {
        double sum = 0;
        for (Module m : modules) sum += m.average.get();
        overallAverage.set(modules.isEmpty() ? 0 : sum / modules.size());
    }

    public String getName() {
        return name;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        for (Module m : modules) if (m.getName().equals(name)) return m;
        return null;
    }
}