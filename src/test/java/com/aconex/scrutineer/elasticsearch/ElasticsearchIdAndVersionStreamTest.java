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
public class ElasticsearchIdAndVersionStreamTest {

	private static final String SORTED_FILENAME = "elastic-search-sorted.dat";

	@Mock
	private ElasticsearchDownloader elasticsearchDownloader;
	@Mock
	private ElasticsearchSorter elasticsearchSorter;
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
		final ElasticsearchIdAndVersionStream elasticsearchIdAndVersionStream = spy(new ElasticsearchIdAndVersionStream(elasticsearchDownloader, elasticsearchSorter, iteratorFactory, SystemUtils
				.getJavaIoTmpDir().getAbsolutePath()));
		doReturn(sortedOutputStream).when(elasticsearchIdAndVersionStream).createSortedOutputStream();
		doReturn(unSortedOutputStream).when(elasticsearchIdAndVersionStream).createUnsortedOutputStream();
		doReturn(unSortedInputStream).when(elasticsearchIdAndVersionStream).createUnSortedInputStream();
		elasticsearchIdAndVersionStream.open();
		verify(elasticsearchDownloader).downloadTo(unSortedOutputStream);
		verify(elasticsearchSorter).sort(unSortedInputStream, sortedOutputStream);
	}

	@Test
	public void shouldReturnAFileIterator() throws IOException {
		final ElasticsearchIdAndVersionStream elasticsearchIdAndVersionStream = new ElasticsearchIdAndVersionStream(elasticsearchDownloader, elasticsearchSorter, iteratorFactory, SystemUtils
				.getJavaIoTmpDir().getAbsolutePath());
		when(iteratorFactory.forFile(new File(SystemUtils.getJavaIoTmpDir(), SORTED_FILENAME))).thenReturn(iterator);
		assertThat(elasticsearchIdAndVersionStream.iterator(), is(iterator));
	}

}
