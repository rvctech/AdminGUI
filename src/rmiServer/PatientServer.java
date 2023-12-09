package rmiServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import logic.MySQLConnection;
import model.IPatient;
import model.Patient;

public class PatientServer {

	public static void main(String args[]) {
		try {
			// DB setup:
			MySQLConnection.setupDB();

			// create rmiregistry:
			java.rmi.registry.LocateRegistry.createRegistry(2099);

			IPatient patient = new Patient("firstName", "lastName");
			IPatient patient2 = new Patient("start", "end", "date", 1, 1);
			IPatient patient3 = new Patient(1, "session");

			// URL format: "//host:port/name"
			String hostAndPort = "//127.0.0.1:2099/";

			// Rebind the name to a new object; replaces any existing binding.
			Naming.rebind(hostAndPort + "NN", patient);
			Naming.rebind(hostAndPort + "AA", patient2);
			Naming.rebind(hostAndPort + "BB", patient3);

			System.out.println("Patient Server: objects registered ...");
		} catch (RemoteException e) {
			System.out.println("Error: " + e);
		} catch (MalformedURLException e) {
			System.out.println("Error: " + e);
		}

	}

}
