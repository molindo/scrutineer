package at.molindo.scrutineer.sort;

import java.io.IOException;
import java.util.Iterator;

import at.molindo.scrutineer.IdAndVersion;

/**
 * wraps an {@link IdAndVersionDataReader} and turns it into an {@link Iterator}
 *
 */
public class IdAndVersionInputStreamIterator implements Iterator<IdAndVersion> {

	private final IdAndVersionDataReader idAndVersionDataReader;
	private IdAndVersion currentValue;

	public IdAndVersionInputStreamIterator(final IdAndVersionDataReader idAndVersionDataReader) {
		try {
			this.idAndVersionDataReader = idAndVersionDataReader;
			currentValue = idAndVersionDataReader.readNext();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasNext() {
		return currentValue != null;
	}

	@Override
	public IdAndVersion next() {
		try {
			final IdAndVersion result = currentValue;
			currentValue = idAndVersionDataReader.readNext();
			return result;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
