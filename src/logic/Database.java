package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	// MySQL constants:
	public static final String HOST = "localhost";
	public static final String PORT = "3306";
	public static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/";
	public static final String timezone = "? serverTimezone=UTC";

	// Default values:
	private String driver = "com.mysql.jdbc.Driver";
	private String url = URL;

	private String user = "root";
	private String pw = "root";

	private Connection connection = null;

	public Database(String driver, String url, String user, String pw) throws ClassNotFoundException {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pw = pw;
		
		// loading the driver
		Class.forName(driver);
	}

	public Database(String databaseName, String user, String pw) throws ClassNotFoundException {
		this.url = url + databaseName + timezone;
		this.user = user;
		this.pw = pw;
		// loading the driver
		Class.forName(driver);
	}

	public Database(String databaseName) throws ClassNotFoundException {
		this.url = url + databaseName + timezone;
		// loading the driver
		Class.forName(driver);
	}

	private void openDatabase() throws SQLException {
		connection = DriverManager.getConnection(url, user, pw);
	}

	private void closeDatabase() throws SQLException {
		connection.close();
	}

	public int getMaxId(String table) throws SQLException {
		String sql = "Select Max(patient_id) As maxId From " + table;

		openDatabase();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();

		int max = rs.getInt("maxId");

		if (stmt != null)
			stmt.close();
		if (rs != null)
			rs.close();
		closeDatabase();

		return max;
	}

	public ArrayList<Object[]> query(String sql, Object... args) throws SQLException {
		openDatabase();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				statement.setObject(i + 1, args[i]);
			}
			resultSet = statement.executeQuery();
			ArrayList<Object[]> list = new ArrayList<Object[]>();
			while (resultSet.next()) {
				Object[] elements = new Object[resultSet.getMetaData().getColumnCount()];
				for (int i = 0; i < elements.length; i++) {
					elements[i] = resultSet.getObject(i + 1);
				}
				list.add(elements);
			}
			return list;
		} finally {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			closeDatabase();
		}
	}

	public int update(String sql, Object... args) throws SQLException {
		openDatabase();
		int rowCount = 0;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				statement.setObject(i + 1, args[i]);
			}
			rowCount = statement.executeUpdate();
			return rowCount;
		} finally {
			if (statement != null)
				statement.close();
			closeDatabase();
		}
	}

}
