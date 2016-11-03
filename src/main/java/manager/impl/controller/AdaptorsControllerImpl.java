package manager.impl.controller;


import adaptor.impl.AdaptorImpl;
import adaptor.Adaptor;
import manager.Manager;
import common.template.manageradaptor.vo.AdaptorSettingsVO;
import common.template.manageradaptor.vo.JobVO;
import common.utils.Constants;
import common.utils.bean.BeanUtils;
import common.utils.bean.ObjectCallProperties;
import properties.template.NamesTrProperties;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class AdaptorsControllerImpl implements AdaptorsController
{

	private Constants.CALL_TYPE callType;

	// key - server ip
	private Map<String, Adaptor> adaptors;
	private Set<String> activeAdaptors;
	private Set<String> activeAdaptorsTEMP;

	private Adaptor localAdaptor;

	private Manager manager;

	private Lock lock;
	private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public AdaptorsControllerImpl(Manager manager, Constants.CALL_TYPE callType) {
		loadProperties();

		this.callType = callType;
		this.lock = new ReentrantLock();
		this.adaptors = new HashMap<String, Adaptor>();
		this.activeAdaptors = new HashSet<>();
		this.manager = manager;
	}

	public void loadProperties() {
		new NamesTrProperties();

		/*
		 * 
		 * Fill local variables with needed properties ...
		 * 
		 * 
		 */
	}

	@Override
	public void connectAdaptors() {
		if(Constants.CALL_TYPE.RMI.equals(callType)){
			connectRemoteAdaptors();
		} else if(Constants.CALL_TYPE.LOCAL.equals(callType)){
			initLocalAdaptor();
		}
	}

	/**
	 * Creates the local instance of Adaptor and put in to
	 * the map of available adaptors
	 */
	public void initLocalAdaptor() {
		try {
			//			put(Constants.LOCAL_ADAPTOR_NAME, settings);
			this.localAdaptor = new AdaptorImpl();
			this.localAdaptor.setManager(this.manager);
		} catch (RemoteException e) {
			this.LOGGER.severe(e.getMessage() + "\n" + e.getCause());
		}

	}

	/**
	 * Loads set of adaptor's settings from properties.
	 * Tries to connect to remote adaptor. If connection was established then put
	 * adaptor to the map of active adaptors.
	 */
	public void connectRemoteAdaptors() {
		/*
		 * Currently only getting mocked data from properties
		 * TODO - settings should be retrieved from mysql settings table! (check ekp)
		 */
		Set<AdaptorSettingsVO> adaptorsSettingsSet = NamesTrProperties.getMOMENTARY_remoteAdaptorsSettings();

		this.lock.lock();
		try {
			for (AdaptorSettingsVO settings : adaptorsSettingsSet) {
				Adaptor adaptor = connectRemoteAdaptor(settings.getHost(), settings.getPort(),
						settings.getBindingName());

				if (adaptor != null) {
					try {
						adaptor.setManager(this.manager);
					} catch (RemoteException e) {
						this.LOGGER.severe(e.getMessage());
					}
					addAdaptor(adaptor);
				} 
			} 

		} finally {
			this.lock.unlock();
		}

	}

	/**
	 * returns remote adaptor via rmi
	 * 
	 * @param host
	 * @param port
	 * @param bindingName
	 * @return
	 */
	public Adaptor connectRemoteAdaptor(final String host, final int port, final String bindingName) {
		Adaptor adaptor = null;
		try {
			adaptor = (Adaptor) BeanUtils
					.getObject(new ObjectCallProperties(Constants.CALL_TYPE.RMI, host, port, bindingName));
		} catch (Exception e) {
			e.printStackTrace();
			this.LOGGER.severe(e.getMessage() + "\n" + e.getCause());
		}
		return adaptor;
	}

	@Override
	public boolean addAdaptor(Adaptor adaptor){
		boolean added = false;

		if(adaptor == null){
			return added;
		}

		this.lock.lock();
		try{
			try {
				String ip = adaptor.getSettings().getHost();
				this.adaptors.put(ip, adaptor);
				this.activeAdaptors.add(ip);
				LOGGER.info("ADDED ADAPTOR: "+ip);
				added = true;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} finally {
			this.lock.unlock();
		}

		return added;
	}


	@Override
	public Adaptor pickAvailableAdaptor() {

		if(Constants.CALL_TYPE.RMI.equals(this.callType)){
			this.activeAdaptorsTEMP = new HashSet<>(this.activeAdaptors);
			Adaptor adaptor = null;
			this.lock.lock();
			try {
				for (String ip : activeAdaptorsTEMP) {

					adaptor = this.adaptors.get(ip);
					try {
						/* TODO
						 * maximum workers number could be retrieved also from adaptor, and we can simply create a function in adaptor side to check if there are available workers or not!
						 */
						if (adaptor != null && (adaptor.getActualWorkersCount() < adaptor.getSettings().getMaximumWorkersNumber())){
							return adaptor;
						}
					} catch (RemoteException e) {
						onRemoteException(ip);
						LOGGER.info("Remote exception during attempt to get remote worker-adaptor - "+ ip);
						e.printStackTrace();
					}
					adaptor = null;
				}
			} finally {
				this.lock.unlock();
			}

			return adaptor;

		} else if (Constants.CALL_TYPE.LOCAL.equals(callType)) {
			try {
				if (this.localAdaptor != null && (this.localAdaptor.getSettings()
						.getActualWorkersCount() < this.localAdaptor.getSettings().getMaximumWorkersNumber())) {
					return this.localAdaptor;
				}
			} catch (RemoteException e) {
				LOGGER.severe(e.getMessage() + "\n" + e.getCause());
			}
		}

		return null;

	}

	@Override
	public void onRemoteException(String ip) {
		this.activeAdaptors.remove(ip);
		LOGGER.info("REMOVED ADAPTOR: "+ip);
	}

	/**
	 * NO OP
	 */
	@Override public boolean sendToProperAdaptor(JobVO jobVO) {return false;}

}