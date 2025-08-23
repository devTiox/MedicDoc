package Scenes;

import Containers.CardsPanel;
import javax.swing.*;
import java.awt.*;

public class MenuScene extends Scene {

    public MenuScene(String panelName, CardLayout cardLayout, CardsPanel parentPanel) {
        super(panelName, cardLayout, parentPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setButtons(this);
    }

    private void setButtons(JPanel buttonPanel) {
        int buttonWidth = screenWidth / 4;
        int buttonHeight = screenHeight / 7;
        int buttonGap = buttonHeight / 3;

        Dimension prefSize = new Dimension(buttonWidth, buttonHeight);
        Font font = new Font("Arial", Font.ITALIC, buttonHeight / 4);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(Box.createVerticalStrut(buttonGap + buttonGap / 8));
        //noinspection SpellCheckingInspection
        buttonPanel.add(createButton("Dodaj Pacjenta", prefSize, font));
        buttonPanel.add(Box.createVerticalStrut(buttonGap));
        //noinspection SpellCheckingInspection
        buttonPanel.add(createButton("Spis Pacjentów", prefSize, font));
        buttonPanel.add(Box.createVerticalStrut(buttonGap));
        //noinspection SpellCheckingInspection
        buttonPanel.add(createButton("Wyjdź", prefSize, font));
        buttonPanel.setOpaque(false);
    }

    private JButton createButton(String name, Dimension size, Font font) {
        JButton button = new JButton(name);
        switch (name) {
            //noinspection SpellCheckingInspection
            case "Dodaj Pacjenta" -> button.addActionListener(e->{
                parentPanel.resetAddingScene();
                cardLayout.show(parentPanel, "ADD_PATIENT");
            });
            //noinspection SpellCheckingInspection
            case "Spis Pacjentów"  -> {
//                parentPanel.updatePatientsListScene();
                button.addActionListener(e-> cardLayout.show(parentPanel, "PATIENTS_LIST"));
            }
            //noinspection SpellCheckingInspection
            case "Wyjdź" -> button.addActionListener(e -> System.exit(0));
        }
        button.setBackground(Color.WHITE);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setAlignmentX(Box.CENTER_ALIGNMENT);
        return button;
    }
}