package dev.httpmarco.polocloud.node.cluster.impl;

import dev.httpmarco.polocloud.node.cluster.ClusterNodeData;
import dev.httpmarco.polocloud.node.cluster.common.AbstractClusterNode;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public final class ExternalClusterNode extends AbstractClusterNode {

    private final ManagedChannel managedChannel;

    public ExternalClusterNode(final ClusterNodeData clusterNodeData) {
        this.managedChannel = ManagedChannelBuilder.forAddress(clusterNodeData.nodeHostname(), clusterNodeData.nodePort())
                .usePlaintext()
                .build();
    }

    @Override
    public void updateSnapshot() {

    }

    @Override
    public boolean available() {
        return false;
    }

    @Override
    public String name() {
        return "";
    }
}
