package Scenes;

import Containers.CardsPanel;
import Data.Lists;
import Data.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class newDocumentationScene extends Scene {

    public newDocumentationScene(String title, CardLayout cardLayout, CardsPanel parentPanel, Patient patient) {
        super(title, cardLayout, parentPanel);

        // Adaptacyjne czcionki
        int baseFontSize = getAdaptiveFontSize();
        UIManager.put("CheckBox.font", new Font("Arial", Font.BOLD, Math.max(12, baseFontSize - 4)));
        UIManager.put("Label.font", new Font("Arial", Font.BOLD, Math.max(14, baseFontSize)));
        Font arial = new Font("Arial", Font.PLAIN, Math.max(12, baseFontSize - 2));
        UIManager.put("TextArea.font", arial);
        UIManager.put("TextField.font", arial);
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, Math.max(14, baseFontSize + 2)));

        CardLayout thisCardLayout = new CardLayout();
        
        this.setLayout(thisCardLayout);
        this.setBackground(new Color(255, 255, 255));

        TextFieldsPanel textFieldsPanel = new TextFieldsPanel(this, thisCardLayout, patient);
        CheckBoxesPanel checkBoxesPanel = new CheckBoxesPanel(this, thisCardLayout, patient);
        TextDocumentationPanel textDocumentationPanel = new TextDocumentationPanel(this, patient);

        this.add(checkBoxesPanel, "CHECK-BOXES");
        this.add(textFieldsPanel, "TEXT-FIELDS");
        this.add(textDocumentationPanel, "TEXT-DOCUMENTATION");
        thisCardLayout.show(this, "TEXT-FIELDS");
    }

    private int getAdaptiveFontSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double scaleFactor = Math.min(screenSize.width / 1920.0, screenSize.height / 1080.0);
        return Math.max(12, (int)(16 * scaleFactor));
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

        this.setBackground(new Color(255,220,240));
        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        setUp();
    }

    private void setUp(){
        // Główny panel z przewijaniem
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.setBackground(new Color(255,220,240));

        GridBagConstraints gbc = new GridBagConstraints();
        int adaptiveInset = Math.max(3, getAdaptiveFontSize() / 4);
        gbc.insets = new Insets(adaptiveInset, adaptiveInset, adaptiveInset, adaptiveInset);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        setUpFields(medicaments, medicamentsField, gbc, mainPanel);
        setUpFields(supplements, supplementsField, gbc, mainPanel);
        setUpFields(diseases, diseasesField, gbc, mainPanel);
        setUpFields(physicalActivity, physicalActivityField, gbc, mainPanel);
        setUpFields(foodIntolerance, foodIntoleranceField, gbc, mainPanel);
        setUpFields(allergies, allergiesField, gbc, mainPanel);
        setUpFields(alerts, alertsField, gbc, mainPanel);

        // Przycisk ZAPISZ
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(adaptiveInset * 2, adaptiveInset, adaptiveInset, adaptiveInset);

        JButton saveButton = new JButton("ZAPISZ");
        int buttonSize = Math.max(40, getAdaptiveFontSize() * 2);
        saveButton.setPreferredSize(new Dimension(buttonSize * 3, buttonSize));
        saveButton.addActionListener(e->{
            checkFields();
            combineDocumentation();
            parentLayout.show(parent, "CHECK-BOXES");
        });
        mainPanel.add(saveButton, gbc);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private int getAdaptiveFontSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double scaleFactor = Math.min(screenSize.width / 1920.0, screenSize.height / 1080.0);
        return Math.max(12, (int)(16 * scaleFactor));
    }

    private void combineDocumentation() {
        patient.documentation += fieldsDocumentation + "\n";
    }

    private void setUpFields(JLabel label, JTextArea field, GridBagConstraints gbc, JPanel panel){
        // Adaptacyjne rozmiary
        int rows = Math.max(2, 4 - (getAdaptiveFontSize() > 16 ? 1 : 0));
        int cols = Math.min(80, Math.max(40, (int)(80 * getScreenScaleFactor())));

        field.setLineWrap(true);
        field.setWrapStyleWord(true);
        field.setRows(rows);
        field.setColumns(cols);

        // Scrollable text area dla długich tekstów
        JScrollPane fieldScroll = new JScrollPane(field);
        fieldScroll.setPreferredSize(new Dimension(400, Math.max(60, rows * getAdaptiveFontSize() + 20)));
        fieldScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel.add(label, gbc);
        gbc.gridy++;
        panel.add(fieldScroll, gbc);
        gbc.gridy++;
    }

    private double getScreenScaleFactor() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return Math.min(screenSize.width / 1920.0, screenSize.height / 1080.0);
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
        if(fieldContent.isEmpty()){
            fieldContent = "BRAK";
        }
        base += fieldContent;
        base += "\n";
        fieldsDocumentation += base;
    }
}

