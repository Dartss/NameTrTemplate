package test;

import jsmarty.template.manageradaptor.xxx_component.manager.XXX_Manager;
import jsmarty.template.manageradaptor.xxx_component.manager.impl.XXX_ManagerImpl;

import java.rmi.RemoteException;

public class InitManager {
	
	public static void main(String[] args) throws RemoteException {
		XXX_Manager manager = new XXX_ManagerImpl();
	}

}
