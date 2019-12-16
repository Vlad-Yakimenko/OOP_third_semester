package project.first.list;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MyListTest {

    private final MyList<Integer> myList = new MyArrayList<>();

    @Test
    public void sizeForEmpty() {
        // do nothing

        //then
        assertEquals(0, myList.size());
    }

    @Test
    public void sizeForNotEmpty() {
        //given
        myList.add(0);

        //then
        assertEquals(1, myList.size());
    }

    @Test
    public void isEmpty() {
        //given
        // do nothing

        //when
        //do nothing

        //then
        assertTrue(myList.isEmpty());

        //given
        myList.add(0);

        //then
        assertFalse(myList.isEmpty());
    }

    @Test
    public void addToEnd() {
        //given

        //when
        myList.add(0);
        myList.add(1);
        myList.add(2);

        //then
        assertEquals("[0, 1, 2]", myList.toString());
    }

    @Test
    public void addByIndex() {
        //given

        //when
        myList.add(0, 0);
        myList.add(1);
        myList.add(2, 2);
        myList.add(0, -1);
        myList.add(2, 7);
        myList.add(5, 45);
        myList.add(1, 17);
        myList.add(0, 3);

        //then
        assertEquals("[3, -1, 17, 0, 7, 1, 2, 45]", myList.toString());
    }

    @Test
    public void removeByIndex() {
        //given
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);
        myList.add(6);
        myList.add(7);

        //when
        try {
            myList.remove(6);
            myList.remove(1);
            myList.remove(0);
            myList.remove(1);
        } catch (Exception e) {
            System.out.println("Exception");
        }

        //then
        assertEquals("[3, 5, 6]", myList.toString());
    }

    @Test
    public void notCorrectRemoveByIndex() {
        //given
        Exception exception = null;
        String message = null;
        myList.add(1);

        //when
        try {
            myList.remove(1);
        } catch (Exception e) {
            exception = e;
        }
        if (exception != null) {
            message = exception.toString();
        }

        //then
        assertEquals("java.lang.IllegalAccessException", message);
    }

    @Test
    public void removeByObject() {
        //given
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);
        Integer five = 5, two = 2;

        //when
        myList.remove(five);
        myList.remove(two);

        //then
        assertEquals("[1, 3, 4]", myList.toString());
    }

    @Test
    public void notCorrectRemoveByObject() {
        //given
        myList.add(1);
        Integer object = 5;

        //when
        boolean response = myList.remove(object);

        //then
        assertFalse(response);
    }

    @Test
    public void clear() {
        //given
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);

        //when
        myList.clear();

        //then
        assertTrue(myList.isEmpty());
        assertEquals("[]", myList.toString());
    }

    @Test
    public void clearForEmptyList() {
        //given
        //do nothing

        //when
        myList.clear();

        //then
        assertTrue(myList.isEmpty());
        assertEquals("[]", myList.toString());
    }

    @Test
    public void indexOf() {
        //given
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);

        //when
        int index = myList.indexOf(4);
        int notCorrectArgument = myList.indexOf(0);

        //then
        assertEquals(3, index);
        assertEquals(-1, notCorrectArgument);
    }

    @Test
    public void contains() {
        //given
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);

        //when
        boolean correctRequest = myList.contains(4);
        boolean notCorrectRequest = myList.contains(0);

        //then
        assertTrue(correctRequest);
        assertFalse(notCorrectRequest);
    }

    @Test
    public void get() {
        //given
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);

        //when
        Object correctRequest = null;
        try {
            correctRequest = myList.get(4);
            myList.get(5);
        } catch (Exception e) {
            assertEquals("IllegalAccessException", e.getClass().getSimpleName());
        }

        //then
        assertEquals(5, correctRequest);
    }

    @Test
    public void sort() {
        //given
        myList.add(7);
        myList.add(6);
        myList.add(5);
        myList.add(4);
        myList.add(3);
        myList.add(2);
        myList.add(1);

        assertEquals("[7, 6, 5, 4, 3, 2, 1]", myList.toString());

        //when
        myList.sort("Insertion");

        //then
        assertEquals("[1, 2, 3, 4, 5, 6, 7]", myList.toString());
    }

    @Test
    public void sortTheSortedArray() {
        //given
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);
        myList.add(6);
        myList.add(7);

        assertEquals("[1, 2, 3, 4, 5, 6, 7]", myList.toString());

        //when
        myList.sort("Insertion");

        //then
        assertEquals("[1, 2, 3, 4, 5, 6, 7]", myList.toString());
    }

    @Test
    public void toArray() {
        //given
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);

        //when
        Object[] response = myList.toArray();
        Object[] expected = {1, 2, 3, 4, 5};

        //then
        assertTrue(Arrays.deepEquals(expected, response));
    }
}