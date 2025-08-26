package Scenes;

import Containers.CardsPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DocumentationScene extends Scene {
    //TODO: refactor this shit into 2 separate classes one with checkBoxes one with writing fields{
    // Zażywane leki (wraz z powodem)
    // Zażywane suplementy:
    // Schorzenia, przewlekłe choroby :
    // Aktywność fizyczna i rodzaj wykonywanej pracy:
    // (ile razy w tygodniu i intensywność+ praca siedząca/fizyczna)
    // Nietolerancje pokarmowe + nielubiane produkty:
    // Alergie (pokarmowe/wziewne/na leki):
    // }
    private String documentation;

    private final JPanel leftPanel;
    private final JPanel rightPanel;
    private final JPanel middlePanel;

    private JLabel digestiveLabel;
    private JLabel urinaryLabel;
    private JLabel mentalLabel;
    private JLabel muscularLabel;
    private JLabel physicalLabel;
    private JLabel otherLabel;

    private JCheckBox[] digestive;
    private JCheckBox[] urinary;
    private JCheckBox[] mental;
    private JCheckBox[] muscularSkeletal;
    private JCheckBox[] physical;
    private JCheckBox[] other;


    public DocumentationScene(String title, CardLayout cardLayout, CardsPanel parentPanel) {
        super(title, cardLayout, parentPanel);
        UIManager.put("CheckBox.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("Label.font", new Font("Arial", Font.ITALIC, 25));

        CardLayout thisCardLayout = new  CardLayout();
        this.setLayout(thisCardLayout);

        JPanel textPanel = new JPanel();
        JPanel checkBoxesPanel = new JPanel(new BorderLayout());
        checkBoxesPanel.setOpaque(false);

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setOpaque(false);

        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);


        checkBoxesPanel.add(leftPanel, BorderLayout.WEST);
        checkBoxesPanel.add(middlePanel, BorderLayout.CENTER);
        checkBoxesPanel.add(rightPanel,  BorderLayout.EAST);

        setUpCheckBoxes();
        this.add(checkBoxesPanel, "CHECKBOXES");

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "enter");
        getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCheckedBoxes();
            }
        });
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void setUpCheckBoxes() {
        Lists lists = new Lists();

        digestiveLabel = new JLabel("KŁAD POKARMOWY");
        urinaryLabel = new JLabel("UKŁAD MOCZOWY");
        mentalLabel = new JLabel("STAN PSYCHICZNY");
        muscularLabel = new JLabel("UKŁAD MIĘŚNIOWO KOSTNY");
        physicalLabel = new JLabel("SPRAWNOŚĆ");
        otherLabel = new JLabel("INNE");

        digestive = new JCheckBox[31];
        urinary = new JCheckBox[9];
        mental = new JCheckBox[28];
        muscularSkeletal = new JCheckBox[14];
        physical = new JCheckBox[13];
        other = new JCheckBox[11];

        int index = 0;
        leftPanel.add(urinaryLabel);
        for (String symptom : lists.urinarySymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            urinary[index] = checkBox;
            leftPanel.add(checkBox);
            index++;
        }

        index = 0;
        leftPanel.add(digestiveLabel);
        for (String symptom : lists.digestiveSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            digestive[index] = checkBox;
            leftPanel.add(checkBox);
            index++;
        }

        index = 0;
        middlePanel.add(mentalLabel);
        for (String symptom : lists.mentalSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            mental[index] = checkBox;
            middlePanel.add(checkBox);
            index++;
        }

        rightPanel.add(muscularLabel);
        index = 0;
        for (String symptom : lists.musculoSkeletalSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            muscularSkeletal[index] = checkBox;
            rightPanel.add(checkBox);
            index++;
        }

        index = 0;
        middlePanel.add(physicalLabel);
        for (String symptom : lists.physicalSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            physical[index] = checkBox;
            middlePanel.add(checkBox);
            index++;
        }

        index = 0;
        rightPanel.add(otherLabel);
        for (String symptom : lists.otherSymptoms) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setOpaque(false);
            other[index] = checkBox;
            rightPanel.add(checkBox);
            index++;
        }
    }

    private void getCheckedBoxes() {
        ArrayList<String> checkedUrinary = new ArrayList<>();
        String urinaryChecked = urinaryLabel.getText().toUpperCase() + ":";

        ArrayList<String> checkedDigestive = new ArrayList<>();
        String digestiveChecked = digestiveLabel.getText().toUpperCase() + ":";

        ArrayList<String> checkedMental = new ArrayList<>();
        String mentalChecked = mentalLabel.getText().toUpperCase() + ":";

        ArrayList<String> checkedMuscularSkeletal = new ArrayList<>();
        String muscularSkeletalChecked = muscularLabel.getText().toUpperCase() + ":";

        ArrayList<String> checkedPhysical = new ArrayList<>();
        String physicalChecked = physicalLabel.getText().toUpperCase() + ":";

        ArrayList<String> checkedOther = new ArrayList<>();
        String otherChecked = otherLabel.getText().toUpperCase() + ":";

        check(urinary, checkedUrinary);
        makeItString(checkedUrinary, urinaryChecked);

        check(digestive, checkedDigestive);
        makeItString(checkedDigestive, digestiveChecked);

        check(mental, checkedMental);
        makeItString(checkedMental, mentalChecked);

        check(muscularSkeletal, checkedMuscularSkeletal);
        makeItString(checkedMuscularSkeletal, muscularSkeletalChecked);

        check(physical, checkedPhysical);
        makeItString(checkedPhysical, physicalChecked);

        check(other, checkedOther);
        makeItString(checkedOther, otherChecked);

    }

    private void check(JCheckBox[] checkBoxes, ArrayList<String> stringArray) {
        for(JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                stringArray.add(checkBox.getText());
            }
        }
    }

    private void makeItString(ArrayList<String> array, String base){
        base += array;
        base += "\n";
        documentation += base;
        System.out.println(base);
    }

}

