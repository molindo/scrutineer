package at.molindo.scrutineer.sort;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.fasterxml.sort.DataWriter;
import com.fasterxml.sort.DataWriterFactory;

import at.molindo.scrutineer.IdAndVersion;
import at.molindo.scrutineer.IdAndVersionFactory;

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
