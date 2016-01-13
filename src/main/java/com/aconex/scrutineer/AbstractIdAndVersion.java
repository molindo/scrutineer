package com.aconex.scrutineer;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class AbstractIdAndVersion implements IdAndVersion {

	private final long version;

	protected AbstractIdAndVersion(final long version) {
		this.version = version;
	}

	@Override
	public long getVersion() {
		return version;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof AbstractIdAndVersion)) {
			return false;
		}
		final AbstractIdAndVersion other = (AbstractIdAndVersion) obj;
		return compareTo(other) == 0;
	}

	@Override
	public int hashCode() {
		return appendId(new HashCodeBuilder()).append(version).toHashCode();
	}

	@Override
	public String toString() {
		return getId() + ":" + getVersion();
	}

	@Override
	public int compareTo(final IdAndVersion other) {
		return appendId(new CompareToBuilder(), other).append(version, other.getVersion()).toComparison();
	}

	@Override
	public final void writeToStream(final ObjectOutputStream objectOutputStream) throws IOException {
		writeId(objectOutputStream);
		objectOutputStream.writeLong(version);
	}

	protected abstract HashCodeBuilder appendId(HashCodeBuilder appender);

	protected abstract CompareToBuilder appendId(CompareToBuilder appender, IdAndVersion other);

	protected abstract void writeId(ObjectOutputStream objectOutputStream) throws IOException;
}
