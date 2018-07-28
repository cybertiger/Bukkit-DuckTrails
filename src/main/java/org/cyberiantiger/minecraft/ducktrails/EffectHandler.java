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
 * limitations under the License.
 */
package org.cyberiantiger.minecraft.ducktrails;

import java.util.concurrent.atomic.AtomicReference;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 *
 * @author antony
 */
public abstract class EffectHandler {
    public static class BasicEffectHandler extends EffectHandler {
        private final Effect effect;
        private final float dx;
        private final float dy;
        private final float dz;
        private final float v;
        private final int id;
        private final int data;
        private final int count;

        public BasicEffectHandler(Effect effect, float dx, float dy, float dz, float v, int count) {
            this(effect, dx, dy, dz, v, 0, 0, count);
        }

        public BasicEffectHandler(Effect effect, float dx, float dy, float dz, float v, int id, int data, int count) {
            this.effect = effect;
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
            this.v = v;
            this.id = id;
            this.data = data;
            this.count = count;
        }

        @Override
        protected void showEffectInternal(Server server, Player player, Location from, Location to) {
            sendEffect(server, player, effect, to, dx, dy, dz, v, 256f, count, id, data);
        }
    }

    public static class RainbowEffectHandler extends EffectHandler {
        private float hue = 0f;
        private final Effect effect;


        public RainbowEffectHandler(Effect effect) {
            this.effect = effect;
        }

        @Override
        protected void showEffectInternal(Server server, Player player, Location from, Location to) {
            int argb =  java.awt.Color.HSBtoRGB(hue, 1f, 1f);
            float r = ((argb >> 16) & 0xff) / 255f;
            float g = ((argb >> 8) & 0xff) / 255f;
            float b = (argb & 0xff) / 255f;
            r = r == 0f ? 0.001f : r;
            sendEffect(server, player, effect, to, r, g, b, 1f, 256f, 0);
            hue = hue + 0.01f;
            hue = hue >= 1f ? 0f : hue;
        }
    }
    
    private AtomicReference<Location> lastLocation = new AtomicReference<Location>(null);

    public Location getLastLocation() {
        return lastLocation.get();
    }

    protected abstract void showEffectInternal(Server server, Player player, Location from, Location to);

    public final void showEffect(Server server, Player player, Location from, Location to) {
        lastLocation.set(to);
        showEffectInternal(server, player, from, to);
    }

    protected void sendEffect(Server server, Player source, Effect effect, Location loc, float dx, float dy, float dz, float v, float r, int count) {
        sendEffect(server, source, effect, loc, dx, dy, dz, v, r, count, 0, 0);
    }

    protected void sendEffect(Server server, Player source, Effect effect, Location loc, float dx, float dy, float dz, float v, float r, int count, int id, int data) {
        float r2 = r*r;
        for (Player p : server.getOnlinePlayers()) {
            if (p.getWorld() == loc.getWorld() && p.canSee(source)) {
                if (loc.distanceSquared(p.getLocation()) < r2) {
                    p.spigot().playEffect(loc, effect, id, data, dx, dy, dz, v, count, (int)r);
                }
            }
        }
    }
}
