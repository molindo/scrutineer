package at.molindo.scrutineer.sort;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.sort.Sorter;
import com.google.common.io.CountingInputStream;

import at.molindo.scrutineer.IdAndVersion;
import at.molindo.scrutineer.sort.StreamSorter;

public class StreamSorterTest {

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
		final StreamSorter elasticsearchSorter = new StreamSorter(sorter);
		elasticsearchSorter.sort(inputstream, outputstream);
		verify(sorter).sort(isA(CountingInputStream.class), eq(outputstream));
	}
}
