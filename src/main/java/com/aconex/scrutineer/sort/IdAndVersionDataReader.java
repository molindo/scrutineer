package com.aconex.scrutineer.sort;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.aconex.scrutineer.stream.FileIdAndVersionStream;
import com.fasterxml.sort.DataReader;

public class IdAndVersionDataReader extends DataReader<IdAndVersion> {

	private static final int BYTES_PER_CHAR = 2;
	private static final int BYTES_PER_LONG = 8;
	private static final int BYTES_PER_OBJECT_POINTER = 24;
	private static final int BYTES_PER_ARRAY_POINTER = 28;

	private final FileIdAndVersionStream stream;
	private Iterator<IdAndVersion> iterator;

	public IdAndVersionDataReader(final IdAndVersionFactory factory, final ObjectInputStream objectInputStream) {
		stream = new FileIdAndVersionStream(factory, objectInputStream);
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
	public IdAndVersion readNext() throws IOException {
		if (iterator == null) {
			iterator = stream.iterator();
		}
		return iterator.hasNext() ? iterator.next() : null;
	}

	@Override
	public void close() throws IOException {
		iterator = null;
		stream.close();
	}

}
