package adaptor.impl;

import adaptor.worker.impl.WorkerImpl;
import common.constants.GlobalConstants;
import common.rmi.RmiUtils;
import adaptor.Adaptor;
import manager.Manager;
import common.template.manageradaptor.vo.AdaptorSettingsVO;
import common.template.manageradaptor.vo.JobVO;
import common.utils.Constants;
import common.utils.bean.BeanUtils;
import common.utils.bean.ObjectCallProperties;
import common.properties.template.NamesTrProperties;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

/**
 * 
 * Adaptor serves as main manager on worker side - it is a medium between manager and workers threads
 * 
 * @author rud
 *
 */
public class AdaptorImpl extends UnicastRemoteObject implements Adaptor, Serializable {

	private transient ThreadPoolExecutor executorService;
	private Manager manager;

	private String managerHost;
	private int managerPort;	
	private String managerBindingName;

	// settings
	private Constants.CALL_TYPE callType;
	private AdaptorSettingsVO settings;

	private transient Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public AdaptorImpl() throws RemoteException {
		init();
	}

	@Override
	public void init() throws RemoteException{

		loadProperties();

		initExecutor();

		if (this.callType.equals(Constants.CALL_TYPE.RMI)) {
			this.exportToRmi();
			try {
				this.manager = (Manager) BeanUtils
						.getObject(new ObjectCallProperties(Constants.CALL_TYPE.RMI, managerHost, managerPort, managerBindingName));
			} catch (Exception e) {
				this.logger.severe(e.getMessage());
			}
		}
		notifyManager();	
	}

	@Override
	public void loadProperties() {
		new NamesTrProperties();
		this.callType = NamesTrProperties.getCallType();

		if(callType.equals(Constants.CALL_TYPE.RMI)){
			this.settings = NamesTrProperties.getMOMENTARY_remoteAdaptorsSettings().iterator().next();

			this.managerHost = NamesTrProperties.getManagerHost();
			this.managerPort = NamesTrProperties.getManagerPort();
			this.managerBindingName = NamesTrProperties.getManagerBindingName();
		}else{
			this.settings = new AdaptorSettingsVO();
			this.settings.setName(GlobalConstants.LOCAL_ADAPTOR_NAME);
			this.settings.setMaximumWorkersNumber(NamesTrProperties.getAdaptorMaxWorkersCount());
		}
	}

	public void initExecutor() throws RemoteException{
		if(this.executorService == null){
			this.executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.settings.getMaximumWorkersNumber());
		}
	}

	public void reinitExecutor(int maximumWorkersNumber) throws RemoteException {
		setMaximumWorkerThreads(maximumWorkersNumber);
		if(this.executorService != null){
			this.executorService.shutdown();
			this.executorService = null;
		}
		initExecutor();
	}

	/**
	 * starts rmi server
	 */
	@Override
	public void exportToRmi() {
		try {
			RmiUtils.setServeHostnameConfiguration(this.settings.getHost());
			RmiUtils.bindObject(this.settings.getHost(), this.settings.getPort(), this.settings.getBindingName(), this);
		} catch (Exception e) {
			logger.info(e.getMessage() + "\n" + e.getCause());
		}
	}

	/**
	 * notifies manager about new adaptor
	 */
	@Override
	public void notifyManager() {
		if(this.manager != null){
			try {
				this.manager.addAdaptor(this);
				this.manager.notifyAdaptorAvailable();
			} catch (RemoteException e) {
				logger.severe(e.getMessage() + "\n" + e.getCause());
			}
		}
	}


    @Override
	public void executeJob(JobVO jobVO) throws RemoteException {
		if(executorService == null){
			initExecutor();
		}
		//		this.LOGGER.info("sending JobVO to worker. Creating worker thread for job " + new JsonHandlerImpl().serialize(jobVO));
		this.settings.incrementActualWorkersCount();
		executorService.execute(new WorkerImpl(this, jobVO));
		//		this.LOGGER.info("Created worker thread for this job");
	}

	@Override
	public void returnJob(JobVO jobVO) {
		try {
			//			this.LOGGER.info("returning job to manager - " + new JsonHandlerImpl().serialize(jobVO));
			this.settings.decrementActualWorkersCount();
			this.manager.onJobExecuted(jobVO);
			//			this.LOGGER.info("Job returned");
		} catch (RemoteException e) {
			logger.severe(e.getMessage());
		}
	}

	@Override
	public AdaptorSettingsVO getSettings() throws RemoteException {
		return this.settings;
	}
	@Override
	public Integer getActualWorkersCount() throws RemoteException{
		return this.settings.getActualWorkersCount();
	}

	public void setManager(Manager remoteManager) throws RemoteException {
		this.manager = remoteManager;

	}

	public Manager getManager() throws RemoteException {
		return this.manager;
	}

	@Override
	public void setMaximumWorkerThreads(int maximumWorkerThreads) throws RemoteException {
		this.getSettings().setMaximumWorkersNumber(maximumWorkerThreads);
	}

	/**
	 * NO OP
	 */
	@Override
	public void notifyJobAvailable() throws RemoteException {}

}