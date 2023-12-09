package frontend;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.Naming;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import model.IPatient;

class JxFrame1 extends JFrame {

	String url = "rmi://127.0.0.1:2099/";
	String pName = "NN";
	String pBook = "AA";

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
		private JLabel time = new JLabel("Time ");
		private JLabel patients = new JLabel("Patients");
		private JLabel therapist = new JLabel("Therapist");

		private JLabel label5 = new JLabel("    -------------------------------------------     ");

		private JTextField textID = new JTextField("ID", COLS);
		private JTextField textName = new JTextField("Name", COLS);

		String[] bt = { "9.00-10.00", "10.00-11.00", "11.00-12.00", "13.00-14.00", "14.00-15.00" };
		String[] ts = { "Orthopedic", "Neurology", "Sports", "Geriatrics" };

		private JComboBox<String> cb = new JComboBox<String>(bt);
		private JComboBox<String> cb2 = new JComboBox<String>(ts);

		private JTextArea showPatientsArea = new JTextArea(5, 20);
		//private JScrollPane scrollToShowArea = new JScrollPane(showPatientsArea);

		private JButton add = new JButton("Add");
		private JButton delete = new JButton("Delete");
		private JButton book = new JButton("Book");
		private JButton getPatient = new JButton("Get Patient");

		private ActionListener buttonListener = new JxButtonListener();

		private class JxButtonListener implements ActionListener // inner class
		{
			public void actionPerformed(ActionEvent e) {
				Object theButton = e.getSource();
				if (theButton == add) {
					System.out.println("You pressed button Add");
					try {
						// lookup, returns a proxy object:
						IPatient patient = (IPatient) Naming.lookup(url + pName);
						String str = textName.getText();
						String[] names = str.split("\\s+");
						patient.add(names[0], names[1]);

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(JxPanel.this, // parent
																	// component
								"Insert Message", // message
								"Insert Exception", // title
								JOptionPane.WARNING_MESSAGE);
					}
				} else if (theButton == delete) {
					System.out.println("You pressed button Delete");
					try {
						// lookup, returns a proxy object:
						IPatient patient = (IPatient) Naming.lookup(url + pName);
						patient.delete(Integer.valueOf(textID.getText()));

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(JxPanel.this, // parent
																	// component
								"Delete Message", // message
								"Delete Exception", // title
								JOptionPane.WARNING_MESSAGE);
					}
				} else if (theButton == book) {
					System.out.println("You pressed button Book");
					String start;
					String end;
					int therapistId;
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime now = LocalDateTime.now();

					// Get current value of time
					if (cb.getSelectedItem().toString().equals("9.00-10.00")) {
						start = "09:00:00";
						end = "10:00:00";
					} else if (cb.getSelectedItem().toString().equals("10.00-11.00")) {
						start = "10:00:00";
						end = "11:00:00";
					} else if (cb.getSelectedItem().toString().equals("12.00-12.00")) {
						start = "11:00:00";
						end = "12:00:00";
					} else if (cb.getSelectedItem().toString().equals("12.00-13.00")) {
						start = "12:00:00";
						end = "13:00:00";
					} else if (cb.getSelectedItem().toString().equals("13.00-14.00")) {
						start = "13:00:00";
						end = "14:00:00";
					} else {
						start = "14:00:00";
						end = "15:00:00";
					}

					System.out.println("Start: " + start);
					System.out.println("End: " + end);

					// Get current value of therapists
					if (cb2.getSelectedItem().toString().equals("Orthopedic")) {
						therapistId = 1;
					} else if (cb2.getSelectedItem().toString().equals("Neurology")) {
						therapistId = 2;
					} else if (cb2.getSelectedItem().toString().equals("Sports")) {
						therapistId = 3;
					} else {
						therapistId = 4;
					}

					System.out.println("Therapist ID: " + therapistId);

					try {
						// lookup, returns a proxy object:
						IPatient patient = (IPatient) Naming.lookup(url + pBook);
						patient.book(start, end, dtf.format(now), Integer.valueOf(textID.getText()), therapistId);

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(JxPanel.this, // parent
								// component
								"Get Message", // message
								"Get Exception", // title
								JOptionPane.WARNING_MESSAGE);
					}

					System.out.println(dtf.format(now));

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
			this.add(time);
			this.add(cb);
			this.add(therapist);
			this.add(cb2);

			// this.add(label5);

			this.add(patients);
			this.add(showPatientsArea);
			showPatientsArea.setEditable(false);

			this.add(label5);

			this.add(add);
			this.add(delete);
			this.add(book);
			this.add(getPatient);

			add.addActionListener(buttonListener);
			delete.addActionListener(buttonListener);
			book.addActionListener(buttonListener);
			getPatient.addActionListener(buttonListener);

		}

	}

	public JxFrame1() {
		this.setTitle("Admin");
		this.setSize(320, 480);

		this.addWindowListener(windowListener);

		panel = new JxPanel();

		Container contentPane = getContentPane();
		contentPane.add(panel);
	}
}

public class AdminGUI {

	public static void main(String[] args) {
		Frame f = new JxFrame1();
		f.setVisible(true);

	}

}
