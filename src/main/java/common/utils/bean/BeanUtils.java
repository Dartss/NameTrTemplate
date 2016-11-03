package common.utils.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import jsmarty.core.common.rmi.RmiUtils;
import jsmarty.core.common.utils.Constants.CALL_TYPE;

public class BeanUtils {

	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static Object getObject(ObjectCallProperties objectCallProperties) throws Exception{
		// ADD CHECKS ON NULL REQUIRED FIELDS AND IF ANY FIELD IS MISSING THROW CUSTOMIZED EXCEPTION TODO
		Object object = null;
		if(objectCallProperties.getCalltype() == CALL_TYPE.RMI){
			try {
				object = RmiUtils.getRemoteObject(objectCallProperties.getHost(), objectCallProperties.getPort(), objectCallProperties.getObjectName());
			} catch (MalformedURLException | RemoteException | NotBoundException mex) {
//				mex.printStackTrace();
				logger.info("Warning: could not reach remote object");
				logger.info(mex.getMessage());
			}
		}else if(objectCallProperties.getCalltype() == CALL_TYPE.LOCAL){
			try {
				Class loadedClass = ClassLoader.getSystemClassLoader().loadClass(objectCallProperties.getObjectName());
				try {
					object = (Object) loadedClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException cnfex) {
				cnfex.printStackTrace();
			}
		}
		return object;
	}
	
	/**
	 * Calculate and return the size of an object
	 * @param object
	 * @return
	 */
	public static long getObjectSize(Object object) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.close();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
		return byteArrayOutputStream.size();
	}
	
}