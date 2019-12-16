package project.first.list;

public class MyLinkedList<T extends Comparable<T>> implements MyList<T> {

    private static class Node<T> {
        T data;
        Node next, previous;

        Node(T data, Node next, Node previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }
    private Node<T> start, end;
    private int size;
    private Sorter<T> sorter = new Sorter<>();

    public MyLinkedList() {
        start = end = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean add(T value) {
        if(start == null) {
            start = end = new Node<>(value, null, null);
        } else {
            end.next = new Node<>(value, null, end);
            end = end.next;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, T element) throws IllegalArgumentException {
        if (index > size() || index < 0) throw new IllegalArgumentException();
        else if (index == 0) {
            start = new Node<>(element, start, null);
            if (end == null) end = start;
        }
        else {
            Node temp = start;
            int counter = 0;
            while (temp.next != null && counter != index - 1) {
                temp = temp.next;
                counter++;
            }
            temp.next = new Node<>(element, temp.next, temp);
        }
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) throws IllegalAccessException {
        T removed = null;
        if (index > size() - 1 || index < 0 || size() == 0) throw new IllegalAccessException();
        else if (index == 0) {
            removed = start.data;
            if (start.next != null) {
                start = start.next;
                start.previous = null;
            } else {
                start = end = null;
            }
        } else {
            Node temp = start;
            int counter = 0;
            while (counter < index - 1 && temp.next != null) {
                temp = temp.next;
                counter++;
            }
            if (counter == index - 1) {
                if (temp.next != null) {
                    removed = (T) temp.next.data;
                    Node nodeAfterDeletedNode = temp.next.next;
                    temp.next = nodeAfterDeletedNode;
                    if (nodeAfterDeletedNode != null) {
                        nodeAfterDeletedNode.previous = temp;
                    }
                }
            }
        }
        return removed;
    }

    @Override
    public boolean remove(T value) throws IllegalArgumentException {
        int index = indexOf(value);
        if (index < 0) return false;
        else {
            try {
                remove(index);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
            return true;
        }
    }

    @Override
    public void clear() {
        start = end = null;
        size = 0;
    }

    @Override
    public int indexOf(T value) {
        Node temp = start;
        int index = 0;
        while (temp != null) {
            if (temp.data.equals(value)) return index;
            index++;
            temp = temp.next;
        }
        return -1;
    }

    @Override
    public boolean contains(T value) {
        int index = indexOf(value);
        return index >= 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) throws IllegalAccessException {
        if (index >= size() || index < 0) throw new IllegalAccessException();
        else {
            Node<T> temp = start;
            int counter = 0;
            while (temp.next != null && counter != index) {
                temp = temp.next;
                counter++;
            }
            return temp.data;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(String typeOfSort) {
        boolean requestedNameOfSortIsCorrect = sorter.setSortingStrategyByName(typeOfSort);
        if (requestedNameOfSortIsCorrect) {
            if (size() > 0) {
                T[] array = sorter.adapting(this.toArray());
                sorter.executeSort(array);

                Node<T> temp = start;
                int index = 0;
                while (temp != null) {
                    temp.data = array[index];
                    index++;
                    temp = temp.next;
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object[] toArray() {
        Object[] response = new Object[size()];
        Node<T> temp = start;
        int index = 0;
        while (temp != null) {
            response[index] = temp.data;
            index++;
            temp = temp.next;
        }
        return response;
    }

    @Override
    public String toString() {
        if (size() > 0) {
            StringBuilder result = new StringBuilder("[");
            Node temp = start;
            while (temp.next != null) {
                result.append(temp.data.toString());
                result.append(", ");
                temp = temp.next;
            }
            result.append(temp.data.toString());
            result.append(']');

            return result.toString();
        }

        return "[]";
    }
}