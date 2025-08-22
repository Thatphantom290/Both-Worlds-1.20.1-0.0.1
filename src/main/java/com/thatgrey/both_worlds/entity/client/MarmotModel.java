package com.thatgrey.both_worlds.entity.client;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.custom.MarmotEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class MarmotModel extends GeoModel<MarmotEntity> {

    @Override
    public ResourceLocation getModelResource(MarmotEntity object) {
        return new ResourceLocation(Both_Worlds.MODID, "geo/marmot.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MarmotEntity entity) {
        return switch (entity.getVariant()) {
            case 1 -> new ResourceLocation(Both_Worlds.MODID, "textures/entity/marmot/marmot_grey.png");
            case 2 -> new ResourceLocation(Both_Worlds.MODID, "textures/entity/marmot/marmot_white.png");
            case 3 -> new ResourceLocation(Both_Worlds.MODID, "textures/entity/marmot/marmot_black.png");
            case 4 -> new ResourceLocation(Both_Worlds.MODID, "textures/entity/marmot/marmot_beige.png");
            default -> new ResourceLocation(Both_Worlds.MODID, "textures/entity/marmot/marmot.png");
        };
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
                body.setScaleX(0.6F);
                body.setScaleY(0.6F);
                body.setScaleZ(0.6F);
                body.setPosY(-5.0F);
                body.setPosZ(-2.0F);
            }
            if (head != null) {
                head.setScaleX(1.0F);
                head.setScaleY(1.0F);
                head.setScaleZ(1.0F);
                head.setPosY(-5.0F);
                head.setPosZ(-2.0F);
            }
        } else {
            if (body != null) {
                body.setScaleX(1.0F);
                body.setScaleY(1.0F);
                body.setScaleZ(1.0F);
                body.setPosY(0.0F);
                body.setPosZ(0.0F);
            }
            if (head != null) {
                head.setScaleX(1.0F);
                head.setScaleY(1.0F);
                head.setScaleZ(1.0F);
                head.setPosY(0.0F);
                head.setPosZ(0.0F);
            }
        }

        if (head == null) return;

        Player player = animatable.level().getNearestPlayer(animatable, 8);
        if (player != null) {
            double dx = player.getX() - animatable.getX();
            double dz = player.getZ() - animatable.getZ();
            double dy;

            if (animatable.isSitting()) {
                dy = player.getEyeY() - (animatable.getY() + 0.2);
            } else {
                dy = player.getEyeY() - (animatable.getY() + animatable.getEyeHeight());
            }

            float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - animatable.getYRot();
            float pitch = (float) -Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));

            yaw = Mth.clamp(yaw, 45, 40);
            pitch = Mth.clamp(pitch, 45, 40);

            head.setRotY((float) Math.toRadians(yaw));
            head.setRotX((float) Math.toRadians(pitch));
        } else {
            head.setRotY(0);
            head.setRotX(0);
        }
    }
}
