package me.harrylesanto.deathcounter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        UUID playerId = e.getPlayer().getUniqueId();
        if (ConfigManager.getConfigFile().get(playerId.toString()) == null) {
            ConfigManager.getConfigFile().set(playerId + ".name", e.getPlayer().getName());
            ConfigManager.getConfigFile().set(playerId + ".deaths", 0);
        }
        else if (ConfigManager.getConfigFile().get(playerId + ".name") != e.getPlayer().getName()) {
            ConfigManager.getConfigFile().set(playerId + ".name", e.getPlayer().getName());
        }
        ConfigManager.save();

        int currentDeathCount = ConfigManager.getConfigFile().getInt(playerId + ".deaths");
        Component component = Component.text(e.getPlayer().getName() + " ");
        component = component.append(Component.text("[" + currentDeathCount + "]", NamedTextColor.DARK_RED));
        e.getPlayer().playerListName(component);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        UUID playerId = e.getPlayer().getUniqueId();
        if (ConfigManager.getConfigFile().get(playerId.toString()) == null) {
            ConfigManager.getConfigFile().set(playerId + ".deaths", 0);
        }

        int currentDeathCount = ConfigManager.getConfigFile().getInt(playerId + ".deaths");
        ConfigManager.getConfigFile().set(playerId + ".deaths", currentDeathCount + 1);
        ConfigManager.save();

        currentDeathCount = ConfigManager.getConfigFile().getInt(playerId + ".deaths");

        Component component = Component.text(e.getPlayer().getName() + " ");
        component = component.append(Component.text("[" + currentDeathCount + "]", NamedTextColor.DARK_RED));
        e.getPlayer().playerListName(component);
    }
}
