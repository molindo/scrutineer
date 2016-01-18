package at.molindo.scrutineer.sort;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.slf4j.Logger;

import at.molindo.scrutineer.IdAndVersion;
import at.molindo.scrutineer.IdAndVersionFactory;
import at.molindo.scrutineer.IdAndVersionStream;
import at.molindo.scrutineer.LogUtils;

public class StreamDownloader {

	private static final Logger LOG = LogUtils.loggerForThisClass();

	static final int BATCH_SIZE = 100000;
	static final int SCROLL_TIME_IN_MINUTES = 10;

	private final IdAndVersionStream idAndVersionStream;
	private final IdAndVersionFactory idAndVersionFactory;

	public StreamDownloader(final IdAndVersionStream idAndVersionStream, final IdAndVersionFactory idAndVersionFactory) {
		this.idAndVersionStream = idAndVersionStream;
		this.idAndVersionFactory = idAndVersionFactory;
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
					idAndVersionFactory.writeToStream(iterator.next(), objectOutputStream);
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
