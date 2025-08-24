package com.thatgrey.both_worlds.entity.client;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.custom.HyraxEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class HyraxModel extends GeoModel<HyraxEntity> {

    @Override
    public ResourceLocation getModelResource(HyraxEntity entity) {
        return new ResourceLocation(Both_Worlds.MODID, "geo/hyrax.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HyraxEntity entity) {
        HyraxEntity.Variant v = HyraxEntity.Variant.byId(entity.getVariant());
        switch (v) {
            case BEIGE:
                return new ResourceLocation(Both_Worlds.MODID, "textures/entity/hyrax/hyrax_beige.png");
            case GREY:
                return new ResourceLocation(Both_Worlds.MODID, "textures/entity/hyrax/hyrax_grey.png");
            case BROWN:
                return new ResourceLocation(Both_Worlds.MODID, "textures/entity/hyrax/hyrax_brown.png");
            default:
                return new ResourceLocation(Both_Worlds.MODID, "textures/entity/hyrax/hyrax_brown.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(HyraxEntity entity) {
        return new ResourceLocation(Both_Worlds.MODID, "animations/hyrax.animation.json");
    }

    @Override
    public void setCustomAnimations(HyraxEntity animatable, long instanceId, AnimationState<HyraxEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone body = this.getAnimationProcessor().getBone("everything");

        if (animatable.isBaby()) {
            if (body != null) {
                body.setScaleX(0.7F);
                body.setScaleY(0.7F);
                body.setScaleZ(0.7F);
                body.setPosY(0.2F);
                body.setPosX(0.0F);
                body.setPosZ(0.0F);
            }
        } else {
            if (body != null) {
                body.setScaleX(1.0F);
                body.setScaleY(1.0F);
                body.setScaleZ(1.0F);
                body.setPosY(0F);
                body.setPosZ(0F);
                body.setPosX(0.0F);
            }
        }
    }
}
