package at.molindo.scrutineer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import at.molindo.scrutineer.StringIdAndVersion;

public class StringIdAndVersionTest {

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

}
