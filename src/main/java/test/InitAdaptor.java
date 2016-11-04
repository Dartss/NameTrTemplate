package test;

import java.rmi.RemoteException;

import adaptor.impl.AdaptorImpl;

public class InitAdaptor
{
    public static void main(String[] args)
    {
	try
	{
	    AdaptorImpl adaptor = new AdaptorImpl();
	} catch (RemoteException e)
	{
	    e.printStackTrace();
	}
    }
}
