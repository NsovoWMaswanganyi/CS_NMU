public interface PropertyListener <T> {
    void valueChanged(Property<T> property, T oldValue, T newValue);
}
