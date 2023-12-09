package logic;
import java.util.ArrayList;

public class TestPatientDatabse {

	public static void main(String[] args) throws Exception {
		System.out.println("MainPatientDatabase1 begin");

		String table = MySQLConnection.MYSQL_TABLE_PATIENT;

		PatientDatabase db = PatientDatabase.getInstance();

	

		String sqlStr = "select * from " + table;
		ArrayList<Object[]> list = db.queryDB(sqlStr);

		// System.out.println ("Size of result: " + list.size());

		db.printQueryList(list);
		System.out.println("-------------------------------------");

		// db.deleteDB (table, 6);

		ArrayList<Object[]> list1 = db.queryDB(sqlStr);
		db.printQueryList(list1);
		System.out.println("-------------------------------------");

		System.out.println("MainPatientDatabase1 end");
	}

}
