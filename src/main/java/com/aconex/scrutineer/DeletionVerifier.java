package com.aconex.scrutineer;

import java.util.Iterator;

public class DeletionVerifier {
	private final IdAndVersionStream primaryStream;
	private final ExistenceChecker existenceChecker;
	private final IdAndVersionStreamVerifierListener listener;

	public DeletionVerifier(final IdAndVersionStream primaryStream, final ExistenceChecker existenceChecker, final IdAndVersionStreamVerifierListener listener) {
		this.primaryStream = primaryStream;
		this.existenceChecker = existenceChecker;
		this.listener = listener;
	}

	public void verify() {
		primaryStream.open();

		try {
			final Iterator<IdAndVersion> iterator = primaryStream.iterator();
			iterateAndCheck(iterator);

		} finally {
			primaryStream.close();
		}
	}

	private void iterateAndCheck(final Iterator<IdAndVersion> iterator) {
		while (iterator.hasNext()) {
			final IdAndVersion idAndVersion = iterator.next();
			if (existenceChecker.exists(idAndVersion)) {
				listener.onMissingInPrimaryStream(idAndVersion);
			}
		}
	}
}
