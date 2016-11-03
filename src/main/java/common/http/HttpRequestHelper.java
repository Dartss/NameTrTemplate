package common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

/**
 * Http client helper
 * based on Apache Http client
 * 
 * @author yev
 *
 */
public class HttpRequestHelper
{

	private static PoolingHttpClientConnectionManager httpPool;
	private static CloseableHttpClient httpClient;
	private HttpConfig httpConfig;

	private static final String responseErrorMsg = HttpConfig.errors.get(404);
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public HttpRequestHelper(HttpConfig httpConfig) {
		this.httpConfig = httpConfig;
		int maxConnections = httpConfig.getMaximumConnectionsCount();
		int maxPerRoute = httpConfig.getMaximumPerRoute();
		initHttpPool(maxConnections, maxPerRoute);
	}

	/**
	 * initializing PoolingHttpClientConnectionManager;
	 * 
	 * setMaxTotal
	 * Set the maximum number of total open connections.
	 * 
	 * setDefaultMaxPerRoute
	 * set the maximum number of concurrent connections per route,
	 * which is 2 by default.
	 * 
	 */
	private static synchronized void initHttpPool(int maxConnections, int maxPerRoute)
	{
		LOGGER.info("Creating Http pool - setting maximum total: " + maxConnections + " - setting maximum per route: "
				+ maxPerRoute);
		httpPool = new PoolingHttpClientConnectionManager();
		httpPool.setMaxTotal(maxConnections);
		httpPool.setDefaultMaxPerRoute(maxPerRoute);

		httpClient = HttpClients.custom().setConnectionManager(httpPool).setConnectionManagerShared(true).build();
	}

	public static synchronized void shutdown() throws IOException
	{
		LOGGER.info("HTTPRequestHelper - clsosing http client and http pool");
		httpClient.close();
		httpPool.shutdown();
		httpPool.close();
		LOGGER.info("HTTPRequestHelper - shutdown http client and http pool Done");
	}

