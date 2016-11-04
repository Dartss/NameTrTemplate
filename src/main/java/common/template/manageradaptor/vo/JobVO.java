package common.template.manageradaptor.vo;

import java.io.Serializable;

import lebedev.YandexKeyVO;

public class JobVO implements Serializable
{
    private String originWord;
    private String translatedWord;
    private YandexKeyVO yandexKeyVO;
    private boolean success;
    private int statusCode;

    public JobVO()
    {
    }

    public String getOriginWord()
    {
	return originWord;
    }

    public void setOriginWord(String originWord)
    {
	this.originWord = originWord;
    }

    public String getTranslatedWord()
    {
	return translatedWord;
    }

    public void setTranslatedWord(String translatedWord)
    {
	this.translatedWord = translatedWord;
    }

    public YandexKeyVO getYandexKeyVO()
    {
	return yandexKeyVO;
    }

    public void setApiKey(YandexKeyVO yandexKeyVO)
    {
	this.yandexKeyVO = yandexKeyVO;
    }

    public boolean isSuccess()
    {
	return success;
    }

    public void setSuccess(boolean success)
    {
	this.success = success;
    }

    public int getStatusCode()
    {
	return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
	this.statusCode = statusCode;
    }
}