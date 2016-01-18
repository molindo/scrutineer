package com.aconex.scrutineer;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class StringIdAndVersion extends AbstractIdAndVersion {

	public static final IdAndVersionFactory FACTORY = StringIdAndVersionFactory.INSTANCE;

	private final String id;

	public StringIdAndVersion(final String id, final long version) {
		super(version);
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	protected HashCodeBuilder appendId(final HashCodeBuilder appender) {
		return appender.append(id);
	}

	@Override
	protected CompareToBuilder appendId(final CompareToBuilder appender, final IdAndVersion other) {
		return appender.append(id, ((StringIdAndVersion) other).id);
	}

}
