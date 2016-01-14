package com.aconex.scrutineer.sort;

import java.io.IOException;
import java.io.ObjectOutputStream;

import com.aconex.scrutineer.IdAndVersion;
import com.fasterxml.sort.DataWriter;

public class IdAndVersionDataWriter extends DataWriter<IdAndVersion> {
	private final ObjectOutputStream objectOutputStream;

	public IdAndVersionDataWriter(final ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	@Override
	public void writeEntry(final IdAndVersion item) throws IOException {
		item.writeToStream(objectOutputStream);
	}

	@Override
	public void close() throws IOException {
		objectOutputStream.close();
	}
}
