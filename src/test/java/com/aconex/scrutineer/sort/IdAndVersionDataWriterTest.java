package com.aconex.scrutineer.sort;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aconex.scrutineer.IdAndVersion;
import com.aconex.scrutineer.IdAndVersionFactory;

public class IdAndVersionDataWriterTest {

	@Mock
	private ObjectOutputStream objectOutputStream;

	@Mock
	private IdAndVersion idAndVersion;

	@Mock
	private IdAndVersionFactory idAndVersionFactory;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldWriteEntry() throws IOException {
		final IdAndVersionDataWriter idAndVersionDataWriter = new IdAndVersionDataWriter(objectOutputStream, idAndVersionFactory);
		idAndVersionDataWriter.writeEntry(idAndVersion);

		verify(idAndVersionFactory).writeToStream(idAndVersion, objectOutputStream);
	}

	@Test
	public void shouldCloseStream() throws IOException {
		final IdAndVersionDataWriter idAndVersionDataWriter = new IdAndVersionDataWriter(objectOutputStream, idAndVersionFactory);
		idAndVersionDataWriter.close();

		verify(objectOutputStream).close();
	}

}
