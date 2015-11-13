package com.aconex.scrutineer;


import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

public class NodeFactory {

    public Node createNode(String clusterName) {
        return nodeBuilder().settings(createSettings(clusterName)).node();
    }

    Settings createSettings(String clusterName) {
        return ImmutableSettings.settingsBuilder()
                .put("cluster.name", clusterName)
                .put("node.client", true)
                .put("node.name", "scrutineer")
                .build();
    }

}
