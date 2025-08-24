package com.thatgrey.both_worlds.events;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.ModEntities;
import com.thatgrey.both_worlds.entity.client.HyraxRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = Both_Worlds.MODID,
        bus   = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            EntityRenderers.register(
                    ModEntities.HYRAX.get(),
                    HyraxRenderer::new
            );
        });
    }
}