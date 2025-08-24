package com.thatgrey.both_worlds.entity.custom;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.util.RandomSource;

import java.util.EnumSet;

public class RandomSitGoal<T extends Mob & Sittable> extends Goal {

    private final T entity;
    private int sitTime;

    public RandomSitGoal(T entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        RandomSource random = entity.getRandom(); // use Minecraft 1.20.1 RandomSource
        return !entity.isSitting() && random.nextInt(436) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return entity.isSitting() && sitTime > 0;
    }

    @Override
    public void start() {
        entity.getNavigation().stop();
        entity.setSitting(true);
        RandomSource random = entity.getRandom();
        sitTime = 100 + random.nextInt(359);
    }

    @Override
    public void stop() {
        entity.setSitting(false);
    }

    @Override
    public void tick() {
        if (sitTime > 0) sitTime--;
    }
}
