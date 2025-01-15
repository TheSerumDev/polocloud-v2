package dev.httpmarco.polocloud.node.cluster.common;

import dev.httpmarco.polocloud.node.cluster.ClusterNode;
import dev.httpmarco.polocloud.node.cluster.ClusterNodeSnapshot;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class AbstractClusterNode implements ClusterNode {

    private ClusterNodeSnapshot snapshot;

}
