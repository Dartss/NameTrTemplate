package common.runnable;

import java.util.logging.Logger;

/**
 * Apparently this clas is created by PAtrick and inherited by several classes so they become "Runnables"
 * The use of this class should be revised.
 * @author rud
 *
 */
public class SM_WorkerThread implements Runnable {

	private boolean terminate;

	public SM_WorkerThread() {
		terminate = false;
	}

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Override
	public void run() {
		while (!terminate) {

			System.out.println("thread "+Thread.currentThread().getId());

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("thread "+Thread.currentThread().getId()+": is dying");
	}

	public void terminate() {
		terminate = true;
	}

}