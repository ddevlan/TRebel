package com.ddylan.trebel.listeners;

import com.ddylan.library.LibraryPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.zeroturnaround.javarebel.ClassEventListener;

public class NotificationClassReloadListener implements ClassEventListener {

    private static boolean reloading = false;

    public static boolean isReloading() {
        return reloading;
    }

    private int debug = 0;

    @Override
    public void onClassEvent(int i, Class aClass) {

        LibraryPlugin library = JavaPlugin.getPlugin(LibraryPlugin.class);

        if (!reloading) {
            Bukkit.getServer().getPluginManager().disablePlugin(library);
            Bukkit.getServer().getPluginManager().enablePlugin(library);
            reloading = !reloading;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendOpNotification(ChatColor.GRAY + "" + debug + " classes were reloaded.");
                    reloading = !reloading;
                    debug = 0;
                }
            }.runTaskLater(library, 20 * 5);
        }

        if (!isReloading()) {
            ChatColor color = i == ClassEventListener.EVENT_LOADED ? ChatColor.GREEN : ChatColor.YELLOW;
            String name = aClass.getName();

            sendOpNotification(ChatColor.GRAY + "TRebel: " + color + name);
        }
        debug++;
    }

    private void sendOpNotification(String notification) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) {
                player.sendMessage(notification);
            }
        }
    }

    @Override
    public int priority() {
        return 1;
    }
}
