package com.thatgrey.both_worlds.entity.custom;

import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RandomSitGoal extends Goal {
    private final MarmotEntity marmot;
    private int sitTime;

    public RandomSitGoal(MarmotEntity marmot) {
        this.marmot = marmot;
        this.setFlags(EnumSet.of(Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return !marmot.isSitting() && marmot.getRandom().nextInt(436) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return marmot.isSitting() && sitTime > 0;
    }

    @Override
    public void start() {
        marmot.getNavigation().stop();
        marmot.setSitting(true);
        sitTime = 100 + marmot.getRandom().nextInt(359);
    }

    @Override
    public void stop() {
        marmot.setSitting(false);
    }

    @Override
    public void tick() {
        if (sitTime > 0) {
            sitTime--;
        } else {
            marmot.setSitting(false);
        }
    }
}
