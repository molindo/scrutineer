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
import at.molindo.scrutineer.StringIdAndVersion;
import at.molindo.scrutineer.StringIdAndVersionFactory;

public class StringIdAndVersionFactoryTest {
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
		final StringIdAndVersion idAndVersion = new StringIdAndVersion("2", 3);
		StringIdAndVersionFactory.INSTANCE.writeToStream(idAndVersion, objectOutputStream);
		verify(objectOutputStream).writeUTF(idAndVersion.getId());
		verify(objectOutputStream).writeLong(idAndVersion.getVersion());
	}

	@Test
	public void shouldReadFromInputStream() throws IOException {
		when(objectInputStream.readUTF()).thenReturn("10");
		when(objectInputStream.readLong()).thenReturn(10L);

		final IdAndVersion idAndVersion = StringIdAndVersion.FACTORY.readFromStream(objectInputStream);

		assertThat(idAndVersion.getId(), is("10"));
		assertThat(idAndVersion.getVersion(), is(10L));
	}

}
