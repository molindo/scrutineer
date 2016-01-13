package com.aconex.scrutineer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class IdAndVersionConsistencyFailureTest {

	private static final String NOT_IN_SECONDARY_EXAMPLE = "NOTINSECONDARY\t123\t456";
	private static final String NOT_IN_PRIMARY_EXAMPLE = "NOTINPRIMARY\t%s\t%d";
	private static final String MISMATCH_EXAMPLE = "MISMATCH\t123\t456\tsecondaryVersion=3645";

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfStringIsInvalid() {
		IdAndVersionConsistencyFailure.fromString("foo");
	}

	@Test
	public void shouldParseNotInSecondaryFormat() {
		final IdAndVersionConsistencyFailure idAndVersionConsistencyFailure = IdAndVersionConsistencyFailure
				.fromString(NOT_IN_SECONDARY_EXAMPLE);
		assertThat(idAndVersionConsistencyFailure.isNotInPrimary(), is(false));
		assertThat(idAndVersionConsistencyFailure.isNotInSecondary(), is(true));
		assertThat(idAndVersionConsistencyFailure.isMismatch(), is(false));

	}

	@Test
	public void shouldReturnPrimaryId() {
		final IdAndVersionConsistencyFailure idAndVersionConsistencyFailure = IdAndVersionConsistencyFailure
				.fromString(NOT_IN_SECONDARY_EXAMPLE);
		assertThat(idAndVersionConsistencyFailure.getPrimaryId(), is("123"));
	}

	@Test
	public void shouldReturnPrimaryVersion() {
		final IdAndVersionConsistencyFailure idAndVersionConsistencyFailure = IdAndVersionConsistencyFailure
				.fromString(NOT_IN_SECONDARY_EXAMPLE);
		assertThat(idAndVersionConsistencyFailure.getPrimaryVersion(), is("456"));
	}

	@Test
	public void shouldParseNotInPrimaryFormat() {
		final IdAndVersionConsistencyFailure idAndVersionConsistencyFailure = IdAndVersionConsistencyFailure
				.fromString(NOT_IN_PRIMARY_EXAMPLE);
		assertThat(idAndVersionConsistencyFailure.isNotInPrimary(), is(true));
		assertThat(idAndVersionConsistencyFailure.isNotInSecondary(), is(false));
		assertThat(idAndVersionConsistencyFailure.isMismatch(), is(false));
	}

	@Test
	public void shouldParseMismatchFormat() {
		final IdAndVersionConsistencyFailure idAndVersionConsistencyFailure = IdAndVersionConsistencyFailure
				.fromString(MISMATCH_EXAMPLE);
		assertThat(idAndVersionConsistencyFailure.isNotInPrimary(), is(false));
		assertThat(idAndVersionConsistencyFailure.isNotInSecondary(), is(false));
		assertThat(idAndVersionConsistencyFailure.isMismatch(), is(true));
	}

}
