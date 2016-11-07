package manager.impl.controller;

import common.jdbc.JdbcHandler;
import common.queuer.QueuerPoolHandler;
import common.queuer.QueuerPoolHandlerImpl;
import org.apache.log4j.Logger;
import redis.clients.jedis.Pipeline;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueueLoader
{
    private QueuerPoolHandler queuerPoolHandler;
    private final int SQL_PUSHERS_THREADS_COUNT;
    private final String QUEUE_NAME;
    private final String FILE_PATH;

    private final String QUEURY_FOR_NOT_TRANSLATED = "SELECT * FROM names_translation WHERE ara_word IS NULL;";
    private final String SQL_QUEUERY = "INSERT INTO names_translation(eng_word) VALUES(?)";

    private JdbcHandler jdbcHandler;
    final static Logger logger = Logger.getLogger(QueueLoader.class);

    public QueueLoader(String QUEUE_NAME, String FILE_PATH, String host, int port, JdbcHandler jdbcHandler, int pushersCount)
    {
        this.SQL_PUSHERS_THREADS_COUNT = pushersCount;
	this.QUEUE_NAME = QUEUE_NAME;
	this.jdbcHandler = jdbcHandler;
	this.FILE_PATH = FILE_PATH;
	this.queuerPoolHandler = new QueuerPoolHandlerImpl(host, port);
    }

    /**
     *
     */

    public void prepareQueue()
    {
	try
	{
	    logger.info("Trying to load from file with path : " + FILE_PATH);

	    pullFromFileToQueue();

	    pushAllFromQueueToMySql();

	    fillUpQueueFromMySql();
	} catch (FileNotFoundException e)
	{
	    try
	    {
		logger.info("File not found, initializing data from MYSQL");

		fillUpQueueFromMySql();
	    } catch (SQLException e1)
	    {
		logger.error("Error while filling up queue from MySql " + e1.getMessage());
	    }

	} catch (IOException e)
	{
	    logger.info("No sources found ------------!!!!!");
	    e.printStackTrace();
	} catch (InterruptedException | SQLException e)
	{
	    e.printStackTrace();
	}
    }

    private void pullFromFileToQueue() throws IOException
    {
        Pipeline pipeline = queuerPoolHandler.getJedisPipeline();
	BufferedReader in = new BufferedReader(new FileReader(FILE_PATH));
	String init;

	while ((init = in.readLine()) != null)
	{
	    List<String> splittedName = Arrays.asList(init.split(" +"));
	    for (String word : splittedName)
	    {
		pipeline.sadd(QUEUE_NAME, word);
	    }
	}

	pipeline.sync();
    }

    private void fillUpQueueFromMySql() throws SQLException
    {
	Pipeline pipeline = queuerPoolHandler.getJedisPipeline();

	ResultSet rs = null;
	rs = jdbcHandler.queryForStreamResult(QUEURY_FOR_NOT_TRANSLATED);
	while (rs.next())
	{
	    byte[] word = rs.getString("eng_word").getBytes();
	    pipeline.sadd("names".getBytes(), word);
	}
	pipeline.sync();
    }

    private void pushAllFromQueueToMySql() throws InterruptedException
    {
	List<Thread> sqlPushers = new ArrayList<>();
	for (int i = 0; i < SQL_PUSHERS_THREADS_COUNT; i ++) {
	    Thread pusher = new Thread(new Runnable()
	    {
		@Override public void run()
		{
		    String word;
		    while ((word = queuerPoolHandler.spop("names")) != null) {
			try
			{
			    jdbcHandler.insert(SQL_QUEUERY, Arrays.asList(word));
			} catch (SQLException e)
			{
			    e.printStackTrace();
			}
		    }
		}
	    });
	    sqlPushers.add(pusher);
	    pusher.start();
	}
	for (Thread pusher : sqlPushers) {
	    pusher.join();
	}
    }
}