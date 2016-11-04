package model;

import java.io.Serializable;

public class YandexKeyVO implements Serializable
{
    private final String host;
    private final String key;
    private int dailyUsages;
    private int monthlyUsages;

    public YandexKeyVO(String host, String key, int dailyUsages, int monthlyUsages) {
	this.host = host;
	this.key = key;
	this.dailyUsages = dailyUsages;
	this.monthlyUsages = monthlyUsages;
    }

    public YandexKeyVO(String host, String key) {
	this(host, key, 0, 0);
    }

    public synchronized String getHost()
    {
	return host;
    }

    public synchronized String getKey()
    {
	return key;
    }

    public int getDailyUsages()
    {
	return dailyUsages;
    }

    public void setDailyUsages(int dailyUsages)
    {
	this.dailyUsages = dailyUsages;
    }

    public int getMonthlyUsages()
    {
	return monthlyUsages;
    }

    public void setMonthlyUsages(int monthlyUsages)
    {
	this.monthlyUsages = monthlyUsages;
    }

    public synchronized void incrementUsages(int usages)
    {
	this.dailyUsages += usages;
	this.monthlyUsages += usages;
    }

}
