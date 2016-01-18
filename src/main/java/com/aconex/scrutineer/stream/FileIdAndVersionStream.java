package com.aconex.scrutineer.stream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

import org.slf4j.Logger;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.aconex.scrutineer.IdAndVersionStream;
import com.aconex.scrutineer.LogUtils;

public class FileIdAndVersionStream implements IdAndVersionStream {

	private static final Logger LOG = LogUtils.loggerForThisClass();

	private final IdAndVersionFactory idAndVersionFactory;
	private final ObjectInputStream stream;

	private final boolean sorted;

	public FileIdAndVersionStream(final IdAndVersionFactory idAndVersionFactory, final ObjectInputStream stream) {
		this(idAndVersionFactory, stream, false);
	}

	public FileIdAndVersionStream(final IdAndVersionFactory idAndVersionFactory, final ObjectInputStream stream, final boolean sorted) {
		this.idAndVersionFactory = idAndVersionFactory;
		this.stream = stream;
		this.sorted = sorted;
	}

	@Override
	public boolean isSorted() {
		return sorted;
	}

	@Override
	public void open() {
	}

	@Override
	public Iterator<IdAndVersion> iterator() {
		return new FileIdAndVersionStreamIterator(idAndVersionFactory, stream);
	}

	@Override
	public void close() {
		try {
			stream.close();
		} catch (final IOException e) {
			LOG.warn("failed to close steram", e);
		}
	}

}
