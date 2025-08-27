package Scenes;

import Containers.CardsPanel;
import Data.Lists;
import Data.Patient;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class newDocumentationScene extends Scene {

    public newDocumentationScene(String title, CardLayout cardLayout, CardsPanel parentPanel, Patient patient) {
        super(title, cardLayout, parentPanel);

        UIManager.put("CheckBox.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("Label.font", new Font("Arial", Font.ITALIC, 35));
        UIManager.put("TextArea.font", new Font("Arial", Font.BOLD, 25));
        UIManager.put("TextField.font", new Font("Arial", Font.BOLD, 20));
        UIManager.put("Button.font", new Font("Arial", Font.ITALIC, 40));
        CardLayout thisCardLayout = new  CardLayout();
        this.setLayout(thisCardLayout);

        TextFieldsPanel textFieldsPanel = new TextFieldsPanel(this, thisCardLayout, patient);
        CheckBoxesPanel checkBoxesPanel = new CheckBoxesPanel(this, patient);

        this.add(checkBoxesPanel, "CHECK-BOXES");
        this.add(textFieldsPanel, "TEXT-FIELDS");
        thisCardLayout.show(this, "TEXT-FIELDS");
    }
}

@SuppressWarnings("SpellCheckingInspection")
class TextFieldsPanel extends JPanel{
    private final Patient patient;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final String todayDate = "Data wpisu: " + sdf.format(new Date()) + "\n---------------------------\n";
    private String fieldsDocumentation = todayDate;

    private final JLabel medicaments = new JLabel("Zażywane leki(wraz z powodem):".toUpperCase());
    private final JLabel supplements = new JLabel("Zażywane suplementy:".toUpperCase());
    private final JLabel diseases = new JLabel("Schorzenia, przewlekłe choroby:".toUpperCase());
    private final JLabel physicalActivity = new JLabel(("Aktywność fizyczna i rodzaj wykonywanej pracy\n".toUpperCase()) +
            "(ile razy w tygodniu i intensywność+ praca siedząca/fizyczna):".toUpperCase());
    private final JLabel foodIntolerance = new JLabel("Nietolerancje pokarmowe + nielubiane produkty:".toUpperCase());
    private final JLabel allergies = new JLabel("Alergie (pokarmowe/wziewne/na leki):".toUpperCase());
    private final JLabel alerts = new JLabel("Uwagi:".toUpperCase());

    private final JTextArea medicamentsField = new JTextArea();
    private final JTextArea supplementsField = new JTextArea();
    private final JTextArea diseasesField = new JTextArea();
    private final JTextArea physicalActivityField = new JTextArea();
    private final JTextArea foodIntoleranceField = new JTextArea();
    private final JTextArea allergiesField = new JTextArea();
    private final JTextArea alertsField = new JTextArea();

    private final JPanel parent;
    private final CardLayout parentLayout;

    public TextFieldsPanel(JPanel parent,CardLayout parentLayout, Patient patient) {
        this.parent = parent;
        this.parentLayout = parentLayout;
        this.patient = patient;

        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 10, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;

        setUp(gbc);
    }

    private void setUp(GridBagConstraints gbc){
        setUpFields(medicaments, medicamentsField, gbc);
        setUpFields(supplements, supplementsField, gbc);
        setUpFields(diseases, diseasesField, gbc);
        setUpFields(physicalActivity, physicalActivityField, gbc);
        setUpFields(foodIntolerance, foodIntoleranceField, gbc);
        setUpFields(allergies, allergiesField, gbc);
        setUpFields(alerts, alertsField, gbc);

        JButton saveButton = new JButton("ZAPISZ");
        saveButton.addActionListener(e->{
            checkFields();
            combineDocumentation();
            parentLayout.show(parent, "CHECK-BOXES");
        });
        this.add(saveButton, gbc);
    }

    private void combineDocumentation() {
        patient.documentation += fieldsDocumentation + "\n";
    }

    private void setUpFields(JLabel label, JTextArea field, GridBagConstraints gbc){
        field.setLineWrap(true);
        field.setWrapStyleWord(true);
        field.setRows(3);
        field.setColumns(80);
        this.add(label, gbc);
        gbc.gridy++;
        this.add(field, gbc);
        gbc.gridy += 2;
    }

    private void checkFields(){
        makeItString(medicamentsField.getText(), medicaments.getText());
        makeItString(supplementsField.getText(), supplements.getText());
        makeItString(diseasesField.getText(), diseases.getText());
        makeItString(physicalActivityField.getText(), physicalActivity.getText());
        makeItString(foodIntoleranceField.getText(), foodIntolerance.getText());
        makeItString(allergiesField.getText(), allergies.getText());
        makeItString(alertsField.getText(), alerts.getText());
        System.out.println(fieldsDocumentation);
    }

    private void makeItString(String fieldContent, String base){
        base += fieldContent;
        base += "\n";
        fieldsDocumentation += base;
    }
}

@SuppressWarnings("SpellCheckingInspection")
class CheckBoxesPanel extends JPanel{
    private String checkBoxesDocumentation;

    private final Patient patient;
    private final newDocumentationScene parent;

    private final JLabel digestiveLabel = new JLabel("UKŁAD POKARMOWY: ");
    private final JLabel urinaryLabel = new JLabel("UKŁAD MOCZOWY: ");
    private final JLabel mentalLabel = new JLabel("STAN PSYCHICZNY: ");
    private final JLabel muscularLabel = new JLabel("UKŁAD MIĘŚNIOWO KOSTNY: ");
    private final JLabel physicalLabel = new JLabel("SPRAWNOŚĆ: ");
    private final JLabel otherLabel = new JLabel("INNE: ");

    private final JTextField[] otherFields = new JTextField[]{
            new JTextField(),
            new JTextField(),
            new JTextField(),
            new JTextField()
    };

    private final JCheckBox[] digestive = new JCheckBox[31];
    private final JCheckBox[] urinary = new JCheckBox[9];
    private final JCheckBox[] mental = new JCheckBox[28];
    private final JCheckBox[] muscularSkeletal = new JCheckBox[14];
    private final JCheckBox[] physical = new JCheckBox[13];
    private final JCheckBox[] other = new JCheckBox[11];

    public CheckBoxesPanel(newDocumentationScene parent, Patient patient){
        this.setOpaque(false);
        this.patient = patient;
        this.parent = parent;

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

        setUpButtonAndFields(rightPanel);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(rightPanel,  BorderLayout.EAST);
    }

    private void setUpButtonAndFields(JPanel panel){
        for(JTextField field : otherFields){
            panel.add(field);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton saveButton = new JButton("ZAPISZ");
        saveButton.addActionListener(e -> {
            checkBoxesDocumentation = "";
            getCheckedBoxes();
            combineDocumentation();
            new PatientScene(patient.patientSceneTitle, parent.cardLayout, parent.parentPanel, patient);
            parent.cardLayout.show(parent.parentPanel, patient.patientSceneTitle);
        });
        panel.add(saveButton);
    }

    private void combineDocumentation(){
        patient.documentation += checkBoxesDocumentation + "\n";
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
        checkBoxesDocumentation += base;
    }
}