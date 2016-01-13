package com.aconex.scrutineer.javautil;

import java.util.Iterator;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionStream;

public class JavaIteratorIdAndVersionStream implements IdAndVersionStream {
	private final Iterator<IdAndVersion> iterator;

	public JavaIteratorIdAndVersionStream(final Iterator<IdAndVersion> iterator) {
		this.iterator = iterator;
	}

	@Override
	public void open() {
		// Empty
	}

	@Override
	public Iterator<IdAndVersion> iterator() {
		return iterator;
	}

	@Override
	public void close() {
		// Empty
	}
}
