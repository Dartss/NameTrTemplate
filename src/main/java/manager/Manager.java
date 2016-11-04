package manager;

import adaptor.Adaptor;
import model.JobVO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Manager extends Remote
{
    void addAdaptor(Adaptor adaptor) throws RemoteException;

    Adaptor pickAvailableAdaptor() throws RemoteException;

    void exportToRmi(final String host, final int port, final String bindingName) throws RemoteException;

    /**
     * Request for manager to execute some job
     *
     * @return return true if job accepted, false - otherwise
     * (This should be handled upon the logic of the component)
     * i.e. we might need to set the failure calls, dismiss the job or requeue it etc.
     */
    boolean executeJob(JobVO jobVO) throws RemoteException;

    /**
     * Invoked by the adaptor upon finishing the job
     *
     * @throws RemoteException
     */
    void onJobExecuted(JobVO jobVO) throws RemoteException;

    /**
     * Invoked by adaptors to inform manager that adaptor is available
     *
     * @throws RemoteException
     */
    void notifyAdaptorAvailable() throws RemoteException;
}