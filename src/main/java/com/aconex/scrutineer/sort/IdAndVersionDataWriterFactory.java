package com.aconex.scrutineer.sort;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.fasterxml.sort.DataWriter;
import com.fasterxml.sort.DataWriterFactory;

public class IdAndVersionDataWriterFactory extends DataWriterFactory<IdAndVersion> {
	private final IdAndVersionFactory idAndVersionFactory;

	public IdAndVersionDataWriterFactory(final IdAndVersionFactory idAndVersionFactory) {
		this.idAndVersionFactory = idAndVersionFactory;
	}

	@Override
	public DataWriter<IdAndVersion> constructWriter(final OutputStream outputStream) throws IOException {
		return new IdAndVersionDataWriter(new ObjectOutputStream(outputStream), idAndVersionFactory);
	}
}
