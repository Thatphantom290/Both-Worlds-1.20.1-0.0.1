package com.thatgrey.both_worlds.entity.client;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.custom.MarmotEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

import java.util.ArrayList;
import java.util.List;

public class MarmotModel extends GeoModel<MarmotEntity> {

    @Override
    public ResourceLocation getModelResource(MarmotEntity object) {
        return new ResourceLocation(Both_Worlds.MODID, "geo/marmot.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MarmotEntity entity) {
        MarmotEntity.Variant v = MarmotEntity.Variant.byId(entity.getVariant());

        List<String> tries = new ArrayList<>();
        switch (v) {
            case BROWN:
                tries.add("textures/entity/marmot/marmot.png");
                tries.add("textures/entity/marmot/marmot_brown.png");
                tries.add("textures/entity/marmot/marmot-brown.png");
                break;
            case BEIGE:
                tries.add("textures/entity/marmot/marmot_beige.png");
                break;
            case GREY:
                tries.add("textures/entity/marmot/marmot_grey.png");
                break;
            case WHITE:
                tries.add("textures/entity/marmot/marmot_white.png");
                break;
            default:
                tries.add("textures/entity/marmot/marmot.png");
        }

        System.out.println("[Both_Worlds] getTextureResource called variant=" + v + " (id=" + entity.getVariant() + ")");

        for (String path : tries) {
            ResourceLocation rl = new ResourceLocation(Both_Worlds.MODID, path);
            System.out.println("[Both_Worlds] tryTexture -> " + rl);
            return rl;
        }

        return new ResourceLocation(Both_Worlds.MODID, "textures/entity/marmot/marmot.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MarmotEntity entity) {
        return new ResourceLocation(Both_Worlds.MODID, "animations/marmot.animation.json");
    }

    @Override
    public void setCustomAnimations(MarmotEntity animatable, long instanceId, AnimationState<MarmotEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        CoreGeoBone body = this.getAnimationProcessor().getBone("Marmot");
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        if (animatable.isBaby()) {
            if (body != null) {
                body.setScaleX(0.5F);
                body.setScaleY(0.5F);
                body.setScaleZ(0.5F);
                body.setPosY(-1.0F);
            }
            if (head != null) {
                head.setScaleX(1.5F);
                head.setScaleY(1.5F);
                head.setScaleZ(1.5F);
                head.setPosY(-1.0F);
            }
        } else {
            if (body != null) {
                body.setScaleX(1.0F);
                body.setScaleY(1.0F);
                body.setScaleZ(1.0F);
            }
            if (head != null) {
                head.setScaleX(1.0F);
                head.setScaleY(1.0F);
                head.setScaleZ(1.0F);
            }
        }
    }
}
