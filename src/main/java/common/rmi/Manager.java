package common.rmi;

import java.rmi.RemoteException;

public interface Manager {

	public void createStubs() throws RemoteException;

	/**
	 * Get stubs for each Remote server, 
	 * Create remote workers,
	 * run workers
	 */
	public void createWorkers(int nbWorkersPerHost) throws RemoteException;
	public void addWorkers(int nbWorkers) throws RemoteException;

}