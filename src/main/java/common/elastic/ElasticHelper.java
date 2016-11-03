package common.elastic;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import jsmarty.core.common.elastic.model.ElasticDocumentVO;
import jsmarty.core.common.exception.UnreachableElasticServerException;
import jsmarty.core.common.logging.DefaultLogger;
import jsmarty.core.common.logging.core.Logger;

/**
 * ElasticHandler based on elasticsearch java API library
 * 
 * @author Vit
 *
 */
public class ElasticHelper
{

    private String host;
    private int port;
    private Client client;
    private int connectionTrialCounter = 0;
    public static Map<String, Client> clients;

    private final static Logger logger = DefaultLogger.getInstance();

    /**
     * Create a client for elastic search database
     * 
     * @param host
     *            of ES server
     * @param port
     *            of ES server
     * @throws UnreachableElasticServerException
     * @throws UnknownHostException
     */
    public ElasticHelper(String host, int port) throws UnreachableElasticServerException {
	logger.debug("Creating Elastic Helper");
	//
	this.host = host;
	this.port = port;

	if (clients == null)
	{
	    clients = new HashMap<String, Client>();
	    logger.debug("ElasticHelper: Creating clients map");
	}

	String key = generateClientKey(host, port);
	this.client = getUniqueClientPerHost(this, key);
    }

    private static synchronized Client getUniqueClientPerHost(ElasticHelper elasticHelper, String key)
    {
	Client client = null;
	if (clients.containsKey(key))
	{
	    logger.debug("Elastic helper: Getting an already created client for:" + key);
	    client = clients.get(key);
	} else
	{
	    logger.debug("Elastic helper: Creating new client for:" + key);
	    client = elasticHelper.getClient();
	    clients.put(key, client);
	}
	return client;
    }

    private Client getClient()
    {
	Client client = null;
	try
	{
	    client = getClient(this);
	} catch (UnreachableElasticServerException e)
	{
	    logger.error("UnreachableElasticServerException: Error get Client", e);
	}
	return client;
    }

    public synchronized boolean isClientConnected()
    {
	try
	{
	    /*
	     * The call below will break and return a NoNodeAvailableException
	     * in case the connection is cut with Elastic server
	     */
	    this.client.admin().indices().prepareGetTemplates().get();
	    this.connectionTrialCounter = 0;
	} catch (NoNodeAvailableException nnaex)
	{
	    logger.error("NoNodeAvailableException: Error checking if client is connected", nnaex);
	    return false;
	}
	return true;
    }

