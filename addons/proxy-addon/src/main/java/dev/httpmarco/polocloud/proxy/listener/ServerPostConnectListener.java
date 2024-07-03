/*
 * Copyright 2024 Mirco Lindenau | HttpMarco
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.httpmarco.polocloud.proxy.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import dev.httpmarco.polocloud.proxy.VelocityPlatformPlugin;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerPostConnectListener {

    private final VelocityPlatformPlugin platform;

    @Subscribe
    public void onPost(ServerPostConnectEvent event) {
        this.platform.getTabManager().addPlayer(event.getPlayer());
        this.platform.getServer().getAllPlayers().forEach(player -> this.platform.getTabManager().update(player));
    }


}
