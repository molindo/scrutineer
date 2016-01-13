package com.aconex.scrutineer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LongIdAndVersionTest {

	@Mock
	ObjectOutputStream objectOutputStream;

	@Mock
	ObjectInputStream objectInputStream;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldBeEqualWhenIdAndVersionAreTheSame() {
		final LongIdAndVersion idAndVersion1 = new LongIdAndVersion(2L, 3);
		final LongIdAndVersion idAndVersion2 = new LongIdAndVersion(2L, 3);
		assertThat(idAndVersion1, is(idAndVersion2));
		assertThat(idAndVersion1.hashCode(), is(idAndVersion2.hashCode()));
	}

	@Test
	public void shouldNotBeEqualWhenIdsDiffer() {
		final LongIdAndVersion idAndVersion1 = new LongIdAndVersion(2L, 3);
		final LongIdAndVersion idAndVersion2 = new LongIdAndVersion(3L, 3);
		assertThat(idAndVersion1, is(not(idAndVersion2)));
		assertThat(idAndVersion1.hashCode(), is(not(idAndVersion2.hashCode())));
	}

	@Test
	public void shouldNotBeEqualWhenVersionsDiffer() {
		final LongIdAndVersion idAndVersion1 = new LongIdAndVersion(2L, 2);
		final LongIdAndVersion idAndVersion2 = new LongIdAndVersion(2L, 15);
		assertThat(idAndVersion1, is(not(idAndVersion2)));
		assertThat(idAndVersion1.hashCode(), is(not(idAndVersion2.hashCode())));
	}

	@Test
	public void shouldGet0WhenComapringEqualObjects() {
		final LongIdAndVersion idAndVersion1 = new LongIdAndVersion(2L, 3);
		final LongIdAndVersion idAndVersion2 = new LongIdAndVersion(2L, 3);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(0));
	}

	@Test
	public void shouldGetn1WhenComapringLesserIds() {
		final LongIdAndVersion idAndVersion1 = new LongIdAndVersion(2L, 3);
		final LongIdAndVersion idAndVersion2 = new LongIdAndVersion(3L, 3);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(-1));
	}

	@Test
	public void shouldGetn1WhenComapringLesserVersions() {
		final LongIdAndVersion idAndVersion1 = new LongIdAndVersion(2L, 3);
		final LongIdAndVersion idAndVersion2 = new LongIdAndVersion(2L, 4);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(-1));
	}

	@Test
	public void shouldGet1WhenComapringGreaterIds() {
		final LongIdAndVersion idAndVersion1 = new LongIdAndVersion(2L, 3);
		final LongIdAndVersion idAndVersion2 = new LongIdAndVersion(1L, 3);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(1));
	}

	@Test
	public void shouldGet1WhenComapringGreaterVersions() {
		final LongIdAndVersion idAndVersion1 = new LongIdAndVersion(2L, 3);
		final LongIdAndVersion idAndVersion2 = new LongIdAndVersion(2L, 2);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(1));
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNPEWhenComparedToNull() {
		final LongIdAndVersion idAndVersion = new LongIdAndVersion(2L, 3);
		idAndVersion.compareTo(null);
	}

	@Test
	public void shouldPrintTheIdAndVersionInToString() {
		final LongIdAndVersion idAndVersion = new LongIdAndVersion(2L, 3);
		assertThat(idAndVersion.toString(), is("2:3"));
	}

	@Test
	public void shouldWriteToOutputStream() throws IOException {
		final LongIdAndVersion idAndVersion = new LongIdAndVersion(2L, 3);
		idAndVersion.writeToStream(objectOutputStream);
		verify(objectOutputStream).writeLong(idAndVersion.getLongId());
		verify(objectOutputStream).writeLong(idAndVersion.getVersion());
	}

	@Test
	public void shouldReadFromInputStream() throws IOException {
		when(objectInputStream.readLong()).thenReturn(10L);
		when(objectInputStream.readLong()).thenReturn(10L);

		final IdAndVersion idAndVersion = LongIdAndVersion.FACTORY.readFromStream(objectInputStream);

		assertThat(idAndVersion.getId(), is("10"));
		assertThat(idAndVersion.getVersion(), is(10L));
	}
}
