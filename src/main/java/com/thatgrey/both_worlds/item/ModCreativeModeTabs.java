package com.thatgrey.both_worlds.item;

import com.thatgrey.both_worlds.Both_Worlds;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Both_Worlds.MODID);

    public static final RegistryObject<CreativeModeTab> BOTH_WORLDS_TAB = CREATIVE_MODE_TABS.register("both_worlds_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.MARMOT_SPAWN_EGG.get()))
                    .title(Component.translatable("creativetab.both_worlds_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.MARMOT_SPAWN_EGG.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
