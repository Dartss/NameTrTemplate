package common.elastic;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;

import jsmarty.core.common.elastic.model.ElasticDocumentVO;
import jsmarty.core.common.exception.UnreachableElasticServerException;

/**
 * ElasticHandler for ElasticHelper
 * 
 * @author yev
 *
 */
public class ElasticHandler {

	public ElasticHelper elasticHelper;
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public ElasticHandler(String host, int port) throws UnknownHostException, UnreachableElasticServerException {
		createInstance(host, port);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				LOGGER.info("shutdownhook: Closing connection to elastic ");
				try {
					close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (UnreachableElasticServerException e) {
					e.printStackTrace();
				}
				LOGGER.info("shutdownhook: Closed connection to elastic ");
			}
		});
	}

	public synchronized void createInstance(String host, int port) throws UnknownHostException, UnreachableElasticServerException{
		elasticHelper = new ElasticHelper(host, port);
	}

	/**
	 * To be called only from inside main class (manager - shutdownhook) of the calling component
	 * close the client
	 */
	public synchronized void close() throws UnknownHostException, UnreachableElasticServerException{
		elasticHelper.shutDown();
	}

	/**
	 * Create new index in ES server
	 * 
	 * @param name of index
	 * @param jsonBody settings for this index
	 * @return CreateIndexResponse if connection established, otherwise - null
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public CreateIndexResponse createIndex(String name, String jsonBody) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.createIndex(name, jsonBody);
	}

	/**
	 * Create/save document inside the ES index
	 * 
	 * @param index name
	 * @param type name
	 * @param id of document
	 * @param jsonSource serialized document in JSON String format
	 * @return IndexResponse if connection established, otherwise - null
	 */
	public IndexResponse createDocument(String index, String type, String id, String jsonSource) throws UnknownHostException, UnreachableElasticServerException{
		LOGGER.info("Elastic Handler : creating document - calling helper" );
		return elasticHelper.createDocument(index, type, id, jsonSource);
	}

	/**
	 * Create/save document inside the ES index. Id will be generated
	 * automatically
	 * 
	 * @param index name
	 * @param type name
	 * @param jsonSource serialized document in JSON String format
	 * @return IndexResponse if connection established, otherwise - null
	 */
	public IndexResponse createDocument(String index, String type, String jsonSource) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.createDocument(index, type, jsonSource);
	}

	/**
	 * Get document by it's id
	 * 
	 * @param index name
	 * @param type name
	 * @param id of document
	 * @param operationThreaded - if true then execute query in a separate thread
	 * @return GetResponse if connection established, otherwise - null
	 */
	public GetResponse getDocument(String index, String type, String id, boolean operationThreaded) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.getDocument(index, type, id, operationThreaded);
	}

	/**
	 * Get document by it's id
	 * 
	 * @param index name
	 * @param type name
	 * @param id of document
	 * @return GetResponse if connection established, otherwise - null
	 */
	public GetResponse getDocument(String index, String type, String id) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.getDocument(index, type, id);
	}

	/**
	 * Delete document by it's id
	 * 
	 * @param index name
	 * @param type name
	 * @param id of document
	 * @return DeleteResponse if connection established, otherwise - null
	 */
	public DeleteResponse deleteDocument(String index, String type, String id) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.deleteDocument(index, type, id);
	}

	/**
	 * Update document by it's id. Make sure that document with this id exist
	 * 
	 * @param index name
	 * @param type name
	 * @param id of document
	 * @param jsonSource serialized document in JSON String format
	 * @return UpdateResponse if connection established, otherwise - null
	 */
	public UpdateResponse updateDocument(String index, String type, String id, String jsonSource) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.updateDocument(index, type, id, jsonSource);
	}

	/**
	 * If the document does not exist, the content of the upsert element will be
	 * used to index the fresh doc.
	 * 
	 * @param index name
	 * @param type name
	 * @param id of document
	 * @param jsonSource serialized document in JSON String format
	 * @return UpdateResponse if connection established, otherwise - null
	 */
	public UpdateResponse upsertDocument(String index, String type, String id, String jsonSource) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.upsertDocument(index, type, id, jsonSource);
	}

	/**
	 * Multiple get from single index
	 * 
	 * @param index name
	 * @param type name
	 * @param ids list of documents ids
	 * @return MultiGetResponse if connection established, otherwise - null
	 */
	public MultiGetResponse multiGet(String index, String type, List<String> ids) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.multiGet(index, type, ids);
	}

	/**
	 * Multiple get from multiple indexes
	 * 
	 * @param documents contains index, type, id for each get item query
	 * @return MultiGetResponse if connection established, otherwise - null
	 */
	public MultiGetResponse multiGet(List<ElasticDocumentVO> documents) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.multiGet(documents);
	}

	/**
	 * Bulk request. Create many documents in a bulk
	 * 
	 * @param index name
	 * @param type name
	 * @param ids list of ids
	 * @param jsonSources list of serialized document in JSON String format
	 * @return BulkResponse if connection established, otherwise - null
	 * @throws UnreachableElasticServerException 
	 */
	public BulkResponse bulkCreate(String index, String type, List<String> ids, List<String> jsonSources)
			throws IllegalArgumentException, UnreachableElasticServerException {
		return elasticHelper.bulkCreate(index, type, ids, jsonSources);
	}

	/**
	 * Bulk request. Create many documents in a bulk
	 * @param documents a list of document for saving
	 * @return BulkResponse if connection established, otherwise - null
	 */
	public BulkResponse bulkCreate(List<ElasticDocumentVO> documents) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.bulkCreate(documents);
	}

	/**
	 * Bulk request. Create many documents in a bulk. Ids will be generated
	 * automatically
	 * 
	 * @param index name
	 * @param type name
	 * @param jsonSources list of serialized document in JSON String format
	 * @return BulkResponse if connection established, otherwise - null
	 */
	public BulkResponse bulkCreate(String index, String type, List<String> jsonSources) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.bulkCreate(index, type, jsonSources);
	}

	/**
	 * Bulk request. Update many documents in a bulk.
	 * 
	 * @param index name
	 * @param type name
	 * @param ids list of ids
	 * @param updateBodies list of serialized document in JSON String format
	 * @return BulkResponse if connection established, otherwise - null
	 * @throws UnreachableElasticServerException 
	 */
	public BulkResponse bulkUpdate(String index, String type, List<String> ids, List<String> updateBodies) throws IllegalArgumentException, UnreachableElasticServerException {
		return elasticHelper.bulkUpdate(index, type, ids, updateBodies);
	}

	/**
	 * Bulk request. Update many documents in a bulk.
	 * Uses single update body for each doc update
	 * 
	 * @param index name
	 * @param type name
	 * @param ids list of ids
	 * @param updateBody serialized document in JSON String format
	 * @return BulkResponse if connection established, otherwise - null
	 */
	public BulkResponse bulkUpdate(String index, String type, List<String> ids, String updateBody) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.bulkUpdate(index, type, ids, updateBody);
	}

	/**
	 * Bulk request. Update many documents in a bulk.
	 * 
	 * @param index name
	 * @param type name
	 * @param ids list of ids
	 * @param updateBody serialized document in JSON String format
	 * @return BulkResponse if connection established, otherwise - null
	 */
	public BulkResponse bulkUpdate(List<ElasticDocumentVO> documents) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.bulkUpdate(documents);
	}

	/**
	 * Bulk request. Delete many documents in a bulk.
	 * 
	 * @param index name
	 * @param type name
	 * @param ids list if ids
	 * @return BulkResponse if connection established, otherwise - null
	 */
	public BulkResponse bulkDelete(String index, String type, List<String> ids) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.bulkDelete(index, type, ids);
	}

	/**
	 * Bulk request. Delete many documents in a bulk.
	 * 
	 * @param index name
	 * @param type name
	 * @param ids list if ids
	 * @return BulkResponse if connection established, otherwise - null
	 */
	public BulkResponse bulkDelete(List<ElasticDocumentVO> documents) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.bulkDelete(documents);
	}

	/**
	 * Search request.
	 * 
	 * @param indexes - list of indexes
	 * @param types - list of types
	 * @param jsonQuery - query in JSON String format
	 * @return SearchResponse if connection established, otherwise - null
	 */
	public SearchResponse search(String[] indexes, String[] types, String jsonQuery) throws UnknownHostException, UnreachableElasticServerException{
		return elasticHelper.search(indexes, types, jsonQuery);
	}

	/**
	 * Search request.
	 * 
	 * @param index - index
	 * @param type - type
	 * @param jsonQuery - query in JSON String format
	 * @return SearchResponse if connection established, otherwise - null
	 */
	public SearchResponse search(String index, String type, String jsonQuery) throws UnknownHostException, UnreachableElasticServerException{
		return this.search(new String[]{index} , new String[]{type}, jsonQuery);
	}

	/**
	 * MultiSearch request
	 * @param documents
	 * @return
	 */
	public MultiSearchResponse multiSearch(List<ElasticDocumentVO> documents){
		return this.elasticHelper.multiSearch(documents);

	}

}