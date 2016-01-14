package com.aconex.scrutineer.sort;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.StringIdAndVersion;
import com.aconex.scrutineer.sort.IdAndVersionDataReader;
import com.aconex.scrutineer.sort.IdAndVersionInputStreamIterator;

public class IdAndVersionInputStreamIteratorTest {

	private static final String ID = "12";
	private static final long VERSION = 77L;
	@Mock
	private IdAndVersionDataReader idAndVersionDataReader;

	@Before
	public void setup() throws IOException {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldThrowUnsupportedExceptionOnRemove() {
		final IdAndVersionInputStreamIterator idAndVersionInputStreamIterator = new IdAndVersionInputStreamIterator(idAndVersionDataReader);
		idAndVersionInputStreamIterator.remove();
	}

	@Test
	public void hasNextShouldReturnFalseForAnEmptyStream() throws IOException {
		when(idAndVersionDataReader.readNext()).thenReturn(null);
		final IdAndVersionInputStreamIterator idAndVersionInputStreamIterator = new IdAndVersionInputStreamIterator(idAndVersionDataReader);
		assertThat(idAndVersionInputStreamIterator.hasNext(), is(false));
	}

	@Test
	public void hasNextShouldReturnTrueForNonEmptyStream() throws IOException {
		when(idAndVersionDataReader.readNext()).thenReturn(new StringIdAndVersion(ID, VERSION));
		final IdAndVersionInputStreamIterator idAndVersionInputStreamIterator = new IdAndVersionInputStreamIterator(idAndVersionDataReader);
		assertThat(idAndVersionInputStreamIterator.hasNext(), is(true));
	}

	@Test
	public void shouldGetTheNextItem() throws IOException {
		final IdAndVersion idAndVersion = new StringIdAndVersion(ID, VERSION);
		when(idAndVersionDataReader.readNext()).thenReturn(idAndVersion);
		final IdAndVersionInputStreamIterator idAndVersionInputStreamIterator = new IdAndVersionInputStreamIterator(idAndVersionDataReader);
		assertThat(idAndVersionInputStreamIterator.next(), is(idAndVersion));
	}

	@Test
	public void hasNextShouldReturnFalseAtEndOfTheStream() throws IOException {
		final IdAndVersion idAndVersion = new StringIdAndVersion(ID, VERSION);
		when(idAndVersionDataReader.readNext()).thenReturn(idAndVersion).thenReturn(null);
		final IdAndVersionInputStreamIterator idAndVersionInputStreamIterator = new IdAndVersionInputStreamIterator(idAndVersionDataReader);
		assertThat(idAndVersionInputStreamIterator.next(), is(idAndVersion));
		assertThat(idAndVersionInputStreamIterator.hasNext(), is(false));
	}

}
