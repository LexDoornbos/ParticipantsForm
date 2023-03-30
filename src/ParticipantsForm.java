import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ParticipantsForm extends JFrame {
    private JPanel panelMain;
    private JTextField txtName;
    private JButton btnClick;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JTextArea statusArea;
    private JTextArea listArea;

//
    public ParticipantsForm() {
        setTitle("Participants Form");  // window title
        setBounds(600, 200, 600, 600);  // set position and size of window
        setVisible(true);
        setContentPane(panelMain);
        statusArea.setMargin(new Insets(10, 10, 10,10));  // add a little padding on the textAreas
        listArea.setMargin(new Insets(10, 10, 10,10));
        statusArea.setEditable(false);  // make text-area's non-editable
        listArea.setEditable(false);
        txtName.setText("Enter name:");  // set initial textField content
        // focus listener to automatically clear textField when user wants to enter name
        txtName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { txtName.setText("");}

            @Override
            public void focusLost(FocusEvent e) {}
        });
        // set up the hour and minute spinner with proper constraints
        SpinnerNumberModel hourModel = new SpinnerNumberModel(12, 0 ,23, 1);
        hourSpinner.setModel(hourModel);
        SpinnerNumberModel minuteModel = new SpinnerNumberModel(30, 0 ,59, 1);
        minuteSpinner.setModel(minuteModel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // make program exit when window is closed

        // initialize an empty ArrayList for storing Participants
        ArrayList<String> participantsList = new ArrayList<>();
        // Arraylist for checking if a chosen time slot has already been taken
        ArrayList<String> reservedTimeSlots = new ArrayList<>();

        // read the contents of the text file and populate the text area.. (Thank you ChatGPT)
        try {
            File file = new File("participants.txt");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    participantsList.add(line);
                    reservedTimeSlots.add(line.substring(line.lastIndexOf(" ") + 1));
                }
                scanner.close();
                // display the contents of the participantsList array in the text area
                listArea.setText(String.join("\n", participantsList));
            }
        } catch (FileNotFoundException e) {
            statusArea.append("File not found: participants.txt\n");
        }

        // what happens on btnClick
        btnClick.addActionListener(e -> {
            // get name from textField
            String name = txtName.getText();
            // get time slot from spinners
            String timeSlot = String.format("%02d", hourSpinner.getValue()) + ":" + String.format("%02d", minuteSpinner.getValue());
            // check if name entered is not empty or still at default value
            if (name.equals("Enter name:") || name.equals("")) {
                statusArea.append("The given name is not valid.\n");
            // check if the time slot is already reserved
            } else if (reservedTimeSlots.contains(timeSlot)) {
                statusArea.append("Time slot taken. Please choose another.\n");
            } else {
                // create new instance of Participant class and assign entered name and time slot
                Participant participant = new Participant(name, timeSlot);
                // add to ArrayList participants and reservedTimeSlots
                participantsList.add(participant.getName() + " on time " + participant.getTimeSlot());
                reservedTimeSlots.add(participant.getTimeSlot());
                statusArea.append("The participant was added.\n");
                // clear listArea before appending participantsList
                listArea.setText("");
                listArea.append(String.join("\n", participantsList));
                // append the new participant to text file.. (Thank you ChatGPT)
                try {
                    // open the text file for appending
                    BufferedWriter writer = new BufferedWriter(new FileWriter("participants.txt", true));
                    writer.write(participant.getName() + " on time " + participant.getTimeSlot() + "\n");
                    writer.close();
                } catch (IOException ex) {
                    // if there's an error writing to the text file, just print an error message and continue
                    System.err.println("Error writing to participants.txt: " + ex.getMessage());
                }
            }
            // reset input fields to chosen defaults
            txtName.setText("Enter name:");
            hourModel.setValue(12);
            minuteModel.setValue(30);
        });
    }

    // participant class to populate participantsList
    public class Participant {
        private String name;
        private String timeSlot;

        public Participant(String name, String timeSlot) {
            this.name = name;
            this.timeSlot = timeSlot;
        }

        public String getName() {
            return name;
        }

        public String getTimeSlot() {
            return timeSlot;
        }
    }


    public static void main(String[] args) {
        // calling new instance of constructor of class ParticipantsForm
        new ParticipantsForm();
    }
}
