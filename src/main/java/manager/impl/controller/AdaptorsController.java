package manager.impl.controller;

import adaptor.Adaptor;
import model.JobVO;

/**
 * Controller to handle the communication between manager and adaptor (worker)
 * Mainly to connect to adaptor - and pick available ative adaptor for manager
 * @author rud
 *
 */
public interface AdaptorsController {

	public void loadProperties();
	public void connectAdaptors();
	public void connectRemoteAdaptors();
	public Adaptor connectRemoteAdaptor(final String host, final int port, final String bindingName);
	public void initLocalAdaptor();
	public boolean addAdaptor(Adaptor adaptor);
	/**
	 * return adaptor which is active and has available workers
	 * @return
	 */
	public Adaptor pickAvailableAdaptor();
	public boolean sendToProperAdaptor(JobVO jobVO);
	public void onRemoteException(String ip);

}