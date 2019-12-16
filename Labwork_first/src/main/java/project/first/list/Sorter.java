package project.first.list;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

interface SortingStrategy<T extends Comparable<T>> {
    void sort(T[] array);
}

class InsertionSort<T extends Comparable<T>> implements SortingStrategy<T> {

    @Override
    public void sort(T[] array) {
        for (int i = 1; i < array.length; ++i) {
            T key = array[i];
            int j = i - 1;

            while ((j >= 0) && (key.compareTo(array[j]) < 0)) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }
}

class QuickSort<T extends Comparable<T>> implements SortingStrategy<T> {

    @Override
    public void sort(T[] array) {
        sorting(array, 0, array.length - 1);
    }

    private int partition(T[] array, int low, int high)
    {
        T pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++)
        {
            if (pivot.compareTo(array[j]) > 0)
            {
                i++;

                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        T temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i+1;
    }


    private void sorting(T[] arr, int low, int high)
    {
        if (low < high)
        {
            int pivot = partition(arr, low, high);

            sorting(arr, low, pivot - 1);
            sorting(arr, pivot + 1, high);
        }
    }
}

class MergeSort<T extends Comparable<T>> implements SortingStrategy<T> {

    public void sort(T[] array) {
        sorting(array, 0, array.length - 1);
    }

    @SuppressWarnings("unchecked")
    private void merge(T[] array, int left, int middle, int right) {

        int sizeForFirstTemp = middle - left + 1;
        int sizeForSecondTemp = right - middle;

        Comparable[] leftTemp = new Comparable[sizeForFirstTemp];
        Comparable[] rightTemp = new Comparable[sizeForSecondTemp];

        System.arraycopy(array, left, leftTemp, 0, sizeForFirstTemp);
        for (int i = 0; i < sizeForSecondTemp; i++)
            rightTemp[i] = array[middle + 1 + i];

        int i = 0, j = 0;

        int k = left;
        while (i < sizeForFirstTemp && j < sizeForSecondTemp)
        {
            if (leftTemp[i].compareTo(rightTemp[j]) <= 0)
            {
                array[k] = (T)leftTemp[i];
                i++;
            }
            else
            {
                array[k] = (T)rightTemp[j];
                j++;
            }
            k++;
        }

        while (i < sizeForFirstTemp)
        {
            array[k] = (T)leftTemp[i];
            i++;
            k++;
        }

        while (j < sizeForSecondTemp)
        {
            array[k] = (T)rightTemp[j];
            j++;
            k++;
        }
    }

    private void sorting(T[] array, int left, int right) {
        if (left < right)
        {
            int middle = left + (right - left) / 2;

            sorting(array, left, middle);
            sorting(array, middle + 1, right);

            merge(array, left, middle, right);
        }
    }
}

class SelectionSort<T extends Comparable<T>> implements SortingStrategy<T> {

    public void sort(T[] array) {

        int sizeOfArray = array.length;

        for (int i = 0; i < sizeOfArray - 1; i++)
        {
            int indexOfMinValue = i;
            for (int j = i + 1; j < sizeOfArray; j++)
                if (array[j].compareTo(array[indexOfMinValue]) < 0)
                    indexOfMinValue = j;

            T temp = array[indexOfMinValue];
            array[indexOfMinValue] = array[i];
            array[i] = temp;
        }
    }
}

class ShellSort<T extends Comparable<T>> implements SortingStrategy<T> {

    public void sort(T[] array) {

        int sizeOfArray = array.length;

        for (int gap = sizeOfArray / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < sizeOfArray; i += 1) {

                T temp = array[i];

                int j;
                for (j = i; j >= gap && array[j - gap].compareTo(temp) > 0; j -= gap)
                    array[j] = array[j - gap];

                array[j] = temp;
            }
        }
    }
}

class Sorter<T extends Comparable<T>> {

    private SortingStrategy<T> sortingStrategy;

    private Map<String, SortingStrategy<T>> allSortingStrategies = new HashMap<>();

    Sorter() {
        allSortingStrategies.put("Insertion", new InsertionSort<>());
        allSortingStrategies.put("Quick", new QuickSort<>());
        allSortingStrategies.put("Merge", new MergeSort<>());
        allSortingStrategies.put("Selection", new SelectionSort<>());
        allSortingStrategies.put("Shell", new ShellSort<>());
    }

    private void setSortingStrategy(SortingStrategy<T> sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    boolean setSortingStrategyByName(String typeOfSort) {

        SortingStrategy<T> receivedSortingStrategyObject = allSortingStrategies.get(typeOfSort);
        if (receivedSortingStrategyObject != null) {
            setSortingStrategy(receivedSortingStrategyObject);
        } else {
            throw new IllegalArgumentException();
        }
        return true;
    }

    void executeSort(T[] array) {
        if (array != null) sortingStrategy.sort(array);
    }

    @SuppressWarnings("unchecked")
    T[] adapting(Object[] objects) {

        if (objects != null) {
            T[] array = (T[]) Array.newInstance(objects[0].getClass(), objects.length);
            for (int i = 0; i < array.length; i++) {
                array[i] = (T) objects[i];
            }

            return array;
        }

        return null;
    }
}