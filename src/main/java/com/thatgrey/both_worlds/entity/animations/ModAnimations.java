package com.thatgrey.both_worlds.entity.animations;

import software.bernie.geckolib.core.animation.RawAnimation;

public class ModAnimations {
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("IDLE");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("WALK");
    public static final RawAnimation SIT  = RawAnimation.begin().thenLoop("SIT");
}

