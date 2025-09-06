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
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 30));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 20));
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

    private void setUpTextFields(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10, 20, 10);
        gbc.gridy = 0;
        JLabel label;
        JTextField field;
        for(int i = 0; i < 7; i++){
            gbc.gridx = 0;
            label = labelsFields[i];
            label.setFont(new Font("Arial", Font.BOLD, 30));
            this.add(label, gbc);
            gbc.gridx++;
            field = textField[i];
            field.setOpaque(true);
            field.setFont(new Font("Arial", Font.BOLD, 30));
            this.add(field, gbc);
            gbc.gridy++;
        }
        saveButton(gbc);
    }

    private boolean checkDataValidation(){
        boolean valid = true;
        String text;
        JLabel label;
        JTextField field;
        for(int i = 0; i < 7; i++){
            label = labelsFields[i];
            field = textField[i];
            text = field.getText();
            switch(i){
                case 0, 1, 2, 6 -> {
                    if(text.trim().isEmpty()) {
                        field.setBackground(Color.RED);
                        valid = false;
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }
                case 3 -> {
                    if (text.length() != 10 || text.charAt(2) != '/' || text.charAt(5) != '/') {
                        field.setBackground(Color.RED);
                        valid = false;
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }
                case 4 -> {
                    if(text.length() != 11){
                        field.setBackground(Color.RED);
                        valid = false;
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }case 5 -> {
                    if(text.length() != 11 && text.length() != 9){
                        field.setBackground(Color.RED);
                        valid = false;
                    } else {
                        field.setBackground(Color.WHITE);
                    }
                }
            }field.repaint();
        }
        return valid;
    }

    private void saveButton(GridBagConstraints gbc) {
        JButton saveButton = new JButton("ZAPISZ");
        saveButton.setFont(new Font("Arial", Font.ITALIC, 40));
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
        this.add(saveButton,gbc);
    }
}
