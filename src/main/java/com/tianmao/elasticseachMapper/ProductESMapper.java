package com.tianmao.elasticseachMapper;

import com.alibaba.fastjson.JSON;
import com.tianmao.pojo.Product;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Product索引操作
 * @author 胡建德
 */
public class ProductESMapper{

    @Autowired
    private RestHighLevelClient client;

    //索引名
    public static final String  INDEX = "product";

    public void save(Product product) throws IOException {
        if(!isExitIndex(ProductESMapper.INDEX)){
            createIndex(ProductESMapper.INDEX);
        }
        IndexRequest indexRequest = new IndexRequest(ProductESMapper.INDEX);

        indexRequest.id(String.valueOf(product.getId()));
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        indexRequest.timeout("1s");

        indexRequest.source(JSON.toJSONString(product), XContentType.JSON);
        client.index(indexRequest,RequestOptions.DEFAULT);
    }

    public void delete(Product product) throws IOException {
        if(!isExitIndex(ProductESMapper.INDEX)){
            createIndex(ProductESMapper.INDEX);
        }
        this.deleteById(product.getId());
    }

    public void deleteById(int id) throws IOException {
        if(!isExitIndex(ProductESMapper.INDEX)){
            createIndex(ProductESMapper.INDEX);
        }
        DeleteRequest request = new DeleteRequest(ProductESMapper.INDEX,String.valueOf(id));
        client.delete(request,RequestOptions.DEFAULT);
    }

    public boolean isExitIndex(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return client.indices().exists(request,RequestOptions.DEFAULT);
    }

    public void createIndex(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 批量插入
     * @param productList
     * @throws IOException
     */
    public void saveList(List<Product> productList) throws IOException {
        if(!isExitIndex(ProductESMapper.INDEX)){
            createIndex(ProductESMapper.INDEX);
        }
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        for(Product product : productList){
            bulkRequest.add(
                    new IndexRequest(ProductESMapper.INDEX).id(String.valueOf(product.getId()))
                    .source(JSON.toJSONString(product),XContentType.JSON)
            );
        }
        //加入一个标志证明已把所有数据加入elasticsearch中
        bulkRequest.add(new IndexRequest(ProductESMapper.INDEX).id("all").source(true,XContentType.JSON));
        client.bulk(bulkRequest,RequestOptions.DEFAULT);
    }

    public List<Product> search(String keyword,int start,int size) throws IOException {
        if(!isExitIndex(ProductESMapper.INDEX)){
            createIndex(ProductESMapper.INDEX);
        }
        List<Product> products = new ArrayList<>();
        SearchRequest request = new SearchRequest(ProductESMapper.INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(start);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", keyword);
        sourceBuilder.query(termQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        for(SearchHit doment: search.getHits().getHits()){
            Map<String, Object> sourceAsMap = doment.getSourceAsMap();
            for(String key :sourceAsMap.keySet()){
                Product product = JSON.toJavaObject((JSON) sourceAsMap.get(key),Product.class);
                products.add(product);
            }
        }
        return products;
    }

    public boolean isExitData() throws IOException {
        if(!isExitIndex(ProductESMapper.INDEX)){
            createIndex(ProductESMapper.INDEX);
        }
        GetRequest request = new GetRequest(ProductESMapper.INDEX,"all");
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        return client.exists(request,RequestOptions.DEFAULT);
    }
}




