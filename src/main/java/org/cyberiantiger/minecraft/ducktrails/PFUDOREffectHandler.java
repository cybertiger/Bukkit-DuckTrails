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

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import static org.cyberiantiger.minecraft.ducktrails.Note.*;

/**
 *
 * @author antony
 */
public class PFUDOREffectHandler extends MusicalEffectHandler {
    private static final Note[] PFUDOR_MELODY = new Note[]{
        C5, // Bar 1
        null,
        A4,
        C5,
        A4,
        G4,
        null,
        F4,
        D4,
        F4,
        G4,
        C5,
        null,
        C4,
        null,
        null,};
    public static final Track PFUDOR_MELODY_TRACK = new Track(Sound.NOTE_PIANO) {
        @Override
        public Note getNote(int offset) {
            return PFUDOR_MELODY[offset % PFUDOR_MELODY.length];
        }

        @Override
        public float getVolume(int offset) {
            return 1f;
        }
    };
    private final Effect effect;
    double distance = 0.0D;

    public PFUDOREffectHandler(Effect effect) {
        super(new Track[]{PFUDOR_MELODY_TRACK});
        this.effect = effect;
    }

    @Override
    protected void showEffectInternal(Server server, Player player, Location from, Location to) {
        super.showEffectInternal(server, player, from, to);
        Vector forwards = from.toVector().subtract(to.toVector());
        double movement = forwards.length();
        forwards.multiply(1 / movement);
        Vector sideways = new Vector(forwards.getZ(), 0, -forwards.getX());
        double sideLength = sideways.length();
        if (sideLength == 0) {
            sideways.setX(1D);
            sideways.setZ(0D);
        } else {
            sideways.multiply(1 / sideLength);
        }
        distance += movement;
        // Show pink fluffy unicorn every 8 blocks
        if (distance >= 8.0D) {
            distance = 0.0D;
            Vector up = forwards.clone();
            up.crossProduct(sideways);
            Location pony = to.clone();
            pony.add(up);
            double baseX = pony.getX() - 0.5;
            double baseY = pony.getY() - 0.5;
            double baseZ = pony.getZ() - 0.5;
            for (int i = 0; i < 50; i++) {
                pony.setX(baseX + Math.random());
                pony.setY(baseY + Math.random());
                pony.setZ(baseZ + Math.random());
                sendEffect(server, player, Effect.POTION_SWIRL, pony, 1f, 0.7f, 0.7f, 1f, 256f, 0);
            }
        }
        sideways.multiply(0.5D);
        to.subtract(sideways);
        sideways.multiply(1 / 8D);
        for (int i = 0; i < 17; i++) {
            int argb = java.awt.Color.HSBtoRGB(i / 15.0f, 1f, 1f);
            float r = ((argb >> 16) & 0xff) / 255f;
            float g = ((argb >> 8) & 0xff) / 255f;
            float b = (argb & 0xff) / 255f;
            r = r == 0f ? 0.001f : r;
            sendEffect(server, player, effect, to, r, g, b, 1f, 256f, 0);
            to.add(sideways);
        }
    }
    
}
