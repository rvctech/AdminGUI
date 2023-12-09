package frontend;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.Naming;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.IPatient;

class JxFrame2 extends JFrame {

	String url = "rmi://127.0.0.1:2099/";
	String pName = "NN";
	String pBook = "AA";
	String pudate = "BB";

	private JPanel panel;

	private WindowListener windowListener = new JxWindowListener();

	private class JxWindowListener extends WindowAdapter // inner class
	{
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	private class JxPanel extends JPanel {
		private int COLS = 25;

		private JLabel patientID = new JLabel("Patient ID");
		private JLabel patientName = new JLabel("Patient Name");
		private JLabel patients = new JLabel("Patients Details");

		private JLabel label5 = new JLabel("    -------------------------------------------     ");

		private JTextField textID = new JTextField("ID", COLS);
		private JTextField textName = new JTextField("Name", COLS);

		private JTextArea showPatientsArea = new JTextArea(5, 20);

		private JButton update = new JButton("Update");
		private JButton getPatient = new JButton("Get Patient");
		private JButton getPatientDetails = new JButton("Get Patient Details");

		private ActionListener buttonListener = new JxButtonListener();

		private class JxButtonListener implements ActionListener // inner class
		{
			public void actionPerformed(ActionEvent e) {
				Object theButton = e.getSource();
				if (theButton == update) {
					System.out.println("You pressed button Update");
					try {
						// lookup, returns a proxy object:
						IPatient patient = (IPatient) Naming.lookup(url + pudate);
						// patient.update(Integer.valueOf(textID.getText()),
						// showPatientsArea.getText());
						patient.update(showPatientsArea.getText(), Integer.valueOf(textID.getText()));

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(JxPanel.this, // parent
																	// component
								"update Message", // message
								"update Exception", // title
								JOptionPane.WARNING_MESSAGE);
					}
				} else if (theButton == getPatientDetails) {
					System.out.println("You pressed button Get Patient Details");
					try {
						// lookup, returns a proxy object:
						IPatient patient = (IPatient) Naming.lookup(url + pName);
						String results = patient.getPatientDetails(Integer.valueOf(textID.getText()));

						showPatientsArea.setText("");
						showPatientsArea.append(results + "\n");

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(JxPanel.this, // parent
																	// component
								"Delete Message", // message
								"Delete Exception", // title
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					System.out.println("You pressed button Get Patients");
					try {
						// lookup, returns a proxy object:
						IPatient patient = (IPatient) Naming.lookup(url + pName);

						String result = patient.getPatients(textName.getText());

						showPatientsArea.setText("");
						showPatientsArea.append(result + "\n");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(JxPanel.this, // parent
																	// component
								"Get Message", // message
								"Get Exception", // title
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}

		public JxPanel() {
			// this.setLayout(new FlowLayout());
			this.add(patientID);
			this.add(textID);
			this.add(patientName);
			this.add(textName);

			// this.add(label5);

			this.add(patients);
			this.add(showPatientsArea);
			showPatientsArea.setEditable(true);

			// this.add(label5);

			this.add(update);
			this.add(getPatient);
			this.add(getPatientDetails);

			update.addActionListener(buttonListener);
			getPatientDetails.addActionListener(buttonListener);
			getPatient.addActionListener(buttonListener);

		}

	}

	public JxFrame2() {
		this.setTitle("Doctor");
		this.setSize(320, 480);

		this.addWindowListener(windowListener);

		panel = new JxPanel();

		Container contentPane = getContentPane();
		contentPane.add(panel);
	}
}

public class DoctorGUI {

	public static void main(String[] args) {
		Frame f = new JxFrame2();
		f.setVisible(true);

	}

}
