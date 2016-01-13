package com.aconex.scrutineer.elasticsearch;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;

import com.aconex.scrutineer.IdAndVersionFactory;
import com.aconex.scrutineer.LogUtils;

public class ElasticSearchDownloader {

	private static final Logger LOG = LogUtils.loggerForThisClass();

	static final int BATCH_SIZE = 100000;
	static final int SCROLL_TIME_IN_MINUTES = 10;
	private long numItems = 0;

	private final Client client;
	private final String indexName;
	private final String query;
	private final IdAndVersionFactory idAndVersionFactory;

	public ElasticSearchDownloader(final Client client, final String indexName, final String query, final IdAndVersionFactory idAndVersionFactory) {
		this.client = client;
		this.indexName = indexName;
		this.query = query;
		this.idAndVersionFactory = idAndVersionFactory;
	}

	public void downloadTo(final OutputStream outputStream) {
		final long begin = System.currentTimeMillis();
		doDownloadTo(outputStream);
		LogUtils.infoTimeTaken(LOG, begin, numItems, "Scan & Download completed");
	}

	private void doDownloadTo(final OutputStream outputStream) {
		try {
			final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			consumeBatches(objectOutputStream, startScroll().getScrollId());
			objectOutputStream.close();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	void consumeBatches(final ObjectOutputStream objectOutputStream, final String initialScrollId) throws IOException {

		String scrollId = initialScrollId;
		SearchResponse batchSearchResponse = null;
		do {
			batchSearchResponse = client.prepareSearchScroll(scrollId)
					.setScroll(TimeValue.timeValueMinutes(SCROLL_TIME_IN_MINUTES)).execute().actionGet();
			scrollId = batchSearchResponse.getScrollId();
		} while (writeSearchResponseToOutputStream(objectOutputStream, batchSearchResponse));
	}

	boolean writeSearchResponseToOutputStream(final ObjectOutputStream objectOutputStream, final SearchResponse searchResponse) throws IOException {
		final SearchHit[] hits = searchResponse.getHits().hits();
		for (final SearchHit hit : hits) {
			idAndVersionFactory.create(hit.getId(), hit.getVersion()).writeToStream(objectOutputStream);
			numItems++;
		}
		return hits.length > 0;
	}

	QueryStringQueryBuilder createQuery() {
		return QueryBuilders.queryString(query).defaultOperator(QueryStringQueryBuilder.Operator.AND)
				.defaultField("_all");
	}

	@SuppressWarnings("PMD.NcssMethodCount")
	SearchResponse startScroll() {
		final SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName);
		searchRequestBuilder.setSearchType(SearchType.SCAN);
		searchRequestBuilder.setQuery(createQuery());
		searchRequestBuilder.setSize(BATCH_SIZE);
		searchRequestBuilder.setExplain(false);
		searchRequestBuilder.setNoFields();
		searchRequestBuilder.setVersion(true);
		searchRequestBuilder.setScroll(TimeValue.timeValueMinutes(SCROLL_TIME_IN_MINUTES));

		return searchRequestBuilder.execute().actionGet();
	}

}
