package com.aconex.scrutineer;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DeletionVerifierTest {

	@Mock
	private IdAndVersionStreamVerifierListener listener;

	@Mock
	private IdAndVersionStream primaryDeletedStream, secondaryStream;

	@Mock
	private ExistenceChecker existenceChecker;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldConfirmADeleteWasMissed() {

		final IdAndVersion expectedNotDeletedIdAndVersion = new LongIdAndVersion(2, 2);
		final IdAndVersion expectedDeletedIdAndVersion = new LongIdAndVersion(1, 1);

		final List<IdAndVersion> longIdAndVersions = Arrays
				.asList(expectedDeletedIdAndVersion, expectedNotDeletedIdAndVersion);
		when(primaryDeletedStream.iterator()).thenReturn(longIdAndVersions.iterator());

		when(existenceChecker.exists(expectedDeletedIdAndVersion)).thenReturn(false);
		when(existenceChecker.exists(expectedNotDeletedIdAndVersion)).thenReturn(true);

		final DeletionVerifier deletionVerifier = new DeletionVerifier(primaryDeletedStream, existenceChecker, listener);

		deletionVerifier.verify();

		verify(existenceChecker, times(2)).exists(any(IdAndVersion.class));
		verify(primaryDeletedStream).close();
		verify(listener).onMissingInPrimaryStream(expectedNotDeletedIdAndVersion);
	}
}
