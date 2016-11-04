package adaptor.worker.impl;

import adaptor.impl.AdaptorImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.*;
import common.template.manageradaptor.vo.JobVO;
import lebedev.YandexKeyVO;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Worker should get job from adaptor as param in constructor
 * And it should process the job upon each component's logic
 */
public class WorkerImpl implements Runnable
{
    private static final MediaType REQUEST_MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded");
    private AdaptorImpl adaptor;
    private JobVO jobVO;
    private ExecutorService executorService;
    private CompletionService<JobVO> completionService;
    private static final String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?lang=ar&key=";
    private YandexKeyVO yandexKeyVO;
    private JsonParser jsonParser;
    private OkHttpClient client;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public WorkerImpl(AdaptorImpl adaptor, JobVO jobVO) {
	this.client = new OkHttpClient();
	this.jsonParser = new JsonParser();
	this.adaptor = adaptor;
	this.jobVO = jobVO;
	this.executorService = Executors.newFixedThreadPool(1);
	this.completionService = new ExecutorCompletionService<>(executorService);
	this.yandexKeyVO = jobVO.getYandexKeyVO();
    }

    @Override
    public void run()
    {
	String toTranslate = jobVO.getOriginWord();
	String translated;

	Map<String, String> params = new HashMap<>();
	params.put("text=", toTranslate);
	int statusCode = 0;
	try
	{
	    String url = yandexUrl + yandexKeyVO.getKey();
	    LOGGER.info("Worker makes call to Yandex: " + url);
	    String translateRequest = "text=" + toTranslate;
	    RequestBody body = RequestBody.create(REQUEST_MEDIA_TYPE_JSON, translateRequest);
	    Request request = new Request.Builder().url(yandexUrl + yandexKeyVO.getKey()).post(body).build();
	    Response response = client.newCall(request).execute();
	    
	    JsonObject sourceObject = jsonParser.parse(response.body().string()).getAsJsonObject();
	    statusCode = response.code();
	    LOGGER.info("Status code is:" + statusCode);

	    if (statusCode == 200)
	    {
		translated = sourceObject.getAsJsonArray("text").get(0).getAsString();
		LOGGER.info("Word " + toTranslate + " translated to " + translated);
		this.jobVO.setTranslatedWord(translated);
		this.jobVO.setSuccess(Boolean.TRUE);
	    } else
	    {
		this.jobVO.setSuccess(Boolean.FALSE);
	    }
	} catch (Exception e)
	{
	    this.jobVO.setSuccess(Boolean.FALSE);
	    e.printStackTrace();
	}
	this.jobVO.setStatusCode(statusCode);
	this.adaptor.returnJob(jobVO);
    }
}