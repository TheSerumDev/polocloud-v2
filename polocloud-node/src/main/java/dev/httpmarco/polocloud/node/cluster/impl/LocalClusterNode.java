package dev.httpmarco.polocloud.node.cluster.impl;

import dev.httpmarco.polocloud.node.cluster.ClusterNodeData;
import dev.httpmarco.polocloud.node.cluster.common.AbstractClusterNode;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class LocalClusterNode extends AbstractClusterNode {

    private final Server server;

    public LocalClusterNode(@NotNull ClusterNodeData data) throws IOException {
        this.server = ServerBuilder.forPort(data.nodePort()).build();
        this.server.start();
    }

    @Override
    public String name() {
        //todo
        return "";
    }

    @Override
    public boolean available() {
        return !this.server.isTerminated() && !this.server.isShutdown();
    }

    @Override
    public void updateSnapshot() {

    }
}
