package com.aconex.scrutineer.sort;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Iterator;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;
import com.aconex.scrutineer.IdAndVersionStream;
import com.aconex.scrutineer.stream.FileIdAndVersionStream;
import com.fasterxml.sort.DataReaderFactory;
import com.fasterxml.sort.DataWriterFactory;
import com.fasterxml.sort.SortConfig;
import com.fasterxml.sort.Sorter;
import com.fasterxml.sort.util.NaturalComparator;

/**
 * {@link IdAndVersionStream} implementation that wraps another and sorts it using {@link Sorter}, writing the data to
 * temporary files
 */
public class SortedIdAndVersionStream implements IdAndVersionStream {

	private static final int DEFAULT_SORT_MEM = 256 * 1024 * 1024;

	private final IdAndVersionStream unsortedStream;
	private final IdAndVersionFactory idAndVersionFactory;
	private final File unsortedFile;
	private final File sortedFile;

	private IdAndVersionStream sortedStream;

	public SortedIdAndVersionStream(final IdAndVersionFactory idAndVersionFactory, final IdAndVersionStream unsortedStream) {
		this(idAndVersionFactory, unsortedStream, null);
	}

	public SortedIdAndVersionStream(final IdAndVersionFactory idAndVersionFactory, final IdAndVersionStream unsortedStream, final String workingDirectory) {
		this.idAndVersionFactory = idAndVersionFactory;
		this.unsortedStream = unsortedStream;
		try {
			unsortedFile = Files.createTempFile(workingDirectory, ".unsorted.dat").toFile();
			sortedFile = Files.createTempFile(workingDirectory, ".sorted.dat").toFile();
		} catch (final IOException e) {
			throw new RuntimeException("creating temporary files failed", e);
		}
	}

	/**
	 * download stream and sort it
	 */
	@Override
	public void open() {
		sort();
		sortedStream = createSortedStream();
	}

	private void sort() {
		unsortedStream.open();
		new StreamDownloader(unsortedStream).downloadTo(createUnsortedOutputStream());
		new StreamSorter(createSorter(idAndVersionFactory))
				.sort(createUnSortedInputStream(), createSortedOutputStream());
		unsortedStream.close();
		unsortedFile.delete();
	}

	private Sorter<IdAndVersion> createSorter(final IdAndVersionFactory factory) {
		final SortConfig sortConfig = createSortConfig();
		final DataReaderFactory<IdAndVersion> dataReaderFactory = new IdAndVersionDataReaderFactory(factory);
		final DataWriterFactory<IdAndVersion> dataWriterFactory = new IdAndVersionDataWriterFactory();
		return new Sorter<IdAndVersion>(sortConfig, dataReaderFactory, dataWriterFactory, new NaturalComparator<IdAndVersion>());
	}

	protected SortConfig createSortConfig() {
		return new SortConfig().withMaxMemoryUsage(DEFAULT_SORT_MEM);
	}

	IdAndVersionStream createSortedStream() {
		try {
			return new FileIdAndVersionStream(idAndVersionFactory, new ObjectInputStream(createSortedInputStream()));
		} catch (final IOException e) {
			throw new RuntimeException("failed to open sorted input stream", e);
		}
	}

	@Override
	public Iterator<IdAndVersion> iterator() {
		if (sortedStream == null) {
			throw new IllegalStateException();
		}
		return sortedStream.iterator();
	}

	@Override
	@SuppressWarnings({ "ResultOfMethodCallIgnored" })
	public void close() {
		if (sortedStream != null) {
			sortedStream.close();
			sortedStream = null;
		}
		sortedFile.delete();
	}

	OutputStream createUnsortedOutputStream() {
		try {
			return new BufferedOutputStream(new FileOutputStream(unsortedFile));
		} catch (final IOException e) {
			throw new RuntimeException("failed to create unsorted output stream", e);
		}
	}

	InputStream createUnSortedInputStream() {
		try {
			return new BufferedInputStream(new FileInputStream(unsortedFile));
		} catch (final IOException e) {
			throw new RuntimeException("failed to create unsorted input stream", e);
		}
	}

	OutputStream createSortedOutputStream() {
		try {
			return new BufferedOutputStream(new FileOutputStream(sortedFile));
		} catch (final IOException e) {
			throw new RuntimeException("failed to create sorted output stream", e);
		}
	}

	InputStream createSortedInputStream() {
		try {
			return new BufferedInputStream(new FileInputStream(sortedFile));
		} catch (final IOException e) {
			throw new RuntimeException("failed to create unsorted input stream", e);
		}
	}

}
