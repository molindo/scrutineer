package com.aconex.scrutineer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class LongIdAndVersionTest {

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

}
