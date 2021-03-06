package adaptor.worker.impl;

import adaptor.impl.AdaptorImpl;
import com.google.gson.JsonParser;
import common.http.HttpRequestHandler;
import common.http.HttpResponse;
import model.JobVO;
import model.YandexKeyVO;
import org.json.JSONObject;

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
//    private static final MediaType REQUEST_MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded");
    private AdaptorImpl adaptor;
    private JobVO jobVO;
    private ExecutorService executorService;
    private CompletionService<JobVO> completionService;
    private static final String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private YandexKeyVO yandexKeyVO;
    private JsonParser jsonParser;
    private HttpRequestHandler httpRequestHandler;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public WorkerImpl(AdaptorImpl adaptor, JobVO jobVO,HttpRequestHandler requestHandler) {
	this.httpRequestHandler = requestHandler;
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
	int responseCode = 0;
	try
	{
	    String url = yandexUrl + yandexKeyVO.getKey();
	    LOGGER.info("Worker makes call to Yandex: " + url);

	    Map<String, String> parameters = new HashMap<>();
	    parameters.put("key", yandexKeyVO.getKey());
	    parameters.put("lang", "eng");
	    parameters.put("lang", "ara");
	    parameters.put("text", toTranslate);
	    HttpResponse response = httpRequestHandler.executePost(yandexUrl, null, parameters);
	    JSONObject jsonResponse = new JSONObject(response.getBody());
	    responseCode = jsonResponse.getInt("code");

	    LOGGER.info("Status code is:" + responseCode);

	    if (responseCode == 200)
	    {

		translated = jsonResponse.get("text").toString();
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
	this.jobVO.setStatusCode(responseCode);
	this.adaptor.returnJob(jobVO);
    }
}