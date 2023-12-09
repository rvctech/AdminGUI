package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
	// MySQL constants:
	//public static final String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	public static final String MYSQL_HOST = "localhost";
	public static final String MYSQL_PORT = "3306";
	public static final String TIMEZONE = "? serverTimezone=UTC";

	public static final String MYSQL_URL = "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/";

	public static final String MYSQL_DB = "patients journal";
	public static final String MYSQL_TABLE_PATIENT = "patient";
	public static final String MYSQL_TABLE_APPOINTMENT = "appointment";
	public static final String MYSQL_TABLE_THERAPIST = "therapist";
	public static final String MYSQL_TABLE_TREATMENT_HISTORY = "treatment_history";
	public static final String MYSQL_TABLE_REPORT = "report";

	public static final String USER = "root";
	public static final String PASSWORD = "root";

	private static Connection con; // connection to actual DB

	private MySQLConnection() {
	}

	public static Connection getConnection(/*
											 * String dbName, String login,
											 * String password
											 */) {
		if (con == null)
			setupDB(/* dbName, login, password */);
		return con;
	}

	public static void setupDB(/*
								 * String dbName, String login, String password
								 */) {
		try {
			// loading the driver:
			Class.forName(MYSQL_DRIVER_NAME);
			// connect to DB:
			con = DriverManager.getConnection(MYSQL_URL + MYSQL_DB + TIMEZONE, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("** Unable to load database driver. **");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("** Unable to connect to database. *");
			System.exit(1);
		}
	}

}
