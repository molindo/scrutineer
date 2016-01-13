package com.aconex.scrutineer.elasticsearch;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;

public class IteratorFactory {

	private final IdAndVersionFactory factory;

	public IteratorFactory(final IdAndVersionFactory factory) {
		this.factory = factory;
	}

	public Iterator<IdAndVersion> forFile(final File file) {
		try {
			return new IdAndVersionInputStreamIterator(new IdAndVersionDataReader(factory, new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
