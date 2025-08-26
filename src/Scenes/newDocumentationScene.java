package Scenes;


import Containers.CardsPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class newDocumentationScene extends Scene {

    public newDocumentationScene(String title, CardLayout cardLayout, CardsPanel parentPanel) {
        super(title, cardLayout, parentPanel);
        UIManager.put("CheckBox.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("Label.font", new Font("Arial", Font.ITALIC, 25));

        CardLayout thisCardLayout = new  CardLayout();
        this.setLayout(thisCardLayout);

        JPanel textPanel = new JPanel();
        CheckBoxesPanel checkBoxesPanel = new CheckBoxesPanel();

        this.add(checkBoxesPanel, "CHECKBOXES");
        thisCardLayout.show(this, "CHECKBOXES");
    }
}

class CheckBoxesPanel extends JPanel{
    private String documentation;

    private final JLabel digestiveLabel = new JLabel("UKŁAD POKARMOWY");
    private final JLabel urinaryLabel = new JLabel("UKŁAD MOCZOWY");
    private final JLabel mentalLabel = new JLabel("STAN PSYCHICZNY");
    private final JLabel muscularLabel = new JLabel("UKŁAD MIĘŚNIOWO KOSTNY");
    private final JLabel physicalLabel = new JLabel("SPRAWNOŚĆ");
    private final JLabel otherLabel = new JLabel("INNE");

    private final JCheckBox[] digestive = new JCheckBox[31];
    private final JCheckBox[] urinary = new JCheckBox[9];
    private final JCheckBox[] mental = new JCheckBox[28];
    private final JCheckBox[] muscularSkeletal = new JCheckBox[14];
    private final JCheckBox[] physical = new JCheckBox[13];
    private final JCheckBox[] other = new JCheckBox[11];

    public CheckBoxesPanel(){
        this.setOpaque(false);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setOpaque(false);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        Lists lists = new Lists();
        setUpCheckBoxes(urinaryLabel, urinary, leftPanel, lists.urinarySymptoms);
        setUpCheckBoxes(digestiveLabel, digestive, leftPanel, lists.digestiveSymptoms);
        setUpCheckBoxes(mentalLabel, mental, middlePanel, lists.mentalSymptoms);
        setUpCheckBoxes(physicalLabel, physical, middlePanel, lists.physicalSymptoms);
        setUpCheckBoxes(muscularLabel, muscularSkeletal, rightPanel, lists.musculoSkeletalSymptoms);
        setUpCheckBoxes(otherLabel, other, rightPanel, lists.otherSymptoms);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(rightPanel,  BorderLayout.EAST);

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "enter");
        getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                documentation = "";
                getCheckedBoxes();
                System.out.println(documentation);
            }
        });

    }

    private void setUpCheckBoxes(JLabel label, JCheckBox[] checkBoxes, JPanel panel, java.util.List<String> list) {
        int index = 0;
        panel.add(label);
        for (String symptom : list) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            checkBoxes[index] = checkBox;
            panel.add(checkBox);
            index++;
        }
    }

    private void getCheckedBoxes() {
        check(urinary, urinaryLabel);
        check(digestive, digestiveLabel);
        check(mental, mentalLabel);
        check(muscularSkeletal, muscularLabel);
        check(physical, physicalLabel);
        check(other, otherLabel);
    }

    private void check(JCheckBox[] checkBoxes, JLabel label) {
        ArrayList<String> checkedArray = new ArrayList<>();
        String checked = label.getText().toUpperCase() + ":";
        for(JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                checkedArray.add(checkBox.getText());
            }
        }
        makeItString(checkedArray, checked);
    }

    private void makeItString(ArrayList<String> array, String base){
        base += array;
        base += "\n";
        documentation += base;
    }
}