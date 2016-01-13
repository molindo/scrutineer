package com.aconex.scrutineer;

import java.io.IOException;
import java.io.ObjectInputStream;

public enum LongIdAndVersionFactory implements IdAndVersionFactory {

	INSTANCE;

	@Override
	public LongIdAndVersion create(final Object id, final long version) {
		return new LongIdAndVersion(toLong(id), version);
	}

	private long toLong(final Object id) {
		if (id instanceof Number) {
			return ((Number) id).longValue();
		} else {
			return Long.parseLong(id.toString());
		}
	}

	@Override
	public LongIdAndVersion readFromStream(final ObjectInputStream inputStream) throws IOException {
		return new LongIdAndVersion(inputStream.readLong(), inputStream.readLong());
	}
}
