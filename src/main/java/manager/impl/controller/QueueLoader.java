package manager.impl.controller;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueueLoader
{
    private Jedis jedis;
    private final String QUEUE_NAME;
    private final String FILE_PATH;

    public QueueLoader(Jedis jedis, String QUEUE_NAME, String FILE_PATH) {
    	this.jedis = jedis;
	this.QUEUE_NAME = QUEUE_NAME;
	this.FILE_PATH = FILE_PATH;
    }

    final static Logger logger = Logger.getLogger(QueueLoader.class);

    public List<String> getFromFile(String path)
    {
	List<String> wordsFromFile = new ArrayList<>();
	try (BufferedReader in = new BufferedReader(new FileReader(path)))
	{
	    String init;
	    while ((init = in.readLine()) != null)
	    {
//		this.jedis.sadd(QUEUE_NAME, Arrays.asList(init.split(" ")));
	    }
	} catch (IOException e1)
	{
	    e1.printStackTrace();
	}
	return wordsFromFile;
    }
}