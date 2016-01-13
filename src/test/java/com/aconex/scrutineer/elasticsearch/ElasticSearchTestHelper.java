package com.aconex.scrutineer.elasticsearch;

import org.elasticsearch.client.Client;

public class ElasticSearchTestHelper {

	private final Client client;

	public ElasticSearchTestHelper(final Client client) {
		this.client = client;
	}

	public void deleteIndexIfItExists(final String indexName) {
		if (client.admin().indices().prepareExists(indexName).execute().actionGet().isExists()) {
			client.admin().indices().prepareDelete(indexName).execute().actionGet();
		}

	}
}
