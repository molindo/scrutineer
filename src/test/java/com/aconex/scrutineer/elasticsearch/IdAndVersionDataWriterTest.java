package com.aconex.scrutineer.elasticsearch;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aconex.scrutineer.IdAndVersion;

public class IdAndVersionDataWriterTest {

	@Mock
	private ObjectOutputStream objectOutputStream;

	@Mock
	private IdAndVersion idAndVersion;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldWriteEntry() throws IOException {
		final IdAndVersionDataWriter idAndVersionDataWriter = new IdAndVersionDataWriter(objectOutputStream);
		idAndVersionDataWriter.writeEntry(idAndVersion);

		verify(idAndVersion).writeToStream(objectOutputStream);
	}

	@Test
	public void shouldCloseStream() throws IOException {
		final IdAndVersionDataWriter idAndVersionDataWriter = new IdAndVersionDataWriter(objectOutputStream);
		idAndVersionDataWriter.close();

		verify(objectOutputStream).close();
	}

}
