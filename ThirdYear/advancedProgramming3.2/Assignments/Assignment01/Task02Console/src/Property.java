import java.util.ArrayList;
import java.util.Collections;

public class Property <T> {

    /// fields

    private T value;
    private Object owner;

    private ArrayList<PropertyListener<T>> listeners = new ArrayList<>();

    /// Class methods
    public Property(Object owner, T initialValue) {
        value = initialValue;
        this.owner = owner;
    }

    public Object getOwner(){
        return owner;
    }

    public T get(){
        return value;
    }

    public void set(T newValue) {

        T oldValue = this.value;

        this.value = newValue;

        if (!java.util.Objects.equals(oldValue, newValue)) {
            notifyListeners(oldValue, newValue);
        }

    }

    public void addListener(PropertyListener<T> listener) {
        listeners.add(listener);
    }

    public void addListeners(PropertyListener<T>... listeners) {
        Collections.addAll(this.listeners, listeners);
    }

    public void removeListener(PropertyListener<T> listener) {
        listeners.remove(listener);
    }

    protected void notifyListeners(T oldValue, T newValue) {
        for (PropertyListener<T> listener : listeners){
            listener.valueChanged(this,oldValue,newValue);
        }
    }




}
