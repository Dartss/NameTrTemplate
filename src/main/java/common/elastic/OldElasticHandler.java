package common.elastic;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * This handler contains methods that helps the caller querying elasticsearch
 * TODO Should contain more generic methods; and be used whenever communication with elasticsearch is needed
 *  
 * @author rud
 *
 */
@Deprecated
public class OldElasticHandler {

	/**
	 * This function returns the id of the last inserted item in an elastic index
	 * @param elasticIndexUrl
	 * @return
	 */
	public int lastInsertedId(String elasticIndexUrl){
		int id=0;
		String response = null;
		String query = "{"+'"'+"fields"+'"'+":["+'"'+"id"+'"'+"],"+'"'+"size"+'"'+":1,"+'"'+"query"+'"'+":{"+'"'+"match_all"+'"'+":{}},"+'"'+"sort"+'"'+":[{"+'"'+"id"+'"'+":{"+'"'+"order"+'"'+":"+'"'+"desc"+'"'+"}}]}";
		try {
			response = post(elasticIndexUrl, query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonObject jsonResponse=new JsonObject(response);
		JsonObject result = (JsonObject)jsonResponse.getJsonObject("hits");
		JsonArray hits = (JsonArray) result.getJsonArray("hits");

		JsonObject hit = hits.getJsonObject(0);
		id = new Integer(hit.getString("_id"));

		return id;
	}

	/**
	 * Used In BitService to persist the checked and verified locations;
	 * @param id
	 * @param uuid
	 * @param locationName
	 * @param phonetic
	 * @param phoneticHex
	 * @param coordinateId
	 * @return
	 */
	public String buildPutLocationQuery(int id, String uuid, String locationName, String phonetic, String phoneticHex, int coordinateId){
		return "{"+'"'+"id"+'"'+" : "+'"'+id+'"'+","+'"'+"uuid"+'"'+" : "+'"'+uuid+'"'+","+'"'+"name"+'"'+" : "+'"'+locationName+'"'+","+'"'+"phonetic"+'"'+" : "+'"'+phonetic+'"'+","+'"'+"phoneticHex"+'"'+" : "+'"'+phoneticHex+'"'+","+'"'+"levelId"+'"'+" : "+'"'+99+'"'+","+'"'+"polygonId"+'"'+" : "+coordinateId+"}";
	}

	/**
	 * Used In BitService to persist the checked and verified locations;
	 * @param id
	 * @param coordinate
	 * @return
	 */
	public String buildPutGeoPointQuery(int id, String coordinate){
		return "{"+'"'+"id"+'"'+" : "+'"'+id+'"'+","+'"'+"location"+'"'+" : {"+'"'+"type"+'"'+" : "+'"'+"point"+'"'+","+'"'+"coordinates"+'"'+" : "+coordinate+"}}";
	}

	public boolean persist(String stringUrl, String requestBody) throws Exception, IOException{
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(5L, TimeUnit.HOURS);
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, requestBody);

		Request request = new Request.Builder().url(stringUrl).post(body).addHeader("content-type"
				+ "", "application/json").build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException ioex) {
			throw ioex;
		} catch (Exception ex) {
			throw ex;
		}
		if (response.code() == 201){
			return true;
		}else {
			return false;
		}
	}

	public String post(String url, String queryFilter) throws IOException{
		String response="";
		Long startTime = System.currentTimeMillis();
		URL serverUrl;
		try {
			serverUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");

			BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
			httpRequestBodyWriter.write(queryFilter);
			httpRequestBodyWriter.close();

			// Reading from the HTTP response body
			Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
			while(httpResponseScanner.hasNextLine()) {
				response+=httpResponseScanner.nextLine();
			}
			httpResponseScanner.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} 
		return response;
	}

	public String delete(String url, String queryFilter) throws IOException{
		String response="";
		URL serverUrl;
		try {
			serverUrl = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection)serverUrl.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("DELETE");

			BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
			httpRequestBodyWriter.write(queryFilter);
			httpRequestBodyWriter.close();

			// Reading from the HTTP response body
			Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
			while(httpResponseScanner.hasNextLine()) {
				response+=httpResponseScanner.nextLine();
			}
			httpResponseScanner.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (FileNotFoundException fnfex){
			fnfex.printStackTrace();
		}
		return response;
	}

}