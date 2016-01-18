package at.molindo.scrutineer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface IdAndVersionFactory {
	IdAndVersion create(Object id, long version);

	IdAndVersion readFromStream(ObjectInputStream inputStream) throws IOException;

	void writeToStream(IdAndVersion idAndVersion, ObjectOutputStream objectOutputStream) throws IOException;
}
