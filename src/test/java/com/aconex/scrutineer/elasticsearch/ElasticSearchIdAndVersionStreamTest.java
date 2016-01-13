package com.aconex.scrutineer.elasticsearch;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.commons.lang.SystemUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.aconex.scrutineer.IdAndVersion;

@SuppressWarnings("unsafe")
public class ElasticSearchIdAndVersionStreamTest {

	private static final String UNSORTED_FILENAME = "elastic-search-unsorted.dat";

	private static final String SORTED_FILENAME = "elastic-search-sorted.dat";

	@Mock
	private ElasticSearchDownloader elasticSearchDownloader;
	@Mock
	private ElasticSearchSorter elasticSearchSorter;
	@Mock
	private IteratorFactory iteratorFactory;
	@Mock
	private Iterator<IdAndVersion> iterator;
	@Mock
	private OutputStream sortedOutputStream;
	@Mock
	private OutputStream unSortedOutputStream;
	@Mock
	private InputStream unSortedInputStream;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void shouldDownloadAndSortOnSetup() throws IOException {
		final ElasticSearchIdAndVersionStream elasticSearchIdAndVersionStream = spy(new ElasticSearchIdAndVersionStream(elasticSearchDownloader, elasticSearchSorter, iteratorFactory, SystemUtils
				.getJavaIoTmpDir().getAbsolutePath()));
		doReturn(sortedOutputStream).when(elasticSearchIdAndVersionStream).createSortedOutputStream();
		doReturn(unSortedOutputStream).when(elasticSearchIdAndVersionStream).createUnsortedOutputStream();
		doReturn(unSortedInputStream).when(elasticSearchIdAndVersionStream).createUnSortedInputStream();
		elasticSearchIdAndVersionStream.open();
		verify(elasticSearchDownloader).downloadTo(unSortedOutputStream);
		verify(elasticSearchSorter).sort(unSortedInputStream, sortedOutputStream);
	}

	@Test
	public void shouldReturnAFileIterator() throws IOException {
		final ElasticSearchIdAndVersionStream elasticSearchIdAndVersionStream = new ElasticSearchIdAndVersionStream(elasticSearchDownloader, elasticSearchSorter, iteratorFactory, SystemUtils
				.getJavaIoTmpDir().getAbsolutePath());
		when(iteratorFactory.forFile(new File(SystemUtils.getJavaIoTmpDir(), SORTED_FILENAME))).thenReturn(iterator);
		assertThat(elasticSearchIdAndVersionStream.iterator(), is(iterator));
	}

}
