package project.second;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseManager {

    void connect(String user, String password) throws SQLException;

    void createNewNote(String name, String note, String group);

    ResultSet getNotes();

    ResultSet getNotesByGroup(String group);

    ResultSet getArchivedNotes();

    void archiveTheNote(String name, boolean archive);

    boolean isConnected();
}