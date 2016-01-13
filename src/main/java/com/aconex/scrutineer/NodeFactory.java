package com.aconex.scrutineer;

import static org.elasticsearch.node.NodeBuilder.*;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

public class NodeFactory {

	public Node createNode(final String clusterName) {
		return nodeBuilder().settings(createSettings(clusterName)).node();
	}

	Settings createSettings(final String clusterName) {
		return ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).put("node.client", true)
				.put("node.name", "scrutineer").build();
	}

}
