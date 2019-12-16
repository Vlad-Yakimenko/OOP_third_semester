package project.first.list;

import java.util.*;

public class MyArrayList<T extends Comparable<T>> implements MyList<T> {

    private transient Object[] data;
    private int size;
    private Sorter<T> sorter = new Sorter<>();

    public MyArrayList() {
        data = new Object[10];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean add(T value) {
        increasingSizeOfMemory();
        data[size()] = value;
        ++size;
        return true;
    }

    @Override
    public void add(int index, T element) throws IllegalArgumentException {
        if (index > size() || index < 0) throw new IllegalArgumentException();
        increasingSizeOfMemory();
        System.arraycopy(data, index, data, index + 1, size() - index);
        data[index] = element;
        ++size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) throws IllegalAccessException {
        if (index >= size() || index < 0) throw new IllegalAccessException();
        else {
            T deletedObject = (T) data[index];
            System.arraycopy(data, index + 1, data, index, --size - index);
            data[size] = null;
            return deletedObject;
        }
    }

    @Override
    public boolean remove(T value) throws IllegalArgumentException {
        int index = indexOf(value);
        if (index == -1) {
            return false;
        } else {
            try {
                remove(index);
            } catch(Exception e) {
                throw new IllegalArgumentException(e);
            }
            return true;
        }
    }

    @Override
    public void clear() {
        for (int to = size, i = size = 0; i < to; i++)
            data[i] = null;
    }

    @Override
    public int indexOf(T value) {
        for (int i = 0; i < size(); i++) {
            if (data[i] == null && value == null || data[i] != null && data[i].equals(value)) return i;
        }
        return -1;
    }

    @Override
    public boolean contains(T value) {
        for (Object object : data) {
            if (object == null && value == null || object != null && object.equals(value)) return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) throws IllegalAccessException {
        if (index >= size() || index < 0) throw new IllegalAccessException();
        else {
            return (T) data[index];
        }
    }

    @Override
    public void sort(String typeOfSort) {
        boolean requestedNameOfSortIsCorrect = sorter.setSortingStrategyByName(typeOfSort);
        if (requestedNameOfSortIsCorrect) {
            if (size() > 0) {
                T[] array = sorter.adapting(this.toArray());
                sorter.executeSort(array);
                data = array;
            }
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size());
    }

    private int newCapacity() {
        int oldCapacity = data.length;
        return oldCapacity + (oldCapacity >> 1);
    }

    private Object[] grow() {
        return Arrays.copyOf(data, newCapacity());
    }

    private void increasingSizeOfMemory() {
        if (size == data.length)
            data = grow();
    }

    @Override
    public String toString() {
        if (size() > 0) {
            StringBuilder result = new StringBuilder("[");
            for (int i = 0; i < size() - 1; i++) {
                result.append(data[i]);
                result.append(", ");
            }
            result.append(data[size() - 1]);
            result.append(']');

            return result.toString();
        }

        return "[]";
    }
}