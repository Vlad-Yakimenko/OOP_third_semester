package project.first.books;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Personage {

    private String name;
    private List<String> pseudonyms = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    Personage(String name, Book book) {
        this.name = name;
        this.books.add(book);
        DatabaseOfPersonages.insertIntoDatabase(this);
    }

    void setPersonageInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add pseudonyms for " + name);
        System.out.println("Enter pseudonyms of personage(pseudonym, pseudonym): ");
        this.pseudonyms = new ArrayList<>(Arrays.asList(scanner.nextLine().split(", ")));
        System.out.println("Ok");
    }

    void addNewBook(String nameOfBook) {
        final Book bookFromDatabase = DatabaseOfBooks.isExistInDatabase(nameOfBook);
        if (bookFromDatabase != null) {
            books.add(bookFromDatabase);
        } else {
            Book book = new Book();
            book.setBookInformation();
            books.add(book);
        }
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Name of personage: ");
        result.append(name);
        result.append('\n');
        result.append("Pseudonyms: ");
        for (String pseudonym : pseudonyms) {
            result.append(pseudonym);
            result.append(", ");
        }
        result.delete(result.length() - 2, result.length() - 1);
        result.append('\n');
        result.append("Books: ");
        for (Book book : books) {
            result.append(book.getName());
            result.append(", ");
        }
        result.delete(result.length() - 2, result.length() - 1);

        return result.toString();
    }
}