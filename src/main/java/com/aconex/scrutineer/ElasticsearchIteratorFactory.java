package com.aconex.scrutineer;

import java.util.Iterator;

public interface ElasticsearchIteratorFactory {

	/**
	 * 
	 * 
	 * @param idAndVersionFactory
	 *            the {@link IdAndVersionFactory} to {@link IdAndVersionFactory#create(Object, long) create} new
	 *            {@link IdAndVersion} objects
	 * @return an {@link Iterator} that never returns <code>null</code> elements
	 */
	Iterator<IdAndVersion> iterate(IdAndVersionFactory idAndVersionFactory);

}
