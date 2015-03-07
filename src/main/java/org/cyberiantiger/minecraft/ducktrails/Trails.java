/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
            return new NyanEffectHandler(Effect.FIREWORKS_SPARK);
        }
    },
    PFUDOR("ducktrails.trail.pfudor", "Leave a pink trail behind you like a Pink Fluffy Unicorn!") {
        public EffectHandler createHandler(Player player) {
            return new PFUDOREffectHandler(Effect.COLOURED_DUST);
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
