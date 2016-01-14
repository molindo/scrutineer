package com.aconex.scrutineer.sort;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

import java.io.IOException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.aconex.scrutineer.IdAndVersionStream;
import com.aconex.scrutineer.LongIdAndVersion;
import com.aconex.scrutineer.LongIdAndVersionFactory;
import com.aconex.scrutineer.stream.IterableIdAndVersionStream;
import com.google.common.collect.ImmutableList;

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
