/*
 * Copyright 2015 Antony Riley
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
 * limitations under the License.Copyright [yyyy] [name of copyright owner]
 */
package org.cyberiantiger.minecraft.ducktrails;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author antony
 */
public class Main extends JavaPlugin implements Listener {
    public static final String LOCALE_FILE = "locale.properties";

    private class MusicRunnable extends TimerTask { 
        public void run() {
            long nanoTime = System.nanoTime();
            for (Player p : getServer().getOnlinePlayers()) {
                EffectHandler e = effectHandlers.get(p);
                if (e instanceof MusicalEffectHandler) {
                    MusicalEffectHandler m = (MusicalEffectHandler) e;

                    if (m.isPlaying(nanoTime)) {
                        m.playNext(getServer(), p);
                    }
                }
            }
        }
    }
    private Timer timer;
    private MusicRunnable timerTask;
    private Map<Player, EffectHandler> effectHandlers = new WeakHashMap<Player, EffectHandler>();
    private Properties translations = new Properties();
    private Map<String,Trail> trailNames = new HashMap<String,Trail>();

    private boolean copyDefault(String source, String dest) {
        File destFile = new File(getDataFolder(), dest);
        if (!destFile.exists()) {
            try {
                destFile.getParentFile().mkdirs();
                InputStream in = getClass().getClassLoader().getResourceAsStream(source);
                if (in != null) {
                    try {
                        OutputStream out = new FileOutputStream(destFile);
                        try {
                            ByteStreams.copy(in, out);
                        } finally {
                            out.close();
                        }
                    } finally {
                        in.close();
                    }
                    return true;
                }
            } catch (IOException ex) {
                getLogger().log(Level.WARNING, "Error copying default " + dest, ex);
            }
        }
        return false;
    }

    private File getDataFile(String name) {
        return new File(getDataFolder(), name);
    }

    private Reader openFile(File file) throws IOException {
        return new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), Charsets.UTF_8);
    }

    private Reader openDataFile(String file) throws IOException {
        return openFile(new File(getDataFolder(), file));
    }

    private Reader openResource(String resource) throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(resource);
        if (in == null) { 
            throw new FileNotFoundException(resource);
        }
        return new InputStreamReader(new BufferedInputStream(in), Charsets.UTF_8);
    }

    private void loadTranslations() {
        translations.clear();
        try {
            Reader in = openResource(LOCALE_FILE);
            try {
                translations.load(in);
            } finally { 
                in.close();
            }
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Error reading translation defaults: " + LOCALE_FILE, ex);
        }
        try {
            Reader in = openDataFile(LOCALE_FILE);
            try {
                translations.load(in);
            } finally {
                in.close();
            }
        } catch (IOException ex) {
            getLogger().log(Level.WARNING, "Error reading translations " + getDataFile(LOCALE_FILE), ex);
        }
        for (Map.Entry<Object,Object> e : translations.entrySet()) {
            String format = e.getValue().toString();
            e.setValue(format.replace('&', ChatColor.COLOR_CHAR));
        }
        trailNames.clear();
        for (Trail trail : Trail.values()) {
            trailNames.put(translate("ducktrails." + trail.name().toLowerCase() + ".name"), trail);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();
        // Ignore events where the player didn't move.
        if (from.getX() == to.getX() && from.getZ() == to.getZ() && from.getY() == to.getY()) {
            return;
        }
        Player player = e.getPlayer();
        EffectHandler effectHandler = effectHandlers.get(player);
        if (effectHandler != null) {
            effectHandler.showEffect(getServer(), player, from.clone(), to.clone());
        }
    }

    @Override
    public void onEnable() {
        copyDefault(LOCALE_FILE, LOCALE_FILE);
        loadTranslations();
        getServer().getPluginManager().registerEvents(this, this);
        timer = new Timer();
        timerTask = new MusicRunnable();
        timer.scheduleAtFixedRate(timerTask, 0L, 160L);
    }

    @Override
    public void onDisable() {
        timer.cancel();
        timer = null;
        timerTask = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(command.getUsage());
            return true;
        }
        args[0] = args[0].toLowerCase();
        if ("off".equals(args[0])) {
            if (! (sender instanceof Player)) {
                sender.sendMessage(translate("command.ducktrails.notplayer"));
                return true;
            }
            Player player = (Player) sender;
            sender.sendMessage(translate("command.ducktrails.off"));
            effectHandlers.remove(player);
        } else if ("list".equals(args[0])) {
            if (! (sender instanceof Player)) {
                sender.sendMessage(translate("command.ducktrails.notplayer"));
                return true;
            }
            Player player = (Player) sender;
            List<Trail> effects = new ArrayList<Trail>(Trail.values().length);
            for (Trail trail : Trail.values()) {
                if (player.hasPermission(trail.getPermission())) {
                    effects.add(trail);
                }
            }
            if (effects.isEmpty()) {
                player.sendMessage(translate("command.ducktrails.list.none"));
            } else {
                Collections.sort(effects);
                player.sendMessage(translate("command.ducktrails.list.header"));
                for (Trail trail : effects) {
                    player.sendMessage(translate("ducktrails." + trail.name().toLowerCase() + ".description"));
                }
            }
        } else if ("reload".equals(args[0])) {
            if (!sender.hasPermission("ducktrails.reload")) {
                sender.sendMessage(translate("command.ducktrails.reload.permission", "ducktrails.reload"));
            } else {
                loadTranslations();
                sender.sendMessage(translate("command.ducktrails.reload"));
            }
        } else {
            if (! (sender instanceof Player)) {
                sender.sendMessage(translate("command.ducktrails.notplayer"));
                return true;
            }
            Player player = (Player) sender;
            Trail trail = trailNames.get(args[0]);
            if (trail == null) {
                sender.sendMessage(translate("command.ducktrails.notrail",args[0]));
            } else {
                if (player.hasPermission(trail.getPermission())) {
                    sender.sendMessage(translate("ducktrails." + trail.name().toLowerCase() + ".activate"));
                    EffectHandler effectHandler = trail.createHandler(player);
                    effectHandlers.put(player, effectHandler);
                } else {
                    sender.sendMessage(translate("command.ducktrails.trail.permission", args[0], trail.getPermission()));
                }
            }
        }
        return true;
    }

    public String translate(String key, String... args) {
        String format = translations.getProperty(key);
        if (format == null) {
            getLogger().warning("Missing translation for key: " + key);
            return key;
        }
        return String.format(format, (Object[]) args);
    }
}