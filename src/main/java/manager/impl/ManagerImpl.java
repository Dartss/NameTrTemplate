package manager.impl;

import adaptor.Adaptor;
import common.jdbc.JdbcHandler;
import common.properties.template.NamesTrProperties;
import common.rmi.RmiUtils;
import common.template.manageradaptor.vo.JobVO;
import common.utils.Constants;
import lebedev.KeyHandler;
import lebedev.YandexKeyVO;
import manager.Manager;
import manager.impl.controller.AdaptorsController;
import manager.impl.controller.AdaptorsControllerImpl;
import manager.impl.controller.QueueControllerImpl;
import manager.impl.controller.QueueLoader;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ManagerImpl extends UnicastRemoteObject implements Manager
{

    private transient QueueControllerImpl queueController;
    private transient AdaptorsController adaptorsController;

    private Constants.CALL_TYPE callType;
    private String managerHost;
    private int managerPort;
    private String managerBindingName;

    private JdbcHandler jdbcHandler;
    private QueueLoader queueLoader;

    private String inputQueueName;
    private String inputFilePath;

    private KeyHandler keyHandler;

    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final String SQL_QUEUERY = "INSERT INTO names_translation(eng_word, ara_word) VALUES";

    public ManagerImpl() throws RemoteException {
	init();
    }

    private void init() throws RemoteException
    {
	loadProperties();

	this.adaptorsController = new AdaptorsControllerImpl(this, callType);
	this.adaptorsController.connectAdaptors();

	this.jdbcHandler = new JdbcHandler(NamesTrProperties.getJdbcUrl(),
			NamesTrProperties.getJdbcDriver(),
			NamesTrProperties.getJdbcUser(),
			NamesTrProperties.getJdbcPassword());

	this.keyHandler = new KeyHandler();

	this.queueLoader = new QueueLoader(inputQueueName, inputFilePath);
	new Thread(queueLoader).start();

	// export manager to RMI
	if (this.callType.equals(Constants.CALL_TYPE.RMI))
	{
	    this.exportToRmi(this.managerHost, this.managerPort, this.managerBindingName);
	}
	this.queueController = new QueueControllerImpl(this);
	new Thread(this.queueController).start();
    }

    /**
     * load NamesTrProperties
     */
    private void loadProperties()
    {
	new NamesTrProperties();
	this.callType = NamesTrProperties.getCallType();
	this.managerHost = NamesTrProperties.getManagerHost();
	this.managerPort = NamesTrProperties.getManagerPort();
	this.managerBindingName = NamesTrProperties.getManagerBindingName();
	this.inputFilePath = NamesTrProperties.getInputFilePath();
	this.inputQueueName = NamesTrProperties.getInputQueueName();
    }

    @Override public boolean executeJob(JobVO jobVO) throws RemoteException
    {
	boolean jobAccepted = false;
	// search for available adaptor
	Adaptor adaptor = pickAvailableAdaptor();

	// send job to adaptor
	if (adaptor != null)
	{
	    YandexKeyVO yandexKeyVO = keyHandler.getAvailableKey(adaptor.getSettings().getHost(), jobVO.getOriginWord().length());
	    if (yandexKeyVO != null)
	    {
		jobVO.setApiKey(yandexKeyVO);
		try
		{
		    adaptor.executeJob(jobVO);
		    jobAccepted = true;
		} catch (RemoteException e)
		{
		    this.queueController.requeueJob(jobVO);
		    LOGGER.severe(e.getMessage());
		}
	    }
	} else
	{
	    /*
	     * "false" should be return only if adaptor == null.
	     * all other exceptions should be handled inside the manager
	     */
	    jobAccepted = false;
	}

	return jobAccepted;
    }

    @Override public void onJobExecuted(JobVO jobVO) throws RemoteException
    {
	// notify adaptor available
	notifyAdaptorAvailable();

	boolean success = jobVO.isSuccess();

	if (success)
	{
	    // push job to mysql
	    try
	    {
		jdbcHandler.insert(SQL_QUEUERY + "(" + jobVO.getOriginWord()+ ","+ jobVO.getTranslatedWord() +");");
	    } catch (SQLException e)
	    {
		e.printStackTrace();
	    }
	} else
	{
	    // requeue job
	    this.queueController.requeueJob(jobVO);
	}
    }

    /**
     * Picks the available adaptor (which has available worker threads)
     * If there are no any available adaptors, then try to reconnect remote
     * adaptors
     * and check for available adaptor one more time.
     *
     * @return
     */
    @Override public Adaptor pickAvailableAdaptor() throws RemoteException
    {
	Adaptor adaptor = this.adaptorsController.pickAvailableAdaptor();
	return adaptor;
    }

    /**
     * notifies controller about available adaptor's worker threads
     */
    @Override public void notifyAdaptorAvailable() throws RemoteException
    {
	//this.logger.info("Adaptor available!");
	synchronized (this.queueController)
	{
	    this.queueController.notify();
	}
    }

    /**
     * start manager's rmi server
     *
     * @param host
     * @param port
     * @param bindingName
     */
    @Override public void exportToRmi(final String host, final int port, final String bindingName) throws RemoteException
    {
	try
	{
	    RmiUtils.setServeHostnameConfiguration(host);
	    RmiUtils.bindObject(host, port, bindingName, this);
	} catch (Exception e)
	{
	    this.LOGGER.severe("Can't launch Manager RMI server " + e.getStackTrace().toString());
	}
    }

    @Override public void addAdaptor(Adaptor adaptor)
    {
	this.adaptorsController.addAdaptor(adaptor);
    }

}