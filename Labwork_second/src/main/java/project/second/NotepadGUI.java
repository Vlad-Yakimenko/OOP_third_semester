package project.second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

class NotepadGUI {

    private DatabaseManager databaseManager;

    private JFrame jFrame;
    private JPanel jPanel;
    private JScrollPane jScrollPane;

    NotepadGUI() throws InterruptedException {
        databaseManager = new JDBCDatabaseManager();
        jFrameSetting();
        login();
        while (!databaseManager.isConnected()) {
            Thread.sleep(250);
        }
        start();
    }

    private void jFrameSetting() {
        jFrame = new JFrame();
        jPanel = new JPanel();
        jScrollPane = new JScrollPane();
        jFrame.setVisible(true);
        jFrame.setSize(600, 600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(jPanel);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login() {
        AtomicReference<String> user = new AtomicReference<>();

        jFrame.setTitle("Sing in");

        jPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.weightx = 0;
        constraints1.weighty = 0;
        constraints1.gridx = 0;
        constraints1.gridy = 0;
        constraints1.gridheight = 1;
        constraints1.gridwidth = 1;
        jPanel.add(new JLabel("Login: "), constraints1);

        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.weightx = 0;
        constraints2.weighty = 0;
        constraints2.gridx = 1;
        constraints2.gridy = 0;
        constraints2.gridheight = 1;
        constraints2.gridwidth = 2;
        JTextField jTextField = new JTextField(25);
        jPanel.add(jTextField, constraints2);

        GridBagConstraints constraints3 = new GridBagConstraints();
        constraints3.weightx = 0;
        constraints3.weighty = 0;
        constraints3.gridx = 0;
        constraints3.gridy = 1;
        constraints3.gridheight = 1;
        constraints3.gridwidth = 1;
        jPanel.add(new JLabel("Password: "), constraints3);

        GridBagConstraints constraints4 = new GridBagConstraints();
        constraints4.weightx = 0;
        constraints4.weighty = 0;
        constraints4.gridx = 1;
        constraints4.gridy = 1;
        constraints4.gridheight = 1;
        constraints4.gridwidth = 2;
        JPasswordField jPasswordField = new JPasswordField(25);
        jPanel.add(jPasswordField, constraints4);

        jTextField.addActionListener(e -> user.set(e.getActionCommand()));

        jPasswordField.addActionListener(e -> {
            try {
                databaseManager.connect(user.get(), e.getActionCommand());
            } catch (SQLException ex) {
                System.out.println("Wrong password or login!");
            }
        });

        jPanel.revalidate();
    }

    private void start() {
        jFrame.setTitle("Notepad");
        menuSetting();
        updateScreen();
    }

    private void menuSetting() {
        JMenuBar jMenuBar = new JMenuBar();
        jFrame.setJMenuBar(jMenuBar);

        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu group = new JMenu("Group");
        JMenu archive = new JMenu("Archive");
        jMenuBar.add(file);
        jMenuBar.add(edit);
        jMenuBar.add(group);
        jMenuBar.add(archive);

        JMenuItem newNote = new JMenuItem("New note");
        newNote.addActionListener(e -> addNewNote());
        JMenuItem exit = new JMenuItem("Exit");
        edit.addActionListener(e -> System.exit(0));

        file.add(newNote);
        file.addSeparator();
        file.add(exit);

        JMenuItem refreshScreen = new JMenuItem("Refresh");
        refreshScreen.addActionListener(e -> updateScreen());

        edit.add(refreshScreen);

        JMenuItem all = new JMenuItem("All");
        all.addActionListener(e -> updateScreen());

        group.add(all);
        setAllGroups(group);

        JMenuItem archived = new JMenuItem("Archived items");
        archived.addActionListener(e -> {
            clearScreen();
            printingArchivedNotes();
        });

        archive.add(archived);
    }

    private void setAllGroups(JMenu jMenu) {
        ResultSet allNotes = databaseManager.getNotes();
        Set<String> groups = new HashSet<>();

        try {
            while (allNotes.next()) {
                groups.add(allNotes.getString("groupName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String group : groups) {
            JMenuItem jMenuItem = new JMenuItem(group);
            jMenuItem.addActionListener(e -> {
                clearScreen();
                printingNotesByGroup(jMenuItem.getActionCommand());
            });

            jMenu.add(jMenuItem);
        }
    }

    private void updateScreen() {
        clearScreen();
        keyStrokeSetting();
        printingAllNotes();
        jFrame.revalidate();
    }

    private void clearScreen() {
        jFrame.getContentPane().removeAll();
        jPanel = new JPanel();
        jScrollPane.setViewportView(jPanel);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jFrame.add(jScrollPane);
    }

    private void keyStrokeSetting() {
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl M");
        InputMap inputMap = jPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(keyStroke, "addNewNote");
        ActionMap actionMap = jPanel.getActionMap();
        actionMap.put("addNewNote", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewNote();
            }
        });
    }

    private void addNewNote() {
        JFrame noteFrame = new JFrame();
        JPanel notePanel = new JPanel();
        noteFrame.setVisible(true);
        noteFrame.setSize(400, 400);
        noteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        noteFrame.setLayout(new BorderLayout());
        noteFrame.add(notePanel, BorderLayout.NORTH);

        notePanel.add(new JLabel("Title:"));
        JTextField fieldForTitle = new JTextField(15);
        fieldForTitle.addActionListener(e -> noteFrame.setTitle(e.getActionCommand()));
        notePanel.add(fieldForTitle);

        AtomicReference<String> group = new AtomicReference<>();
        notePanel.add(new JLabel("Group:"));
        JTextField fieldForGroup = new JTextField(15);
        fieldForGroup.addActionListener(e -> group.set(e.getActionCommand()));
        notePanel.add(fieldForGroup);

        TextField fieldForNote = new TextField(15);
        fieldForNote.addActionListener(e -> {
            databaseManager.createNewNote(noteFrame.getTitle(), e.getActionCommand(), group.get());
            noteFrame.setVisible(false);
            noteFrame.dispose();
        });
        noteFrame.add(fieldForNote, BorderLayout.CENTER);
    }

    private void printingAllNotes() {
        ResultSet resultSet = databaseManager.getNotes();
        printNotes(resultSet, false);
        jFrame.revalidate();
    }

    private void printingNotesByGroup(String group) {
        ResultSet resultSet = databaseManager.getNotesByGroup(group);
        printNotes(resultSet, false);
        jFrame.revalidate();
    }

    private void printingArchivedNotes() {
        ResultSet resultSet = databaseManager.getArchivedNotes();
        printNotes(resultSet, true);
        jFrame.revalidate();
    }

    private void printNotes(ResultSet resultSet, boolean archived) {
        boolean isEmpty = true;

        try {
            while (resultSet.next()) {
                String bufferForName = resultSet.getString("name");
                String bufferForNote = resultSet.getString("note");

                if (archived) {
                    setDataOnPanel(bufferForName, bufferForNote, true);
                } else {
                    setDataOnPanel(bufferForName, bufferForNote, false);
                }

                isEmpty = false;
            }

            if (isEmpty) {
                jPanel.add(new JLabel("Nothing to show"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDataOnPanel(String name, String note, boolean isArchived) {
        JPanel notePanel = new JPanel();

        JTextArea nameOfNote = new JTextArea(name, 1, 20);
        nameOfNote.setEditable(false);
        nameOfNote.setLineWrap(true);
        notePanel.add(nameOfNote);

        JTextArea jTextArea = new JTextArea(note, 15, 75);
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        notePanel.add(new JScrollPane(jTextArea));

        if (!isArchived) {
            JButton archive = new JButton("Archive");
            archive.addActionListener(e -> {
                databaseManager.archiveTheNote(name, true);

                if (jPanel.getComponents().length == 1) {
                    clearScreen();
                    jPanel.add(new JLabel("Nothing To show"));
                } else {
                    jPanel.remove(notePanel);
                }

                jPanel.revalidate();
            });
            notePanel.add(archive);
        } else {
            JButton archive = new JButton("Unarchive");
            archive.addActionListener(e -> {
                databaseManager.archiveTheNote(name, false);

                if (jPanel.getComponents().length == 1) {
                    clearScreen();
                    jPanel.add(new JLabel("Nothing To show"));
                } else {
                    jPanel.remove(notePanel);
                }

                jPanel.revalidate();
            });
            notePanel.add(archive);
        }

        jPanel.add(notePanel);
    }
}