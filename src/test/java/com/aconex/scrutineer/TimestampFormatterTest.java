package com.aconex.scrutineer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.elasticsearch.common.joda.time.DateTimeZone;
import org.junit.Test;

public class TimestampFormatterTest {

	@Test
	public void shouldConvertVersionToTimestamp() {
		final Object timestamp = new TimestampFormatter(DateTimeZone.forOffsetHours(4)).apply(0L);

		assertThat(timestamp, instanceOf(String.class));
		assertThat(timestamp.toString(), is("0(1970-01-01T04:00:00.000+04:00)"));
	}
}
