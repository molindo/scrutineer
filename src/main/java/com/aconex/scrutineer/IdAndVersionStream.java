package com.aconex.scrutineer;

import java.util.Iterator;

public interface IdAndVersionStream extends Iterable<IdAndVersion> {

	void open();

	@Override
	Iterator<IdAndVersion> iterator();

	void close();
}
