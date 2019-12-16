package project.second;

import java.sql.*;

public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;

    @Override
    public void connect(String user, String password) throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Notepad", user, password);
        if (connection != null) {
            System.out.println("Connected to database!");
        } else {
            System.out.println("Connecting failure");
        }
    }

    @Override
    public void createNewNote(String name, String note, String group) {
        final String table = "notes";
        final String nameColumn = "\"name\"";
        final String noteColumn = "\"note\"";
        final String groupColumn = "\"groupName\"";
        try {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO " + table + " " +
                    "(" + nameColumn + ", " + noteColumn + ", " + groupColumn +
                    ") VALUES ('" + name + "', '" + note + "', '" + group + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getNotes() {
        String table = "notes";
        String archiveColumn = "archive";
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + table +
                    " WHERE " + archiveColumn + " = false");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public ResultSet getNotesByGroup(String group) {
        String table = "notes";
        String groupColumn = "\"groupName\"";
        String archiveColumn = "\"archive\"";

        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + table +
                    " WHERE " + archiveColumn + " = false AND " + groupColumn + " = \'" + group + "\'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public ResultSet getArchivedNotes() {
        String table = "notes";
        String archiveColumn = "\"archive\"";

        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + table +
                    " WHERE " + archiveColumn + " = true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public void archiveTheNote(String name, boolean archive) {
        String tableName = "notes";
        String nameColumn = "name";
        String archiveColumn = "archive";
        try {
            Statement statement = connection.createStatement();
            if (archive) {
                statement.execute("UPDATE " + tableName +
                        " SET " + archiveColumn + " = true WHERE " + nameColumn + " = \'" + name + "\'");
            } else {
                statement.execute("UPDATE " + tableName +
                        " SET " + archiveColumn + " = false WHERE " + nameColumn + " = \'" + name + "\'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}