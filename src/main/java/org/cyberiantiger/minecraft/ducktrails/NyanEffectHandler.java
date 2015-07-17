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
import static org.cyberiantiger.minecraft.ducktrails.Note.*;

/**
 *
 * @author antony
 */
public class NyanEffectHandler extends MusicalEffectHandler {
    private static final Note[] NYAN_CAT_MELODY = new Note[]{
        // Bar 1
        D_SHARP4, E4, F_SHARP4, null, B4, null, D_SHARP4, E4, F_SHARP4, B4, C_SHARP5, D_SHARP5, C_SHARP5, A_SHARP4, B4, null,
        // Bar 2
        F_SHARP4, null, D_SHARP4, E4, F_SHARP4, null, B4, null, C_SHARP5, A_SHARP4, B4, C_SHARP5, E5, D_SHARP5, E5, C_SHARP5,
        // Bar 3
        F_SHARP4, null, G_SHARP4, null, D_SHARP4, D_SHARP4, null, B3, D4, C_SHARP4, B3, null, B3, null, C_SHARP4, null,
        // Bar 4
        D4, null, D4, C_SHARP4, B3, C_SHARP4, D_SHARP4, F_SHARP4, G_SHARP4, D_SHARP4, F_SHARP4, C_SHARP4, D_SHARP4, B3, C_SHARP4, B3,
        // Bar 5
        D_SHARP4, null, F_SHARP4, null, G_SHARP4, D_SHARP4, F_SHARP4, C_SHARP4, D_SHARP4, B3, D4, D_SHARP4, D4, C_SHARP4, B3, C_SHARP4,
        // Bar 6
        D4, null, B3, C_SHARP4, D_SHARP4, F_SHARP4, C_SHARP4, D_SHARP4, C_SHARP4, B3, C_SHARP4, null, B3, null, C_SHARP4, null,
        // Bar 7
        B3, null, F_SHARP3, G_SHARP3, B3, null, F_SHARP3, G_SHARP3, B3, C_SHARP4, D_SHARP4, B3, E4, D_SHARP4, E4, F_SHARP4,
        // Bar 8
        B3, null, B3, null, F_SHARP3, G_SHARP3, B3, F_SHARP3, E4, D_SHARP4, C_SHARP4, B3, F_SHARP3, null /* D_SHARP3 */, null /* E3 8*/, F_SHARP3,
        // Bar 9
        B3, null, F_SHARP3, G_SHARP3, B3, null, F_SHARP3, G_SHARP3, B3, B3, C_SHARP4, D_SHARP4, B3, F_SHARP3, G_SHARP3, F_SHARP3,
        // Bar 10
        B3, null, B3, A_SHARP3, B3, F_SHARP3, G_SHARP3, B3, E4, D_SHARP4, E4, F_SHARP4, B3, null, A_SHARP3, null,
        // Bar 11
        B3, null, B3, A_SHARP3, B3, F_SHARP3, G_SHARP3, B3, E4, D_SHARP4, E4, F_SHARP4, B4, C_SHARP4,};
    public static final Track NYAN_CAT_MELODY_TRACK = new Track(Sound.NOTE_PIANO) {
        @Override
        public Note getNote(int offset) {
            // LOOP to 3rd Bar (16 notes per bar)
            if (offset >= NYAN_CAT_MELODY.length) {
                offset -= 32;
                offset %= NYAN_CAT_MELODY.length - 32;
                offset += 32;
            }
            return NYAN_CAT_MELODY[offset];
        }

        @Override
        public float getVolume(int offset) {
            return 1f;


        }
    };

    private final Effect effect;

    public NyanEffectHandler(Effect effect) {
        super(new Track[]{NYAN_CAT_MELODY_TRACK});
        this.effect = effect;
    }

    @Override
    protected void showEffectInternal(Server server, Player player, Location from, Location to) {
        super.showEffectInternal(server, player, from, to);
        for (int i = 0; i < 20; i++) {
            int argb = java.awt.Color.HSBtoRGB(i / 20.0f, 1f, 1f);
            float r = ((argb >> 16) & 0xff) / 255f;
            float g = ((argb >> 8) & 0xff) / 255f;
            float b = (argb & 0xff) / 255f;
            r = r == 0f ? 0.001f : r;
            sendEffect(server, player, effect, to, r, g, b, 1f, 256f, 0);
            to.setY(to.getY() + 2 / 20d);
        }
    }
    
}
