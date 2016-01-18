package at.molindo.scrutineer;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import at.molindo.scrutineer.IdAndVersion;

public class HasIdAndVersionMatcher extends TypeSafeMatcher<IdAndVersion> {

	private final String id;

	private final long version;

	public HasIdAndVersionMatcher(final String id, final long version) {
		this.id = id;
		this.version = version;
	}

	@Override
	public boolean matchesSafely(final IdAndVersion idAndVersion) {
		return idAndVersion.getId().endsWith(id) && idAndVersion.getVersion() == version;
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText(" has id and version " + id + ":" + version);
	}

	@Factory
	public static <T> Matcher<IdAndVersion> hasIdAndVersion(final String id, final long version) {
		return new HasIdAndVersionMatcher(id, version);
	}
}
