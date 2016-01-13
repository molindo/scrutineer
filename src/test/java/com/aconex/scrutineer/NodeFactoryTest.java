package com.aconex.scrutineer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

import org.elasticsearch.common.settings.Settings;
import org.junit.Test;

public class NodeFactoryTest {

	@Test
	public void shouldCreateNodeWithCorrectSettings() {

		final NodeFactory nodeFactory = new NodeFactory();

		final Settings settings = nodeFactory.createSettings("mycluster");

		assertThat(settings.get("cluster.name"), is("mycluster"));
		assertThat(settings.get("node.client"), is("true"));
		assertThat(settings.get("node.name"), is("scrutineer"));
	}

}
