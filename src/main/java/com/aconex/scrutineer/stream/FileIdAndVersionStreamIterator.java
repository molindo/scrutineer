package com.aconex.scrutineer.stream;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;

public class FileIdAndVersionStreamIterator implements Iterator<IdAndVersion> {

	private final ObjectInputStream stream;
	private final IdAndVersionFactory idAndVersionFactory;

	private IdAndVersion next;

	public FileIdAndVersionStreamIterator(final IdAndVersionFactory idAndVersionFactory, final ObjectInputStream stream) {
		this.idAndVersionFactory = idAndVersionFactory;
		this.stream = stream;
		next = readNext();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IdAndVersion next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		try {
			return next;
		} finally {
			next = readNext();
		}
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	private IdAndVersion readNext() {
		try {
			return idAndVersionFactory.readFromStream(stream);
		} catch (final EOFException e) {
			return null;
		} catch (final IOException e) {
			throw new RuntimeException("failed to read from stream", e);
		}
	}
}
