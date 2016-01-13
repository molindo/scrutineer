package com.aconex.scrutineer.javautil;

public class ControlledTimeSource implements TimeSource {
	private long expectedTime;

	public ControlledTimeSource(final long expectedTime) {
		this.expectedTime = expectedTime;
	}

	@Override
	public long getCurrentTime() {
		return expectedTime;
	}

	public void setCurrentTime(final long currentTime) {
		expectedTime = currentTime;
	}
}
