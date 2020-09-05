package com.tianmao.elasticseachMapper;

import com.tianmao.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author 胡建德
 */
public interface ProductESMapper extends ElasticsearchRepository<Product,Integer> {
}
