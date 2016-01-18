package at.molindo.scrutineer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import at.molindo.scrutineer.IdAndVersion;
import at.molindo.scrutineer.LongIdAndVersion;
import at.molindo.scrutineer.LongIdAndVersionFactory;

public class LongIdAndVersionFactoryTest {

	@Mock
	ObjectOutputStream objectOutputStream;

	@Mock
	ObjectInputStream objectInputStream;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldWriteToOutputStream() throws IOException {
		final LongIdAndVersion idAndVersion = new LongIdAndVersion(2L, 3);
		LongIdAndVersionFactory.INSTANCE.writeToStream(idAndVersion, objectOutputStream);
		verify(objectOutputStream).writeLong(idAndVersion.getLongId());
		verify(objectOutputStream).writeLong(idAndVersion.getVersion());
	}

	@Test
	public void shouldReadFromInputStream() throws IOException {
		when(objectInputStream.readLong()).thenReturn(10L);
		when(objectInputStream.readLong()).thenReturn(10L);

		final IdAndVersion idAndVersion = LongIdAndVersion.FACTORY.readFromStream(objectInputStream);

		assertThat(idAndVersion.getId(), is("10"));
		assertThat(idAndVersion.getVersion(), is(10L));
	}
}
