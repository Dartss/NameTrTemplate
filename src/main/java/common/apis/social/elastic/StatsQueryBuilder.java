package common.apis.social.elastic;

public class StatsQueryBuilder {

	public static String buildSearchSdoQuery(long from, int size, long savedAfter, long savedBefore, String sortOrder) {
		return "{ " + "\"from\" : " + from + "," + "\"size\":" + size + "," + "\"query\": {" + "\"range\": {"
				+ "\"flags.saveInElasticTimeMS\": {" + "\"gte\": " + savedAfter + "," + "\"lte\": " + savedBefore + "}"
				+ "}" + "}," + "\"sort\": [{" + "\"flags.saveInElasticTimeMS\": " + "{\"order\": \"" + sortOrder + "\"}"
				+ "}]" + "}";
	}
	public static String buildSearchSocialstatJobsQuery(long from, int size, long beforeNextUpdateTimeMillis,
			boolean shouldUpdate, String sortOrder) {
		return "{" + "\"from\" : " + from + "," + "\"size\" : " + size + "," + "\"query\" : {" + "\"bool\" : {"
				+ "\"must\" : [" + "{\"match\" : {\"should_update\" : " + shouldUpdate + " }},"
				+ "{\"range\" : {\"next_update\" : {\"lte\" : " + beforeNextUpdateTimeMillis + "}}}" + "]" + "}" + "},"
				+ "\"sort\" : [{\"next_update\" : {\"order\": \"" + sortOrder + "\"}}]" + "}";
	}
	public static String buildSearchActiveSocialstatJobsQuery(long from, int size, long fromNextUpdateTimeMillis,
			boolean shouldUpdate, String sortOrder) {
		return "{" + "\"from\" : " + from + "," + "\"size\" : " + size + "," + "\"query\" : {" + "\"bool\" : {"
				+ "\"must\" : [" + "{\"match\" : {\"should_update\" : " + shouldUpdate + " }},"
				+ "{\"range\" : {\"next_update\" : {\"lte\" : " + fromNextUpdateTimeMillis + "}}}" + "]" + "}" + "},"
				+ "\"sort\" : [{\"next_update\" : {\"order\": \"" + sortOrder + "\"}}]" + "}";
	}
	public static String buildSearchSocialstatJobQuery(String id) {
		return "{" + "\"size\" : 1 ," + "\"query\" : {" + "\"match\" : {\"sdo_uuid\" : \"" + id + "\"}" + "}" + "}";
	}
	public static String buildSearchSocialstatsQuery(long from, int size, String sdoUuid, String order) {
		return "{" + "\"from\": " + from + " ," + "\"size\": " + size + ", " + "\"query\" : {"
				+ "\"match\" : {\"sdo_uuid\": \"" + sdoUuid + "\"}}, " + "\"sort\" : { "
				+ "\"timestamp\" : { \"order\" : \"" + order + "\"} }" + "}";
	}
	public static String buildSearchStatsBySdoUuid(int size, String sdoUuid, String order){
		return  "{"   
				+   "\"size\" : " + size + ","
				+   "\"sort\" : ["
				+             "{ \"timestamp\" : {\"order\" : \"" + order + "\"}}"
				+             "],"
				+   "\"query\" : {"
				+       "\"match\": {"
				+           "\"sdo_uuid\": \""+ sdoUuid + "\""
				+       "}"
				+   "}"
				+ "}";
	}	
	/**
	 * returns smartystats schema
	 * @return
	 */
	public static String buildSmartystatsMapping(){
		return "{\"mappings\" : {\"tweet\" : {\"_source\" : { \"enabled\" : true },\"properties\" : {\"sdo_uuid\" : { \"type\" : \"string\", \"index\" : \"not_analyzed\" },\"retweets\" : { \"type\" : \"integer\", \"index\" : \"not_analyzed\" },\"favorites\" : { \"type\" : \"integer\", \"index\" : \"not_analyzed\" },\"replies\" : { \"type\" : \"integer\", \"index\" : \"not_analyzed\" },\"timestamp\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" }}},\"facebook_post\" : {\"_source\" : { \"enabled\" : true },\"properties\" : {\"sdo_uuid\" : { \"type\" : \"string\", \"index\" : \"not_analyzed\" },\"shares\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" },\"likes\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" },\"comments\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" },\"timestamp\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" }}},\"url_facebook\" : {\"_source\" : { \"enabled\" : true },\"properties\" : {\"sdo_uuid\" : { \"type\" : \"string\", \"index\" : \"not_analyzed\" },\"shares\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" },\"likes\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" },\"comments\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" },\"timestamp\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" }}},\"url_twitter\" : {\"_source\" : { \"enabled\" : true },\"properties\" : {\"sdo_uuid\" : { \"type\" : \"string\", \"index\" : \"not_analyzed\" },\"shares\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" },\"timestamp\" : { \"type\" : \"long\", \"index\" : \"not_analyzed\" }}}}}";
	}

	/**
	 * returns smartyjobs schema
	 * @return
	 */
	public static String buildSmartyjobsMapping(){
		return "{\"mappings\":{\"tweet\":{\"_source\":{\"enabled\":true},\"properties\":{\"sdo_uuid\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"origin_url\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"origin_id\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"last_update\":{\"type\":\"long\",\"index\":\"not_analyzed\"},\"next_update\":{\"type\":\"long\",\"index\":\"not_analyzed\"},\"should_update\":{\"type\":\"boolean\",\"index\":\"not_analyzed\"},\"is_primary\":{\"type\":\"boolean\",\"index\":\"not_analyzed\"}}},\"facebook_post\":{\"_source\":{\"enabled\":true},\"properties\":{\"sdo_uuid\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"origin_url\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"origin_id\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"last_update\":{\"type\":\"long\",\"index\":\"not_analyzed\"},\"next_update\":{\"type\":\"long\",\"index\":\"not_analyzed\"},\"should_update\":{\"type\":\"boolean\",\"index\":\"not_analyzed\"},\"is_primary\":{\"type\":\"boolean\",\"index\":\"not_analyzed\"}}},\"url_facebook\":{\"_source\":{\"enabled\":true},\"properties\":{\"sdo_uuid\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"origin_url\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"origin_id\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"last_update\":{\"type\":\"long\",\"index\":\"not_analyzed\"},\"next_update\":{\"type\":\"long\",\"index\":\"not_analyzed\"},\"should_update\":{\"type\":\"boolean\",\"index\":\"not_analyzed\"},\"is_primary\":{\"type\":\"boolean\",\"index\":\"not_analyzed\"}}},\"url_twitter\":{\"_source\":{\"enabled\":true},\"properties\":{\"sdo_uuid\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"origin_url\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"origin_id\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"last_update\":{\"type\":\"long\",\"index\":\"not_analyzed\"},\"next_update\":{\"type\":\"long\",\"index\":\"not_analyzed\"},\"should_update\":{\"type\":\"boolean\",\"index\":\"not_analyzed\"},\"is_primary\":{\"type\":\"boolean\",\"index\":\"not_analyzed\"}}}}}";
	}
}