package com.aconex.scrutineer.javautil;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.aconex.scrutineer.IdAndVersion;

public class JavaIteratorIdAndVersionStreamTest {

	private Iterator<IdAndVersion> iterator;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldPassThroughIterator() {
		final JavaIteratorIdAndVersionStream javaIteratorIdAndVersionStream = new JavaIteratorIdAndVersionStream(iterator);
		assertThat(javaIteratorIdAndVersionStream.iterator(), is(iterator));
	}
}
