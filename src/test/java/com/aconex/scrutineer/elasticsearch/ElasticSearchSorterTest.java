package com.aconex.scrutineer.elasticsearch;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.aconex.scrutineer.IdAndVersion;
import com.fasterxml.sort.Sorter;
import com.google.common.io.CountingInputStream;

public class ElasticSearchSorterTest {

	@Mock
	private Sorter<IdAndVersion> sorter;
	@Mock
	private InputStream inputstream;
	@Mock
	private OutputStream outputstream;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void shouldSortInputStream() throws IOException {
		final ElasticSearchSorter elasticSearchSorter = new ElasticSearchSorter(sorter);
		elasticSearchSorter.sort(inputstream, outputstream);
		verify(sorter).sort(isA(CountingInputStream.class), eq(outputstream));
	}
}
