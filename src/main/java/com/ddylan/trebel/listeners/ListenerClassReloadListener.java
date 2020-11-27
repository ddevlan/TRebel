package com.ddylan.trebel.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.zeroturnaround.javarebel.ClassEventListener;

public class ListenerClassReloadListener implements ClassEventListener {

    private Class lastClass;

    //TODO: be able to tell if a class is a subclass, so things like the image below don't happen
    //
    @Override
    public void onClassEvent(int i, Class clazz) {
        if (!Listener.class.isAssignableFrom(clazz) || !checkForListener(clazz)) {
            return;
        }

        Listener listener = findInstance(clazz);

        if (listener != null) {
            HandlerList.unregisterAll(listener);
        } else {
            if (clazz.isAnnotationPresent(com.ddylan.library.listener.Listener.class)) {
                try {
                    listener = (Listener) clazz.newInstance();
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Class: " + clazz.getCanonicalName());
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Did you really just add a Listener that does stuff in the constructor?");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "JRebel is pretty magic, but it's not that magic.");
                    return;
                }
            }
        }

        if (listener == null) {
            try {
                listener = (Listener) clazz.newInstance();
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "Class: " + clazz.getCanonicalName());
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Did you really just add a Listener that does stuff in the constructor?");
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "JRebel is pretty magic, but it's not that magic.");
                return;
            }
        }

        Bukkit.getPluginManager().registerEvents(listener, JavaPlugin.getProvidingPlugin(clazz));
        this.lastClass = clazz;
    }

    @Override
    public int priority() {
        return 1;
    }

    private Listener findInstance(Class<?> type) {
        Listener existing = null;

        for (HandlerList handler : HandlerList.getHandlerLists()) {
            for (RegisteredListener registered : handler.getRegisteredListeners()) {
                Listener regListener = registered.getListener();

                if (regListener.getClass() == type) {
                    existing = regListener;
                }
            }
        }

        return existing;
    }

    private boolean checkForListener(Class<?> clazz) {
        for (Class<?> type : clazz.getInterfaces()) {
            if (Listener.class.equals(type)) {
                return true;
            }
        }
        return false;
    }

}
