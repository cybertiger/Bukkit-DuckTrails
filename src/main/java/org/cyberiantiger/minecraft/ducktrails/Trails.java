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

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import static org.cyberiantiger.minecraft.ducktrails.EffectHandler.*;
import static org.cyberiantiger.minecraft.ducktrails.MusicalEffectHandler.*;

/**
 *
 * @author antony
 */
public enum Trails {

    FLAME("ducktrails.trail.flame", "Leave a trail of flames") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.FLAME, 0f, 0f, 0f, 0.02f, 1);
        }
    },
    HEARTS("ducktrails.trail.hearts", "Leave a trail of hearts") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.HEART, 0f, 1f, 0f, 0.2f, 0);
        }
    },
    RUBY("ducktrails.trail.ruby", "Leave a trail of ruby colored dust") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 1f, 0f, 0f, 1f, 0);
        }
    },
    EMERALD("ducktrails.trail.emerald", "Leave a trail of emerald colored dust") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 0.0001f, 1f, 0f, 1f, 0);
        }
    },
    SAPHIRE("ducktrails.trail.saphire", "Leave a trail of saphire colored dust") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 0.0001f, 0f, 1f, 1f, 0);
        }
    },
    TOPAZ("ducktrails.trail.topaz", "Leave a trail of topaz colored dust") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 1f, 1f, 0f, 1f, 0);
        }
    },
    AMETHYST("ducktrails.trail.amethyst", "Leave a trail of amethyst colored dust") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 1f, 0f, 1f, 1f, 0);
        }
    },
    DIAMOND("ducktrails.trail.diamond", "Leave a trail of diamond colored dust") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 0.0001f, 1f, 1f, 1f, 0);
        }
    },
    OPAL("ducktrails.trail.opal", "Leave a trail of rainbow colored dust") {
        public EffectHandler createHandler(Player player) {
            return new RainbowEffectHandler(Effect.COLOURED_DUST);
        }
    },
    NYAN("ducktrails.trail.nyan", "Leave a rainbow behind you like a Nyan Cat!") {
        public EffectHandler createHandler(Player player) {
            return new NyanEffectHandler(Effect.COLOURED_DUST);
        }
    },
    PFUDOR("ducktrails.trail.pfudor", "Leave a pink trail behind you like a Pink Fluffy Unicorn!") {
        public EffectHandler createHandler(Player player) {
            return new PFUDOREffectHandler(Effect.COLOURED_DUST);
        }
    },
    DUCKTALE("ducktrails.trail.ducktale", "Leave a quackingly good duck tales trail!") {
        public EffectHandler createHandler(Player player) {
            return new DuckTalesEffectHandler(Effect.FIREWORKS_SPARK);
        }
    },
    DRAGONBORN("ducktrails.trail.dragonborn", "Leave a dragonborn themed trail!") {
        public EffectHandler createHandler(Player player) {
            return new DragonbornEffectHandler(Effect.COLOURED_DUST);
        }
    };

    private final String permission;
    private final String description;

    private Trails(String permission, String description) {
        this.permission = permission;
        this.description = description;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public abstract EffectHandler createHandler(Player player);
}
