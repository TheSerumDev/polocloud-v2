package dev.httpmarco.polocloud.node.cluster;

import dev.httpmarco.polocloud.api.Available;
import dev.httpmarco.polocloud.api.Named;

public interface ClusterNode extends Available, Named {

    void updateSnapshot();

    ClusterNodeSnapshot snapshot();

}
