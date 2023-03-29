import javax.swing.*;
import java.util.ArrayList;

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
        // window title
        setTitle("Participants Form");
        // set position and size of window
        setBounds(600, 200, 600, 600);
        setVisible(true);
        setContentPane(panelMain);
        // make text-area's non-editable
        statusArea.setEditable(false);
        listArea.setEditable(false);
        // set initial textField content
        txtName.setText("Enter name:");
        // set up the hour and minute spinner with proper constraints
        SpinnerNumberModel hourModel = new SpinnerNumberModel(0, 0 ,23, 1);
        hourSpinner.setModel(hourModel);
        SpinnerNumberModel minuteModel = new SpinnerNumberModel(0, 0 ,59, 1);
        minuteSpinner.setModel(minuteModel);
        // make program exit when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initialize an empty ArrayList for storing Participants
        ArrayList<Participant> participants = new ArrayList<>();

        // what happens on btnClick
        btnClick.addActionListener(e -> {
            // get name from textField
            String name = txtName.getText();
            // get time slot from spinners
            String timeSlot = String.format("%02d", hourSpinner.getValue()) + ":" + String.format("%02d", minuteSpinner.getValue());
            // check if name entered is not empty or still at default value
            if (name.equals("Enter name:") || name.equals("")) {
                statusArea.append("The given name is not valid.\n");
            } else {
                // create new instance of Participant class and assign entered name and time slot
                Participant participant = new Participant(name, timeSlot);
                // add to ArrayList participants
                participants.add(participant);
                System.out.println(participants);
                statusArea.append("The participant was added.\n");
                listArea.append(participant.getName() + " on time " + participant.getTimeSlot() + "\n");
            }
            txtName.setText("Enter name:");
            hourModel.setValue(12);
            minuteModel.setValue(30);
        });
    }

    public static class Participant {
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
