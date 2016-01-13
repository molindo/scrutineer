package com.aconex.scrutineer.elasticsearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.LogUtils;
import com.fasterxml.sort.Sorter;
import com.google.common.io.CountingInputStream;

public class ElasticsearchSorter {

	private static final Logger LOG = LogUtils.loggerForThisClass();

	private final Sorter<IdAndVersion> sorter;

	public ElasticsearchSorter(final Sorter<IdAndVersion> sorter) {
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
