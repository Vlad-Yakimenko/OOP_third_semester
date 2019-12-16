package project.first.books;

import java.util.ArrayList;
import java.util.List;

class DatabaseOfPersonages {

    private static List<Personage> personagesInDatabase = new ArrayList<>();

    static void insertIntoDatabase(Personage personage) {
        personagesInDatabase.add(personage);
    }

    static Personage isExistInDatabase(String nameOfPersonage) {
        for (Personage personage : personagesInDatabase) {
            if (personage.getName().equalsIgnoreCase(nameOfPersonage)) return personage;
        }

        return null;
    }
}