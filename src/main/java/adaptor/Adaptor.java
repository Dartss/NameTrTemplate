package adaptor;

import model.AdaptorSettingsVO;
import model.JobVO;
import manager.Manager;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * RemoteException is required here in the method definition,
 * but will only be required in the methods that are using the remote objects
 * in the implementation
 */
/**
 * TODO: add description
 * 
 * @author vit
 *
 */
public interface Adaptor extends Remote
{

    public void init() throws RemoteException;

    public void loadProperties() throws RemoteException;

    public void initExecutor() throws RemoteException;

    public void reinitExecutor(int maximumWorkersNumber) throws RemoteException;

    public AdaptorSettingsVO getSettings() throws RemoteException;

    public void setMaximumWorkerThreads(int maximumWorkerThreads) throws RemoteException;

    public void setManager(Manager remoteManager) throws RemoteException;

    public Manager getManager() throws RemoteException;

    /**
     * 
     * @throws RemoteException
     */
    public void exportToRmi() throws RemoteException;

    /**
     * Used to notify the manager once the adaptor started
     * 
     * @throws RemoteException
     */
    public void notifyManager() throws RemoteException;

    /**
     * Called by the manager to invoke the job execution
     * Increment workers count should be handled in the implementation of this
     * method
     * 
     * @param jobVO
     * @throws RemoteException
     */
    public void executeJob(JobVO jobVO) throws RemoteException;

    /**
     * Invoked by the workers to return back job to the manager
     * Decrement workers count should be handled in the implementation of this
     * method
     * 
     * @return
     * @throws RemoteException
     *             will not actually be thrown
     */
    public void returnJob(JobVO jobVO) throws RemoteException;

    /**
     * 
     * @return
     * @throws RemoteException
     */
    public Integer getActualWorkersCount() throws RemoteException;

}