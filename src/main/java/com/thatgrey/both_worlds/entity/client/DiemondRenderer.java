package com.thatgrey.both_worlds.entity.client;

import com.thatgrey.both_worlds.entity.custom.DiemondEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DiemondRenderer extends GeoEntityRenderer<DiemondEntity> {

    public DiemondRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DiemondModel());
        this.shadowRadius = 1f;
    }

    @Override
    public ResourceLocation getTextureLocation(DiemondEntity entity) {
        return this.model.getTextureResource(entity);
    }
}
