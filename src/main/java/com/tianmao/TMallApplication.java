package com.tianmao;

import com.tianmao.domain.PortUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author 胡建德
 */
@SpringBootApplication
@MapperScan("com.tianmao.mapper")
@EnableCaching
@EnableElasticsearchRepositories(basePackages = "com.tianmao.elasticseachMapper")
@EnableJpaRepositories(basePackages = {"com.tianmao.mapper", "com.tianmao.pojo"})
public class TMallApplication {
    static {
        PortUtil.checkPort(6379,"Redis 服务端",true);
        PortUtil.checkPort(9300,"ElasticSearch 服务端",true);
        PortUtil.checkPort(5601,"Kibana 工具", true);
    }

    public static void main(String[] args){
        SpringApplication.run(TMallApplication.class,args);
    }
}
