package at.molindo.scrutineer.stream;

import java.util.Iterator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import at.molindo.scrutineer.IdAndVersion;
import at.molindo.scrutineer.IdAndVersionStream;

public class IterableIdAndVersionStream implements IdAndVersionStream {

	private final ImmutableList<IdAndVersion> list;

	public IterableIdAndVersionStream(final IdAndVersion... idAndVersions) {
		list = ImmutableList.copyOf(idAndVersions);
	}

	public IterableIdAndVersionStream(final Iterable<? extends IdAndVersion> iterable) {
		list = ImmutableList.copyOf(iterable);
	}

	@Override
	public boolean isSorted() {
		return Ordering.natural().isOrdered(list);
	}

	@Override
	public void open() {
		// Empty
	}

	@Override
	public Iterator<IdAndVersion> iterator() {
		return list.iterator();
	}

	@Override
	public void close() {
		// Empty
	}
}
