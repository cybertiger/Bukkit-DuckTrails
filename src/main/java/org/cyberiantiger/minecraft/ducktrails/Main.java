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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;
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
            effectHandler.showEffect(getServer(), player, from, to);
        }
    }

    @Override
    public void onEnable() {
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
        if (! (sender instanceof Player)) {
            sender.sendMessage("Sorry, you need to be a player to use this command.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 1) {
            sender.sendMessage(command.getUsage());
            return true;
        }
        if ("off".equals(args[0])) {
            sender.sendMessage("You will now leave no trail.");
            effectHandlers.remove(player);
        } else if ("list".equals(args[0])) {
            List<Trails> effects = new ArrayList<Trails>(Trails.values().length);
            for (Trails trail : Trails.values()) {
                if (player.hasPermission(trail.getPermission())) {
                    effects.add(trail);
                }
            }
            if (effects.isEmpty()) {
                player.sendMessage("No trails are available to you, sorry");
            } else {
                Collections.sort(effects);
                player.sendMessage("Trails available to you:");
                for (Trails trail : effects) {
                    player.sendMessage(trail.name().toLowerCase() + " - " + trail.getDescription());
                }
            }
        } else {
            try {
                Trails trail = Trails.valueOf(args[0].toUpperCase());
                if (player.hasPermission(trail.getPermission())) {
                    sender.sendMessage("You will now leave a " + trail.name().toLowerCase() + " trail!");
                    EffectHandler effectHandler = trail.createHandler(player);
                    effectHandlers.put(player, effectHandler);
                } else {
                    sender.sendMessage("You do not have permission for that trail.");
                }
            } catch (IllegalArgumentException e) {
                sender.sendMessage("No trail named " + args[0]);
            }
        }
        return true;
    }

}
