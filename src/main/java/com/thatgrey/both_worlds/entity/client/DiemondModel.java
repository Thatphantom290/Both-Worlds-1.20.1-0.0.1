package com.thatgrey.both_worlds.entity.client;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.custom.DiemondEntity;
import com.thatgrey.both_worlds.entity.custom.HyraxEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class DiemondModel extends GeoModel<DiemondEntity> {

    @Override
    public ResourceLocation getModelResource(DiemondEntity entity) {
        return new ResourceLocation(Both_Worlds.MODID, "geo/diemond.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DiemondEntity diemondEntity) {
        return null;
    }

    @Override
    public ResourceLocation getAnimationResource(DiemondEntity entity) {
        return new ResourceLocation(Both_Worlds.MODID, "animations/diemond.animation.json");
    }
}
