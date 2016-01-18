package at.molindo.scrutineer;

import java.util.Iterator;

public interface IdAndVersionStream extends Iterable<IdAndVersion> {

	/**
	 * @return <code>true</code> if this stream is suitable for verification. False if not sorted or order unknown (or
	 *         irrelevant)
	 */
	boolean isSorted();

	void open();

	@Override
	Iterator<IdAndVersion> iterator();

	void close();
}
