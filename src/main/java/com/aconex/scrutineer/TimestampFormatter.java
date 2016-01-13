package com.aconex.scrutineer;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.google.common.base.Function;

public class TimestampFormatter implements Function<Long, Object> {

	private final DateTimeFormatter dateTimeFormatter;

	public TimestampFormatter() {
		this(DateTimeZone.getDefault());
	}

	public TimestampFormatter(final DateTimeZone dateTimeZone) {
		dateTimeFormatter = ISODateTimeFormat.dateTime().withZone(dateTimeZone);
	}

	@Override
	public Object apply(final Long timestamp) {
		final String timestampFormatted = dateTimeFormatter.print(timestamp);
		return String.format("%d(%s)", timestamp, timestampFormatted);
	}
}
