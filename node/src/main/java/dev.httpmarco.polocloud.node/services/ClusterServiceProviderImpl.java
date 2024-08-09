package dev.httpmarco.polocloud.node.services;

import dev.httpmarco.osgan.networking.packet.PacketBuffer;
import dev.httpmarco.polocloud.api.packet.resources.services.ServiceOnlinePacket;
import dev.httpmarco.polocloud.api.packet.resources.services.ServiceShutdownCallPacket;
import dev.httpmarco.polocloud.api.services.ClusterService;
import dev.httpmarco.polocloud.api.services.ClusterServiceFactory;
import dev.httpmarco.polocloud.api.services.ClusterServiceProvider;
import dev.httpmarco.polocloud.api.services.ClusterServiceState;
import dev.httpmarco.polocloud.node.Node;
import dev.httpmarco.polocloud.node.cluster.LocalNode;
import dev.httpmarco.polocloud.node.packets.resources.services.ClusterSyncRegisterServicePacket;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
@Getter
@Accessors(fluent = true)
public final class ClusterServiceProviderImpl extends ClusterServiceProvider {

    private final List<ClusterService> services = new CopyOnWriteArrayList<>();
    private final ClusterServiceFactory factory = new ClusterServiceFactoryImpl();
    private final ClusterServiceQueue clusterServiceQueue = new ClusterServiceQueue();

    public ClusterServiceProviderImpl() {
        var localNode = Node.instance().clusterProvider().localNode();

        localNode.transmit().listen(ClusterSyncRegisterServicePacket.class, (it, packet) -> {
            services.add(packet.service());
            log.info("The service &8'&f{}&8' &7is starting now&8...", packet.service().name());
        });

        localNode.transmit().listen(ServiceShutdownCallPacket.class, (transmit, packet) -> {
            ClusterService service = Node.instance().serviceProvider().find(packet.id());

            if (service == null) {
                log.error("Tried to shut down a service, but the service is not present in the cluster. {}", packet.id());
                return;
            }

            if (service instanceof ClusterLocalServiceImpl localService) {
                localService.shutdown();
            } else {
                log.warn("Try to shutdown a service that is not present on this node! {}", service.name());
            }
        });

        localNode.transmit().listen(ServiceOnlinePacket.class, (transmit, packet) -> {
            var service = find(packet.id());
            if (service == null) {
                transmit.channel().close();
                return;
            }

            if (service instanceof ClusterLocalServiceImpl localService) {
                localService.state(ClusterServiceState.ONLINE);
                localService.update();
            } else {
                transmit.channel().close();
                return;
            }

            log.info("The service &8'&f{}&8' &7is online&8.", service.name());
        });
    }

    @Override
    public @NotNull CompletableFuture<List<ClusterService>> servicesAsync() {
        return CompletableFuture.completedFuture(services);
    }

    @Override
    public @NotNull CompletableFuture<ClusterService> findAsync(UUID id) {
        return CompletableFuture.completedFuture(services.stream().filter(it -> it.id().equals(id)).findFirst().orElse(null));
    }

    @Override
    public CompletableFuture<ClusterService> findAsync(String name) {
        return CompletableFuture.completedFuture(services.stream().filter(it -> it.name().equals(name)).findFirst().orElse(null));
    }

    @Contract(pure = true)
    @Override
    public @Nullable ClusterService read(PacketBuffer buffer) {
        return null;
    }
}
