package at.molindo.scrutineer;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class LongIdAndVersion extends AbstractIdAndVersion {

	public static final IdAndVersionFactory FACTORY = LongIdAndVersionFactory.INSTANCE;

	private final long id;

	public LongIdAndVersion(final long id, final long version) {
		super(version);
		this.id = id;
	}

	@Override
	public String getId() {
		// TODO
		return Long.toString(id);
	}

	public long getLongId() {
		return id;
	}

	@Override
	protected HashCodeBuilder appendId(final HashCodeBuilder appender) {
		return appender.append(id);
	}

	@Override
	protected CompareToBuilder appendId(final CompareToBuilder appender, final IdAndVersion other) {
		return appender.append(id, ((LongIdAndVersion) other).id);
	}

}
