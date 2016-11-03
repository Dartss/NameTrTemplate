package common.apis.social.elastic;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.search.SearchHit;

import jsmarty.core.common.elastic.ElasticHandler;
import jsmarty.core.common.elastic.model.ElasticDocumentVO;
import jsmarty.core.common.exception.UnreachableElasticServerException;
import jsmarty.core.common.json.JsonHandler;
import jsmarty.core.common.json.impl.JsonHandlerImpl;
import jsmarty.core.sdo.SDO;
import jsmarty.core.sdo.stats.BaseStats;
import jsmarty.core.socialstats.model.SocialStatsJob;

public class StatsElasticHandler extends ElasticHandler{

    private static int LOAD_PER_CALL = 250;
    private static JsonHandler jsonHandler = new JsonHandlerImpl();
    
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public StatsElasticHandler(String host, int port) throws UnknownHostException, UnreachableElasticServerException {
    	super(host, port);
//        this.elasticHandler = new ElasticHandler(host, port);
    }

    /**
     * Get all SDOs that match createdAfterTimeMillis <
     * flags.saveInElasticTimeMS < createdBeforeTimeMillis
     * 
     * @param index
     * @param type
     * @param createdAfterTimeMillis
     * @param createdBeforeTimeMillis
     * @param createdAtOrder
     *            sorting ASC or DESC be flags.saveInElasticTimeMS field
     * @return
     * @throws UnreachableElasticServerException
     * @throws UnknownHostException
     */
    public List<SDO> getSDOs(String index, String type, long createdAfterTimeMillis, long createdBeforeTimeMillis,
            String sortOrder) throws UnknownHostException, UnreachableElasticServerException {
        List<SDO> sdos = new ArrayList<SDO>();
        long loaded = 0;
        long total = 0;
        do {
            SearchResponse response = super.search(new String[] { index }, new String[] { type }, StatsQueryBuilder.buildSearchSdoQuery(loaded, LOAD_PER_CALL, createdAfterTimeMillis, createdBeforeTimeMillis, sortOrder));
            if (response != null) {
                for (SearchHit hit : response.getHits().getHits()) {
                	String source = hit.getSourceAsString();
                    if (source != null) {
                        SDO sdo = null;
                        sdo = (SDO) jsonHandler.deserialize(source, new SDO());
                        if (sdo != null) {
                            sdos.add(sdo);
                        }
                    }
                }
                total = response.getHits().getTotalHits();
                loaded += response.getHits().getHits().length;
            }
        } while (loaded < total);
        return sdos;
    }
    
//    public void bulkCreate(List<ElasticDocumentVO> documents)
//            throws UnreachableElasticServerException, UnknownHostException {
//        BulkResponse response = bulkCreate(documents);
//    }
    
    public List<SocialStatsJob> getSocialstatJobs(String index, String[] types, int maxSize,
            long toNextUpdateTimeMillis, boolean shouldUpdate, String sortOrder)
            throws UnknownHostException, UnreachableElasticServerException {
        List<SocialStatsJob> jobs = new ArrayList<SocialStatsJob>();
        long loaded = 0;
        long total = 0;
        do {
            SearchResponse response = super.search(new String[] { index }, types,
                    StatsQueryBuilder.buildSearchSocialstatJobsQuery(loaded, maxSize, toNextUpdateTimeMillis,
                            shouldUpdate, sortOrder));
            if (response != null) {
                for (SearchHit hit : response.getHits().getHits()) {
                    String source = hit.getSourceAsString();
                    if (source != null) {
                        SocialStatsJob job = null;
                        job = (SocialStatsJob) jsonHandler.deserialize(source, new SocialStatsJob());
                        job.setType(hit.getType());
                        if (job != null) {
                            jobs.add(job);
                        }
                    }
                }
                total = Math.min(response.getHits().getTotalHits(), maxSize);
                loaded += response.getHits().getHits().length;
            }
        } while (loaded < total);
        return jobs;
    }
    public List<SocialStatsJob> getActiveSocialstatJobs(String index, String[] types, long fromNextUpdateTimeMillis,
            boolean shouldUpdate, String sortOrder) throws UnknownHostException, UnreachableElasticServerException {
        List<SocialStatsJob> jobs = new ArrayList<SocialStatsJob>();
        long loaded = 0;
        long total = 0;
        do {
            SearchResponse response = super.search(new String[] { index }, types,
                    StatsQueryBuilder.buildSearchActiveSocialstatJobsQuery(loaded, LOAD_PER_CALL,
                            fromNextUpdateTimeMillis, shouldUpdate, sortOrder));
            if (response != null) {
                for (SearchHit hit : response.getHits().getHits()) {
                    String source = hit.getSourceAsString();
                    if (source != null) {
                        SocialStatsJob job = null;
                        job = (SocialStatsJob) jsonHandler.deserialize(source, new SocialStatsJob());
                        job.setType(hit.getType());
                        if (job != null) {
                            jobs.add(job);
                        }
                    }
                }
                total = response.getHits().getTotalHits();
                loaded += response.getHits().getHits().length;
            }
        } while (loaded < total);
        return jobs;
    }
    public SocialStatsJob getSocialstatJob(String index, String type, String id)
            throws UnreachableElasticServerException, UnknownHostException {
        GetResponse response = super.getDocument(index, type, id);
        SocialStatsJob job = null;
        if (response != null) {
            job = (SocialStatsJob) jsonHandler.deserialize(response.getSourceAsString(), new SocialStatsJob());
        }
        return job;
    }
    public SocialStatsJob searchSocialstatJob(String index, String type, String id)
            throws UnknownHostException, UnreachableElasticServerException {
        SearchResponse response = super.search(index, type,
                StatsQueryBuilder.buildSearchSocialstatJobQuery(id));
        SocialStatsJob job = null;
        if (response != null) {
            for (SearchHit hit : response.getHits().getHits()) {
                job = (SocialStatsJob) jsonHandler.deserialize(hit.getSourceAsString(), new SocialStatsJob());
                break;
            }
        }
        return job;
    }
    