@SuppressWarnings("SpellCheckingInspection")
class CheckBoxesPanel extends JPanel{
    private String checkBoxesDocumentation;

    private final Patient patient;
    private final JPanel parent;
    private final CardLayout parentLayout;

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

    public CheckBoxesPanel(JPanel parent,CardLayout parentLayout, Patient patient){
        this.setOpaque(false);
        this.patient = patient;
        this.parent = parent;
        this.parentLayout = parentLayout;

        this.setLayout(new BorderLayout());

        // Główny panel z przewijaniem
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        mainPanel.setBackground(new Color(255,220,240));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

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

        mainPanel.add(leftPanel);
        mainPanel.add(middlePanel);
        mainPanel.add(rightPanel);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private int getAdaptiveFontSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double scaleFactor = Math.min(screenSize.width / 1920.0, screenSize.height / 1080.0);
        return Math.max(12, (int)(16 * scaleFactor));
    }

    private void setUpButtonAndFields(JPanel panel){
        int fieldHeight = Math.max(25, getAdaptiveFontSize() + 8);
        int spacing = Math.max(5, getAdaptiveFontSize() / 3);

        for(JTextField field : otherFields){
            field.setPreferredSize(new Dimension(200, fieldHeight));
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldHeight));
            panel.add(field);
            panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        }

        JButton saveButton = new JButton("ZAPISZ");
        int buttonHeight = Math.max(40, getAdaptiveFontSize() * 2);
        saveButton.setPreferredSize(new Dimension(150, buttonHeight));
        saveButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonHeight));
        saveButton.addActionListener(e -> {
            checkBoxesDocumentation = "";
            getCheckedBoxes();
            combineDocumentation();
            parentLayout.show(parent, "TEXT-DOCUMENTATION");
        });
        panel.add(Box.createRigidArea(new Dimension(0, spacing * 2)));
        panel.add(saveButton);
    }

    private void combineDocumentation(){
        patient.documentation += checkBoxesDocumentation + "\n";
    }

    private void setUpCheckBoxes(JLabel label, JCheckBox[] checkBoxes, JPanel panel, java.util.List<String> list) {
        int index = 0;
        int spacing = Math.max(2, getAdaptiveFontSize() / 6);

        // Wyróżnienie etykiety
        label.setFont(new Font("Arial", Font.BOLD, Math.max(14, getAdaptiveFontSize())));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));

        for (String symptom : list) {
            JCheckBox checkBox = new JCheckBox(symptom);
            checkBox.setBackground(new Color(255,220,240));
            checkBox.setFont(new Font("Arial", Font.PLAIN, Math.max(10, getAdaptiveFontSize() - 2)));
            checkBox.addItemListener(e -> {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    checkBox.setBackground(Color.GREEN);
                }else checkBox.setBackground(new Color(255,220,240));
            });
            checkBoxes[index] = checkBox;
            panel.add(checkBox);
            index++;
        }
        panel.add(Box.createRigidArea(new Dimension(0, spacing * 2)));
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
        if(label.equals(otherLabel)){
            for(JTextField field : otherFields){
                if(!field.getText().isEmpty())
                    checkedArray.add(field.getText());
            }
        }
        if(checkedArray.isEmpty()){
            checkedArray.add("BRAK");
        }
        makeItString(checkedArray, checked);
    }

    private void makeItString(ArrayList<String> array, String base){
        base += array;
        base += "\n";
        checkBoxesDocumentation += base;
    }
}

class TextDocumentationPanel extends JPanel {
    private final Patient patient;
    private final JTextArea textArea;

    public TextDocumentationPanel(newDocumentationScene parent, Patient patient) {
        this.patient = patient;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(255,220,240));

        // Adaptacyjne rozmiary
        int fontSize = getAdaptiveFontSize();
        int margin = Math.max(10, fontSize);

        textArea = new JTextArea();
        textArea.setEditable(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(margin, margin, margin, margin));

        // Scrollable text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.add(scrollPane, BorderLayout.CENTER);

