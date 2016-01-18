package at.molindo.scrutineer.sort;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import com.fasterxml.sort.DataReader;
import com.fasterxml.sort.DataReaderFactory;

import at.molindo.scrutineer.IdAndVersion;
import at.molindo.scrutineer.IdAndVersionFactory;

public class IdAndVersionDataReaderFactory extends DataReaderFactory<IdAndVersion> {

	private final IdAndVersionFactory factory;

	public IdAndVersionDataReaderFactory(final IdAndVersionFactory factory) {
		this.factory = factory;
	}

	@Override
	public DataReader<IdAndVersion> constructReader(final InputStream inputStream) throws IOException {
		return new IdAndVersionDataReader(factory, new ObjectInputStream(inputStream));

	}
}
