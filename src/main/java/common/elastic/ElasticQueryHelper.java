package common.elastic;

import java.util.List;

/**
 * Uses for building common queries to elastic search server
 * @author Vit
 *
 */

public class ElasticQueryHelper {
	
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
	public static String buildLocation(int id, String uuid, String locationName, String phonetic, String phoneticHex, int coordinateId){
		return "{"+'"'+"id"+'"'+" : "+'"'+id+'"'+","+'"'+"uuid"+'"'+" : "+'"'+uuid+'"'+","+'"'+"name"+'"'+" : "+'"'+locationName+'"'+","+'"'+"phonetic"+'"'+" : "+'"'+phonetic+'"'+","+'"'+"phoneticHex"+'"'+" : "+'"'+phoneticHex+'"'+","+'"'+"levelId"+'"'+" : "+'"'+99+'"'+","+'"'+"polygonId"+'"'+" : "+coordinateId+"}";
	}

	/**
	 * Used In BitService to persist the checked and verified locations;
	 * @param id
	 * @param coordinate
	 * @return
	 */
	public static String buildGeoPoint(int id, String coordinate){
		return "{"+'"'+"id"+'"'+" : "+'"'+id+'"'+","+'"'+"location"+'"'+" : {"+'"'+"type"+'"'+" : "+'"'+"point"+'"'+","+'"'+"coordinates"+'"'+" : "+coordinate+"}}";
	}
	
	/**
	 * search all records with that match input sdo_uuid field
	 * @param from
	 * @param size
	 * @param uuid
	 * @return
	 */
	public static String buildSearchByUuid(int from, int size, String uuid){
		return "{" + 
					"\"from\" : " + from + "," + 
					"\"size\" : " + size + "," +
					"\"query\" : {" +
						"\"match\" : {\"sdo_uuid\" : \"" + uuid + "\"}" +
					"}" +
				"}";
	}
	
	
	/**
	 * Search all records by matching at least one uuid from uuids list
	 * sort by timestamp in asc order
	 * uses by GraphGarbageCollector
	 * @param from
	 * @param size
	 * @param uuids
	 * @return
	 */
	public static String buildSearchByUuids(long from, int size, List<String> uuids, String sortOrder){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i < uuids.size(); i++){
			sb.append("{\"term\" : { \"sdo_uuid\" : \""+ uuids.get(i) +"\" }}");
			
			if(i < uuids.size() - 1){
				sb.append(",");
			}
		}
		
		return "{" +
					"\"from\" : " + from +"," +
					"\"size\" : " + size +"," +
					"\"query\" : { " +
						"\"bool\" : {" +
							"\"should\" : [" +
		                        	sb.toString() +
		                      "]" +
		                  "}" +
		            "}," + 
		            "\"sort\" : [" + 
		            	"{ \"timestamp\" : {\"order\" : \"" + sortOrder +"\"}}" + 

		            "]" +
		        "}";       
	}
	
	/**
	 * Search all records by matching at least one uuid from uuids list
	 * uses by GraphGarbageCollector
	 * @param from
	 * @param size
	 * @param uuids
	 * @return
	 */
	public static String buildSearchByUuids(long from, int size, List<String> uuids){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i < uuids.size(); i++){
			sb.append("{\"term\" : { \"sdo_uuid\" : \""+ uuids.get(i) +"\" }}");
			
			if(i < uuids.size() - 1){
				sb.append(",");
			}
		}
		
		return "{" +
					"\"from\" : " + from +"," +
					"\"size\" : " + size +"," +
					"\"query\" : { " +
						"\"bool\" : {" +
							"\"should\" : [" +
		                        	sb.toString() +
		                      "]" +
		                  "}" +
		            "}" + 
		        "}";       
	}
	
	/**
	 * search sdo that has flags.saveInElasticTimeMS > savedAfter
	 * Uses by statsmanager
	 * @param size
	 * @param savedAfter
	 * @param order
	 * @return
	 */
	public static String buildSearchSdoQuery(int size, long savedAfter, String order){
		return "{\"size\": " + size +",\"query\": {\"range\": { "
				+ "\"flags.saveInElasticTimeMS\": "
				+ "{\"gt\": " + savedAfter + "}}},"
				+ "\"sort\": [{\"flags.saveInElasticTimeMS\": {\"order\": \"" + order +"\"}}]}";
	}
	
	/**
	 * Search jobs that has next_update time <= updateTime
	 * and should update == shouldUpdate
	 * Uses by FacebookLinks Queuer
	 * @param size
	 * @param lte
	 * @param shouldUpdate
	 * @return
	 */
	public static String buildSearchJobsQuery(int size, long updateTime, boolean shouldUpdate){
		return	"{\"size\" : " + size +"," 
				+ "\"query\": {\"bool\": {\"must\": [{\"range\": {\"next_update\": {\"lte\": " + updateTime + "}}},"
				+ "{\"term\": {\"should_update\": " + shouldUpdate +"}}]}}}";
	}
	
	/**
	 * search jobs by sdo_uuid
	 * @param size
	 * @param uuid
	 * @return
	 */
	public static String buildSearchJobsBySdoUuid(int size, String uuid){
		return "{\"size\" : " + size + ", \"query\" : {\"match\" : {\"sdo_uuid\" : \"" + uuid +"\"}}}";
	}
	
	/**
	 * Search stats with sort order
	 * @param sdoUuid
	 * @return
	 */
	public static String buildSearchStatsBySdoUuid(int size, String sdoUuid, String order){
		return 	"{"   
				+	"\"size\" : " + size + ","
				+	"\"sort\" : ["
				+	          "{ \"timestamp\" : {\"order\" : \"" + order + "\"}}"
				+	          "],"
				+	"\"query\" : {"
				+		"\"match\": {"
				+			"\"sdo_uuid\": \""+ sdoUuid + "\""
				+		"}"
				+ 	"}"
				+ "}";
	}
	
	/**
	 * Search jobs with next_update and should_update filter
	 * @param from
	 * @param size
	 * @param untilTime
	 * @param shouldUpdate
	 * @return
	 */
	public static String buildSearchJobsQuery(long from, int size, long untilTime, boolean shouldUpdate) {
		return "{ \"from\" : " + from +", \"size\" : " + size + "," 
				+ "\"query\": {\"bool\": {\"must\": [{\"range\": {\"next_update\": {\"lte\": " + untilTime +"}}},"
				+ "{\"match\" : {\"should_update\": " + shouldUpdate + "}}]}}}";
	}
}
