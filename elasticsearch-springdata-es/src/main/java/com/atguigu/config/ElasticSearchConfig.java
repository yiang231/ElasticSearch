package com.atguigu.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@SpringBootConfiguration
@ConfigurationProperties(prefix = "elasticsearch1")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

	private String host;

	private Integer port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	//@Bean
	@Override
	public RestHighLevelClient elasticsearchClient() {
		return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
	}

}
