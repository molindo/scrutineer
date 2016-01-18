package com.aconex.scrutineer.sort;

import java.io.IOException;
import java.io.ObjectOutputStream;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.fasterxml.sort.DataWriter;

public class IdAndVersionDataWriter extends DataWriter<IdAndVersion> {
	private final ObjectOutputStream objectOutputStream;
	private final IdAndVersionFactory idAndVersionFactory;

	public IdAndVersionDataWriter(final ObjectOutputStream objectOutputStream, final IdAndVersionFactory idAndVersionFactory) {
		this.objectOutputStream = objectOutputStream;
		this.idAndVersionFactory = idAndVersionFactory;
	}

	@Override
	public void writeEntry(final IdAndVersion item) throws IOException {
		idAndVersionFactory.writeToStream(item, objectOutputStream);
	}

	@Override
	public void close() throws IOException {
		objectOutputStream.close();
	}
}
