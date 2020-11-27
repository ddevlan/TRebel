package com.ddylan.trebel;

import com.ddylan.trebel.listeners.CommandClassReloadListener;
import com.ddylan.trebel.listeners.ListenerClassReloadListener;
import com.ddylan.trebel.listeners.NotificationClassReloadListener;
import org.zeroturnaround.javarebel.ClassEventListener;
import org.zeroturnaround.javarebel.ClassResourceSource;
import org.zeroturnaround.javarebel.Plugin;
import org.zeroturnaround.javarebel.ReloaderFactory;

import java.util.Arrays;

public final class TRebel implements Plugin {

    /*private static Map<UUID, Integer> temp;

    public static Map<UUID, Integer> getTemp() {
        return temp;
    }*/

    public void preinit() {
//        temp = new HashMap<>();

        for (ClassEventListener listener : Arrays.asList(
                new CommandClassReloadListener(),
                new ListenerClassReloadListener(),
                new NotificationClassReloadListener())) {
            ReloaderFactory.getInstance().addClassReloadListener(listener);
        }
        System.out.print("########################################################");
        System.out.print("# TRebel is now ready for use and JRebel is listening for hot-swaps. #");
        System.out.print("########################################################");
    }

    public boolean checkDependencies(ClassLoader classLoader, ClassResourceSource classResourceSource) {
        return classLoader.getClass().getName().equals("org.bukkit.plugin.java.PluginClassLoader");
    }

    public String getId() {
        return "trebel";
    }

    public String getName() {
        return "TRebel";
    }

    public String getDescription() {
        return "gettinguap.gg JRebel integration.";
    }

    public String getAuthor() {
        return "ddylan";
    }

    public String getWebsite() {
        return "countincommas.cc";
    }

    public String getSupportedVersions() {
        return "";
    }

    public String getTestedVersions() {
        return "null";
    }
}
