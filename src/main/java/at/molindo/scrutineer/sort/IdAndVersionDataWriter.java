package at.molindo.scrutineer.sort;

import java.io.IOException;
import java.io.ObjectOutputStream;

import com.fasterxml.sort.DataWriter;

import at.molindo.scrutineer.IdAndVersion;
import at.molindo.scrutineer.IdAndVersionFactory;

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
