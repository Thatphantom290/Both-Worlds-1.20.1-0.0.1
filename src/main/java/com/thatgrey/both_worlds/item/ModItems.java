package com.thatgrey.both_worlds.item;

import com.thatgrey.both_worlds.Both_Worlds;
import com.thatgrey.both_worlds.entity.ModEntities;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Both_Worlds.MODID);

    public static final RegistryObject<Item> MARMOT_SPAWN_EGG =
            ITEMS.register("marmot_spawn_egg",
                    () -> new ForgeSpawnEggItem(ModEntities.MARMOT, 0x62422d, 0x9f7962,
                            new Item.Properties()));

    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
