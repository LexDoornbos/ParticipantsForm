import javax.swing.*;
import javax.xml.namespace.QName;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class ParticipantsForm extends JFrame {
    private JPanel panelMain;
    private JTextField txtName;
    private JButton btnClick;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JTextArea statusArea;
    private JTextArea listArea;


    public ParticipantsForm() {
        // set up the hour and minute spinner
        SpinnerNumberModel hourModel = new SpinnerNumberModel(0, 0 ,23, 1);
        hourSpinner.setModel(hourModel);
        SpinnerNumberModel minuteModel = new SpinnerNumberModel(0, 0 ,59, 1);
        minuteSpinner.setModel(minuteModel);
        Calendar cal = Calendar.getInstance();

        btnClick.addActionListener(e -> {
            String name = txtName.getText();
            String timeSlot = String.format("%02d", hourSpinner.getValue()) + ":" + String.format("%02d", minuteSpinner.getValue());

            if (name.equals("Enter name:") || name.equals("")) {
                statusArea.append("The given name is not valid.\n");
            } else {
                statusArea.append("The participant was added.\n");
                listArea.append(name + " on time " + timeSlot + "\n");
            }
            txtName.setText("Enter name:");
            hourModel.setValue(12);
            minuteModel.setValue(30);
        });
    }

    public static void main(String[] args) {
        ParticipantsForm h = new ParticipantsForm();
        h.setContentPane(h.panelMain);
        h.setTitle("Participants Form");
        h.setBounds(600, 200, 600, 600);
        h.setVisible(true);
        h.statusArea.setEditable(false);
        h.listArea.setEditable(false);
        h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        h.txtName.setText("Enter name:");
    }
}
