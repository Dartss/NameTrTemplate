package common.elastic;

/**
 * Contains queries for index creation
 * @author Vit
 *
 */

public class ElasticMapperHelper {

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