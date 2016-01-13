package com.aconex.scrutineer.elasticsearch;

import static com.aconex.scrutineer.HasIdAndVersionMatcher.*;
import static org.elasticsearch.node.NodeBuilder.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Iterator;

import org.apache.commons.lang.SystemUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.node.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.aconex.scrutineer.StringIdAndVersion;
import com.fasterxml.sort.DataReaderFactory;
import com.fasterxml.sort.DataWriterFactory;
import com.fasterxml.sort.SortConfig;
import com.fasterxml.sort.Sorter;
import com.fasterxml.sort.util.NaturalComparator;

public class ElasticSearchIdAndVersionStreamIntegrationTest {

	private static final String INDEX_NAME = "local";
	private final IdAndVersionFactory idAndVersionFactory = StringIdAndVersion.FACTORY;
	private Client client;
	private ElasticSearchTestHelper elasticSearchTestHelper;

	@Before
	public void setup() {
		final Node node = nodeBuilder().local(true).node();
		client = node.client();
		deleteIndexIfExists();

		indexIdAndVersion("1", 1);
		indexIdAndVersion("3", 3);
		indexIdAndVersion("2", 2);

		client.admin().indices().prepareFlush(INDEX_NAME).execute().actionGet();
	}

	@After
	public void teardown() {
		client.close();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldGetStreamFromElasticSearch() {

		final SortConfig sortConfig = new SortConfig().withMaxMemoryUsage(256 * 1024 * 1024);
		final DataReaderFactory<IdAndVersion> dataReaderFactory = new IdAndVersionDataReaderFactory(idAndVersionFactory);
		final DataWriterFactory<IdAndVersion> dataWriterFactory = new IdAndVersionDataWriterFactory();
		final Sorter sorter = new Sorter(sortConfig, dataReaderFactory, dataWriterFactory, new NaturalComparator<IdAndVersion>());
		final ElasticSearchDownloader elasticSearchDownloader = new ElasticSearchDownloader(client, INDEX_NAME, "_type:idandversion", idAndVersionFactory);
		final ElasticSearchIdAndVersionStream elasticSearchIdAndVersionStream = new ElasticSearchIdAndVersionStream(elasticSearchDownloader, new ElasticSearchSorter(sorter), new IteratorFactory(idAndVersionFactory), SystemUtils
				.getJavaIoTmpDir().getAbsolutePath());

		elasticSearchIdAndVersionStream.open();
		final Iterator<IdAndVersion> iterator = elasticSearchIdAndVersionStream.iterator();

		assertThat(iterator.next(), hasIdAndVersion("1", 1));
		assertThat(iterator.next(), hasIdAndVersion("2", 2));
		assertThat(iterator.next(), hasIdAndVersion("3", 3));

		elasticSearchIdAndVersionStream.close();
	}

	private void deleteIndexIfExists() {
		elasticSearchTestHelper = new ElasticSearchTestHelper(client);
		elasticSearchTestHelper.deleteIndexIfItExists(INDEX_NAME);
	}

	private void indexIdAndVersion(final String id, final long version) {
		client.prepareIndex(INDEX_NAME, "idandversion").setId(id).setOperationThreaded(false).setVersion(version)
				.setVersionType(VersionType.EXTERNAL).setSource("{value:1}").execute().actionGet();
	}

}
