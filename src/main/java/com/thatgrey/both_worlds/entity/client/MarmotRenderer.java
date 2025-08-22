package com.thatgrey.both_worlds.entity.client;

import com.thatgrey.both_worlds.entity.ModEntities;
import com.thatgrey.both_worlds.entity.custom.MarmotEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MarmotRenderer extends GeoEntityRenderer<MarmotEntity> {

    public MarmotRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MarmotModel());
        this.shadowRadius = 0.5f;
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.MARMOT.get(), MarmotRenderer::new);
    }
}
