package me.harrylesanto.deathcounter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class DeathCounter extends JavaPlugin {

    private Scoreboard scoreboard;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        ConfigManager.setup();
        ConfigManager.getConfigFile().options().copyDefaults(true);

        scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ConfigManager.save();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Death Counter" +
                    "\n/deathcounter list - lists all death counters" +
                    "\n/deathcounter <player> - searches for a specific player's death count");
            return true;
        }

        if ("list".equals(args[0])) {
            list(sender);
        } else {
            search(sender, args);
        }

        return true;
    }

    public void list(@NotNull CommandSender sender) {
        ArrayList<DeathDuo> players = new ArrayList<>();
        for (String uuid : ConfigManager.getConfigFile().getKeys(false)) {
            try {
                String playerName = ConfigManager.getConfigFile().getString(uuid + ".name");
                int deathCount = ConfigManager.getConfigFile().getInt(uuid + ".deaths");
                players.add(new DeathDuo(playerName, deathCount));
            } catch (Exception ex) {
                getLogger().info(ex.getMessage());
                return;
            }
        }

        Collections.sort(players);

        String message = "";
        for (DeathDuo duo : players) {
            message += duo.getName() + ": " + duo.getDeaths() + (duo.getDeaths() == 1 ? " death" : " deaths") + "\n";
        }

        sender.sendMessage(message);
    }

    public void search(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage("You must enter a player name or list");
            return;
        }

        String targetId = Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString();
        if (ConfigManager.getConfigFile().getString(targetId) == null) {
            sender.sendMessage("Player not found");
        }
        else {
            String targetName = ConfigManager.getConfigFile().getString(targetId + ".name");
            int deaths = ConfigManager.getConfigFile().getInt(targetId + ".deaths");
            sender.sendMessage(targetName + ": " + deaths + (deaths == 1 ? " death" : " deaths"));
        }
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
