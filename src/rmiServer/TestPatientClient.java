package rmiServer;

import java.rmi.Naming;

import model.IPatient;

public class TestPatientClient {

	public static void main(String[] args) {

		String url = "rmi://127.0.0.1:2099/";
		String pName1 = "BB";

		try {
			// lookup, returns a proxy object:
			IPatient pat1 = (IPatient) Naming.lookup(url + pName1);

			// call remote method:
			System.out.println(pat1.getPatientDetails(1));

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

	}
}