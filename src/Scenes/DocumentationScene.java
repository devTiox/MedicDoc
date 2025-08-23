package Scenes;

import Containers.CardsPanel;
import Containers.MainWindow;

import javax.swing.*;
import java.awt.*;

public class DocumentationScene extends Scene {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel middlePanel;

    public DocumentationScene(String title, CardLayout cardLayout, CardsPanel parentPanel, Patient patient) {
        super(title, cardLayout, parentPanel);
        UIManager.put("CheckBox.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("Label.font", new Font("Arial", Font.ITALIC, 25));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setOpaque(false);

        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);


        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel,  BorderLayout.EAST);

        setUpCheckBoxes();

      this.add(mainPanel, BorderLayout.CENTER);
    }

    private void setUpCheckBoxes() {
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

        int index = 0;
        leftPanel.add(urinaryLabel);

        for (String symptom : lists.urinarySymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            urinary[index] = checkBox;
            leftPanel.add(checkBox);
        }

        index = 0;
        leftPanel.add(digestiveLabel);
        for (String symptom : lists.digestiveSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            digestive[index] = checkBox;
            leftPanel.add(checkBox);
        }

        index = 0;
        middlePanel.add(mentalLabel);
        for (String symptom : lists.mentalSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            mental[index] = checkBox;
            middlePanel.add(checkBox);
        }

        rightPanel.add(muscluarLabel);
        index = 0;
        for (String symptom : lists.musculoSkeletalSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            musculoSkeletal[index] = checkBox;
            rightPanel.add(checkBox);
        }

        index = 0;
        middlePanel.add(physicalLabel);
        for (String symptom : lists.physicalSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            physical[index] = checkBox;
            middlePanel.add(checkBox);
        }

        index = 0;
        rightPanel.add(otherLabel);
        for (String symptom : lists.otherSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            other[index] = checkBox;
            rightPanel.add(checkBox);
        }
    }
}