	/**
	 * Execute an http get request
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResponse get(String url, Map<String, String> headerParameters, Map<String, String> urlParameters,
			Map<String, String> authParameters) throws Exception
	{
		HttpGet httpGet = null;

		if (urlParameters != null && !urlParameters.isEmpty())
		{
			StringBuilder link = new StringBuilder(url + "?");
			for (String key : urlParameters.keySet())
			{
				link.append(key + "=" + urlParameters.get(key) + "&");
			}
			link.deleteCharAt(link.length() - 1);

			httpGet = new HttpGet(link.toString());
		} else
		{
			httpGet = new HttpGet(url);
		}

		addDefaultHeaders(httpGet);
		if (headerParameters != null && !headerParameters.isEmpty())
		{
			addHeader(httpGet, headerParameters);
		}

		if (authParameters != null && !authParameters.isEmpty())
		{
			signOAuth(authParameters, httpGet);
		}

		return execute(httpGet);
	}

	/**
	 * executes post http call
	 * 
	 * @param urlParameters
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public HttpResponse post(String url, Map<String, String> headerParameters, Map<String, String> urlParameters,
			Map<String, String> authParameters) throws Exception
	{

		HttpPost httpPost = new HttpPost(url);

		addDefaultHeaders(httpPost);
		if (headerParameters != null && !headerParameters.isEmpty())
		{
			addHeader(httpPost, headerParameters);
		}

		if (urlParameters != null && !urlParameters.isEmpty())
		{
			List<NameValuePair> nameValuePairs = toNameValuePairList(urlParameters);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		}

		if (authParameters != null && !authParameters.isEmpty())
		{
			signOAuth(authParameters, httpPost);
		}

		return execute(httpPost);
	}

	private HttpResponse execute(HttpRequestBase request) throws Exception
	{
		HttpResponse httpResponse = new HttpResponse();
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(request);

		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		InputStream inputStream = closeableHttpResponse.getEntity().getContent();
		String responseMessage = closeableHttpResponse.getStatusLine().getReasonPhrase();

		String body = parseResponseResult(closeableHttpResponse);

		httpResponse.setStatusCode(statusCode);
		httpResponse.setInputStream(inputStream);
		httpResponse.setMessage(responseMessage);
		httpResponse.setBody(body);

		return httpResponse;
	}

	public HttpResponse getInputStream(String url, Map<String, String> headerParameters,
			Map<String, String> urlParameters, Map<String, String> authParameters) throws Exception
	{
		HttpGet httpGet = null;

		if (urlParameters != null && !urlParameters.isEmpty())
		{
			StringBuilder link = new StringBuilder(url + "?");
			for (String key : urlParameters.keySet())
			{
				link.append(key + "=" + urlParameters.get(key) + "&");
			}
			link.deleteCharAt(link.length() - 1);

			httpGet = new HttpGet(link.toString());
		} else
		{
			httpGet = new HttpGet(url);
		}

		addDefaultHeaders(httpGet);
		if (headerParameters != null && !headerParameters.isEmpty())
		{
			addHeader(httpGet, headerParameters);
		}

		if (authParameters != null && !authParameters.isEmpty())
		{
			signOAuth(authParameters, httpGet);
		}

		return getInputStream(httpGet);
	}

	/**
	 * NOTICE
	 * The input stream should be closed by the caller and not here !
	 * 
	 * @param request
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private HttpResponse getInputStream(HttpRequestBase request) throws ClientProtocolException, IOException
	{
		HttpResponse httpResponse = new HttpResponse();

		// apache response
		org.apache.http.HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();

		InputStream inputStream = null;
		if (entity != null)
		{
			inputStream = entity.getContent();
		}

		int statusCode = response.getStatusLine().getStatusCode();

		httpResponse.setInputStream(inputStream);
		httpResponse.setStatusCode(statusCode);

		return httpResponse;
	}

	private void addDefaultHeaders(HttpRequestBase request)
	{
		/*
	Map<String, String> defHeader = new HashMap();
//	defHeader.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:49.0) Gecko/20100101 Firefox/49.0");
	defHeader.put("User-Agent", "Java");
	addHeader(request, defHeader);
		 */
	}

	/**
	 * Add header to request.
	 * 
	 * @param request
	 * @param map
	 */
	private void addHeader(HttpRequestBase request, Map<String, String> map)
	{
		for (Map.Entry<String, String> entry : map.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();

			request.addHeader(key, value);
			request.getAllHeaders();
		}
	}

	private void signOAuth(Map<String, String> parameters, HttpRequestBase request) throws OAuthMessageSignerException,
	OAuthExpectationFailedException, OAuthCommunicationException, OAuthNotAuthorizedException, IOException
	{
		String consumerKey = parameters.get(HttpConstants.OAUTH_CONSUMER_KEY);
		String consumerSecret = parameters.get(HttpConstants.OAUTH_CONSUMER_SECRET);
		String accesToken = parameters.get(HttpConstants.OAUTH_ACCESS_TOKEN);
		String tokenSecret = parameters.get(HttpConstants.OAUTH_TOKEN_SECRET);

		if (consumerKey != null & consumerSecret != null)
		{
			signOAuth(consumerKey, consumerSecret, accesToken, tokenSecret, request);
		}
	}

	private void signOAuth(String consumerKey, String consumerSecret, String accesToken, String tokenSecret,
			HttpRequestBase request) throws OAuthMessageSignerException, OAuthExpectationFailedException,
	OAuthCommunicationException, OAuthNotAuthorizedException, IOException
	{
		CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		consumer.setTokenWithSecret(accesToken, tokenSecret);
		consumer.sign(request);
	}

	private String parseResponseResult(CloseableHttpResponse httpResponse) throws IOException
	{
		String response;
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode == 404)
		{
			// Check if response contains 404 code. If yes, throw exception.
			throw new ConnectException(responseErrorMsg);
		} else
		{
			response = extractResponseResult(httpResponse);
		}
		return response;
	}

	private String extractResponseResult(CloseableHttpResponse httpResponse) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
		String inputLine = "";
		StringBuffer result = new StringBuffer();

		while ((inputLine = reader.readLine()) != null)
		{
			result.append(inputLine);
		}

		reader.close();
		httpResponse.close();
		String response = result.toString();
		return response;
	}

	private static List<NameValuePair> toNameValuePairList(Map<String, String> parametersString)
	{
		List<NameValuePair> nameValuePairList = new ArrayList<>();
		for (String key : parametersString.keySet())
		{
			nameValuePairList.add(new NameValuePairObj(key, parametersString.get(key)));
		}
		return nameValuePairList;
	}
}