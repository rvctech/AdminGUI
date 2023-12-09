package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPatient extends Remote {

	void add(String firstName, String lastName) throws RemoteException;

	void delete(int patientId) throws RemoteException;

	void book(String start, String end, String date, int patientId, int therapistId) throws RemoteException;

	String getPatients(String name) throws RemoteException;

	String getPatientDetails(int patientId) throws RemoteException;

	void update(String session, int patientId) throws RemoteException;

}
