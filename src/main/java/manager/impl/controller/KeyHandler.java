package manager.impl.controller;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import model.YandexKeyVO;
import common.properties.template.NamesTrProperties;

public class KeyHandler
{
    private static final int MILLISECONDS_IN_25_HOURS = 90000000;
    private int yandexDailyLimit;
    private int yandexMonthlyLimit;

    private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private List<YandexKeyVO> keyList;

    public KeyHandler() {

	loadProperties();
	initResetTask();
    }

    public YandexKeyVO getAvailableKey(String host, int charactersAmount)
    {
	if (host == null)
	{
	    return null;
	}

	for (YandexKeyVO currentKey : keyList)
	{
	    if (currentKey.getHost().equals(host) && (currentKey.getDailyUsages() + charactersAmount) < yandexDailyLimit
		    && (currentKey.getMonthlyUsages() + charactersAmount) < yandexMonthlyLimit)
	    {
		LOGGER.info("Giving new key with usages " + currentKey.getKey() + " count = " + currentKey.getDailyUsages());
		currentKey.incrementUsages(charactersAmount);
		return currentKey;
	    }
	}
	System.out.println("No available key found");
	return null;
    }

    private void initResetTask()
    {
	ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	scheduledExecutorService.scheduleAtFixedRate(new Runnable()
	{
	    public void run()
	    {
		resetDailyUsages();
	    }
	}, getTimeTillMidnight(), MILLISECONDS_IN_25_HOURS, TimeUnit.MILLISECONDS);

    }

    public void resetMonthlyUsages()
    {
	for (YandexKeyVO currentKey : keyList)
	{
	    currentKey.setMonthlyUsages(0);
	}
    }

    public void resetDailyUsages()
    {
	for (YandexKeyVO currentKey : keyList)
	{
	    currentKey.setDailyUsages(0);
	}
    }

    public void blockKey(YandexKeyVO yandexKeyVo)
    {
	LOGGER.info("Blocking key : " + yandexKeyVo.getKey());
	for (YandexKeyVO currentKey : keyList)
	{
	    if (currentKey.getKey().equals(yandexKeyVo.getKey()))
	    {
		currentKey.setDailyUsages(yandexDailyLimit);

	    }
	}
    }

    private long getTimeTillMidnight()
    {
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DAY_OF_MONTH, 1);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	c.set(Calendar.MILLISECOND, 0);
	long howMany = (c.getTimeInMillis() - System.currentTimeMillis());
	return howMany;
    }

    private void loadProperties()
    {
	new NamesTrProperties();
	this.keyList = NamesTrProperties.getYandexKeys();
	this.yandexDailyLimit = NamesTrProperties.getYandexDailyLimit();
	this.yandexMonthlyLimit = NamesTrProperties.getYandexMonthlyLimit();
    }

}
