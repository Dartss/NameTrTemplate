package common.template.manageradaptor.vo;

import java.io.Serializable;

import lebedev.YandexKeyVO;

public class JobVO implements Serializable
{
    private String originWord;
    private String translatedWord;
    private YandexKeyVO yandexKeyVO;
    private boolean success;
    private int errorCode;

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

    public int getErrorCode()
    {
	return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
	this.errorCode = errorCode;
    }
}