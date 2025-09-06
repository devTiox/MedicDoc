package Scenes;

import Containers.CardsPanel;
import Data.DataBase;
import Data.Patient;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddingPatientScene extends Scene{

    @SuppressWarnings("SpellCheckingInspection")
    private final JLabel[] labelsFields = new JLabel[]{
            new JLabel("Imię"),
            new JLabel("Nazwisko"),
            new JLabel("Adress zamieszkania"),
            new JLabel("Data Urodzenia"),
            new JLabel("PESEL"),
            new JLabel("Numer telefonu"),
            new JLabel("E-mail")
    };

    private final JTextField[] textField = new JTextField[]{
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20)
    };

    public AddingPatientScene(String title, CardLayout cardLayout, CardsPanel parentPanel) {
        super(title, cardLayout, parentPanel);
        setUpTextFields();
    }

    private void confirmationWindow(Patient newPatient, AddingPatientScene parentScreen){
        // Dostosowujemy rozmiar czcionki do rozdzielczości
        int messageSize = Math.min(30, getAdaptiveFontSize(24));
        int buttonSize = Math.min(20, getAdaptiveFontSize(16));

        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, messageSize));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, buttonSize));

        int result = JOptionPane.showConfirmDialog(parentScreen,
                newPatient + "\nCzy wszystko się zgadza",
                "Potwierdzenie",
                JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION){
            new newDocumentationScene("DOCUMENTATION_SCENE", cardLayout, parentPanel, newPatient);
            try {
                DataBase.insertPatient(newPatient);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            cardLayout.show(parentPanel, "DOCUMENTATION_SCENE");
        }
    }

    public void reset(){
        for(JTextField field : textField){
            field.setText("");
        }
    }

    // Metoda do obliczania adaptacyjnego rozmiaru czcionki
    private int getAdaptiveFontSize(int baseSize) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double scaleFactor = Math.min(screenSize.width / 1920.0, screenSize.height / 1080.0);
        return Math.max(12, (int)(baseSize * scaleFactor));
    }

    private void setUpTextFields(){
        this.setLayout(new BorderLayout());

        // Główny panel z przewijaniem
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        GridBagConstraints gbc = new GridBagConstraints();

        // Adaptacyjne marginesy i czcionki
        int adaptiveFontSize = getAdaptiveFontSize(24);
        int adaptiveInset = Math.max(5, getAdaptiveFontSize(8));

        gbc.insets = new Insets(adaptiveInset, adaptiveInset, adaptiveInset, adaptiveInset);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        JLabel label;
        JTextField field;

        for(int i = 0; i < 7; i++){
            gbc.gridx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0.0;

            label = labelsFields[i];
            label.setFont(new Font("Arial", Font.BOLD, adaptiveFontSize));
            mainPanel.add(label, gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;

            field = textField[i];
            field.setOpaque(true);
            field.setFont(new Font("Arial", Font.PLAIN, adaptiveFontSize));
            field.setPreferredSize(new Dimension(200, adaptiveFontSize + 10));
            field.setMinimumSize(new Dimension(150, adaptiveFontSize + 8));
            mainPanel.add(field, gbc);

            gbc.gridy++;
        }

        // Przycisk zapisz
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(adaptiveInset * 2, adaptiveInset, adaptiveInset, adaptiveInset);

        JButton saveButton = new JButton("ZAPISZ");
        int buttonFontSize = getAdaptiveFontSize(32);
        saveButton.setFont(new Font("Arial", Font.BOLD, buttonFontSize));
        saveButton.setPreferredSize(new Dimension(
                Math.max(150, buttonFontSize * 4),
                Math.max(50, buttonFontSize + 15)
        ));

        saveButton.addActionListener(e -> {
            boolean valid = checkDataValidation();
            this.repaint();

            if(valid) {
                Patient newPatient = new Patient(textField[0].getText(), textField[1].getText(),
                        textField[2].getText(), textField[3].getText(),
                        textField[4].getText(), textField[5].getText(),
                        textField[6].getText(), "");
                confirmationWindow(newPatient, this);
            }
        });

        mainPanel.add(saveButton, gbc);

        // Dodajemy scroll pane do głównego panelu
        this.add(scrollPane, BorderLayout.CENTER);

        // Panel z przyciskami nawigacyjnymi (jeśli potrzebne)
        JPanel navigationPanel = new JPanel(new FlowLayout());
        navigationPanel.setPreferredSize(new Dimension(0, Math.max(40, adaptiveFontSize + 20)));
        this.add(navigationPanel, BorderLayout.SOUTH);
    }

    private boolean checkDataValidation(){
        boolean valid = true;
        String text;
        JTextField field;

        for(int i = 0; i < 7; i++){
            field = textField[i];
            text = field.getText();

            switch(i){
                case 0, 1, 2, 6 -> {
                    if(text.trim().isEmpty()) {
                        field.setBackground(Color.PINK);
                        valid = false;
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }
                case 3 -> {
                    if (text.length() != 10 || text.charAt(2) != '/' || text.charAt(5) != '/') {
                        field.setBackground(Color.PINK);
                        valid = false;
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }
                case 4 -> {
                    if(text.length() != 11){
                        field.setBackground(Color.PINK);
                        valid = false;
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }
                case 5 -> {
                    if(text.length() != 11 && text.length() != 9){
                        field.setBackground(Color.PINK);
                        valid = false;
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }
            }
            field.repaint();
        }
        return valid;
    }
}

//package Scenes;
//
//import Containers.CardsPanel;
//import Data.DataBase;
//import Data.Patient;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.SQLException;
//
//public class AddingPatientScene extends Scene{
//
//    @SuppressWarnings("SpellCheckingInspection")
//    private final JLabel[] labelsFields = new JLabel[]{
//            new JLabel("Imię"),
//            new JLabel("Nazwisko"),
//            new JLabel("Adress zamieszkania"),
//            new JLabel("Data Urodzenia"),
//            new JLabel("PESEL"),
//            new JLabel("Numer telefonu"),
//            new JLabel("E-mail")
//    };
//
//    private final JTextField[] textField = new JTextField[]{
//            new JTextField(20),
//            new JTextField(20),
//            new JTextField(20),
//            new JTextField(20),
//            new JTextField(20),
//            new JTextField(20),
//            new JTextField(20)
//    };
//
//
//    public AddingPatientScene(String title, CardLayout cardLayout, CardsPanel parentPanel) {
//        super(title, cardLayout, parentPanel);
//        setUpTextFields();
//    }
//
//
//
//    private void confirmationWindow(Patient newPatient, AddingPatientScene parentScreen){
//        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 30));
//        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 20));
//        int result = JOptionPane.showConfirmDialog(parentScreen,
//                newPatient + "\nCzy wszystko się zgadza",
//                "Potwierdzenie",
//                JOptionPane.YES_NO_OPTION);
//
//        if(result == JOptionPane.YES_OPTION){
//            new newDocumentationScene("DOCUMENTATION_SCENE", cardLayout, parentPanel, newPatient);
//            try {
//                DataBase.insertPatient(newPatient);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            cardLayout.show(parentPanel, "DOCUMENTATION_SCENE");
//        }
//    }
//
//    public void reset(){
//        for(JTextField field : textField){
//            field.setText("");
//        }
//    }
//
//    private void setUpTextFields(){
//        this.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10,10, 20, 10);
//        gbc.gridy = 0;
//        JLabel label;
//        JTextField field;
//        for(int i = 0; i < 7; i++){
//            gbc.gridx = 0;
//            label = labelsFields[i];
//            label.setFont(new Font("Arial", Font.BOLD, 30));
//            this.add(label, gbc);
//            gbc.gridx++;
//            field = textField[i];
//            field.setOpaque(true);
//            field.setFont(new Font("Arial", Font.BOLD, 30));
//            this.add(field, gbc);
//            gbc.gridy++;
//        }
//        saveButton(gbc);
//    }
//
//    private boolean checkDataValidation(){
//        boolean valid = true;
//        String text;
//        JTextField field;
//        for(int i = 0; i < 7; i++){
//            field = textField[i];
//            text = field.getText();
//            switch(i){
//                case 0, 1, 2, 6 -> {
//                    if(text.trim().isEmpty()) {
//                        field.setBackground(Color.RED);
//                        valid = false;
//                    } else {
//                        field.setBackground(Color.WHITE);
//                    }
//                }
//                case 3 -> {
//                    if (text.length() != 10 || text.charAt(2) != '/' || text.charAt(5) != '/') {
//                        field.setBackground(Color.RED);
//                        valid = false;
//                    } else {
//                        field.setBackground(Color.WHITE);
//                    }
//                }
//                case 4 -> {
//                    if(text.length() != 11){
//                        field.setBackground(Color.RED);
//                        valid = false;
//                    } else {
//                        field.setBackground(Color.WHITE);
//                    }
//                }case 5 -> {
//                    if(text.length() != 11 && text.length() != 9){
//                        field.setBackground(Color.RED);
//                        valid = false;
//                    } else {
//                        field.setBackground(Color.WHITE);
//                    }
//                }
//            }field.repaint();
//        }
//        return valid;
//    }
//
//    private void saveButton(GridBagConstraints gbc) {
//        JButton saveButton = new JButton("ZAPISZ");
//        saveButton.setFont(new Font("Arial", Font.ITALIC, 40));
//        saveButton.addActionListener(e -> {
//            boolean valid = checkDataValidation();
//            this.repaint();
//
//            if(valid) {
//                Patient newPatient = new Patient(textField[0].getText(), textField[1].getText(),
//                        textField[2].getText(), textField[3].getText(),
//                        textField[4].getText(), textField[5].getText(),
//                        textField[6].getText(), "");
//                confirmationWindow(newPatient, this);
//            }
//        });
//        this.add(saveButton,gbc);
//    }
//}