    public BaseStats searchLatestSocialstatsByUuid(String index, String type, Class<? extends BaseStats> statsType, String sdoUuid) throws UnknownHostException, UnreachableElasticServerException{
        BaseStats stats = null;
        SearchResponse response = super.search(new String[]{index}, new String[]{type}, StatsQueryBuilder.buildSearchStatsBySdoUuid(1, sdoUuid, "desc"));
        
        if(response != null){
            SearchHit[] searchHits = response.getHits().getHits();
            
            if(searchHits != null && searchHits.length > 0){
                String baseStatsStr = searchHits[0].getSourceAsString();
                try {
                    stats = (BaseStats) jsonHandler.deserialize(baseStatsStr, statsType.newInstance());
                } catch (InstantiationException e) {
                    // no operation
                } catch (IllegalAccessException e) {
                    // no operation
                }
            }
        }
        
        return stats;
    }
    public List<List<BaseStats>> multiSearchSocialstats(String index, String type, Class<? extends BaseStats> statsType,
            List<String> sdosUuids, int statsPerSdoLimit, String order) {
        List<List<BaseStats>> result = new ArrayList<>();
        // prepare documents
        List<ElasticDocumentVO> documents = new ArrayList<ElasticDocumentVO>(sdosUuids.size());
        for (String sdoUuid : sdosUuids) {
            ElasticDocumentVO doc = new ElasticDocumentVO();
            doc.setIndex(index);
            doc.setType(type);
            doc.setJsonSource(StatsQueryBuilder.buildSearchSocialstatsQuery(0, statsPerSdoLimit, sdoUuid, order));
            documents.add(doc);
        }
        MultiSearchResponse response = super.multiSearch(documents);
        if (response != null) {
            for (Item item : response.getResponses()) {
                SearchResponse searchResponse = item.getResponse();
                List<BaseStats> subList = new ArrayList<BaseStats>();
                if (searchResponse != null) {
                    for (SearchHit hit : searchResponse.getHits()) {
                        BaseStats stats = null;
                        try {
                            stats = (BaseStats) jsonHandler.deserialize(hit.getSourceAsString(),
                                    statsType.newInstance());
                        } catch (InstantiationException e) {
                            // no operation
                        } catch (IllegalAccessException e) {
                            // no operation
                        }
                        subList.add(stats);
                    }
                }
                result.add(subList);
            }
        }
        return result;
    }
    public IndexResponse saveSocialstats(String index, String type, BaseStats stats)
            throws UnknownHostException, UnreachableElasticServerException {
        IndexResponse response = super.createDocument(index, type, jsonHandler.serialize(stats));
        return response;
    }
    public UpdateResponse updateSdo(String index, String type, String id, SDO sdo)
            throws UnknownHostException, UnreachableElasticServerException {
        UpdateResponse response = super.updateDocument(index, type, id, jsonHandler.serialize(sdo));
        return response;
    }
    public UpdateResponse updateSocialstatJob(String index, String type, String id, SocialStatsJob job)
            throws UnknownHostException, UnreachableElasticServerException {
        UpdateResponse response = super.updateDocument(index, type, id, jsonHandler.serialize(job));
        return response;
    }

}