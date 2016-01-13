package com.aconex.scrutineer.elasticsearch;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.slf4j.Logger;

import com.aconex.scrutineer.ElasticsearchIteratorFactory;
import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.aconex.scrutineer.LogUtils;

public class ElasticsearchDownloader {

	private static final Logger LOG = LogUtils.loggerForThisClass();

	static final int BATCH_SIZE = 100000;
	static final int SCROLL_TIME_IN_MINUTES = 10;
	private long numItems = 0;

	private final IdAndVersionFactory idAndVersionFactory;

	private final ElasticsearchIteratorFactory iteratorFactory;

	public ElasticsearchDownloader(final IdAndVersionFactory idAndVersionFactory, final ElasticsearchIteratorFactory iteratorFactory) {
		this.idAndVersionFactory = idAndVersionFactory;
		this.iteratorFactory = iteratorFactory;
	}

	public void downloadTo(final OutputStream outputStream) {
		final long begin = System.currentTimeMillis();
		doDownloadTo(outputStream);
		LogUtils.infoTimeTaken(LOG, begin, numItems, "Scan & Download completed");
	}

	private void doDownloadTo(final OutputStream outputStream) {
		try {
			final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			consumeBatches(objectOutputStream, iteratorFactory.iterate(idAndVersionFactory));
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

}
