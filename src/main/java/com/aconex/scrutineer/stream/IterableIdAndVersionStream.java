package com.aconex.scrutineer.stream;

import java.util.Arrays;
import java.util.Iterator;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionStream;

public class IterableIdAndVersionStream implements IdAndVersionStream {
	private final Iterable<IdAndVersion> iterable;

	public IterableIdAndVersionStream(final IdAndVersion... idAndVersions) {
		iterable = Arrays.asList(idAndVersions);
	}

	@SuppressWarnings("unchecked")
	public IterableIdAndVersionStream(final Iterable<? extends IdAndVersion> iterable) {
		this.iterable = (Iterable<IdAndVersion>) iterable;
	}

	@Override
	public void open() {
		// Empty
	}

	@Override
	public Iterator<IdAndVersion> iterator() {
		return iterable.iterator();
	}

	@Override
	public void close() {
		// Empty
	}
}
