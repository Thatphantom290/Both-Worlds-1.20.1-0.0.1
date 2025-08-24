package com.thatgrey.both_worlds.entity.animations;

import software.bernie.geckolib.core.animation.RawAnimation;

public class ModAnimations {
    public static final RawAnimation MARMOT_IDLE = RawAnimation.begin().thenLoop("marmot_idle");
    public static final RawAnimation MARMOT_WALK = RawAnimation.begin().thenLoop("marmot_walk");
    public static final RawAnimation MARMOT_SIT = RawAnimation.begin().thenLoop("marmot_sit");

    public static final RawAnimation HYRAX_WALK = RawAnimation.begin().thenLoop("hyrax_walk");
    public static final RawAnimation HYRAX_IDLE = RawAnimation.begin().thenLoop("hyrax_idle");
    public static final RawAnimation HYRAX_SIT = RawAnimation.begin().thenLoop("hyrax_sit");
}