        // Panel z przyciskiem
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton saveButton = getJButton(parent, patient, fontSize);
        buttonPanel.add(saveButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton getJButton(newDocumentationScene parent, Patient patient, int fontSize) {
        JButton saveButton = new JButton("ZAPISZ");
        int buttonHeight = Math.max(40, fontSize * 2);
        saveButton.setPreferredSize(new Dimension(150, buttonHeight));
        saveButton.setFont(new Font("Arial", Font.BOLD, Math.max(14, fontSize)));
        saveButton.addActionListener(e -> {
            combineDocumentation();
            new PatientScene(patient.patientSceneTitle, parent.cardLayout, parent.parentPanel, patient);
            parent.cardLayout.show(parent.parentPanel, patient.patientSceneTitle);
        });
        return saveButton;
    }

    private int getAdaptiveFontSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double scaleFactor = Math.min(screenSize.width / 1920.0, screenSize.height / 1080.0);
        return Math.max(12, (int)(16 * scaleFactor));
    }

    private void combineDocumentation(){
        String textDocumentation = textArea.getText();
        patient.documentation += textDocumentation + "\n";
    }
}

//package Scenes;
//
//import Containers.CardsPanel;
//import Containers.MainWindow;
//import Data.Lists;
//import Data.Patient;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ItemEvent;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//public class newDocumentationScene extends Scene {
//
//    public newDocumentationScene(String title, CardLayout cardLayout, CardsPanel parentPanel, Patient patient) {
//        super(title, cardLayout, parentPanel);
//
//        UIManager.put("CheckBox.font", new Font("Arial", Font.BOLD, 20));
//        UIManager.put("Label.font", new Font("Arial", Font.ITALIC, 35));
//        UIManager.put("TextArea.font", new Font("Arial", Font.BOLD, 25));
//        UIManager.put("TextField.font", new Font("Arial", Font.BOLD, 20));
//        UIManager.put("Button.font", new Font("Arial", Font.ITALIC, 40));
//        CardLayout thisCardLayout = new  CardLayout();
//        this.setLayout(thisCardLayout);
//
//        TextFieldsPanel textFieldsPanel = new TextFieldsPanel(this, thisCardLayout, patient);
//        CheckBoxesPanel checkBoxesPanel = new CheckBoxesPanel(this, thisCardLayout, patient);
//        TextDocumentationPanel textDocumentationPanel = new TextDocumentationPanel(this, patient);
//
//        this.add(checkBoxesPanel, "CHECK-BOXES");
//        this.add(textFieldsPanel, "TEXT-FIELDS");
//        this.add(textDocumentationPanel, "TEXT-DOCUMENTATION");
//        thisCardLayout.show(this, "TEXT-FIELDS");
//    }
//}
//
//@SuppressWarnings("SpellCheckingInspection")
//class TextFieldsPanel extends JPanel{
//    private final Patient patient;
//
//    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//    private final String todayDate = "Data wpisu: " + sdf.format(new Date()) + "\n---------------------------\n";
//    private String fieldsDocumentation = todayDate;
//
//    private final JLabel medicaments = new JLabel("Zażywane leki(wraz z powodem):".toUpperCase());
//    private final JLabel supplements = new JLabel("Zażywane suplementy:".toUpperCase());
//    private final JLabel diseases = new JLabel("Schorzenia, przewlekłe choroby:".toUpperCase());
//    private final JLabel physicalActivity = new JLabel(("Aktywność fizyczna i rodzaj wykonywanej pracy\n".toUpperCase()) +
//            "(ile razy w tygodniu i intensywność+ praca siedząca/fizyczna):".toUpperCase());
//    private final JLabel foodIntolerance = new JLabel("Nietolerancje pokarmowe + nielubiane produkty:".toUpperCase());
//    private final JLabel allergies = new JLabel("Alergie (pokarmowe/wziewne/na leki):".toUpperCase());
//    private final JLabel alerts = new JLabel("Uwagi:".toUpperCase());
//
//    private final JTextArea medicamentsField = new JTextArea();
//    private final JTextArea supplementsField = new JTextArea();
//    private final JTextArea diseasesField = new JTextArea();
//    private final JTextArea physicalActivityField = new JTextArea();
//    private final JTextArea foodIntoleranceField = new JTextArea();
//    private final JTextArea allergiesField = new JTextArea();
//    private final JTextArea alertsField = new JTextArea();
//
//    private final JPanel parent;
//    private final CardLayout parentLayout;
//
//    public TextFieldsPanel(JPanel parent,CardLayout parentLayout, Patient patient) {
//        this.parent = parent;
//        this.parentLayout = parentLayout;
//        this.patient = patient;
//
//        this.setOpaque(false);
//        this.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 10, 15);
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//
//        setUp(gbc);
//    }
//
//    private void setUp(GridBagConstraints gbc){
//        setUpFields(medicaments, medicamentsField, gbc);
//        setUpFields(supplements, supplementsField, gbc);
//        setUpFields(diseases, diseasesField, gbc);
//        setUpFields(physicalActivity, physicalActivityField, gbc);
//        setUpFields(foodIntolerance, foodIntoleranceField, gbc);
//        setUpFields(allergies, allergiesField, gbc);
//        setUpFields(alerts, alertsField, gbc);
//
//        JButton saveButton = new JButton("ZAPISZ");
//        saveButton.addActionListener(e->{
//            checkFields();
//            combineDocumentation();
//            parentLayout.show(parent, "CHECK-BOXES");
//        });
//        this.add(saveButton, gbc);
//    }
//
//    private void combineDocumentation() {
//        patient.documentation += fieldsDocumentation + "\n";
//    }
//
//    private void setUpFields(JLabel label, JTextArea field, GridBagConstraints gbc){
//        field.setLineWrap(true);
//        field.setWrapStyleWord(true);
//        field.setRows(3);
//        field.setColumns(80);
//        this.add(label, gbc);
//        gbc.gridy++;
//        this.add(field, gbc);
//        gbc.gridy += 2;
//    }
//
//    private void checkFields(){
//        makeItString(medicamentsField.getText(), medicaments.getText());
//        makeItString(supplementsField.getText(), supplements.getText());
//        makeItString(diseasesField.getText(), diseases.getText());
//        makeItString(physicalActivityField.getText(), physicalActivity.getText());
//        makeItString(foodIntoleranceField.getText(), foodIntolerance.getText());
//        makeItString(allergiesField.getText(), allergies.getText());
//        makeItString(alertsField.getText(), alerts.getText());
//        System.out.println(fieldsDocumentation);
//    }
//
//    private void makeItString(String fieldContent, String base){
//        if(fieldContent.isEmpty()){
//            fieldContent = "BRAK";
//        }
//        base += fieldContent;
//        base += "\n";
//        fieldsDocumentation += base;
//    }
//}
//
//@SuppressWarnings("SpellCheckingInspection")
//class CheckBoxesPanel extends JPanel{
//    private String checkBoxesDocumentation;
//
//    private final Patient patient;
//    private final JPanel parent;
//    private final CardLayout parentLayout;
//
//    private final JLabel digestiveLabel = new JLabel("UKŁAD POKARMOWY: ");
//    private final JLabel urinaryLabel = new JLabel("UKŁAD MOCZOWY: ");
//    private final JLabel mentalLabel = new JLabel("STAN PSYCHICZNY: ");
//    private final JLabel muscularLabel = new JLabel("UKŁAD MIĘŚNIOWO KOSTNY: ");
//    private final JLabel physicalLabel = new JLabel("SPRAWNOŚĆ: ");
//    private final JLabel otherLabel = new JLabel("INNE: ");
//
//    private final JTextField[] otherFields = new JTextField[]{
//            new JTextField(),
//            new JTextField(),
//            new JTextField(),
//            new JTextField()
//    };
//
//    private final JCheckBox[] digestive = new JCheckBox[31];
//    private final JCheckBox[] urinary = new JCheckBox[9];
//    private final JCheckBox[] mental = new JCheckBox[28];
//    private final JCheckBox[] muscularSkeletal = new JCheckBox[14];
//    private final JCheckBox[] physical = new JCheckBox[13];
//    private final JCheckBox[] other = new JCheckBox[11];
//
//    public CheckBoxesPanel(JPanel parent,CardLayout parentLayout, Patient patient){
//        this.setOpaque(false);
//        this.patient = patient;
//        this.parent = parent;
//        this.parentLayout = parentLayout;
//
//        JPanel leftPanel = new JPanel();
//        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
//        leftPanel.setOpaque(false);
//
//        JPanel middlePanel = new JPanel();
//        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
//        middlePanel.setOpaque(false);
//
//        JPanel rightPanel = new JPanel();
//        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
//        rightPanel.setOpaque(false);
//
//        Lists lists = new Lists();
//        setUpCheckBoxes(urinaryLabel, urinary, leftPanel, lists.urinarySymptoms);
//        setUpCheckBoxes(digestiveLabel, digestive, leftPanel, lists.digestiveSymptoms);
//        setUpCheckBoxes(mentalLabel, mental, middlePanel, lists.mentalSymptoms);
//        setUpCheckBoxes(physicalLabel, physical, middlePanel, lists.physicalSymptoms);
//        setUpCheckBoxes(muscularLabel, muscularSkeletal, rightPanel, lists.musculoSkeletalSymptoms);
//        setUpCheckBoxes(otherLabel, other, rightPanel, lists.otherSymptoms);
//
//        setUpButtonAndFields(rightPanel);
//
//        this.add(leftPanel, BorderLayout.WEST);
//        this.add(middlePanel, BorderLayout.CENTER);
//        this.add(rightPanel,  BorderLayout.EAST);
//    }
//
//    private void setUpButtonAndFields(JPanel panel){
//        for(JTextField field : otherFields){
//            panel.add(field);
//            panel.add(Box.createRigidArea(new Dimension(0, 10)));
//        }
//
//        JButton saveButton = new JButton("ZAPISZ");
//        saveButton.addActionListener(e -> {
//            checkBoxesDocumentation = "";
//            getCheckedBoxes();
//            combineDocumentation();
//            parentLayout.show(parent, "TEXT-DOCUMENTATION");
//        });
//        panel.add(saveButton);
//    }
//
//    private void combineDocumentation(){
//        patient.documentation += checkBoxesDocumentation + "\n";
//    }
//
//    private void setUpCheckBoxes(JLabel label, JCheckBox[] checkBoxes, JPanel panel, java.util.List<String> list) {
//        int index = 0;
//        panel.add(label);
//        for (String symptom : list) {
//            JCheckBox checkBox = new JCheckBox(symptom);
//            checkBox.setBackground(new Color(255,220,240));
//            checkBox.addItemListener(e -> {
//                if(e.getStateChange() == ItemEvent.SELECTED){
//                    checkBox.setBackground(Color.GREEN);
//                }else checkBox.setBackground(new Color(255,220,240));
//            });
//            checkBoxes[index] = checkBox;
//            panel.add(checkBox);
//            index++;
//        }
//    }
//
//    private void getCheckedBoxes() {
//        check(urinary, urinaryLabel);
//        check(digestive, digestiveLabel);
//        check(mental, mentalLabel);
//        check(muscularSkeletal, muscularLabel);
//        check(physical, physicalLabel);
//        check(other, otherLabel);
//    }
//
//    private void check(JCheckBox[] checkBoxes, JLabel label) {
//        ArrayList<String> checkedArray = new ArrayList<>();
//        String checked = label.getText().toUpperCase() + ":";
//        for(JCheckBox checkBox : checkBoxes) {
//            if (checkBox.isSelected()) {
//                checkedArray.add(checkBox.getText());
//            }
//        }
//        if(label.equals(otherLabel)){
//            for(JTextField field : otherFields){
//                if(!field.getText().isEmpty())
//                    checkedArray.add(field.getText());
//            }
//        }
//        if(checkedArray.isEmpty()){
//            checkedArray.add("BRAK");
//        }
//        makeItString(checkedArray, checked);
//    }
//
//    private void makeItString(ArrayList<String> array, String base){
//        base += array;
//        base += "\n";
//        checkBoxesDocumentation += base;
//    }
//}
//
//class TextDocumentationPanel extends JPanel {
//    private final Patient patient;
//    private final JTextArea textArea;
//
//    public TextDocumentationPanel(newDocumentationScene parent, Patient patient) {
//        this.patient = patient;
//        this.setLayout(new BorderLayout(100, 50));
//        this.setOpaque(false);
//        textArea = new JTextArea();
//        textArea.setEditable(true);
//        textArea.setSize(MainWindow.screenWidth - 200, MainWindow.screenHeight - 100);
//        this.add(textArea, BorderLayout.CENTER);
//        JButton saveButton = new JButton("ZAPISZ");
//        saveButton.addActionListener(e -> {
//            combineDocumentation();
//            new PatientScene(patient.patientSceneTitle, parent.cardLayout, parent.parentPanel, patient);
//            parent.cardLayout.show(parent.parentPanel, patient.patientSceneTitle);
//        });
//        this.add(saveButton, BorderLayout.SOUTH);
//    }
//
//    private void combineDocumentation(){
//        String textDocumentation = textArea.getText();
//        patient.documentation += textDocumentation + "\n";
//    }
//}