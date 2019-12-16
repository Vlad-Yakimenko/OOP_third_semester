package project.first.books;

import java.util.ArrayList;
import java.util.List;

class DatabaseOfBooks {

    private static List<Book> booksInDatabase = new ArrayList<>();

    static void insertIntoDatabase(Book book) { booksInDatabase.add(book); }

    static Book isExistInDatabase(String nameOfBook) {
        for (Book book : booksInDatabase) {
            if (book.getName().equalsIgnoreCase(nameOfBook)) return book;
        }

        return null;
    }

    static List<Book> getBooksFromDatabase() { return booksInDatabase; }
}