package com.aconex.scrutineer;

import org.elasticsearch.common.joda.time.format.ISODateTimeFormat;

import com.aconex.scrutineer.javautil.SystemTimeSource;
import com.aconex.scrutineer.javautil.TimeSource;

public class CoincidentFilteredStreamVerifierListener implements IdAndVersionStreamVerifierListener {

	private static final org.slf4j.Logger LOGGER = LogUtils.loggerForThisClass();

	private final IdAndVersionStreamVerifierListener otherListener;
	private final long runStartTime;

	public CoincidentFilteredStreamVerifierListener(final IdAndVersionStreamVerifierListener otherListener) {
		this(new SystemTimeSource(), otherListener);
	}

	public CoincidentFilteredStreamVerifierListener(final TimeSource timeSource, final IdAndVersionStreamVerifierListener otherListener) {
		this.otherListener = otherListener;
		runStartTime = timeSource.getCurrentTime();
		LogUtils.info(LOGGER, "Will suppress any inconsistency detected on or after %s", ISODateTimeFormat.dateTime()
				.print(runStartTime));

	}

	@Override
	public void onMissingInSecondaryStream(final IdAndVersion idAndVersion) {
		if (idAndVersion.getVersion() < runStartTime) {
			otherListener.onMissingInSecondaryStream(idAndVersion);
		}
	}

	@Override
	public void onMissingInPrimaryStream(final IdAndVersion idAndVersion) {
		if (idAndVersion.getVersion() < runStartTime) {
			otherListener.onMissingInPrimaryStream(idAndVersion);
		}
	}

	@Override
	public void onVersionMisMatch(final IdAndVersion primaryItem, final IdAndVersion secondaryItem) {
		if (primaryItem.getVersion() < runStartTime && secondaryItem.getVersion() < runStartTime) {
			otherListener.onVersionMisMatch(primaryItem, secondaryItem);
		}
	}
}
