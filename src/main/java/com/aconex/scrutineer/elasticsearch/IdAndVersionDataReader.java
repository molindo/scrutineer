package com.aconex.scrutineer.elasticsearch;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.fasterxml.sort.DataReader;

class IdAndVersionDataReader extends DataReader<IdAndVersion> {

	private static final int BYTES_PER_CHAR = 2;
	private static final int BYTES_PER_LONG = 8;
	private static final int BYTES_PER_OBJECT_POINTER = 24;
	private static final int BYTES_PER_ARRAY_POINTER = 28;

	private final IdAndVersionFactory factory;
	private final ObjectInputStream objectInputStream;

	public IdAndVersionDataReader(final IdAndVersionFactory factory, final ObjectInputStream objectInputStream) {
		this.factory = factory;
		this.objectInputStream = objectInputStream;
	}

	@Override
	public IdAndVersion readNext() throws IOException {
		try {
			return factory.readFromStream(objectInputStream);
		} catch (final EOFException e) {
			return null;
		}
	}

	@Override
	public int estimateSizeInBytes(final IdAndVersion item) {
		final int idAndVersionContainerSize = BYTES_PER_OBJECT_POINTER;
		final int versionSize = BYTES_PER_LONG;
		final int idSize = BYTES_PER_OBJECT_POINTER + BYTES_PER_ARRAY_POINTER
				+ (item.getId().length() * BYTES_PER_CHAR);
		final int totalSize = idAndVersionContainerSize + versionSize + idSize;

		return totalSize;
	}

	@Override
	public void close() throws IOException {
		objectInputStream.close();
	}
}
