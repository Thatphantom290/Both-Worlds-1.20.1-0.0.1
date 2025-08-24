package com.thatgrey.both_worlds.events;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.ModEntities;
import com.thatgrey.both_worlds.entity.custom.HyraxEntity;
import com.thatgrey.both_worlds.entity.custom.MarmotEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Both_Worlds.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MARMOT.get(), MarmotEntity.createAttributes().build());
        event.put(ModEntities.HYRAX.get(), HyraxEntity.createAttributes().build());
    }
}
