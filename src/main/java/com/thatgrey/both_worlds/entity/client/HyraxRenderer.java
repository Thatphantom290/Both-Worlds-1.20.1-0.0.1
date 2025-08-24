package com.thatgrey.both_worlds.entity.client;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.custom.HyraxEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.model.GeoModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class HyraxRenderer extends GeoEntityRenderer<HyraxEntity> {

    public HyraxRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HyraxModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public ResourceLocation getTextureLocation(HyraxEntity entity) {
        return this.model.getTextureResource(entity);
    }
}


