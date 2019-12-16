package project.first.books;

import project.first.list.MyArrayList;
import project.first.list.MyList;

import java.util.*;

public class Book implements Comparable<Book> {

    private String nameOfBook;
    private List<String> authors;
    private Integer[] dateOfRelease = new Integer[3];
    private int numberOfPages;
    private String shortAnnotation;
    private List<Personage> mainHeroes = new ArrayList<>();
    private List<Personage> minorHeroes = new ArrayList<>();
    private List<Personage> episodicHeroes = new ArrayList<>();
    private MyList<Book> bookSeries = new MyArrayList<>();

    public Book() { DatabaseOfBooks.insertIntoDatabase(this); }

    public Book(String nameOfBook, String dateOfRelease, int numberOfPages, String shortAnnotation, String ... authors) {
        this.nameOfBook = nameOfBook;
        this.authors = new ArrayList<>(Arrays.asList(authors));
        String[] dateInString = dateOfRelease.split("[.]");
        for (int i = 0; i < 3; i++) {
            this.dateOfRelease[i] = Integer.parseInt(dateInString[i]);
        }
        this.numberOfPages = numberOfPages;
        this.shortAnnotation = shortAnnotation;

        DatabaseOfBooks.insertIntoDatabase(this);
    }

    public void setBookInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter name of book: ");
        this.nameOfBook = scanner.nextLine();
        System.out.println("Enter authors of book(author, author): ");
        this.authors = new ArrayList<>(Arrays.asList(scanner.nextLine().split(", ")));
        System.out.println("Enter date of release of book(dd.mm.yyyy): ");
        String buffer = scanner.nextLine();
        String[] dateInString = buffer.split("[.]");
        for (int i = 0; i < 3; i++) {
            this.dateOfRelease[i] = Integer.parseInt(dateInString[i]);
        }
        System.out.println("Enter amount of pages in book: ");
        this.numberOfPages = scanner.nextInt();
        System.out.println("Enter short annotation about it: ");
        this.shortAnnotation = scanner.nextLine();
        System.out.println("Good!");
    }

    private void addNewHero(List<Personage> heroes, String nameOfHero) {
        final Personage personageFromDataBase = DatabaseOfPersonages.isExistInDatabase(nameOfHero);
        if (personageFromDataBase != null) {
            personageFromDataBase.addNewBook(this.nameOfBook);
            heroes.add(personageFromDataBase);
        } else {
            Personage personage = new Personage(nameOfHero, this);
            personage.setPersonageInformation();
            personage.addNewBook(this.nameOfBook);
            heroes.add(personage);
        }
    }

    public void addNewMainHero(String nameOfHero) {
        addNewHero(mainHeroes, nameOfHero);
    }

    public void addNewMinorHero(String nameOfHero) {
        addNewHero(minorHeroes, nameOfHero);
    }

    public void addNewEpisodicHero(String nameOfHero) {
        addNewHero(episodicHeroes, nameOfHero);
    }

    public void updateBookSeries() {
        List<Book> allBooks = DatabaseOfBooks.getBooksFromDatabase();
        for (Book bookForChecking : allBooks) {
            if (isInOneBookSeries(bookForChecking)) {
                if (!bookSeries.contains(bookForChecking))
                    bookSeries.add(bookForChecking);
            }
        }
        bookSeries.sort("Insertion");
    }

    private boolean isInOneBookSeries(Book anotherBook) {
        for (Personage mainPersonage : this.mainHeroes) {
            if (anotherBook.mainHeroes.contains(mainPersonage)) return true;
        }
        for (Personage minorPersonage : this.minorHeroes) {
            if (anotherBook.minorHeroes.contains(minorPersonage)) return true;
        }
        return false;
    }

    String getName() {
        return nameOfBook;
    }

    @Override
    public int compareTo(Book anotherBook) {
        int checkingByYear = this.dateOfRelease[2].compareTo(anotherBook.dateOfRelease[2]);
        if (checkingByYear > 0) return 1;
        else if (checkingByYear < 0) return -1;
        else {
            int checkingByMonth = this.dateOfRelease[1].compareTo(anotherBook.dateOfRelease[1]);
            if (checkingByMonth > 0) return 1;
            else if (checkingByMonth < 0) return -1;
            else {
                int checkingByDay = this.dateOfRelease[0].compareTo(anotherBook.dateOfRelease[0]);
                return Integer.compare(checkingByDay, 0);
            }
        }
    }

    @Override
    public String toString() {
        return "";
    }
}