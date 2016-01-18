package at.molindo.scrutineer.sort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;

import com.fasterxml.sort.Sorter;
import com.google.common.io.CountingInputStream;

import at.molindo.scrutineer.IdAndVersion;
import at.molindo.scrutineer.LogUtils;

public class StreamSorter {

	private static final Logger LOG = LogUtils.loggerForThisClass();

	private final Sorter<IdAndVersion> sorter;

	public StreamSorter(final Sorter<IdAndVersion> sorter) {
		this.sorter = sorter;
	}

	public void sort(final InputStream inputStream, final OutputStream outputStream) {
		final long begin = System.currentTimeMillis();
		final CountingInputStream countingInputStream = new CountingInputStream(inputStream);
		doSort(countingInputStream, outputStream);
		LogUtils.infoTimeTaken(LOG, begin, countingInputStream
				.getCount(), "Sorted stream of %d bytes", countingInputStream.getCount());
	}

	private void doSort(final InputStream inputStream, final OutputStream outputStream) {
		try {
			sorter.sort(inputStream, outputStream);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
