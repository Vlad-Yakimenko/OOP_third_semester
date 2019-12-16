package project.first.list;

public interface MyList<T> {

    int size();
    boolean isEmpty();
    boolean add(T value);
    void add(int index, T element) throws IllegalArgumentException;
    T remove(int index) throws IllegalAccessException;
    boolean remove(T value) throws IllegalArgumentException;
    void clear();
    int indexOf(T value);
    boolean contains(T value);
    T get(int index) throws IllegalAccessException;
    void sort(String typeOfSort);
    Object[] toArray();
}