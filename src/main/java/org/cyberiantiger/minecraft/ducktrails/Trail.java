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
import static org.cyberiantiger.minecraft.ducktrails.MusicalEffectHandler.*;

/**
 *
 * @author antony
 */
public enum Trail {

    FLAME("ducktrails.trail.flame") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.FLAME, 0f, 0f, 0f, 0.02f, 1);
        }
    },
    HEARTS("ducktrails.trail.hearts") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.HEART, 0f, 1f, 0f, 0.2f, 0);
        }
    },
    RUBY("ducktrails.trail.ruby") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 1f, 0f, 0f, 1f, 0);
        }
    },
    EMERALD("ducktrails.trail.emerald") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 0.0001f, 1f, 0f, 1f, 0);
        }
    },
    SAPPHIRE("ducktrails.trail.sapphire") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 0.0001f, 0f, 1f, 1f, 0);
        }
    },
    TOPAZ("ducktrails.trail.topaz") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 1f, 1f, 0f, 1f, 0);
        }
    },
    AMETHYST("ducktrails.trail.amethyst") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 1f, 0f, 1f, 1f, 0);
        }
    },
    DIAMOND("ducktrails.trail.diamond") {
        public EffectHandler createHandler(Player player) {
            return new BasicEffectHandler(Effect.COLOURED_DUST, 0.0001f, 1f, 1f, 1f, 0);
        }
    },
    OPAL("ducktrails.trail.opal") {
        public EffectHandler createHandler(Player player) {
            return new RainbowEffectHandler(Effect.COLOURED_DUST);
        }
    },
    NYAN("ducktrails.trail.nyan") {
        public EffectHandler createHandler(Player player) {
            return new NyanEffectHandler(Effect.COLOURED_DUST);
        }
    },
    PFUDOR("ducktrails.trail.pfudor") {
        public EffectHandler createHandler(Player player) {
            return new PFUDOREffectHandler(Effect.COLOURED_DUST);
        }
    },
    DUCKTALES("ducktrails.trail.ducktales") {
        public EffectHandler createHandler(Player player) {
            return new DuckTalesEffectHandler(Effect.FIREWORKS_SPARK);
        }
    },
    DRAGONBORN("ducktrails.trail.dragonborn") {
        public EffectHandler createHandler(Player player) {
            return new DragonbornEffectHandler(Effect.COLOURED_DUST);
        }
    };

    private final String permission;

    private Trail(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public abstract EffectHandler createHandler(Player player);
}
