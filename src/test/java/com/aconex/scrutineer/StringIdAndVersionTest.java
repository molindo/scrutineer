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

public class StringIdAndVersionTest {

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
		final StringIdAndVersion idAndVersion1 = new StringIdAndVersion("2", 3);
		final StringIdAndVersion idAndVersion2 = new StringIdAndVersion("2", 3);
		assertThat(idAndVersion1, is(idAndVersion2));
		assertThat(idAndVersion1.hashCode(), is(idAndVersion2.hashCode()));
	}

	@Test
	public void shouldNotBeEqualWhenIdsDiffer() {
		final StringIdAndVersion idAndVersion1 = new StringIdAndVersion("2", 3);
		final StringIdAndVersion idAndVersion2 = new StringIdAndVersion("3", 3);
		assertThat(idAndVersion1, is(not(idAndVersion2)));
		assertThat(idAndVersion1.hashCode(), is(not(idAndVersion2.hashCode())));
	}

	@Test
	public void shouldNotBeEqualWhenVersionsDiffer() {
		final StringIdAndVersion idAndVersion1 = new StringIdAndVersion("2", 2);
		final StringIdAndVersion idAndVersion2 = new StringIdAndVersion("2", 15);
		assertThat(idAndVersion1, is(not(idAndVersion2)));
		assertThat(idAndVersion1.hashCode(), is(not(idAndVersion2.hashCode())));
	}

	@Test
	public void shouldGet0WhenComapringEqualObjects() {
		final StringIdAndVersion idAndVersion1 = new StringIdAndVersion("2", 3);
		final StringIdAndVersion idAndVersion2 = new StringIdAndVersion("2", 3);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(0));
	}

	@Test
	public void shouldGetn1WhenComapringLesserIds() {
		final StringIdAndVersion idAndVersion1 = new StringIdAndVersion("2", 3);
		final StringIdAndVersion idAndVersion2 = new StringIdAndVersion("3", 3);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(-1));
	}

	@Test
	public void shouldGetn1WhenComapringLesserVersions() {
		final StringIdAndVersion idAndVersion1 = new StringIdAndVersion("2", 3);
		final StringIdAndVersion idAndVersion2 = new StringIdAndVersion("2", 4);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(-1));
	}

	@Test
	public void shouldGet1WhenComapringGreaterIds() {
		final StringIdAndVersion idAndVersion1 = new StringIdAndVersion("2", 3);
		final StringIdAndVersion idAndVersion2 = new StringIdAndVersion("1", 3);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(1));
	}

	@Test
	public void shouldGet1WhenComapringGreaterVersions() {
		final StringIdAndVersion idAndVersion1 = new StringIdAndVersion("2", 3);
		final StringIdAndVersion idAndVersion2 = new StringIdAndVersion("2", 2);
		assertThat(idAndVersion1.compareTo(idAndVersion2), is(1));
	}

	@Test(expected = NullPointerException.class)
	public void shouldThrowNPEWhenComparedToNull() {
		final StringIdAndVersion idAndVersion = new StringIdAndVersion("2", 3);
		idAndVersion.compareTo(null);
	}

	@Test
	public void shouldPrintTheIdAndVersionInToString() {
		final StringIdAndVersion idAndVersion = new StringIdAndVersion("2", 3);
		assertThat(idAndVersion.toString(), is("2:3"));
	}

	@Test
	public void shouldWriteToOutputStream() throws IOException {
		final StringIdAndVersion idAndVersion = new StringIdAndVersion("2", 3);
		idAndVersion.writeToStream(objectOutputStream);
		verify(objectOutputStream).writeUTF(idAndVersion.getId());
		verify(objectOutputStream).writeLong(idAndVersion.getVersion());
	}

	@Test
	public void shouldReadFromInputStream() throws IOException {
		when(objectInputStream.readUTF()).thenReturn("10");
		when(objectInputStream.readLong()).thenReturn(10L);

		final IdAndVersion idAndVersion = StringIdAndVersion.FACTORY.readFromStream(objectInputStream);

		assertThat(idAndVersion.getId(), is("10"));
		assertThat(idAndVersion.getVersion(), is(10L));
	}
}
