package Scenes;

import Containers.CardsPanel;

import javax.swing.*;
import java.awt.*;

public class DocumentationScene extends Scene {

//dodac 3 panele zwykle obok siebie i sie nie jebac z tym
    public DocumentationScene(String title, CardLayout cardLayout, CardsPanel parentPanel, Patient patient) {
        super(title, cardLayout, parentPanel);
        JPanel panel = new JPanel(new GridBagLayout());
        UIManager.put("CheckBox.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("Label.font", new Font("Arial", Font.ITALIC, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 10);
        panel.setOpaque(false);
        setUpCheckBoxes(gbc, panel);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setOpaque(false);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void setUpCheckBoxes(GridBagConstraints gbc, JPanel panel) {
        Lists lists = new Lists();


        JLabel digestiveLabel = new JLabel("<html><b>UKŁAD POKARMOWY</b></html>");
        JLabel urinaryLabel = new JLabel("<html><b>UKŁAD MOCZOWY</b></html>");
        JLabel mentalLabel = new JLabel("<html><b>STAN PSYCHICZNY</b></html>");
        JLabel muscluarLabel = new JLabel("<html><b>UKŁAD MIĘŚNIOWO KOSTNY</b></html>");
        JLabel physicalLabel = new JLabel("<html><b>SPRAWNOŚĆ</b></html>");
        JLabel otherLabel = new JLabel("<html><b>INNE</b></html>");

        JCheckBox[] digestive = new JCheckBox[31];
        JCheckBox[] urinary = new JCheckBox[9];
        JCheckBox[] mental = new JCheckBox[13];
        JCheckBox[] musculoSkeletal = new JCheckBox[14];
        JCheckBox[] physical = new JCheckBox[29];
        JCheckBox[] other = new JCheckBox[9];

        gbc.gridy = 0;
        gbc.gridx = 0;
        int index = 0;
        panel.add(urinaryLabel, gbc);
        gbc.gridy++;
        for (String symptom : lists.urinarySymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            urinary[index] = checkBox;
            add(checkBox, gbc);
            gbc.gridy++;
        }

        gbc.gridy = 33;
        gbc.gridx = 0;
        index = 0;
        panel.add(digestiveLabel, gbc);
        gbc.gridy++;
        for (String symptom : lists.digestiveSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            digestive[index] = checkBox;
            panel.add(checkBox, gbc);
            gbc.gridy++;
        }

        gbc.gridy = 0;
        gbc.gridx = 1;
        index = 0;
        panel.add(mentalLabel, gbc);
        gbc.gridy++;
        for (String symptom : lists.mentalSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            mental[index] = checkBox;
            panel.add(checkBox, gbc);
            gbc.gridy++;
        }

        gbc.gridy = 1;
        gbc.gridx = 33;
        panel.add(muscluarLabel, gbc);
        index = 0;
        gbc.gridy++;
        for (String symptom : lists.musculoSkeletalSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            musculoSkeletal[index] = checkBox;
            panel.add(checkBox, gbc);
            gbc.gridy++;
        }

        gbc.gridy = 0;
        gbc.gridx = 3 ;
        index = 0;
        panel.add(physicalLabel, gbc);
        gbc.gridy++;
        for (String symptom : lists.physicalSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            physical[index] = checkBox;
            panel.add(checkBox, gbc);
            gbc.gridy++;
        }

        gbc.gridy = 33;
        gbc.gridx = 3;
        index = 0;
        panel.add(otherLabel, gbc);
        gbc.gridy++;
        for (String symptom : lists.otherSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            other[index] = checkBox;
            panel.add(checkBox, gbc);
            gbc.gridy++;
        }
    }
}

