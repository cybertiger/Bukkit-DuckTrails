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

import java.util.concurrent.atomic.AtomicLong;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;


/**
 *
 * @author antony
 */
public abstract class MusicalEffectHandler extends EffectHandler {

    private static final long RESET_AFTER = 400000000L; // 0.4s
    private final Track[] track;
    private int trackOffset = 0;
    private AtomicLong lastMove = new AtomicLong(0L);

    public MusicalEffectHandler(Track[] track) {
        this.track = track;
    }

    protected void showEffectInternal(Server server, Player player, Location from, Location to) {
        lastMove.set(System.nanoTime());
    }

    public boolean isPlaying(long nanoTime) {
        boolean result = nanoTime < lastMove.get() + RESET_AFTER;
        if (!result) {
            trackOffset = 0;
        }
        return result;
    }

    public void playNext(Server server, Player player) {
        Location location = getLastLocation();
        if (location != null) {
            for (Track t : track) {
                t.playNote(location, trackOffset);
            }
        }
        trackOffset++;
    }
}
