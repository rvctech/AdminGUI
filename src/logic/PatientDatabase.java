package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Patient;

public class PatientDatabase {

	private Database db;

	private String dbName;

	private String tablePatient;

	private String tableAppointment;

	private static PatientDatabase patientDB;

	public static PatientDatabase getInstance() {
		if (patientDB == null)
			patientDB = new PatientDatabase();
		return patientDB;
	}

	private PatientDatabase(/* String dbName, String table */) {
		this.dbName = MySQLConnection.MYSQL_DB;
		this.tablePatient = MySQLConnection.MYSQL_TABLE_PATIENT;
		this.tableAppointment = MySQLConnection.MYSQL_TABLE_APPOINTMENT;
		try {
			this.db = new Database(dbName);
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL driver not found. Exiting.");
			System.exit(1);
		}
	}

	public void insertDB(Patient patient) {

		String sql = "INSERT INTO " + tablePatient + "(first_name, last_name)" + "VALUES (?, ?)";
		try {

			db.update(sql, patient.firstName, patient.lastName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void bookDB(Patient patient)
	{
		String sql = "INSERT INTO " + tableAppointment + "(start_at, end_at,date, patient_id, therapist_id)"
				+ "VALUES (?, ?, ?, ?, ?)";

		try {
			db.update(sql, patient.start, patient.end, patient.date, patient.patientId, patient.therapistId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteDB(int patientId) // throws SQLException
	{
		String sql = "DELETE FROM " + tablePatient + " WHERE patient_id = ?";
		try {
			db.update(sql, patientId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void patientUpdateDB(Patient patient) {

		String sql = "UPDATE treatment_history SET treatment_details = ? WHERE patient_id = ?";
		try {
			db.update(sql, patient.session, patient.patientId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Object[]> queryDB(String sql, Object... args) throws SQLException {
		return db.query(sql, args);
	}

	public void printQueryList(ArrayList<Object[]> list) {
		for (int i = 0; i < list.size(); i++) {
			Object[] row = list.get(i);
			for (int j = 0; j < row.length; j++) {
				System.out.print(row[j] + "   ");
			}
			System.out.println();

		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Db start ...");
		PatientDatabase db = PatientDatabase.getInstance();
		Patient pat = new Patient("Testing", "Protocol");
		// db.deleteDB(5);
		db.insertDB(pat);

		System.out.println("\nDb end");
	}

}
