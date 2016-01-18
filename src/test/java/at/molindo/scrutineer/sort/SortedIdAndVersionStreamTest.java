package at.molindo.scrutineer.sort;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.IOException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import at.molindo.scrutineer.IdAndVersion;
import at.molindo.scrutineer.IdAndVersionFactory;
import at.molindo.scrutineer.IdAndVersionStream;
import at.molindo.scrutineer.LongIdAndVersion;
import at.molindo.scrutineer.LongIdAndVersionFactory;
import at.molindo.scrutineer.sort.SortedIdAndVersionStream;
import at.molindo.scrutineer.stream.IterableIdAndVersionStream;

@SuppressWarnings("unsafe")
public class SortedIdAndVersionStreamTest {

	private final ImmutableList<LongIdAndVersion> list = ImmutableList
			.of(new LongIdAndVersion(2, 1), new LongIdAndVersion(1, 1));

	private final IdAndVersionFactory idAndVersionFactory = LongIdAndVersionFactory.INSTANCE;

	private final IdAndVersionStream unsortedStream = new IterableIdAndVersionStream(list);

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void shouldReturnSorted() throws IOException {
		final SortedIdAndVersionStream sortedIdAndVersionStream = spy(new SortedIdAndVersionStream(idAndVersionFactory, unsortedStream));

		sortedIdAndVersionStream.open();

		try {
			final Iterator<IdAndVersion> iter = sortedIdAndVersionStream.iterator();
			assertEquals(list.get(1), iter.next());
			assertEquals(list.get(0), iter.next());
		} finally {
			sortedIdAndVersionStream.close();
		}
	}

}
