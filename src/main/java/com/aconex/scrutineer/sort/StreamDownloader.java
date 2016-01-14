package com.aconex.scrutineer.sort;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.slf4j.Logger;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionStream;
import com.aconex.scrutineer.LogUtils;

public class StreamDownloader {

	private static final Logger LOG = LogUtils.loggerForThisClass();

	static final int BATCH_SIZE = 100000;
	static final int SCROLL_TIME_IN_MINUTES = 10;

	private final IdAndVersionStream idAndVersionStream;

	public StreamDownloader(final IdAndVersionStream idAndVersionStream) {
		this.idAndVersionStream = idAndVersionStream;
	}

	public void downloadTo(final OutputStream outputStream) {
		final long begin = System.currentTimeMillis();
		final long numItems = doDownloadTo(outputStream);
		LogUtils.infoTimeTaken(LOG, begin, numItems, "Scan & Download completed");
	}

	@SuppressWarnings("PMD.NcssMethodCount")
	private long doDownloadTo(final OutputStream outputStream) {
		try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
			idAndVersionStream.open();
			try {
				long numItems = 0;
				final Iterator<IdAndVersion> iterator = idAndVersionStream.iterator();
				while (iterator.hasNext()) {
					iterator.next().writeToStream(objectOutputStream);
					numItems++;
				}
				return numItems;
			} finally {
				idAndVersionStream.close();
			}
		} catch (final IOException e) {
			throw new RuntimeException("downloading stream failed", e);
		}
	}

}
