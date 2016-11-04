package test;


import manager.Manager;
import manager.impl.ManagerImpl;

import java.rmi.RemoteException;

public class InitManager {
	
	public static void main(String[] args) throws RemoteException {
	    Manager manager = new ManagerImpl();
	}

}