    public static synchronized Client getClient(ElasticHelper elasticHelper) throws UnreachableElasticServerException
    {
	logger.debug("Creating elastic jobs from sdos.. INSIDE getClient");

	/*
	 * Synchronizing this method, because this same client will be invoqued
	 * by multi threads,
	 * so in case a connection exception occurs on different calls, a new
	 * client will be created
	 * only once and then shared again...
	 */
	while (elasticHelper.client == null || !elasticHelper.isClientConnected())
	{
	    logger.debug("Creating elastic jobs from sdos.. INSIDE getClient - elasticHelper.client == null || !elasticHelper.isClientConnected()");

	    elasticHelper.connectionTrialCounter++;
	    if (elasticHelper.connectionTrialCounter > 1)
	    {
		try
		{
		    Thread.sleep(15000);
		} catch (InterruptedException e)
		{
		    logger.error("InterruptedException: Error sleep thread for getClient", e);
		}
	    } else if (elasticHelper.connectionTrialCounter == 5)
	    {
		elasticHelper.close();
		throw new UnreachableElasticServerException("Unable to connect to Elastic Server on " + elasticHelper.host + " " + elasticHelper.port
			+ ". Make sure the server is running.");
	    }

	    Settings settings = Settings.settingsBuilder().put("client.transport.ignore_cluster_name", true)
		    .put("client.transport.ping_timeout", "120s").put("client.transport.nodes_sampler_interval", "120s").build();

	    try
	    {
		logger.debug("Creating elastic transport client");
		elasticHelper.client = TransportClient.builder().settings(settings).build()
			.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticHelper.host), elasticHelper.port));

	    } catch (UnknownHostException e)
	    {
		e.printStackTrace();
	    }
	}
	logger.debug("Creating elastic jobs from sdos.. INSIDE getClient returning client");

	return elasticHelper.client;
    }

    private String generateClientKey(String host2, int port2)
    {
	return host + ":" + String.valueOf(port);
    }

    private synchronized void close()
    {
	this.client.threadPool().shutdown();
	this.client.close();
    }

    /**
     * shutdown clients
     */
    public synchronized void shutDown()
    {
	logger.debug("Closing elastic transport clients");
	if (this.clients != null)
	{
	    logger.debug("Clients not null - Closing elastic transport clients");
	    Client client = null;
	    for (String key : clients.keySet())
	    {
		client = clients.get(key);
		logger.debug("Closing elastic transport client: " + key);
		client.threadPool().shutdown();
		client.close();
		logger.debug("Closed elastic transport client: " + key);
	    }
	    clients = null;
	}
    }

    /**
     * Create new index in ES server
     * 
     * @param name
     *            of index
     * @param jsonBody
     *            settings for this index
     * @return CreateIndexResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public CreateIndexResponse createIndex(String name, String jsonBody) throws UnreachableElasticServerException
    {
	CreateIndexRequest request = new CreateIndexRequest(name);
	request.source(jsonBody);
	CreateIndexResponse response = null;
	try
	{
	    response = getClient(this).admin().indices().create(request).get();
	} catch (InterruptedException e)
	{
	    logger.error("InterruptedException: Error creating index", e);
	} catch (ExecutionException e)
	{
	    logger.error("ExecutionException: Error creating index", e);
	}
	return response;
    }

    /**
     * Create/save document inside the ES index
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param id
     *            of document
     * @param jsonSource
     *            serialized document in JSON String format
     * @return IndexResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public IndexResponse createDocument(String index, String type, String id, String jsonSource) throws UnreachableElasticServerException
    {
	logger.debug("Elastic Helper : inside create document - getting client and preparing index - " + index + " - " + type + " - " + id + " - "
		+ jsonSource);
	IndexResponse response = getClient(this).prepareIndex(index, type, id).setSource(jsonSource).get();
	logger.debug("Elastic Helper : inside create document - returning response " + response.toString());
	return response;
    }

    /**
     * Create/save document inside the ES index. Id will be generated
     * automatically
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param jsonSource
     *            serialized document in JSON String format
     * @return IndexResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public IndexResponse createDocument(String index, String type, String jsonSource) throws UnreachableElasticServerException
    {
	IndexResponse response = getClient(this).prepareIndex(index, type).setSource(jsonSource).get();
	return response;
    }

    /**
     * Get document by it's id
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param id
     *            of document
     * @param operationThreaded
     *            - if true then execute query in a separate thread
     * @return GetResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public GetResponse getDocument(String index, String type, String id, boolean operationThreaded) throws UnreachableElasticServerException
    {
	GetResponse response = getClient(this).prepareGet(index, type, id).setOperationThreaded(operationThreaded).get();
	return response;
    }

    /**
     * Get document by it's id
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param id
     *            of document
     * @return GetResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public GetResponse getDocument(String index, String type, String id) throws UnreachableElasticServerException
    {
	GetResponse response = getClient(this).prepareGet(index, type, id).setOperationThreaded(false).get();
	return response;
    }

    /**
     * Delete document by it's id
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param id
     *            of document
     * @return DeleteResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public DeleteResponse deleteDocument(String index, String type, String id) throws UnreachableElasticServerException
    {
	DeleteResponse response = getClient(this).prepareDelete(index, type, id).get();
	return response;
    }

    /**
     * Update document by it's id. Make sure that document with this id exist
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param id
     *            of document
     * @param jsonSource
     *            serialized document in JSON String format
     * @return UpdateResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public UpdateResponse updateDocument(String index, String type, String id, String jsonSource) throws UnreachableElasticServerException
    {
	UpdateResponse response = getClient(this).prepareUpdate(index, type, id).setDoc(jsonSource).get();
	return response;
    }

    /**
     * If the document does not exist, the content of the upsert element will be
     * used to index the fresh doc.
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param id
     *            of document
     * @param jsonSource
     *            serialized document in JSON String format
     * @return UpdateResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public UpdateResponse upsertDocument(String index, String type, String id, String jsonSource) throws UnreachableElasticServerException
    {
	IndexRequest indexRequest = new IndexRequest(index, type, id).source(jsonSource);
	UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(jsonSource).upsert(indexRequest);
	UpdateResponse response = null;
	try
	{
	    response = getClient(this).update(updateRequest).get();
	} catch (InterruptedException e)
	{
	    logger.error("InterruptedException: Error when upsert Document: [" + jsonSource + "]", e);
	} catch (ExecutionException e)
	{
	    logger.error("InterruptedException: Error when upsert Document: [" + jsonSource + "]", e);
	}
	return response;
    }

    /**
     * Multiple get from single index
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param ids
     *            list of documents ids
     * @return MultiGetResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public MultiGetResponse multiGet(String index, String type, List<String> ids) throws UnreachableElasticServerException
    {
	MultiGetRequestBuilder request = getClient(this).prepareMultiGet();
	request.add(index, type, ids);
	MultiGetResponse response = request.get();
	return response;
    }

    /**
     * Multiple get from multiple indexes
     * 
     * @param documents
     *            contains index, type, id for each get item query
     * @return MultiGetResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public MultiGetResponse multiGet(List<ElasticDocumentVO> documents) throws UnreachableElasticServerException
    {
	MultiGetRequestBuilder request = getClient(this).prepareMultiGet();
	for (ElasticDocumentVO doc : documents)
	{
	    request.add(doc.getIndex(), doc.getType(), doc.getId());
	}
	MultiGetResponse response = request.get();
	return response;
    }

    /**
     * Bulk request. Create many documents in a bulk
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param ids
     *            list of ids
     * @param jsonSources
     *            list of serialized document in JSON String format
     * @return BulkResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public BulkResponse bulkCreate(String index, String type, List<String> ids, List<String> jsonSources)
	    throws IllegalArgumentException, UnreachableElasticServerException
    {
	if (ids.size() != jsonSources.size())
	{
	    throw new IllegalArgumentException("ids size should be equal to jsonSources size");
	}
	BulkRequestBuilder bulkRequest = client.prepareBulk();
	for (int i = 0; i < ids.size(); i++)
	{
	    bulkRequest.add(getClient(this).prepareIndex(index, type, ids.get(i)).setSource(jsonSources.get(i)));
	}

	BulkResponse response = bulkRequest.get();
	return response;
    }

    /**
     * Bulk request. Create many documents in a bulk
     * 
     * @param documents
     *            a list of document for saving
     * @return BulkResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public BulkResponse bulkCreate(List<ElasticDocumentVO> documents) throws UnreachableElasticServerException
    {
	logger.debug("Creating elastic jobs from sdos.. INSIDE BULK CREATE");
	//
	BulkRequestBuilder bulkRequest = getClient(this).prepareBulk();
	//
	logger.debug("Creating elastic jobs from sdos.. INSIDE BULK CREATE - before loop");
	//
	for (ElasticDocumentVO document : documents)
	{
	    logger.debug("Creating elastic jobs from sdos.. INSIDE BULK CREATE - preparing index : " + document.getIndex() + " - "
		    + document.getType() + " - " + document.getId() + " - " + document.getJsonSource());
	    //
	    bulkRequest
		    .add(getClient(this).prepareIndex(document.getIndex(), document.getType(), document.getId()).setSource(document.getJsonSource()));
	}
	BulkResponse response = bulkRequest.get();
	//
	logger.debug("BulkCreate RESPONSE  : " + response);

	return response;
    }

    /**
     * Bulk request. Create many documents in a bulk. Ids will be generated
     * automatically
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param jsonSources
     *            list of serialized document in JSON String format
     * @return BulkResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public BulkResponse bulkCreate(String index, String type, List<String> jsonSources) throws UnreachableElasticServerException
    {
	BulkRequestBuilder bulkRequest = getClient(this).prepareBulk();
	for (String source : jsonSources)
	{
	    bulkRequest.add(getClient(this).prepareIndex(index, type).setSource(source));
	}
	BulkResponse response = bulkRequest.get();
	return response;
    }

    /**
     * Bulk request. Update many documents in a bulk.
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param ids
     *            list of ids
     * @param updateBodies
     *            list of serialized document in JSON String format
     * @return BulkResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public BulkResponse bulkUpdate(String index, String type, List<String> ids, List<String> updateBodies)
	    throws IllegalArgumentException, UnreachableElasticServerException
    {
	if (ids.size() != updateBodies.size())
	{
	    throw new IllegalArgumentException("ids size should be equal to jsonSources size");
	}
	BulkRequestBuilder bulkRequest = client.prepareBulk();
	int size = Math.min(ids.size(), updateBodies.size());
	for (int i = 0; i < size; i++)
	{
	    bulkRequest.add(getClient(this).prepareUpdate(index, type, ids.get(i)).setDoc(updateBodies.get(i)));
	}

	BulkResponse response = bulkRequest.get();
	return response;
    }

    /**
     * Bulk request. Update many documents in a bulk.
     * Uses single update body for each doc update
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param ids
     *            list of ids
     * @param updateBody
     *            serialized document in JSON String format
     * @return BulkResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public BulkResponse bulkUpdate(String index, String type, List<String> ids, String updateBody) throws UnreachableElasticServerException
    {
	BulkRequestBuilder bulkRequest = getClient(this).prepareBulk();
	for (int i = 0; i < ids.size(); i++)
	{
	    bulkRequest.add(getClient(this).prepareUpdate(index, type, ids.get(i)).setDoc(updateBody));
	}
	BulkResponse response = bulkRequest.get();
	return response;
    }

    /**
     * Bulk request. Update many documents in a bulk.
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param ids
     *            list of ids
     * @param updateBody
     *            serialized document in JSON String format
     * @return BulkResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public BulkResponse bulkUpdate(List<ElasticDocumentVO> documents) throws UnreachableElasticServerException
    {
	BulkRequestBuilder bulkRequest = getClient(this).prepareBulk();
	for (ElasticDocumentVO doc : documents)
	{
	    bulkRequest.add(getClient(this).prepareUpdate(doc.getIndex(), doc.getType(), doc.getId()).setDoc(doc.getJsonSource()));
	}

	BulkResponse response = bulkRequest.get();
	return response;
    }

    /**
     * Bulk request. Delete many documents in a bulk.
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param ids
     *            list if ids
     * @return BulkResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public BulkResponse bulkDelete(String index, String type, List<String> ids) throws UnreachableElasticServerException
    {
	BulkRequestBuilder bulkRequest = getClient(this).prepareBulk();
	for (String id : ids)
	{
	    bulkRequest.add(getClient(this).prepareDelete(index, type, id));
	}
	BulkResponse response = bulkRequest.get();
	return response;
    }

    /**
     * Bulk request. Delete many documents in a bulk.
     * 
     * @param index
     *            name
     * @param type
     *            name
     * @param ids
     *            list if ids
     * @return BulkResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     */
    public BulkResponse bulkDelete(List<ElasticDocumentVO> documents) throws UnreachableElasticServerException
    {
	BulkRequestBuilder bulkRequest = getClient(this).prepareBulk();
	for (ElasticDocumentVO document : documents)
	{
	    bulkRequest.add(getClient(this).prepareDelete(document.getIndex(), document.getType(), document.getId()));
	}

	BulkResponse response = bulkRequest.get();
	return response;
    }

    /**
     * Search request.
     * 
     * @param indexes
     *            - list of indexes
     * @param types
     *            - list of types
     * @param jsonQuery
     *            - query in JSON String format
     * @return SearchResponse if connection established, otherwise - null
     * @throws UnreachableElasticServerException
     * @throws ExecutionException
     */
    public SearchResponse search(String[] indexes, String[] types, String jsonQuery) throws UnreachableElasticServerException
    {
	SearchRequest request = new SearchRequest();
	request.indices(indexes);
	request.types(types);
	request.source(jsonQuery);

	SearchResponse response = null;
	try
	{
	    response = getClient(this).search(request).get();
	} catch (InterruptedException e)
	{
	    logger.error("InterruptedException: Error search: jsonQuery=[" + jsonQuery + "]", e);
	} catch (ExecutionException e)
	{
	    logger.error("ExecutionException: Error search: jsonQuery=[" + jsonQuery + "]", e);
	}
	return response;
    }

    /**
     * Multiple search
     * 
     * @param documents
     *            contains index and jsonSource (searchQuery)
     * @return MultiSearchResponse if connection established, otherwise - null
     */
    public MultiSearchResponse multiSearch(List<ElasticDocumentVO> documents)
    {
	if (client != null)
	{
	    MultiSearchRequestBuilder requestBuilder = client.prepareMultiSearch();
	    for (ElasticDocumentVO doc : documents)
	    {
		SearchRequest request = new SearchRequest();
		request.indices(new String[] { doc.getIndex() });
		request.types(new String[] { doc.getType() });
		request.source(doc.getJsonSource());
		requestBuilder.add(request);
	    }
	    MultiSearchResponse response = requestBuilder.get();
	    return response;
	}
	return null;
    }

}