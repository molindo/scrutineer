package com.aconex.scrutineer;

public class IdAndVersionConsistencyFailure {

	private static final int EXPECTED_NUM_FIELDS = 3;
	private final ConsistencyFailureMode failureMode;
	private final String primaryId;
	private final String primaryVersion;

	public IdAndVersionConsistencyFailure(final ConsistencyFailureMode failureMode, final String primaryId, final String primaryVersion) {
		this.failureMode = failureMode;
		this.primaryId = primaryId;
		this.primaryVersion = primaryVersion;
	}

	public static IdAndVersionConsistencyFailure fromString(final String s) {

		final String[] fields = s.split("\t");
		if (fields == null || fields.length < EXPECTED_NUM_FIELDS) {
			throw new IllegalArgumentException(String
					.format("String '%s' does not look like a tab-separated String with enough fields (3)", s));
		}
		final ConsistencyFailureMode failureMode = ConsistencyFailureMode.valueOf(fields[0]);
		return new IdAndVersionConsistencyFailure(failureMode, fields[1], fields[2]);
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public String getPrimaryVersion() {
		return primaryVersion;

	}

	public boolean isNotInPrimary() {
		return failureMode == ConsistencyFailureMode.NOTINPRIMARY;
	}

	public boolean isMismatch() {
		return failureMode == ConsistencyFailureMode.MISMATCH;
	}

	public static enum ConsistencyFailureMode {
		NOTINSECONDARY, NOTINPRIMARY, MISMATCH,
	}

	public boolean isNotInSecondary() {
		return failureMode == ConsistencyFailureMode.NOTINSECONDARY;
	}

}
