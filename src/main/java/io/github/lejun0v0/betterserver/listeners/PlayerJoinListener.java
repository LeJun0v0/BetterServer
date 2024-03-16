package io.github.lejun0v0.betterserver.listeners;

import io.github.lejun0v0.betterserver.functions.WelcomeMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        player.sendMessage(WelcomeMessage.getWelcomeMessage() + player.getName());
    }
}
