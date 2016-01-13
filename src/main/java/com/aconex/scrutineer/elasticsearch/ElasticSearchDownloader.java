package com.aconex.scrutineer.elasticsearch;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.slf4j.Logger;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.aconex.scrutineer.LogUtils;

public abstract class ElasticSearchDownloader {

	private static final Logger LOG = LogUtils.loggerForThisClass();

	static final int BATCH_SIZE = 100000;
	static final int SCROLL_TIME_IN_MINUTES = 10;
	private long numItems = 0;

	private final IdAndVersionFactory idAndVersionFactory;

	public ElasticSearchDownloader(final IdAndVersionFactory idAndVersionFactory) {
		this.idAndVersionFactory = idAndVersionFactory;
	}

	public void downloadTo(final OutputStream outputStream) {
		final long begin = System.currentTimeMillis();
		doDownloadTo(outputStream);
		LogUtils.infoTimeTaken(LOG, begin, numItems, "Scan & Download completed");
	}

	private void doDownloadTo(final OutputStream outputStream) {
		try {
			final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			consumeBatches(objectOutputStream, iterate(idAndVersionFactory));
			objectOutputStream.close();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	void consumeBatches(final ObjectOutputStream objectOutputStream, final Iterator<IdAndVersion> iterator) throws IOException {
		while (iterator.hasNext()) {
			iterator.next().writeToStream(objectOutputStream);
			numItems++;
		}
	}

	/**
	 * 
	 * 
	 * @param idAndVersionFactory
	 *            the {@link IdAndVersionFactory} to {@link IdAndVersionFactory#create(Object, long) create} new
	 *            {@link IdAndVersion} objects
	 * @return an {@link Iterator} that never returns <code>null</code> elements
	 */
	protected abstract Iterator<IdAndVersion> iterate(IdAndVersionFactory idAndVersionFactory);

}
