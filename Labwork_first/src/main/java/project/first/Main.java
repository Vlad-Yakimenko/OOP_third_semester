package project.first;

import project.first.books.Book;
import project.first.list.MyLinkedList;
import project.first.list.MyList;

public class Main {
    public static void main(String[] args) {
        MyList<Book> myList = new MyLinkedList<>();
        Book book1 = new Book();
        book1.setBookInformation();
        Book book2 = new Book();
        book2.setBookInformation();
        Book book3 = new Book();
        book3.setBookInformation();
        myList.add(book1);
        myList.add(book2);
        myList.add(book3);
        myList.sort("insertion");
        book1.addNewMainHero("lol kek");
        book3.addNewMainHero("lol kek");
        book2.addNewMinorHero("lol kek");
        book1.updateBookSeries();
        book2.updateBookSeries();
        book3.updateBookSeries();
    }
}