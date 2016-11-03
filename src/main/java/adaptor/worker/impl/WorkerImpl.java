package adaptor.worker.impl;

import adaptor.impl.AdaptorImpl;
import common.template.manageradaptor.vo.JobVO;

import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Worker should get job from adaptor as param in constructor
 * And it should process the job upon each component's logic
 *
 */
public class WorkerImpl implements Runnable {

	private AdaptorImpl adaptor;
	private JobVO jobVO;

	private ExecutorService executorService;
	private CompletionService<JobVO> completionService;

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public WorkerImpl(AdaptorImpl adaptor, JobVO jobVO) {
		this.adaptor = adaptor;
		this.jobVO = jobVO;
		this.executorService = Executors.newFixedThreadPool(1);
		this.completionService = new ExecutorCompletionService<>(executorService);
	}

	@Override
	public void run() {

	}

}	