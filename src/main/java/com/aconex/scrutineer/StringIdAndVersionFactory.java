package com.aconex.scrutineer;

import java.io.IOException;
import java.io.ObjectInputStream;

public enum StringIdAndVersionFactory implements IdAndVersionFactory {

	INSTANCE;

	@Override
	public StringIdAndVersion create(final Object id, final long version) {
		return new StringIdAndVersion(toString(id), version);
	}

	private String toString(final Object id) {
		if (id instanceof Number) {
			return ((Number) id).toString();
		} else {
			return id.toString();
		}
	}

	@Override
	public StringIdAndVersion readFromStream(final ObjectInputStream inputStream) throws IOException {
		return new StringIdAndVersion(inputStream.readUTF(), inputStream.readLong());
	}
}
