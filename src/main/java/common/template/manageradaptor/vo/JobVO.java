package common.template.manageradaptor.vo;

import java.io.Serializable;

public class JobVO implements Serializable
{
    private String originWord;
    private String translatedWord;
    private String apiKey;
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

    public String getApiKey()
    {
	return apiKey;
    }

    public void setApiKey(String apiKey)
    {
	this.apiKey = apiKey;
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