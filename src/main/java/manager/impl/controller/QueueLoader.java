package manager.impl.controller;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueueLoader implements Runnable
{
    private Jedis jedis;
    private final String QUEUE_NAME;
    private final String FILE_PATH;

    public QueueLoader(Jedis jedis, String QUEUE_NAME, String FILE_PATH)
    {
	this.jedis = jedis;
	this.QUEUE_NAME = QUEUE_NAME;
	this.FILE_PATH = FILE_PATH;
    }

    final static Logger logger = Logger.getLogger(QueueLoader.class);

    private void pullFromFileToQueue()
    {
	try (BufferedReader in = new BufferedReader(new FileReader(FILE_PATH)))
	{
	    String init;
	    while ((init = in.readLine()) != null)
	    {
		List<String> splittedName = Arrays.asList(init.split(" "));
		for (String word : splittedName)
		{
		    this.jedis.sadd(QUEUE_NAME, word);
		}
	    }
	} catch (IOException e1)
	{
	    e1.printStackTrace();
	}
    }

    @Override public void run()
    {
	pullFromFileToQueue();
    }
}