package model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import logic.MySQLConnection;
import logic.PatientDatabase;

public class Patient extends UnicastRemoteObject implements IPatient, Serializable {

	private static final long serialVersionUID = 1L;
	public String firstName = "";
	public String lastName = "";
	public int patientId = 0;
	public int therapistId = 0;
	public String start = "";
	public String ends = "";
	public String end = "";
	public String date = "";
	public String session = "";

	String tableP = MySQLConnection.MYSQL_TABLE_PATIENT;
	String tableT = MySQLConnection.MYSQL_TABLE_THERAPIST;
	String tableA = MySQLConnection.MYSQL_TABLE_APPOINTMENT;
	String tableTH = MySQLConnection.MYSQL_TABLE_TREATMENT_HISTORY;
	String tableR = MySQLConnection.MYSQL_TABLE_REPORT;

	public Patient(String firstName, String lastName) throws RemoteException {
		this.firstName = firstName;
		this.lastName = lastName;

	}

	public Patient(int patientId, String firstName, String lastName) throws RemoteException {
		this.patientId = patientId;
		this.firstName = firstName;
		this.lastName = lastName;

	}

	public Patient(String start, String end, String date, int patientId, int therapistId) throws RemoteException {

		this.start = start;
		this.end = end;
		this.date = date;
		this.patientId = patientId;
		this.therapistId = therapistId;

	}

	public Patient(int patientId, String session) throws RemoteException {
		this.patientId = patientId;
		this.session = session;

	}

	@Override
	public void add(String firstName, String lastName) throws RemoteException {
		PatientDatabase.getInstance().insertDB(new Patient(firstName, lastName));

	}

	@Override
	public void delete(int patientId) throws RemoteException {
		PatientDatabase.getInstance().deleteDB(patientId);

	}

	@Override
	public void book(String start, String end, String date, int patientId, int therapistId) throws RemoteException {
		PatientDatabase.getInstance().bookDB(new Patient(start, end, date, patientId, therapistId));

	}

	@Override
	public String getPatients(String name) throws RemoteException {
		String sql;
		StringBuffer sb = new StringBuffer();

		if (name == null)
			sql = "SELECT * FROM " + tableP;
		else
			sql = "SELECT * FROM " + tableP + " WHERE first_name = '" + name + "'";

		try {
			ArrayList<Object[]> list = PatientDatabase.getInstance().queryDB(sql);

			for (int i = 0; i < list.size(); i++) {
				Object[] row = list.get(i);
				for (int j = 0; j < row.length; j++) {
					sb.append(row[j] + " ");
				}
				// sb.append("; ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sb.toString();

	}

	@Override
	public String getPatientDetails(int patientId) throws RemoteException {

		String sql = null;
		StringBuffer sb = new StringBuffer();

		if (patientId == 0)
			System.out.println("Patient ID does NOT exist");
		else
			sql = "SELECT treatment_details FROM " + tableTH + " WHERE patient_id = '" + patientId + "'";

		try {
			ArrayList<Object[]> list = PatientDatabase.getInstance().queryDB(sql);

			for (int i = 0; i < list.size(); i++) {
				Object[] row = list.get(i);
				for (int j = 0; j < row.length; j++) {
					sb.append(row[j] + " ");
				}
				// sb.append("; ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sb.toString();

	}

	@Override
	public void update(String session, int patientId) throws RemoteException {
		PatientDatabase.getInstance().patientUpdateDB(new Patient(patientId, session));

	}

}
