package manager.impl.controller;

import common.jdbc.JdbcHandler;
import common.queuer.QueuerPoolHandler;
import common.queuer.QueuerPoolHandlerImpl;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
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
    private final String QUEUE_NAME;
    private final String FILE_PATH;
    private final String QUEURY_FOR_NOT_TRANSLATED = "SELECT * FROM names_translation WHERE ara_word IS NULL;";
    private final String SQL_QUEUERY = "INSERT INTO names_translation(eng_word) VALUES(?)";
    private JdbcHandler jdbcHandler;
    final static Logger logger = Logger.getLogger(QueueLoader.class);

    public QueueLoader(String QUEUE_NAME, String FILE_PATH, String host, int port, JdbcHandler jdbcHandler)
    {
	this.QUEUE_NAME = QUEUE_NAME;
	this.jdbcHandler = jdbcHandler;
	this.FILE_PATH = FILE_PATH;
	this.queuerPoolHandler = new QueuerPoolHandlerImpl(host, port);
    }

    /**
     *
     */

    public void pullFromSourceToQueue()
    {
	try
	{
	    long startTime = System.currentTimeMillis();
	    logger.info("Trying to load from file with path : " + FILE_PATH);
	    List<Thread> sqlPushers = new ArrayList<>();

	    pullFromFileToRedis();

	    for (int i = 0; i < 25; i ++) {
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


	    System.out.println("---------------Time : " + (System.currentTimeMillis() - startTime));
	} catch (FileNotFoundException e)
	{
	    try
	    {
		logger.info("File not found, initializing data from MYSQL");
		Pipeline pipeline = queuerPoolHandler.getJedisPipeline();

		ResultSet rs = null;
		rs = jdbcHandler.queryForStreamResult(QUEURY_FOR_NOT_TRANSLATED);
		while (rs.next())
		{
		    byte[] word = rs.getString("eng_word").getBytes();
		    pipeline.sadd("names".getBytes(), word);
		}
	    	pipeline.sync();
	    } catch (SQLException e1)
	    {
		e1.printStackTrace();
	    }

	} catch (IOException e)
	{
	    logger.info("No sources found ------------!!!!!");
	    e.printStackTrace();
	} catch (InterruptedException e)
	{
	    e.printStackTrace();
	}
    }

    private void pullFromFileToRedis() throws IOException
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
}