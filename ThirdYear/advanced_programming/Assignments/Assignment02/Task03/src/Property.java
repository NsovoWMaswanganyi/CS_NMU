import java.util.ArrayList;

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

        if((oldValue == null && newValue != null) || (oldValue!=null && newValue!=null)) {
            notifyListeners(oldValue,newValue);
        }

    }

    public void addListener(PropertyListener<T> listener) {
        listeners.add(listener);
    }

    public void addListeners( PropertyListener<T>... listeners) {
        for (PropertyListener<T> listener : listeners) {
            this.listeners.add(listener);
        }
    }

    public void removeListener(Property<T> listener) {
        listeners.remove(listener);
    }

    protected void notifyListeners(T oldValue, T newValue) {
        for (PropertyListener<T> listener : listeners){
            listener.valueChanged(this,oldValue,newValue);
        }
    }




}
