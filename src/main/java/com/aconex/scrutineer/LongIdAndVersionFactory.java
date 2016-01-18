package com.aconex.scrutineer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

	@Override
	public void writeToStream(final IdAndVersion idAndVersion, final ObjectOutputStream objectOutputStream) throws IOException {
		objectOutputStream.writeLong(((LongIdAndVersion) idAndVersion).getLongId());
		objectOutputStream.writeLong(idAndVersion.getVersion());
	}

}
